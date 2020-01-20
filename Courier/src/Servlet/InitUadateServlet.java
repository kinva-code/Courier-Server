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
 * Servlet implementation class InitUadateServlet
 */
@WebServlet("/InitUadateServlet")
public class InitUadateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitUadateServlet() {
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
        String sex=null;
        String birthday=null;
        String age=null;
        String phoneNumber=null;
        String city=null;
        String power=null;
        
        StringBuffer json=new StringBuffer();
		String line=null;
		try {
			BufferedReader bufferedReader=request.getReader();
			while((line=bufferedReader.readLine())!=null) {
				json.append(line);
			}
			JSONObject jsonObject=new JSONObject(json.toString());
			System.out.println(jsonObject.get("courier_id"));
			courier_id=Integer.valueOf(jsonObject.getString("courier_id"));
		}catch (Exception e) {
			// TODO: handle exception
		}
        
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
			sql="select * from couriers where courier_id="+courier_id;
			rs=statement.executeQuery(sql);
			while(rs.next()) {
				notfound=false;
				courier_id=rs.getInt("courier_id");
				name=rs.getString("name");
				sex=rs.getString("sex");
				birthday=rs.getString("birthday");
				age=rs.getString("age");
				phoneNumber=rs.getString("phone_num");
				city=rs.getString("city");
				power=rs.getString("power");
			}
			rs.close();
			if(notfound) {
				resultJSON.put("result", "false");
				resultJSON.put("reason", "信息加载失败");
				System.out.println("信息加载失败");
			}else {
				resultJSON.put("result", "true");
				resultJSON.put("name",name);
				resultJSON.put("sex",sex);
				resultJSON.put("birthday",birthday);
				resultJSON.put("age",age);
				resultJSON.put("phone_num",phoneNumber);
				resultJSON.put("city", city);
				resultJSON.put("power",power);
				System.out.println("加载成功");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				resultJSON.put("result", "false");
				resultJSON.put("reason", "信息加载失败");
				System.out.println("信息加载失败");
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
