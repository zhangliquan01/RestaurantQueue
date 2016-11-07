package Action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import Transaction.UserOperation;

import Database.Entity.Like;
import Database.Entity.User;

public class UserAction{

	//用户登陆
	public void Login() {
		// login check
		// returns user
		// if userid = 0,login failed
		// if userid = -1, system errors
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		
		BaseAction baseAction = new BaseAction(request,response);
		
		JSONObject userObject = baseAction.getData();	
		User user = UserOperation.loginCheck(userObject.getString("phone"), userObject.getString("password"));
		
		System.out.println(userObject.toString());
		
//		User user = UserOperation.loginCheck("12345678901", "king");
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("result", user);
//		System.out.println(jsonObject);
		baseAction.addReturnData("result", user);
		baseAction.addReturnData("userid", user.getUserid());
		baseAction.returnData();
	}
	
	//用户注册
	public void Register() throws UnsupportedEncodingException
	{
		// create a user and returns its userid
		// if returns 0, meaning an error occured
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		
		BaseAction baseAction = new BaseAction(request,response);
		JSONObject jsObject = baseAction.getData();
		System.out.println(jsObject);
		System.out.println(jsObject.getString("name"));
		
		User user = new User();
		user.setName(jsObject.getString("name"));
		System.out.println(user.getName());
		
		user.setPassword(jsObject.getString("password"));
		System.out.println(user.getPassword());
		
		user.setPhone(jsObject.getString("phone"));
		System.out.println(user.getPhone());
		
		int userid = UserOperation.createUser(user);
		baseAction.addReturnData("userid", userid);
		baseAction.addReturnData("result", user);
		
		baseAction.returnData();
	}
	//删除喜欢的餐馆
	public void deleteLike(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		
		BaseAction baseAction = new BaseAction(request,response);
		JSONObject jsonObject = baseAction.getData();
		
		Like like = new Like();
		like.setRestid(jsonObject.getInt("restid"));
		like.setUserid(jsonObject.getInt("userid"));
		
		int result = UserOperation.addLike(like);
		baseAction.addReturnData("result", result);
		
		baseAction.returnData();
	}
	///添加喜欢的餐馆
	public void addLike(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		
		BaseAction baseAction = new BaseAction(request,response);
		JSONObject jsonObject = baseAction.getData();
		
		Like like = new Like();
		like.setRestid(jsonObject.getInt("restid"));
		like.setUserid(jsonObject.getInt("userid"));
		
		int result = UserOperation.addLike(like);
		baseAction.addReturnData("result", result);
		
		baseAction.returnData();
	}
	
	//上传图片
	public void PassImage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		BaseAction baseAction = new BaseAction(request,response);
		JSONObject jObject = baseAction.getData();
		
		System.out.println(jObject);
		baseAction.returnData();
	}
	
//	public void getUserName(){
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setCharacterEncoding("utf-8");
//		BaseAction baseAction = new BaseAction(request,response);
//		JSONObject jObject = baseAction.getData();
//		
//		int userid =  jObject.getInt("userid");
//		String name = UserOperation.getUserName(userid);
//		baseAction.addReturnData("name", name);
//		baseAction.returnData();
//	}
}