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
 * Servlet implementation class UpdateAdminServlet
 */
@WebServlet("/UpdateAdminServlet")
public class UpdateAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAdminServlet() {
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
        String power="";
        
        StringBuffer json=new StringBuffer();
		String line=null;
		try {
			BufferedReader bufferedReader=request.getReader();
			while((line=bufferedReader.readLine())!=null) {
				json.append(line);
			}
			JSONObject jsonObject=new JSONObject(json.toString());
			System.out.println(jsonObject.get("courier_id"));
			System.out.println(jsonObject.get("power"));
			courier_id=Integer.valueOf(jsonObject.getString("courier_id"));
			power=jsonObject.getString("power");
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
			sql="update couriers set power=\""+power+"\" where courier_id="+courier_id;
			int count=statement.executeUpdate(sql);
			if(count>0) {
				resultJSON.put("result", "true");
				System.out.println("权限修改成功");
			}else {
				resultJSON.put("result", "false");
				resultJSON.put("reason", "权限修改失败");
				System.out.println("权限修改失败");
			}
		}catch (Exception e) {
			// TODO: handle exception
			try {
				resultJSON.put("result", "false");
				resultJSON.put("reason", "权限修改失败");
				System.out.println("权限修改失败");
			} catch (Exception e2) {
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
