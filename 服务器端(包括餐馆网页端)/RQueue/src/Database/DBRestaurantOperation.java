package Database;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import Database.Entity.Restaurant;

public class DBRestaurantOperation extends DBConnection {
	
	// Create restaurant method
	public int CreateRestaurant(Restaurant rest) {
		
		// returns restid
		// if returns 0, failed.
		int restid=0;
		try {
			this.DatabaseConnect();
			
			// Execute query
			// save restaurant
			session.save(rest);
			// get restid
			query = session.createQuery("select count(*) from Restaurant");
			restid  = ((Long) query.iterate().next()).intValue();
			
			this.DatabaseClose();
			return restid;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return restid;
		}
	}
}
