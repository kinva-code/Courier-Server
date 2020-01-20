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
 * Servlet implementation class UpdateMessageServlet
 */
@WebServlet("/UpdateMessageServlet")
public class UpdateMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMessageServlet() {
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
        String name=null;
        String sex=null;
        String age=null;
        String birthday=null;
        String phoneNumber=null;
        String city=null;
        
        StringBuffer json=new StringBuffer();
		String line=null;
		try {
			BufferedReader bufferedReader=request.getReader();
			while((line=bufferedReader.readLine())!=null) {
				json.append(line);
			}
			JSONObject jsonObject=new JSONObject(json.toString());
			System.out.println(jsonObject.get("id"));
			System.out.println(jsonObject.get("name"));
			System.out.println(jsonObject.get("sex"));
			System.out.println(jsonObject.get("age"));
			System.out.println(jsonObject.get("birthday"));
			System.out.println(jsonObject.get("phone_num"));
			System.out.println(jsonObject.get("city"));
			id=jsonObject.getString("id");
			name=jsonObject.getString("name");
			sex=jsonObject.getString("sex");
			age=jsonObject.getString("age");
			birthday=jsonObject.getString("birthday");
			phoneNumber=jsonObject.getString("phone_num");
			city=jsonObject.getString("city");
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
			sql="select courier_id from couriers where courier_id=\""+id+"\"";
			ResultSet rs=statement.executeQuery(sql);
			if(rs.next()) {
				sql="update couriers set ";
				sql=sql+"sex=\""+sex+"\",age=\""+age+"\",birthday=\""+birthday+"\",phone_num=\""+phoneNumber+"\",city=\""+city+"\" ";
				sql=sql+" where courier_id=\""+id+"\"";
				int count=statement.executeUpdate(sql);
				if(count>0) {
					resultJSON.put("result", "true");
				}
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
