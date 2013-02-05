package org.kaoqin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * http请求封装类 主要用来创建HttpClient对象、封装GET/POST请求,提取页面参数等
 * 
 * @version 1.0, 13/01/17
 * @author tang
 * 
 */
public class HttpHelper {
	/**
	 * HttpClient池，以线程为唯一标识（每个用户考勤单独分配一个线程，每个线程分配一个HttpClient）
	 */
	private static Map _threadHttpClient = new HashMap();
        /**
         * 获取当前线程的HttpClient对象
         * @return 
         */
	public static HttpClient getHttpClient() {
		DefaultHttpClient httpclient = (DefaultHttpClient) _threadHttpClient
				.get(Thread.currentThread().getId());
		if (httpclient != null) {
			return httpclient;
		}
		throw new RuntimeException("当前线程：[id="+Thread.currentThread().getId()+"]未初始化httpclient");
	}

	/**
	 * 创建HttpClient（可自定义拦截器）
	 */
	public static HttpClient createHttpClient(
			HttpRequestInterceptor requestInterceptor) {
		DefaultHttpClient  httpclient = new DefaultHttpClient();
		_threadHttpClient.put(Thread.currentThread().getId(), httpclient);
		// requerst处理
		if (requestInterceptor != null) {
			httpclient.addRequestInterceptor(requestInterceptor);
		} else {
			// 默认request拦截器
			httpclient.addRequestInterceptor(new HttpRequestInterceptor() {
				public void process(final HttpRequest request,
						final HttpContext context) throws HttpException,
						IOException {
					// HTTP报头
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
					request
							.addHeader("User-Agent",
									"Mozilla/5.0 (Windows NT 5.1; rv:8.0) Gecko/20120217 Firefox/8.0");
					request.addHeader("Connection", "keep-alive");
					request.addHeader("Referer","http://" + Configer.getProporty("kaoqinServerHost")
							+ ":8082/");
					request.addHeader("Accept-Language", "zh-CN");
				}

			});
		}
		// 其他拦截器
		// httpclient.addResponseInterceptor.......
		// httpclient.setRedirectHandler......
		return httpclient;
	}

	/**
	 * 创建HttpClient
	 * 
	 * @return HttpClient
	 */
	public static HttpClient createHttpClient() {
		HttpClient httpclient = createHttpClient(null);
		return httpclient;
	}

	/**
	 * 创建HttpClient(通过代理服务器)
	 * 
	 * @param proxyServerIP
	 *            代理服务器IP
	 * @param port
	 *            代理服务器端口
	 * @param requestInterceptor
	 *            请求拦截器
	 * @return HttpClient
	 */
	public static HttpClient createHttpClient(String proxyServerIP, int port,
			HttpRequestInterceptor requestInterceptor) {
		HttpClient httpclient = createHttpClient(requestInterceptor);
		setProxy(httpclient, proxyServerIP, port);
		return httpclient;
	}

	/**
	 * 创建HttpClient(通过代理服务器)
	 * 
	 * @param proxyServerIP
	 *            代理服务器IP
	 * @param port
	 *            代理服务器端口
	 * @return HttpClient
	 */
	public static HttpClient createHttpClient(String proxyServerIP, int port) {
		HttpClient httpclient = createHttpClient(null);
		setProxy(httpclient, proxyServerIP, port);
		return httpclient;
	}

