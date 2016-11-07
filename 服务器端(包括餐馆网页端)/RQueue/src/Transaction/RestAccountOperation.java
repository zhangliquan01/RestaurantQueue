package Transaction;

import Database.DBRestAccountOperation;
import Database.Entity.RestAccount;

public class RestAccountOperation {
	
	public static int createRestAccount(RestAccount restAccount) {
		
		// Create a RestAccount
		// Returns restaccountid
		int restaccountid = 0;
		try {
			
			DBRestAccountOperation restAccountOperation = new DBRestAccountOperation();
			restaccountid = restAccountOperation.CreateRestAccount(restAccount);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return restaccountid;
	}
	
	
	public static int loginCheck(String phone, String password) {
		
		// Login check, phone and password
		// returns restaccountid
		// if returns 0, no such user
		// if returns -1, login failed.
		// if returns -2, system error.
		int result = 0;
		try {
			DBRestAccountOperation restAccountOperation = new DBRestAccountOperation();
			RestAccount restAccount = restAccountOperation.GetRestAccount(phone);
			if (restAccount==null)
				return 0;
			if (restAccount.getPassword().equals(password))
				result = restAccount.getRestaccountid();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = -2;
		}
		return result;
	}
	
	public static int getRestid(int restaccountid){
		
		// Get restid from your restaccountid
		// If returns 0, no such user
		// if returns -2, system error.
		try {
			DBRestAccountOperation restAccountOperation = new DBRestAccountOperation();
			RestAccount restAccount = restAccountOperation.GetRestAccountWithId(restaccountid);
			if (restAccount==null) {
				return 0;
			} else {
				return restAccount.getRestid();
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}

}
