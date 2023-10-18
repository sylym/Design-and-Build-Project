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

public class CarregServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException{
		}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException{
			 CarInfo carinfo = new CarInfo();
			 carinfo.setUserid(req.getParameter("userid"));
			 carinfo.setCarid(req.getParameter("carid"));
			 carinfo.setCarname(req.getParameter("carname"));
			 carinfo.setCarPassword(req.getParameter("carpassword"));
			 UserDAO dao = new UserDAOImpl();   
		     int flag = 0;

		     try {
					flag = dao.registerCarInfo(carinfo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} 
			 if(flag == 1){   
		         res.sendRedirect("./regsuccess.jsp");
		        } else {   
		         res.sendRedirect("./regfail.jsp");
		        }
		 }
	
}
