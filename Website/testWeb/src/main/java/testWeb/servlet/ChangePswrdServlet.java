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

public class ChangePswrdServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException{
		}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		    throws IOException, ServletException{
		     UserInfo userinfo=new UserInfo();
		     HttpSession session=req.getSession();  
		     userinfo.setUsername((String)session.getAttribute("username"));
		     userinfo.setUserid((String)session.getAttribute("userinfoid"));
		     userinfo.setPassword((String)session.getAttribute("password"));
			 UserDAO dao = new UserDAOImpl();   
		     int flag = 0;

		     try {
					flag = dao.changePswrd(userinfo, req.getParameter("newpassword"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} 
			 if(flag == 1){   
		         res.sendRedirect("./changesuccess.jsp");
		        } else {   
		         res.sendRedirect("./changefail.jsp");
		        }
		 }
	
}
