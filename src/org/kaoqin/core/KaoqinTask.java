package org.kaoqin.core;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.kaoqin.util.CodeParser;
import org.kaoqin.util.Configer;
import org.kaoqin.util.HttpHelper;
import org.kaoqin.util.KqLoger;
import org.kaoqin.util.NetworkOption;
import org.kaoqin.vo.User;

/**
 * 单个考勤任务
 * 
 * @author lenovo
 * 
 */
public class KaoqinTask implements Runnable {
	private User user;// 用户对象
        public static boolean taskFinish=true;//考勤任务执行完成标志
        private static int delaySc=6;//修改ip后休眠时间 秒
	public KaoqinTask(User user) {
		this.user = user;
	}

	/**
	 * 执行单个考勤
	 */
	public void run() {
		try {
			setUp();
			process();
		} catch (Exception e) {
			log("考勤失败！程序运行异常：" + e.getMessage());
			e.printStackTrace();
		} finally {
                    try {
                        tearDown();
                    } catch (Exception ex) {
                        Logger.getLogger(KaoqinTask.class.getName()).log(Level.INFO, "考勤异常", ex);
                    }
		}
	}

	/**
	 * 初始化
	 * 
	 * @throws Exception
	 */
	public void setUp() throws Exception {
                //判断上一个考勤任务是否执行完成
                for(int i=0;i<1000;i++){
                    if(KaoqinTask.taskFinish){
                        break;
                    }else{
                        Thread.sleep(2000);
                    }
                }
                KaoqinTask.taskFinish=false;
                //修改IP
                if(Boolean.valueOf(Configer.getProporty("modifyIP"))){
                    log("更改本机网络配置，修改IP地址为："+user.getIp()+",请稍等"+delaySc+"秒....");
                    NetworkOption.modifyIPAddress(user.getIp());
                    Thread.sleep(delaySc*1000);
                }
                
                
		final String yzmCookie = "yinhai.yzm=" + user.getYzm()+";yinhai.ygyzm="+user.getYgyzm();//cookie中的验证码
		final String ip = user.getIp()+","+Configer.getProporty("proxy");
		HttpRequestInterceptor interceptor = new HttpRequestInterceptor() {
			public void process(HttpRequest request, HttpContext context)
					throws HttpException, IOException {
				// 设置http报文头信息
				if (!request.containsHeader("Accept")) {
					request.addHeader("Accept", "*/*");
				}
				if (request.containsHeader("User-Agent")) {
					request.removeHeaders("User-Agent");
				}
				if (request.containsHeader("Connection")) {
					request.removeHeaders("Connection");
				}
				if (request.containsHeader("Accept-Language")) {
					request.removeHeaders("Accept-Language");
				}
				if (request.containsHeader("X-Forwarded-For")) {
					request.removeHeaders("X-Forwarded-For");
				}
				request
						.addHeader("User-Agent",
								"Mozilla/5.0 (Windows NT 5.1; rv:8.0) Gecko/20120217 Firefox/8.0");
				request.addHeader("Connection", "keep-alive");
				request.addHeader("Referer", "http://"
						+ Configer.getProporty("kaoqinServerHost") + ":8082/");
				request.addHeader("Accept-Language", "zh-CN");

				// 验证码
				String cookie = "";
				Header headers[] = request.getHeaders("Cookie");
				if (headers.length > 0) {
					cookie = headers[0].getValue();
					if (!cookie.contains("yinhai.yzm")) {
						cookie += ";" + yzmCookie;
					}
				} else {
					cookie = yzmCookie;
				}
				request.removeHeaders("Cookie");
				request.addHeader("Cookie", cookie);
				// 透明代理ip欺骗
				request.addHeader("X-Forwarded-For", ip);
			}

		};
                //代理
		if ("true".equals(Configer.getProporty("proxyEnable"))) {
			String proxy = Configer.getProporty("proxy");
			int port = Integer.parseInt(Configer.getProporty("port"));
			HttpHelper.createHttpClient(proxy, port, interceptor);
		} else {
			HttpHelper.createHttpClient(interceptor);
		}
	}

