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

public class RegisterServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException{
		}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		    throws IOException, ServletException{
			 UserInfo userinfo = new UserInfo();
			 userinfo.setUsername(req.getParameter("username"));
			 userinfo.setPassword(req.getParameter("password"));
			 userinfo.setUserid(req.getParameter("userid"));
			 UserDAO dao = new UserDAOImpl();   
		     int flag = 0;
		     try {
					flag = dao.registerUserInfo(userinfo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} 
			 if(flag == 1){   
		         res.sendRedirect("./regsuccess.jsp");
		        } else if(flag == 0){   
		         res.sendRedirect("./regfail.jsp");
		        }else if(flag == 2) {
		        	res.sendRedirect("./existuser.jsp");
		        }
		 }
	
}
