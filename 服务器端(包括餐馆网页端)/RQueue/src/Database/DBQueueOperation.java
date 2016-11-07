package Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import Database.Entity.Queue;
import Database.Entity.Restaurant;

public class DBQueueOperation extends DBConnection {
	
	// Create queue method
	public int CreateQueue(Queue queue) {
		// returns queueid
		int queueid=0;
		try {
			this.DatabaseConnect();
			
			// Set Queue state as 0
			queue.setState(0);
			// Execute query
			// save queue
			session.save(queue);
			// get queueid
			query = session.createQuery("select count(*) from Queue");
			queueid  = ((Long) query.iterate().next()).intValue();
			
			this.DatabaseClose();
			return queueid;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return queueid;
		}
	}
	
	// Get current queueing numbers
	public int GetCurrentQueueNumber(int restid) {
		
		// returns numbers
		// if 0, means no people waiting
		int number = 0;
		try {
			this.DatabaseConnect();
			
			// get numbers
			query = session.createQuery("select count(*) from Queue where(restid="+restid 
					+" and state=0)");
			number  = ((Long) query.iterate().next()).intValue();
			
			this.DatabaseClose();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return number;
	}
	
	
	// Get queue numbers from a restaurant 
	public Map<Restaurant, Integer> GetQueueNumbersFromList(List<Restaurant> restList) {
		
		//returns a map
		// key - restid
		Map rest_queuenum = new HashMap();
		try {
			this.DatabaseConnect();
			
			Iterator it = restList.iterator();
			Restaurant restaurant = null;
			while(it.hasNext()) {
				restaurant = (Restaurant)it.next();
				query = session.createQuery("select count(*) from Queue where(restid="+restaurant.getRestid() 
						+" and state=0)");
				int number = ((Long) query.iterate().next()).intValue();
				rest_queuenum.put(restaurant, new Integer(number));
			}
			this.DatabaseClose();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rest_queuenum;
	}

}
