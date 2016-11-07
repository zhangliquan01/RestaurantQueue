package Transaction;

import java.util.ArrayList;
import java.util.List;

import Database.DBConnection;
import Database.DBDishOperation;
import Database.DBQueueOperation;
import Database.DBRestaurantOperation;
import Database.Entity.Dish;
import Database.Entity.Restaurant;

public class RestaurantOperation {

	public static int createRestaurant(Restaurant restaurant) {
		
		// create a restaurant
		// returns restid.
		// if returns 0, failed.
		int result = 0;
		try {
			
			DBRestaurantOperation restaurantOperation = new DBRestaurantOperation();
			result = restaurantOperation.CreateRestaurant(restaurant);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static int createDish(Dish dish) {
		
		// create dish
		// returns dishid
		// if returns 0, failed.
		int dishid = 0;
		try {
			
			DBDishOperation dishOperation = new DBDishOperation();
			dishid = dishOperation.CreateDish(dish);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dishid;
	}

	
	public static int getCurrentQueueNumber(int restid) {
		
		// get current queue number
		int number = 0;
		try {
			DBQueueOperation queueOperation = new DBQueueOperation();
			number = queueOperation.GetCurrentQueueNumber(restid);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return number;
	}
	
	
	public static List<Dish> getAllDishes(int restid) {
		
		// get all dishes
		List<Dish> dishList = new ArrayList<Dish>();
		try {
			DBConnection dbConnection = new DBConnection();
			String sql = "from Dish where restid="+restid;
			dishList = dbConnection.ExecuteSelectQuery(sql);
			if (dishList==null)
				return new ArrayList<Dish>();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dishList;
	}
	
	/*
	public static int loginCheck(int restid, String password) {
		
		// login check
		// returns restid
		// if returns 0,login failed
		// if returns -1, system errors
		int userid = -1;
		try {
			
			DBRestaurantOperation restaurantOperation = new DBRestaurantOperation();
			Restaurant restaurant = restaurantOperation.GetRestaurant(restid);
			if (restaurant.get)
			User user = restaurantOperation.GetRestaurant(username);
			if (user.getName().equals(username) && user.getPassword().equals(password)) {
				
				// name & password match
				userid = user.getUserid();
				
			} else {
				userid = 0;
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return userid;
	}*/
}
