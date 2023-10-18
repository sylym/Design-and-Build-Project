package testWeb.dao;

import javax.servlet.http.HttpSession;

import testWeb.vo.*;

public interface UserDAO {
	public int queryByUserInfo (UserInfo userinfo) throws Exception;
	public int registerUserInfo (UserInfo userinfo) throws Exception;
	public UserInfo getUserInfo(String primkey) throws Exception;
	public int changePswrd (UserInfo userinfo,String newPswrd) throws Exception;
	public CarInfo getCarInfo(String primkey) throws Exception;
	public int querycarByUserInfo(CarInfo carinfo) throws Exception;
	public int CheckUserInfo(UserInfo userinfo) throws Exception;
	public int registerCarInfo (CarInfo carinfo) throws Exception;
	public int deleteUser (UserInfo userinfo) throws Exception;
	public Pics getPicPath(String primkey) throws Exception;
	public int queryPicByCarInfo(CarInfo carinfo) throws Exception;
}
