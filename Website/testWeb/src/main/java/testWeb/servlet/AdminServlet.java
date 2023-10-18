package testWeb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminServlet extends HttpServlet {
	
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
		         res.sendRedirect("./deleteuser.jsp");
		        } else if(choice == 2) {   
		         res.sendRedirect("./finduser.jsp");
		        }else if(choice == 3) {   
			     res.sendRedirect("./carRegister.jsp");
			    }else {
		         res.sendRedirect("./invalidinput.jsp");
		        }
		 }
	
}
