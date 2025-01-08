import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/showdata")
public class showuser extends HttpServlet {
	 private final static String query = "select id,name,email,mobile,dob,city,gender from user";
	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	        //get PrintWriter
	        PrintWriter pw = res.getWriter();
	        //set content type
	        res.setContentType("text/html");
	        //link the bootstrap
	        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
	        pw.println("<marquee><h2 class='text-primary'>User Data</h2></marquee>");
	        //load the JDBC driver
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        }catch(Exception e) {
	            e.printStackTrace();
	        }
	        //generate the connection
	        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/usermgmt","root","Oct2024@mysql");
	                PreparedStatement ps = con.prepareStatement(query);){
	            //resultSet
	            ResultSet rs = ps.executeQuery();
	            pw.println("<div style='margin:auto;width:900px;margin-top:20px;'>");
	            pw.println("<table class='table table-hover table-striped'>");
	            pw.println("<tr>");
	            pw.println("<th>ID</th>");
	            pw.println("<th>Name</th>");
	            pw.println("<th>Email</th>");
	            pw.println("<th>Mobile No</th>");
	            pw.println("<th>DOB</th>");
	            pw.println("<th>City</th>");
	            pw.println("<th>Gender</th>");
	            pw.println("<th>Edit</th>");
	            pw.println("<th>Delete</th>");
	            pw.println("</tr>");
	            while(rs.next()) {
	                pw.println("<tr>");
	                pw.println("<td>"+rs.getInt(1)+"</td>");
	                pw.println("<td>"+rs.getString(2)+"</td>");
	                pw.println("<td>"+rs.getString(3)+"</td>");
	                pw.println("<td>"+rs.getString(4)+"</td>");
	                pw.println("<td>"+rs.getString(5)+"</td>");
	                pw.println("<td>"+rs.getString(6)+"</td>");
	                pw.println("<td>"+rs.getString(7)+"</td>");
	                pw.println("<td><a href='editurl?id="+rs.getInt(1)+"'><button class='btn btn-outline-warning display-block'>Edit</button></a></td>");
	                pw.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'><button class='btn btn-outline-danger display-block'>Delete</button></a></td>");
	                pw.println("</tr>");
	            }
	            pw.println("</table>");
	        }catch(SQLException se) {
	            pw.println("<h2 class='bg-danger text-light text-center'>"+se.getMessage()+"</h2>");
	            se.printStackTrace();
	        }catch(Exception e) {
	            e.printStackTrace();
	        }
	        pw.println("<a href='home.html'><button class='btn btn-outline-info display-block'>Home</button></a>");
	        pw.println("</div>");
	        //close the stream
	        pw.close();
	    }
	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	        doGet(req,res);
	    }

}
