package Database;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import Database.Entity.User;

public class DBUserOperation extends DBConnection {
	
	// Create user method
	public int CreateUser(User user) {
		// returns userid
		// if returns 0, meaning an error occured
		int userid=0;
		try {
			this.DatabaseConnect();
			
			// Execute query
			// save user
			session.save(user);
			// get userid
			query = session.createQuery("select count(*) from User");
			userid  = ((Long) query.iterate().next()).intValue();
			
			this.DatabaseClose();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return userid;
	}
	
	 public User GetUser(String phone) {
		 
		 // return a user with specific phone
		 User user = null;
		 try {
			 
			 this.DatabaseConnect();
			 
			 Query query = session.createQuery("from User where phone=?");
			 query.setString(0, phone);
			 List<User> l = query.list();
			 Iterator it = l.iterator();

			 while (it.hasNext()) {
				 user = (User) it.next();
			 }
			 
			 this.DatabaseClose();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 return user;
	 }

}
