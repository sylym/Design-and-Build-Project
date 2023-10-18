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

public class CheckCarServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException{
		}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		    throws IOException, ServletException{
			 CarInfo carinfo = new CarInfo();
			 HttpSession session=req.getSession();
			 String userinfoid= (String)session.getAttribute("userinfoid");
			 carinfo.setUserid(userinfoid);
			 UserDAO dao = new UserDAOImpl();
			 int flag = 0;
		     try {
					flag = dao.querycarByUserInfo(carinfo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} 
			 if(flag == 1){
				 try {
					 carinfo = dao.getCarInfo(carinfo.getUserid());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				} 
		         session.setAttribute("carname", carinfo.getCarname());
		         session.setAttribute("carinfoid", carinfo.getCarid());
		         res.sendRedirect("./checkCar.jsp");
		        } else {   
		         res.sendRedirect("./error.jsp");
		        }
	}
	
}
