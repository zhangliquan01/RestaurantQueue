package Database;

import Database.Entity.Dish;

public class DBDishOperation extends DBConnection {

	// Create dish method
	public int CreateDish(Dish dish) {
		
		// returns dishid
		// if returns 0, failed.
		int dishid=0;
		try {
			this.DatabaseConnect();
			
			// Execute query
			// save record
			session.save(dish);
			// get dishid
			query = session.createQuery("select count(*) from Dish");
			dishid  = ((Long) query.iterate().next()).intValue();
			
			this.DatabaseClose();
			return dishid;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return dishid;
		}
	}
}
