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

public class FindServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException{
		}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		    throws IOException, ServletException{
			 UserDAO dao = new UserDAOImpl();   
		     UserInfo realUser = new UserInfo();
		     try {
		    	 realUser = dao.getUserInfo(req.getParameter("userid"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} 
			 if(realUser!=null){
				 HttpSession session=req.getSession();
		         session.setAttribute("username", realUser.getUsername());
		         session.setAttribute("userinfoid", realUser.getUserid());
		         session.setAttribute("password", realUser.getPassword());
		         res.sendRedirect("./showuser.jsp");
		        } else {   
		         res.sendRedirect("./cantfinduser.jsp");
		        }
		 }
	
}