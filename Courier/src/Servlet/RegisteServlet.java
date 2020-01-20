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
 * Servlet implementation class RegisteServlet
 */
@WebServlet("/RegisteServlet")
public class RegisteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisteServlet() {
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
		String sex=null;
		String age=null;
		String birthday=null;
		String city=null;
		String phone_num=null;
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
			System.out.println(jsonObject.get("sex"));
			System.out.println(jsonObject.get("age"));
			System.out.println(jsonObject.get("birthday"));
			System.out.println(jsonObject.get("city"));
			System.out.println(jsonObject.get("phone_num"));
			System.out.println(jsonObject.get("password"));
			name=jsonObject.getString("name");
			sex=jsonObject.getString("sex");
			age=jsonObject.getString("age");
			birthday=jsonObject.getString("birthday");
			city=jsonObject.getString("city");
			phone_num=jsonObject.getString("phone_num");
			password=jsonObject.getString("password");
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
			sql="select name from couriers where name=\""+name+"\"";
			ResultSet rs=statement.executeQuery(sql);
			if(rs.next()) {
				rs.close();
				resultJSON.put("result", "false");
				resultJSON.put("reason", "ÓÃ»§ÒÑ´æÔÚ");
				System.out.println("×¢²áÊ§°Ü");
			}else {
				try{
					rs.close();
					sql="insert into couriers(name,password,sex,age,birthday,city,phone_num,power,admin) ";
					sql=sql+"values(\""+name+"\",\""+password+"\",\""+sex+"\",\""+age+"\",\""+birthday+"\",\""+city+"\",\""+phone_num+"\",\""+city+"\",\"false\")";
					System.out.println(city);
					int count=statement.executeUpdate(sql);
					if(count>0){
						resultJSON.put("result", "true");
						System.out.println("×¢²á³É¹¦");
					}else {
						resultJSON.put("result", "false");
						resultJSON.put("reason", "×¢²áÌí¼ÓÊ§°Ü");
						System.out.println("×¢²áÊ§°Ü");
					}
				}catch(Exception e){
					resultJSON.put("result", "false");
					resultJSON.put("reason", "×¢²áÌí¼ÓÊ§°Ü");
					System.out.println("×¢²áerror");
				}
			}
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
