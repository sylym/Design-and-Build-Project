package testWeb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import testWeb.vo.*;
import testWeb.dao.UserDAO;
import testWeb.dao.impl.*;

public class loginOrRegisterServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException{
		}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		    throws IOException, ServletException{
		int choice = 0;
		     try {
		    	  choice = Integer.parseInt(req.getParameter("userchoice"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} 
			 if( choice == 1){       
		         res.sendRedirect("./login.jsp");
		        } else if(choice == 2) {   
		         res.sendRedirect("./register.jsp");
		        }else {
		        	res.sendRedirect("./invalidinput.jsp");
		        }
		 }
	
}