	/**
	 * 设置代理服务器
	 * 
	 * @param httpclient
	 *            HttpClient
	 * @param proxyServerIP
	 *            代理服务器IP
	 * @param port
	 *            代理服务器端口
	 */
	private static void setProxy(HttpClient httpclient, String proxyServerIP,
			int port) {
		HttpHost proxy = new HttpHost(proxyServerIP, port);
		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
				proxy);

	}

	/**
	 * 加载页面-通过GET方式
	 * 
	 * @param client
	 *            HttpClient实例
	 * @param url
	 *            请求路径
	 * @return body
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String doGetBody(HttpClient client, String url)
			throws Exception {
		KqLoger.log("==>>加载页面(GET):" + url);
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		int statusCode = response.getStatusLine().getStatusCode();
		KqLoger.log(getStatusDetailed(statusCode));
		HttpEntity entity = response.getEntity();
		BufferedReader reader = new BufferedReader(new InputStreamReader(entity
				.getContent()));
		String buffer = null;
		StringBuilder sb = new StringBuilder();
		while ((buffer = reader.readLine()) != null) {
			sb.append(buffer);
		}
		get.abort();
		return sb.toString();
	}

	/**
	 * 提交-通过POST方式
	 * 
	 * @param client
	 *            HttpClient实例
	 * @param url
	 *            请求路径
	 * @param qparams
	 *            请求参数
	 * @return body
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String doPostBody(HttpClient client, String url, List qparams)
			throws Exception {
		KqLoger.log("==>>提交表单(POST):" + url);
		HttpPost post = new HttpPost(url);
		// 中文编码
		post.setEntity(new UrlEncodedFormEntity(qparams,
				Configer.Const.DEFAULT_CONTENT_CHARSET));
		HttpResponse response = client.execute(post);
		String body = EntityUtils.toString(response.getEntity());
		int statusCode = response.getStatusLine().getStatusCode();
		KqLoger.log(getStatusDetailed(statusCode));
		post.abort();
		return body;
	}

	/**
	 * 获取请求状态码详细信息
	 * 
	 * @param statusCode
	 *            状态码
	 * @return 状态码具体中文描述
	 * @throws Exception
	 */
	public static String getStatusDetailed(int statusCode) throws Exception {
		StringBuffer detailMsg = new StringBuffer("");
		int staus = statusCode / 100;
		switch (staus) {
		case 1:
			detailMsg.append("状态码").append(statusCode).append("--消息");
			break;
		case 2:
			detailMsg.append("状态码").append(statusCode).append("--成功");
			break;
		case 3:
			detailMsg.append("状态码").append(statusCode).append("--重定向");
			break;
		case 4:
			detailMsg.append("状态码").append(statusCode).append("--请求出错！");
			throw new Exception(detailMsg.toString());
		case 5:
			detailMsg.append("状态码").append(statusCode).append("--服务器出错！");
			throw new Exception(detailMsg.toString());
		default:
			detailMsg.append("状态码").append(statusCode).append("--未知状态码");
			throw new Exception(detailMsg.toString());

		}
		return detailMsg.toString();
	}

	/**
	 * 匹配input标签并提取相应参数
	 * 
	 * @param body
	 *            服务器响应正文
	 * @return input标签的name和value
	 */
	public static Map getAllInputNames(String body) {
		Map parameters = new HashMap();
		Matcher matcher = Pattern.compile("<input[^<]*>").matcher(body);
		while (matcher.find()) {
			String input = matcher.group();
			if (input.contains("name")) {
				parameters.put(getInputProperty(input, "name"),
						getInputProperty(input, "value"));
			}
		}
		parameters.putAll(getOtherParams(body));
		return parameters;
	}

	/**
	 * 获取其他参数(考勤页面)
	 * 
	 * @param body
	 * @return
	 */
	private static Map getOtherParams(String body) {
		Map otherParams = new HashMap();
		Matcher matcherSelect = Pattern.compile("<select[^<]*>(.*?)</select>")
				.matcher(body);
		while (matcherSelect.find()) {
			String select = matcherSelect.group();
			if (select.indexOf("drp_zxbm") > 0) {
				Matcher matcherOption = Pattern.compile("<option[^<]*>")
						.matcher(body);
				if (matcherOption.find()) {
					String option = matcherOption.group();
					String drp_zxbm = option.substring(
							option.indexOf("\"") + 1, option.length() - 2);
					otherParams.put("drp_zxbm", drp_zxbm);
					break;
				}
			}
		}
		return otherParams;
	}

	/**
	 * 提取参数
	 * 
	 * @param input
	 * @param property
	 * @return
	 */
	private static String getInputProperty(String input, String property) {
		Matcher m = Pattern.compile(property + "[\\s]*=[\\s]*\"[^\"]*\"")
				.matcher(input);
		if (m.find()) {
			String v = m.group();
			return v.substring(v.indexOf("\"") + 1, v.length() - 1);
		}
		return null;
	}

}