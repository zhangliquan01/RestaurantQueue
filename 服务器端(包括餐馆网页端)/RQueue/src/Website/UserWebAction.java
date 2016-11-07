package Website;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import Database.Entity.RestAccount;
import Transaction.RestAccountOperation;

public class UserWebAction extends ActionSupport {
	private String phone;
	private String password;
	private String register_restid;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRegister_restid() {
		return register_restid;
	}

	public void setRegister_restid(String register_restid) {
		this.register_restid = register_restid;
	}

	
	
	
	
	public String Register() {
		
		// Create a RestAccount
		// if returns SUCCESS, register success
		// if returns FAILED, an error occured.
		// "error" could be:
		// -1-create failed.
		// -2-system error.
		// -3-cannot be empty.
		String result = "";
		try {
			//result = RestAccountOperation.createRestAccount(restAccount);
			String phoneString = this.getPhone();
			String pwdString = this.getPassword();
			String restidString = this.getRegister_restid();
			if (phoneString==null || pwdString==null || restidString==null) {
				ActionContext.getContext().getSession().put("error", "-3");
				return "FAILED";
			}
			
			RestAccount restAccount = new RestAccount();
			restAccount.setPhone(phoneString);
			restAccount.setPassword(pwdString);
			restAccount.setRestid(Integer.valueOf(restidString).intValue());
			
			int dbResult = RestAccountOperation.createRestAccount(restAccount);
			result = result + dbResult;
			if (dbResult>0) {
				ActionContext.getContext().getSession().put("userid", dbResult);
				ActionContext.getContext().getSession().put("restid", restidString);
				ActionContext.getContext().getSession().put("error", "1");
				return "SUCCESS";
			} else {
				String tempString = ""+dbResult;
				ActionContext.getContext().getSession().put("error", result);
				return "FAILED";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ActionContext.getContext().getSession().put("error", "-2");
			return "FAILED";
		}
	}

	public String LoginCheck() {
		
		// Login check, phone and password
		// if returns SUCCESS, login success
		// if returns FAILED, an error occured.
		// "error" could be:
		// 0-no such user
		// -1-login failed.
		// -2-system error.
		// -3-cannot be empty.
		String result = "";
		try {
			String phoneString = this.getPhone();
			String passwordString = this.getPassword();
			if (phoneString==null || passwordString==null) {
				ActionContext.getContext().getSession().put("error", "-3");
				return "FAILED";
			}
			int dbResult = RestAccountOperation.loginCheck(phoneString.trim(), passwordString.trim());
			result = result + dbResult;
			if (dbResult>0) {
				ActionContext.getContext().getSession().put("userid", result);
				int restid = RestAccountOperation.getRestid(dbResult);
				result = ""+restid;
				ActionContext.getContext().getSession().put("restid", result);
				ActionContext.getContext().getSession().put("error", "1");
				return "SUCCESS";
			} else {
				String tempString = ""+dbResult;
				ActionContext.getContext().getSession().put("error", result);
				return "FAILED";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ActionContext.getContext().getSession().put("error", "-2");
			return "FAILED";
		}
	}
	
	
	
	public String AccessUserCenter() {
		
		// access user center 
		// if SUCCESS, redirect to usercenter.jsp
		// if LOGIN_FAILED, redirect to login.action
		try {
			// Check if user has logged in
			String restaccountid = ActionContext.getContext().getSession().get("userid").toString();
			String restidString = ActionContext.getContext().getSession().get("restid").toString();
			if (restaccountid==null || restaccountid.equals(""))
				return "LOGIN_FAILED";
			if (restidString==null || restidString.equals(""))
				return "LOGIN_FAILED";
			
			return "SUCCESS";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ActionContext.getContext().getSession().put("error", "-2");
			return "FAILED";
		}
	}
}
