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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
        
        String name=null;
		String password=null;
		
        StringBuffer json=new StringBuffer();
		String line=null;
		try {
			BufferedReader bufferedReader=request.getReader();
			while((line=bufferedReader.readLine())!=null) {
				json.append(line);
			}
			JSONObject jsonObject=new JSONObject(json.toString());
			System.out.println(jsonObject.get("name"));
			System.out.println(jsonObject.get("password"));
			name=jsonObject.getString("name");
			password=jsonObject.getString("password");
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		Connection connection=null;
		Statement statement=null;
		String sql=null;
		JSONObject resultJSON=new JSONObject();
		Boolean notfound=true;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/courierdatabase?useSSL=false&serverTimezone=UTC","root","161231");
			statement=connection.createStatement();
			sql="select * from couriers where name=\""+name+"\"";
			ResultSet rs=statement.executeQuery(sql);
			while(rs.next()) {
				notfound=false;
				if(password.equals(rs.getString("password"))) {
					resultJSON.put("result", "true");
					resultJSON.put("id", rs.getString("courier_id"));
					if(rs.getString("admin").equals("true")) {
						resultJSON.put("isAdmin", "true");
					}else {
						resultJSON.put("isAdmin", "false");
					}
					System.out.println("登录成功");
				}else {
					resultJSON.put("result", "false");
					resultJSON.put("reason", "密码不正确");
					System.out.println("登录失败");
				}
			}
			if(notfound){
				resultJSON.put("result", "false");
				resultJSON.put("reason", "用户不存在");
				System.out.println("登录失败");
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
