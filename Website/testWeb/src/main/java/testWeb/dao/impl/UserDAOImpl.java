package testWeb.dao.impl;

import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import testWeb.dao.UserDAO;
import testWeb.db.DBConnect;
import testWeb.vo.UserInfo;
import testWeb.vo.CarInfo;
import testWeb.vo.Pics;

public class UserDAOImpl implements UserDAO {
	
	public int queryByUserInfo(UserInfo userinfo) throws Exception {
		int flag = 0;
		String sql = "select * from userinfo where userinfoid=?";
        PreparedStatement pstmt = null ;   
        DBConnect dbc = null;
        // 下面是针对数据库的具体操作   
        try{   
            // 连接数据库   
            dbc = new DBConnect() ;   
            pstmt = dbc.getConnection().prepareStatement(sql) ; 
            pstmt.setString(1,userinfo.getUserid());   
            // 进行数据库查询操作   
            ResultSet rs = pstmt.executeQuery();
          
            while(rs.next()){
                if("000".equals(rs.getString("userinfoid"))&&rs.getString("password").equals(userinfo.getPassword())) {
                	flag=2;		
                }else if(rs.getString("password").equals(userinfo.getPassword())){
                	flag = 1;
                } 
            }   
            rs.close() ; 
            pstmt.close() ;   
        }catch (SQLException e){   
            System.out.println(e.getMessage());   
        }finally{   
            // 关闭数据库连接   
            dbc.close() ;   
        }   
		return flag;
	}
	
	//注册 向数据库插入新成员
	public int registerUserInfo (UserInfo userinfo) throws Exception{
		int flag=0;
		String sqlTemplate = "insert into userinfo VALUEs (?,?,?)"; 
	    DBConnect dbc = null;
	    PreparedStatement pstmt = null ;
	    UserInfo realUser = new UserInfo();
	    try{   
            // 连接数据库   
            dbc = new DBConnect() ;   
            pstmt = dbc.getConnection().prepareStatement(sqlTemplate);
            realUser = this.getUserInfo(userinfo.getUserid());
            if(realUser == null) {
            pstmt.setString(1,userinfo.getUserid()); 
            pstmt.setString (2,userinfo.getUsername());
            pstmt.setString(3,userinfo.getPassword());
            pstmt.executeUpdate();
            pstmt.close() ;
            flag = 1;
            }else {
            	flag = 2;
            }
        }catch (SQLException e){   
            System.out.println(e.getMessage());
        }finally{   
            // 关闭数据库连接   
            dbc.close() ;   
        }   
		
		return flag;
	}
	
