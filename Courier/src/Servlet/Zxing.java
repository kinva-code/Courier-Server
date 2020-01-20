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
		// ���Σ���ά�����ݺ�����·��
        if (zxing.orCode("https://www.cnblogs.com/lsy131479/", "WebContent/QRcode/4.png")) {
            System.out.println("ok,�ɹ�");
        } else {
            System.out.println("no,ʧ��");
        }
	}
	
	private boolean orCode(String content, String path) {
		//ͼƬ��ȸ߶�
		int width=300;
		int height=300;
		//ͼƬ��ʽ
		String format="png";
		//��ά������
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("name", "����");
			jsonObject.put("phone_num", "15346561927");
			jsonObject.put("sex", "��");
			jsonObject.put("age", "20");
			jsonObject.put("city", "����ʡ-��ɳ��-��´��");
			jsonObject.put("address", "����ʡ��ɳ����´������ѧ����Ԣ12��");
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
		
		//�����ά�����
		HashMap hints=new HashMap();
		//�ַ��������ʽ
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		// ����ĵȼ� L > M > Q > H ���������Խ�߿ɴ洢��Խ�٣�һ��ʹ��M
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // ����ͼƬ�߾�
        hints.put(EncodeHintType.MARGIN, 2);
        try {
        	// �������� �����б� ��1.���� 2.��ʽ 3.��� 4.�߶� 5.��ά�������
        	BitMatrix bitMatrix=new MultiFormatWriter().encode(string, BarcodeFormat.QR_CODE, width, height,hints);
        	// д�뵽����
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