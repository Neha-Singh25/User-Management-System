import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class Register extends HttpServlet {
	private static final String query ="insert into user(name, email, mobile, dob, city, gender)values (?,?,?,?,?,?)";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get printwriter
		PrintWriter pw = res.getWriter();
		//set content type
		res.setContentType("text/html");
		
		//link the bootstrap
		pw.println("<link rel = 'stylesheet' href ='css/bootstrap.css'></link>");
		
		//get the values
		String name = req.getParameter("userName");
		String email = req.getParameter("email");
		String mobile = req.getParameter("mobile");
		String dob = req.getParameter("dob");
		String city = req.getParameter("city");
		String gender = req.getParameter("gender");
		//load the JDBC driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//generate the connection
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/usermgmt","root","Oct2024@mysql");
			
			PreparedStatement ps = con.prepareStatement(query);
			//set the values
			ps.setString(1,name );
			ps.setString(2, email);
			ps.setString(3, mobile);
			ps.setString(4, dob);
			ps.setString(5, city);
			ps.setString(6, gender);
			//execute the query
			int count = ps.executeUpdate();
			pw.println("<div class='card' style='margin:auto; width:300px;margin-top:100px;'>");
			if(count == 1) {
				pw.println("<h2 class='bg-success text-light text-center'>Record is registered successfully.</h2>");
			}else {
				pw.println("<h2 class='bg-danger text-light text-center'>Record is not registered .</h2>");
			}
			
			
		} catch (SQLException e) {
			pw.println("<h2 class='bg-danger text-light text-center'>"+e.getMessage()+"</h2>");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		pw.println("<button class='btn btn-outline-info'><a href='home.html'>Home</a></button>");
		pw.println("</div>");
		//close the stream
		pw.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}
}
