package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import java.io.*;
import java.util.*;
import java.sql.*;

/**
 * Servlet implementation class InitCourierServlet
 */
@WebServlet("/InitCourierServlet")
public class InitCourierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitCourierServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        int courier_id=0;
        String name=null;
        
        Connection connection=null;
		Statement statement=null;
		ResultSet rs=null;
		String sql=null;
		Boolean notfound=true;
		JSONObject resultJSON=new JSONObject();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/courierdatabase?useSSL=false&serverTimezone=UTC","root","161231");
			statement=connection.createStatement();
			sql="select * from couriers where admin=\"false\"";
			rs=statement.executeQuery(sql);
			int i=0;
			while(rs.next()) {
				notfound=false;
				courier_id=rs.getInt("courier_id");
				name=rs.getString("name");
				String message=null;
				message=courier_id+"#"+name;
				resultJSON.put("courier"+i, message);
				i++;
			}
			rs.close();
			if(notfound) {
				resultJSON.put("result", "false");
				resultJSON.put("reason", "�޿��Ա");
				System.out.println("�޿��Ա");
			}else {
				resultJSON.put("result", "true");
				resultJSON.put("length", String.valueOf(i));
				System.out.println("���سɹ�");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				resultJSON.put("result", "false");
				resultJSON.put("reason", "����ʧ��");
				System.out.println("����ʧ��");
			}catch (Exception ex) {
				// TODO: handle exception
			}
		}finally {
			try {
				connection.close();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		response.getOutputStream().write(resultJSON.toString().getBytes("utf-8"));
	}

}
