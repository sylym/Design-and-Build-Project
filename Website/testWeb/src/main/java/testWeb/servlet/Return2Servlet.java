package testWeb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Return2Servlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException{
		}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		    throws IOException, ServletException{
		     try {
		    	 res.sendRedirect("./index.html");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} 
			
		 }
}