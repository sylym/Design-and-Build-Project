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

public class DeleteServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException{
		}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		    throws IOException, ServletException{
		     UserInfo userinfo=new UserInfo();
		     userinfo.setUserid(req.getParameter("userid"));
			 UserDAO dao = new UserDAOImpl();   
		     int flag = 0;

		     try {
		    	 flag = dao.deleteUser(userinfo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} 
		     if(flag == 1){   
		         res.sendRedirect("./deletesuccess.jsp");
		        } else {   
		         res.sendRedirect("./deletefail.jsp");
		        }
		 }
	
}