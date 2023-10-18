package testWeb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import testWeb.vo.*;
import testWeb.dao.UserDAO;
import testWeb.dao.impl.*;

public class PicsServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException{
		}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		    throws IOException, ServletException{
			 CarInfo carinfo = new CarInfo();
			 HttpSession session=req.getSession();
			 String carinfoid= (String)session.getAttribute("carinfoid");
			 UserDAO dao = new UserDAOImpl();
			 int flag = 0;
		     try {
					flag = dao.querycarByUserInfo(carinfo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} 
			 if(flag == 1){
				 Pics pics = new Pics();
				 try {
					 pics = dao.getPicPath(carinfo.getCarid());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
				String picPath = pics.getPath();
				File file = new File(picPath);
				File[] files = file.listFiles();
				
				
				
				
		         
		         
		         res.sendRedirect("./checkCar.jsp");
		        } else {   
		         res.sendRedirect("./error.jsp");
		        }
	}
	
}
