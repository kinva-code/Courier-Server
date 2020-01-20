package Servlet;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.HashMap;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mysql.cj.xdevapi.JsonString;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.sun.xml.internal.bind.v2.runtime.output.Encoded;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import org.json.JSONObject;
import org.json.JSONString;
import org.json.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class Zxing {
	public static void main(String[] args) {
		Zxing zxing=new Zxing();
		// 传参：二维码内容和生成路径
        if (zxing.orCode("https://www.cnblogs.com/lsy131479/", "WebContent/QRcode/4.png")) {
            System.out.println("ok,成功");
        } else {
            System.out.println("no,失败");
        }
	}
	
	private boolean orCode(String content, String path) {
		//图片宽度高度
		int width=300;
		int height=300;
		//图片格式
		String format="png";
		//二维码内容
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("name", "李贤");
			jsonObject.put("phone_num", "15346561927");
			jsonObject.put("sex", "男");
			jsonObject.put("age", "20");
			jsonObject.put("city", "湖南省-长沙市-岳麓区");
			jsonObject.put("address", "湖南省长沙市岳麓区天马学生公寓12栋");
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		String QR_content=jsonObject.toString();
		String test=null;
		try {
			test=URLEncoder.encode(QR_content, "utf-8");
		}catch (Exception e) {
			// TODO: handle exception
		}
	
		BASE64Encoder encoder=new BASE64Encoder();
		String string=encoder.encode(test.getBytes());
		
		//定义二维码参数
		HashMap hints=new HashMap();
		//字符集编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		// 纠错的等级 L > M > Q > H 纠错的能力越高可存储的越少，一般使用M
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置图片边距
        hints.put(EncodeHintType.MARGIN, 2);
        try {
        	// 最终生成 参数列表 （1.内容 2.格式 3.宽度 4.高度 5.二维码参数）
        	BitMatrix bitMatrix=new MultiFormatWriter().encode(string, BarcodeFormat.QR_CODE, width, height,hints);
        	// 写入到本地
        	Path file=new File(path).toPath();
        	MatrixToImageWriter.writeToPath(bitMatrix, format, file);
        	return true;
        }catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
        	return false;
		}
	}
}