	public UserInfo getUserInfo(String primkey) throws Exception{
		UserInfo userinfo = new UserInfo();
		String sql = "select * from userinfo where userinfoid=?";
		PreparedStatement pstmt = null ;   
	    DBConnect dbc = null;
	    try {
	    	dbc = new DBConnect() ;   
            pstmt = dbc.getConnection().prepareStatement(sql) ; 
            pstmt.setString(1,primkey) ;   
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){  
            userinfo.setUserid(rs.getString("userinfoid"));
            userinfo.setUsername(rs.getString("username"));
            userinfo.setPassword(rs.getString("password"));
            }
            rs.close() ; 
            pstmt.close() ; 
	    	
	    }catch (SQLException e){   
            System.out.println(e.getMessage());   
        }finally{   
            // 关闭数据库连接   
            dbc.close() ;   
        }
	    if(userinfo.getUserid()==null) {
	    	userinfo = null;
	    }
		return userinfo;
	}
	
	public int changePswrd (UserInfo userinfo,String newPswrd) throws Exception{
		int flag=0;
		String sqlTemplate = "update userinfo set password = ? where userinfoid = ?"; 
	    DBConnect dbc = null;
	    PreparedStatement pstmt = null ;
	    try{   
            // 连接数据库   
            dbc = new DBConnect() ;   
            pstmt = dbc.getConnection().prepareStatement(sqlTemplate);
            userinfo.setPassword(newPswrd);
            pstmt.setString(1,userinfo.getPassword());
            pstmt.setString(2,userinfo.getUserid());
            pstmt.executeUpdate();
            pstmt.close() ;
            flag = 1;
        }catch (SQLException e){   
            System.out.println(e.getMessage());
        }finally{  
            // 关闭数据库连接   
            dbc.close() ;   
        }   
		
		return flag;
	}
	
	public int deleteUser (UserInfo userinfo) throws Exception{
        int flag=0;
        String sqlTemplate = "DELETE FROM userinfo WHERE userinfoid = ?";
        DBConnect dbc = null;
        PreparedStatement pstmt = null ;
        try{
            // 连接数据库
            dbc = new DBConnect() ;
            pstmt = dbc.getConnection().prepareStatement(sqlTemplate);
            UserInfo realUser = new UserInfo();
            realUser = this.getUserInfo(userinfo.getUserid());
            if(realUser!=null) {
                pstmt.setString(1,userinfo.getUserid());
                pstmt.executeUpdate();
                pstmt.close() ;
                flag = 1;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            // 关闭数据库连接   
            dbc.close() ;
        }

        return flag;
    }
	//得到车的信息
	public CarInfo getCarInfo(String primkey) throws Exception{
		CarInfo carinfo = new CarInfo();
		String sql = "select * from carinfo where userinfoid=?";
		PreparedStatement pstmt = null ;   
	    DBConnect dbc = null;
	    try {
	    	dbc = new DBConnect() ;   
            pstmt = dbc.getConnection().prepareStatement(sql) ; 
            pstmt.setString(1,primkey) ;   
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){  
            carinfo.setUserid(rs.getString("userinfoid"));
            carinfo.setCarid(rs.getString("carinfoid"));
            carinfo.setCarname(rs.getString("carname"));
            carinfo.setCarPassword(rs.getString("carpassword"));
            }
            rs.close() ; 
            pstmt.close() ; 
	    	
	    }catch (SQLException e){   
            System.out.println(e.getMessage());   
        }finally{   
            // 关闭数据库连接   
            dbc.close() ;   
        }   
		return carinfo;
	}
	
	//登录车
	public int querycarByUserInfo(CarInfo carinfo) throws Exception {
		int flag = 0;
		String sql = "select * from carinfo where userinfoid=?";
        PreparedStatement pstmt = null ;   
        DBConnect dbc = null;
        // 下面是针对数据库的具体操作   
        try{   
            // 连接数据库  
            dbc = new DBConnect() ;   
            pstmt = dbc.getConnection().prepareStatement(sql) ; 
            pstmt.setString(1,carinfo.getUserid());   
            // 进行数据库查询操作   
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){  
                // 查询出内容，将其与用户提交的内容对比 
                if(rs.getString("userinfoid").equals(carinfo.getUserid())){
                	flag = 1;
                } 
            }   
            rs.close() ; 
            pstmt.close() ;   
        }catch (SQLException e){   
            System.out.println(e.getMessage());   
        }finally{   
            // 关闭数据库连接   
            dbc.close() ;   
        }   
		return flag;
	}
		
	//查询UserInfo
	public int CheckUserInfo(UserInfo userinfo) throws Exception {
	int flag = 0;
	String sql = "select * from userinfo where userinfoid=?";
	PreparedStatement pstmt = null ;   
	DBConnect dbc = null;
	// 下面是针对数据库的具体操作   
	try{   
	    // 连接数据库   
	    dbc = new DBConnect() ;   
	    pstmt = dbc.getConnection().prepareStatement(sql) ; 
	    pstmt.setString(1,userinfo.getUserid());   
	    // 进行数据库查询操作   
	    ResultSet rs = pstmt.executeQuery();
	    while(rs.next()){  
	        // 查询出内容，将其与用户提交的内容对比 
	        if(rs.getString("userinfoid").equals(userinfo.getUserid())){
	        	flag = 1;
	        } 
	    }   
	    rs.close() ; 
	    pstmt.close() ;   
	}catch (SQLException e){   
	    System.out.println(e.getMessage());   
	}finally{   
	    // 关闭数据库连接   
	    dbc.close() ;   
	}   
	return flag;
	}

	//小车注册
	
	public int registerCarInfo (CarInfo carinfo) throws Exception{
	int flag=0;
	String sqlTemplate = "insert into carinfo VALUES (?,?,?,?)"; 
	DBConnect dbc = null;
	PreparedStatement pstmt = null ;
	try{   
	    // 连接数据库   
	    dbc = new DBConnect() ;   
	    pstmt = dbc.getConnection().prepareStatement(sqlTemplate);
	    pstmt.setString(1,carinfo.getUserid()); 
	    pstmt.setString (2,carinfo.getCarid());
	    pstmt.setString(3,carinfo.getCarname());
	    pstmt.setString(4, carinfo.getCarPassword());
	    pstmt.executeUpdate();
	    pstmt.close() ; 
	    pstmt.close() ;
	    flag = 1;
	}catch (SQLException e){   
	    System.out.println(e.getMessage());
	}finally{   
	    // 关闭数据库连接   
	    dbc.close() ;   
	}   
	
	return flag;
	}

   //得到图片的信息
	public Pics getPicPath(String primkey) throws Exception{
		Pics pics = new Pics();
		String sql = "select * from pics where carinfoid=?";
		PreparedStatement pstmt = null ;   
	    DBConnect dbc = null;
	    try {
	    	dbc = new DBConnect() ;   
          pstmt = dbc.getConnection().prepareStatement(sql) ; 
          pstmt.setString(1,primkey) ;   
          ResultSet rs = pstmt.executeQuery();
          while(rs.next()){  
          pics.setCarinfoid(rs.getString("carinfoid"));
          pics.setPath(rs.getString("path"));
          }
          rs.close() ; 
          pstmt.close() ; 
	    	
	    }catch (SQLException e){   
          System.out.println(e.getMessage());   
      }finally{   
          // 关闭数据库连接   
          dbc.close() ;   
      }   
		return pics;
	}
	
	public int queryPicByCarInfo(CarInfo carinfo) throws Exception {
		int flag = 0;
		String sql = "select * from pics where carinfoid=?";
        PreparedStatement pstmt = null ;   
        DBConnect dbc = null;
        // 下面是针对数据库的具体操作   
        try{   
            // 连接数据库  
            dbc = new DBConnect() ;   
            pstmt = dbc.getConnection().prepareStatement(sql) ; 
            pstmt.setString(1,carinfo.getCarid());   
            // 进行数据库查询操作   
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){  
                // 查询出内容，将其与用户提交的内容对比 
                if(rs.getString("carinfoid").equals(carinfo.getCarid())){
                	flag = 1;
                } 
            }   
            rs.close() ; 
            pstmt.close() ;   
        }catch (SQLException e){   
            System.out.println(e.getMessage());   
        }finally{   
            // 关闭数据库连接   
            dbc.close() ;   
        }   
		return flag;
	}


	
}