	/**
	 * 执行
	 * 
	 * @throws Exception
	 */
	public void process() throws Exception {
		loginToSos();//项目管理系统登录
		String kqLoginUrl=toSosToGetKqLoginUrl();//进入信息管理平台，获取考勤系统单点登录url
		loginToKqSys(kqLoginUrl);//单点登录考勤系统
		for (int i = 0; i < 20; i++) {
			log("循环第" + (i + 1) + "次加载考勤页面,获取并识别验证码,提交考勤信息,直到成功为止，最多20次:");
			if (kq()) {
				break;
			}
		}
		
	}

	/**
	 * 登录
	 * @return
	 * @throws Exception
	 */
	private void loginToSos() throws Exception {
		String body = HttpHelper
				.doGetBody(HttpHelper.getHttpClient(), "http://"
						+ Configer.getProporty("kaoqinServerHost") + ":8082/");
		// 提取页面Form参数,包括ASP.NET隐藏域
		Map params = HttpHelper.getAllInputNames(body);
		log("==>>登录项目管理系统");
		// 提交参数设置
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Configer.Const.EVENTVALIDATION,
				String.valueOf(params.get(Configer.Const.EVENTVALIDATION))));
		qparams.add(new BasicNameValuePair(Configer.Const.VIEWSTATE, String
				.valueOf(params.get(Configer.Const.VIEWSTATE))));
		qparams.add(new BasicNameValuePair("myButton.x", "49"));
		qparams.add(new BasicNameValuePair("myButton.y", "13"));
		qparams.add(new BasicNameValuePair("usr", user.getUsername()));
		qparams.add(new BasicNameValuePair("pwd", user.getPassword()));
		// POST登录
		body = HttpHelper.doPostBody(HttpHelper.getHttpClient(),
				"http://" + Configer.getProporty("kaoqinServerHost")
						+ ":8082/login.aspx", qparams);
		// post.abort();
		// 服务器响应信息处理
		if (body.contains("Object moved")) {
			log("==>>登录项目管理系统成功！");
		} else if (body.contains("没有此用户")) {
			throw new RuntimeException("登录失败：没有此用户!");
		} else if (body.contains("密码错误")) {
			throw new RuntimeException("登录失败：密码错误!");
		} else {
			throw new RuntimeException("登录失败：其他原因...");
		}
	}

	/**
	 * 重定向到信息系统管理平台获取考勤系统单点登录Url
	 * @return
	 * @throws Exception
	 */
	private String toSosToGetKqLoginUrl() throws Exception {
		log("==>>进入银海信息系统管理平台");
		String body = HttpHelper.doGetBody(HttpHelper.getHttpClient(),
				"http://" + Configer.getProporty("kaoqinServerHost")
						+ ":8082/yh_sos.aspx");
		if (body.contains("验证码")) {
			throw new RuntimeException("验证码错误，服务器要求输入rtx发送的验证码,请重置cookie验证码");
		}
		// 提取页面Form参数,包括ASP.NET隐藏域
		Map params = HttpHelper.getAllInputNames(body);
		// 提交参数设置
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Configer.Const.EVENTVALIDATION,
				String.valueOf(params.get(Configer.Const.EVENTVALIDATION))));
		qparams.add(new BasicNameValuePair(Configer.Const.VIEWSTATE, String
				.valueOf(params.get(Configer.Const.VIEWSTATE))));
		qparams.add(new BasicNameValuePair("img_kqxt.x", "53"));
		qparams.add(new BasicNameValuePair("img_kqxt.y", "21"));
		// POST获取考勤系统单点登录链接
		log("==>>POST获取考勤系统单点登录Url");
		body = HttpHelper.doPostBody(HttpHelper.getHttpClient(), "http://"
				+ Configer.getProporty("kaoqinServerHost")
				+ ":8082/yh_sos.aspx", qparams);
		// post.abort();
		// 服务器响应信息处理
		if (body.contains("zsk_open")) {
			int beginIndex = body.indexOf("('", body.indexOf("zsk_open"))+2;
			int endIndex = body.indexOf("')", body.indexOf("zsk_open"));
			String kaoqinLoginUrl = body.substring(beginIndex, endIndex);
			return kaoqinLoginUrl;
		}
		throw new RuntimeException("获取不到考勤系统的单点登录url!");

	}
	/**
	 * 单点登录考勤系统
	 * @param loginUrl
	 * @throws Exception
	 */
	private void loginToKqSys(String loginUrl) throws Exception {
		log("==>>登录考勤系统");
		String body = HttpHelper.doGetBody(HttpHelper.getHttpClient(),loginUrl);
		if (body.contains("ygkqzy")) {
			log("==>>登录考勤系统成功!");
		}else {
			throw new RuntimeException("登录考勤系统失败!");
		}

	}
	
	/**
	 * 考勤
	 * @return
	 * @throws Exception
	 */
	private boolean kq() throws Exception {
		log("==>>加载员工考勤主页,提取参数");
		String body = HttpHelper
				.doGetBody(HttpHelper.getHttpClient(), "http://"
						+ Configer.getProporty("kaoqinServerHost") + ":8081/webfrm_ygkqzy.aspx");
		// 提取页面Form参数,包括ASP.NET隐藏域
		Map params = HttpHelper.getAllInputNames(body);
		// 提交参数设置
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Configer.Const.EVENTVALIDATION,
				String.valueOf(params.get(Configer.Const.EVENTVALIDATION))));
		qparams.add(new BasicNameValuePair(Configer.Const.VIEWSTATE, String
				.valueOf(params.get(Configer.Const.VIEWSTATE))));
		qparams.add(new BasicNameValuePair("drp_zxbm", String
				.valueOf(params.get("drp_zxbm"))));
		// 若当前时间在下班时间以前上班考勤，下班时间以后点下班考勤
		if (isXb()) {
			qparams.add(new BasicNameValuePair("img_xb.x", "78"));
			qparams.add(new BasicNameValuePair("img_xb.y", "107"));
		}else {
			qparams.add(new BasicNameValuePair("img_sb.x", "78"));
			qparams.add(new BasicNameValuePair("img_sb.y", "107"));
		}
		//序列号和mac地址
                qparams.add(new BasicNameValuePair("txt_1",StringUtils.rightPad(user.getSeriousnumber().trim(), 32, " ")));//序列号(补全至32位)
                qparams.add(new BasicNameValuePair("txt_2", user.getMacaddress().trim()));//mac地址
		log("==>>请求获取并解析验证码图片");
		String txtYzm=CodeParser.getCaptchaCode(HttpHelper.getHttpClient());
		log("==>>验证码："+txtYzm);
		qparams.add(new BasicNameValuePair("txt_yzm", txtYzm));
		// POST登录
		log("==>>提交考勤信息");
		body = HttpHelper.doPostBody(HttpHelper.getHttpClient(),
				"http://" + Configer.getProporty("kaoqinServerHost")
						+ ":8081/webfrm_ygkqzy.aspx", qparams);
		// post.abort();
		// 服务器响应信息处理
		String resultmsg="";
		if (body.contains("yh_msg")) {
			int beginIndex = body.indexOf("('", body.indexOf("yh_msg"))+2;
			int endIndex = body.indexOf("','", body.indexOf("yh_msg"));
			resultmsg = body.substring(beginIndex, endIndex);
		}
		if (StringUtils.isEmpty(resultmsg)) {
			throw new RuntimeException("考勤失败!未能获取到考勤结果提示信息");
		}
		log("==>>考勤结果提示信息["+resultmsg+"]");
		if (resultmsg.contains("验证码")) {
			return false;
		} else {
			return true;
		}
		
	}
	/**
	 * 判断当前时间是否已下班
	 * @return
	 */
	private boolean isXb() throws Exception{
		Date xbDate = new SimpleDateFormat("hh:mm:ss").parse(Configer
				.getProporty("xbTime"));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, xbDate.getHours());
		cal.set(Calendar.MINUTE, xbDate.getMinutes());
		// 若当前时间在下班时间以前点上班，下班时间以后点下班
		if (Calendar.getInstance().before(cal)) {
			return false;
		} else {
			return true;
		}
	}
	

	/**
	 * 考勤结束
	 * 
	 * @throws Exception
	 */
	public void tearDown() throws Exception {
            if(Boolean.valueOf(Configer.getProporty("modifyIP"))){
                log("还原本机网络配置,请稍等"+delaySc+"秒...");
                NetworkOption.restore();
                Thread.sleep(delaySc*1000);
            }
            log("此次考勤完成！");
            KaoqinTask.taskFinish=true;
                
	}

	/**
	 * 记录日志（附加用户名）
	 * 
	 * @param msg
	 */
	public void log(String msg) {
		String logMsg = "[" + user.getUsername() + "]  " + msg;
		KqLoger.log(logMsg);
	}

}
