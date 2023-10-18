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

public class UserLoginServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException{
		}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		    throws IOException, ServletException{
			 UserInfo userinfo = new UserInfo();
			 userinfo.setUserid(req.getParameter("userid"));
			 userinfo.setPassword(req.getParameter("password"));
			 UserDAO dao = new UserDAOImpl();   
		     int flag = 0;
		     try {
					flag = dao.queryByUserInfo(userinfo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} 
			 if(flag == 1){
				 CarInfo carinfo = new CarInfo();
				 try {
					 carinfo = dao.getCarInfo(userinfo.getUserid());
					 userinfo = dao.getUserInfo(userinfo.getUserid());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				} 
				 
				 HttpSession session=req.getSession();   
		         session.setAttribute("username", userinfo.getUsername());
		         session.setAttribute("userinfoid", userinfo.getUserid());
		         session.setAttribute("password", userinfo.getPassword());
		         session.setAttribute("carname", carinfo.getCarname());
		         session.setAttribute("carinfoid", carinfo.getCarid());
		         session.setAttribute("carpassword",carinfo.getCarPassword());
		         res.sendRedirect("./index.html");
		        } else if(flag==2) {
						 try {
							 userinfo = dao.getUserInfo(userinfo.getUserid());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
						} 
						 
						 HttpSession session=req.getSession();   
				         session.setAttribute("username", userinfo.getUsername());
				         session.setAttribute("userinfoid", userinfo.getUserid());
				         session.setAttribute("password", userinfo.getPassword());
				         res.sendRedirect("./admin.jsp");
		        }else {   
		         res.sendRedirect("./error.jsp");
		        }
	
	}
	
}
