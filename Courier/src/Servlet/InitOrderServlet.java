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
 * Servlet implementation class InitOrderServlet
 */
@WebServlet("/InitOrderServlet")
public class InitOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitOrderServlet() {
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
        
        int order_id=0;
        int user_id=0;
        String userName=null;
        String tradeName=null;
        int tradeNum=0;
        String tradePrice=null;
        String orderPrice=null;
        String createTime=null;
        String deliveryTime=null;
        String address=null;
		
		String isLogin="";
		int login_user_id=0;
		String isAdmin=null;
		String power=null;
		
        StringBuffer json=new StringBuffer();
		String line=null;
		try {
			BufferedReader bufferedReader=request.getReader();
			while((line=bufferedReader.readLine())!=null) {
				json.append(line);
			}
			JSONObject jsonObject=new JSONObject(json.toString());
			System.out.println(jsonObject.get("isLogin"));
			isLogin=jsonObject.getString("isLogin");
			login_user_id=Integer.valueOf(jsonObject.getString("user_id"));
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		Connection connection=null;
		Statement statement=null;
		ResultSet rs=null;
		String sql=null;
		Boolean notfound=true;
		JSONObject resultJSON=new JSONObject();
		if(isLogin.equals("true")) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/courierdatabase?useSSL=false&serverTimezone=UTC","root","161231");
				statement=connection.createStatement();
				
				sql="select power,admin from couriers where courier_id="+login_user_id;
				rs=statement.executeQuery(sql);
				if(rs.next()) {
					if(rs.getString("admin").equals("true")) {
						rs.close();
						sql="select * from orders,users where orders.user_id=users.user_id";
					}else {
						power=rs.getString("power");
						rs.close();
						sql="select * from orders,users where orders.user_id=users.user_id and city like \"%"+power+"%\"";
					}
					rs=statement.executeQuery(sql);
					int i=0;
					while(rs.next()) {
						notfound=false;
						order_id=rs.getInt("order_id");
				        user_id=rs.getInt("user_id");
						userName=rs.getString("name");
						tradeName=rs.getString("trade_name");
						tradeNum=rs.getInt("trade_num");
						tradePrice=rs.getString("trade_price");
						orderPrice=rs.getString("order_price");
						createTime=rs.getString("create_time");
						deliveryTime=rs.getString("delivery_time");
						address=rs.getString("address");
						String message=null;
						message=order_id+"#"+user_id+"#"+userName+"#"+tradeName+"#"+tradeNum+"#"+tradePrice+"#"
								+orderPrice+"#"+createTime+"#"+deliveryTime+"#"+address;
						resultJSON.put("order"+i, message);
						i++;
					}
					rs.close();
					if(notfound) {
						resultJSON.put("result", "false");
						resultJSON.put("reason", "无订单");
						System.out.println("无订单");
					}else {
						resultJSON.put("result", "true");
						resultJSON.put("length", String.valueOf(i));
						System.out.println("加载成功");
					}
				}else {
					rs.close();
					resultJSON.put("result", "false");
					resultJSON.put("reason", "该用户不存在");
					System.out.println("该用户不存在");
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				try {
					resultJSON.put("result", "false");
					resultJSON.put("reason", "加载失败");
					System.out.println("加载失败");
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
		}else {
			try {
				resultJSON.put("result", "false");
				resultJSON.put("reason", "未登录");
				System.out.println("未登录");
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		response.getOutputStream().write(resultJSON.toString().getBytes("utf-8"));
	}

}
