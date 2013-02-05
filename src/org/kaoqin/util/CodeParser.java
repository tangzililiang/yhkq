package org.kaoqin.util;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

/**
 * 获取验证码，OCR识别验证码,截屏截图等
 * 
 * @author tang
 * 
 */
public class CodeParser {
	/**
	 * 通过Tesseract-OCR识别验证码
	 * 
	 * @param filepath
	 * @return
	 */
	public static String recognitionCodeByOCR(String imgFilePath)
			throws Exception {
		KqLoger.log("解析验证码图片");
		String code = "";// 验证码识别结果
		// cmd执行命令 "tesseract xxx.jpg xxx" 识别验证码并输出结果
		String resultTxtFileName = "temprs";
		String tesseract = "tesseract";
		ProcessBuilder pb = new ProcessBuilder();
		File imgFile = new File(imgFilePath);
		pb.directory(imgFile.getParentFile());
		List<String> cmd = new ArrayList<String>();
		cmd.add(tesseract);
		cmd.add(imgFile.getName());
		cmd.add(resultTxtFileName);
		pb.command(cmd);
		Process process = pb.start();
		int w = process.waitFor();
		// 读取识别结果
		if (w == 0) {
			BufferedReader in = null;
			String resultFilePath = imgFile.getParent() + "/"
					+ resultTxtFileName + ".txt";
			try {
				in = new BufferedReader(new InputStreamReader(
						new FileInputStream(resultFilePath), "UTF-8"));
				code = in.readLine();
			} catch (Exception e) {
				throw e;
			} finally {
				in.close();
			}
			if (new File(resultFilePath).exists()) {
				new File(resultFilePath).delete();
			}
		} else {
			KqLoger.log("识别验证码失败，ERROR_CODE:" + w);
			code = "null";
		}
		if (code == null || code.length() == 0) {
			code = "null";
		}
		return code;
	}

	/**
	 * 矩形切图（已弃用）
	 * 
	 * @param srcImgPath
	 * @return resultImgPath
	 * @throws Exception
	 */
	public static String cutImg(String srcImgPath, String resultImgPath,
			int x1, int y1, int x2, int y2) throws Exception {
		String srcType = srcImgPath.substring(srcImgPath.lastIndexOf('.') + 1);// 图片类型
		String resultType = srcImgPath
				.substring(resultImgPath.lastIndexOf('.') + 1);// 图片类型
		// 取得图片读入器
		Iterator readers = ImageIO.getImageReadersByFormatName(srcType);
		ImageReader reader = (ImageReader) readers.next();
		// 取得图片读入流
		InputStream source = new FileInputStream(srcImgPath);
		ImageInputStream iis = ImageIO.createImageInputStream(source);
		reader.setInput(iis, true);
		// 图片参数
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle rect = new Rectangle(x1, y1, x2, y2);// 如横向0~100，纵向0~50的区域
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, resultType, new File(resultImgPath));
		iis.close();
		source.close();
		new File(srcImgPath).delete();
		return resultImgPath;
	}

	/**
	 * 自定义大小矩形截屏（已弃用）
	 * 
	 * @param filepath
	 *            保存图片路径
	 * @throws Exception
	 */
	public static void captureScreen(String imgPath, int x1, int y1, int x2,
			int y2) throws Exception {
		Robot ro = new Robot();
		BufferedImage bi = ro
				.createScreenCapture(new Rectangle(x1, y1, x2, y2));
		String suffix = imgPath.substring(imgPath.lastIndexOf('.') + 1);
		ImageIO.write(bi, suffix, new File(imgPath));
	}

	/**
	 * 全屏截图
	 * 
	 * @param filepath
	 *            保存图片路径
	 * @throws Exception
	 */
	public static void captureFullScreen(String imgPath) throws Exception {
		Robot ro = new Robot();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screen = new Rectangle(size);
		BufferedImage bi = ro.createScreenCapture(screen);
		String suffix = imgPath.substring(imgPath.lastIndexOf('.') + 1);
		ImageIO.write(bi, suffix, new File(imgPath));
	}

	/**
	 * 获取图片并保存
	 * 
	 * @param client
	 * @param url
	 * @param imgPath
	 * @return 
	 * @throws Exception
	 */
	public static void requestAndSaveImg(HttpClient client, String url, String imgPath)
			throws Exception {
		FileOutputStream output = null;
		InputStream input = null;
		try {
			KqLoger.log("==>>加载图片(GET):" + url);
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			int statusCode = response.getStatusLine().getStatusCode();
			KqLoger.log("状态码:"+statusCode);
			input=response.getEntity().getContent();
			output = new FileOutputStream(new File(imgPath));
			byte[] buffer = new byte[1024];
			while (input.read(buffer, 0, buffer.length) != -1) {
				output.write(buffer, 0, buffer.length);
			}

		} catch (Exception e) {
			throw new RuntimeException("请求获取验证码图片失败，请确认网络代理设置是否正确！", e);
		} finally {
			try {
				if (output != null) {
					output.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (Exception e) {
				throw e;
			}
		}
		// 判断验证码图片是否获取成功
		if (!new File(imgPath).exists()) {
			throw new FileNotFoundException("请求获取验证码图片失败！未找到保存的图片文件.");
		}
	}

	/**
	 * 获取验证码
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getCaptchaCode(HttpClient httpClient) throws Exception {
		String yzmImgPath = new File("").getAbsolutePath() + "\\temp"
				+ new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date())+Thread.currentThread().getId()
				+ ".png";// 验证码图片保存路径
		// 获取验证码图片url(id号随机生成)
		String url = "http://" + Configer.getProporty("kaoqinServerHost")
				+ ":8081/webfrm_yzm.aspx?id" + Math.random();	
		requestAndSaveImg(httpClient, url, yzmImgPath);//请求并保存验证码图片
		String code = recognitionCodeByOCR(yzmImgPath);// 识别验证码
		new File(yzmImgPath).delete();// 删除验证码图片
		return code;
	}
}
