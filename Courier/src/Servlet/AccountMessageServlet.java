package Servlet;

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
 * Servlet implementation class AccountMessageServlet
 */
@WebServlet("/AccountMessageServlet")
public class AccountMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountMessageServlet() {
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
        
        String id=null;
        
        StringBuffer json=new StringBuffer();
		String line=null;
		try {
			BufferedReader bufferedReader=request.getReader();
			while((line=bufferedReader.readLine())!=null) {
				json.append(line);
			}
			JSONObject jsonObject=new JSONObject(json.toString());
			id=jsonObject.getString("id");
			System.out.println("id="+id);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		Connection connection=null;
		Statement statement=null;
		String sql=null;
		JSONObject resultJSON=new JSONObject();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/courierdatabase?useSSL=false&serverTimezone=UTC","root","161231");
			statement=connection.createStatement();
			sql="select * from couriers where courier_id=\""+id+"\"";
			ResultSet rs=statement.executeQuery(sql);
			if(rs.next()) {
				resultJSON.put("result", "true");
				resultJSON.put("name", rs.getString("name"));
				resultJSON.put("sex", rs.getString("sex"));
				resultJSON.put("age", rs.getString("age"));
				resultJSON.put("birthday", rs.getString("birthday"));
				resultJSON.put("phone_num", rs.getString("phone_num"));
				resultJSON.put("city", rs.getString("city"));
			}else {
				resultJSON.put("result", "false");
				resultJSON.put("reason", "该用户信息不存在");
			}
			rs.close();
		}catch (Exception e) {
			// TODO: handle exception
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
