package Database;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import Database.Entity.RestAccount;
import Database.Entity.User;

public class DBRestAccountOperation extends DBConnection {
	
	// Create a new restaurant account
	public int CreateRestAccount(RestAccount restAccount) {
		
		// Create a restAccount
		int restaccountid = 0;
		try {
			
			this.DatabaseConnect();
			
			// Execute query
			// Save entity
			session.save(restAccount);
			// get restaccountid
			query = session.createQuery("select count(*) from RestAccount");
			restaccountid  = ((Long) query.iterate().next()).intValue();
			
			this.DatabaseClose();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return restaccountid;
	}
	
	// Get restAccount
	public RestAccount GetRestAccount(String phone) {
		
		RestAccount restAccount = null;
		try {
			this.DatabaseConnect();
			Query query = session.createQuery("from RestAccount where phone=?");
			query.setString(0, phone);
			List<RestAccount> l = query.list();
			Iterator it = l.iterator();

			while (it.hasNext()) {
				restAccount = (RestAccount) it.next();
			}
			
			this.DatabaseClose();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return restAccount;
	}
	
	// Get restAccount with your restaccountdi
		public RestAccount GetRestAccountWithId(int restaccountid) {
			
			RestAccount restAccount = null;
			try {
				this.DatabaseConnect();
				Query query = session.createQuery("from RestAccount where restaccountid=?");
				query.setInteger(0, restaccountid);
				List<RestAccount> l = query.list();
				Iterator it = l.iterator();

				while (it.hasNext()) {
					restAccount = (RestAccount) it.next();
				}
				
				this.DatabaseClose();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return restAccount;
		}

}
