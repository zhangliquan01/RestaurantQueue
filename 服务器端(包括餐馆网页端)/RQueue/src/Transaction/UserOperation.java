package Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.math.*;

import org.hibernate.annotations.ListIndexBase;

import Database.DBConnection;
import Database.DBLikeOperation;
import Database.DBQueueOperation;
import Database.DBRecordOperation;
import Database.DBUserOperation;
import Database.Entity.Comments;
import Database.Entity.Like;
import Database.Entity.Record;
import Database.Entity.Restaurant;
import Database.Entity.User;

public class UserOperation {
	
	public static int createUser(User user) {
		
		// create a user and returns its userid
		// if returns 0, meaning an error occured
		int userid = 0;
		try {
			
			DBUserOperation userOperation = new DBUserOperation();
			userid = userOperation.CreateUser(user);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return userid;
	}
	
	public static User loginCheck(String phone, String password) {
		
		// login check
		// returns user
		// if userid = 0,login failed
		// if userid = -1, system errors
		// if userid = -2,, no such user
		User user = new User();
		user.setUserid(0);
		try {
			
			DBUserOperation userOperation = new DBUserOperation();
			user = userOperation.GetUser(phone);
			if (user==null) {
				user = new User();
				user.setUserid(-2);
				return user;
			}
			if (!(user.getPhone().equals(phone) && user.getPassword().equals(password))) {
				
				// name & password do not match
				user.setUserid(0);
			}
			user.setPassword("");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			user = new User();
			user.setUserid(-1);
			
		}
		return user;
	}
	
	public static int changePassword(int userid, String password) {
		
		// change password
		// if returns 1, success; returns 0, failed.
		int result = 0;
		try {
			
			DBUserOperation operation = new DBUserOperation();
			result = operation.ExecuteUpdateQuery("update User set password='"+password.trim()+"' where userid="+userid);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public static int addLike(Like like) {
		
		// add a like
		// if returns 0, failed
		int result = 0;
		try {
			
			DBLikeOperation likeOperation = new DBLikeOperation();
			result = likeOperation.ExecuteSaveQuery(like);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public static int deletelike(Like like) {
		
		// delete a like 
		// if returns 0, failed
		int result = 0;
		try {
			DBLikeOperation likeOperation = new DBLikeOperation();
			result = likeOperation.ExecuteDeleteQuery(like);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public static List<Restaurant> getRestaurantsNearbyDistanceLeast(float longitude, float latitude, float range, String restType) {
		
		// Get nearby restaurants listed with lowest distance and returns a list
		// Notice: I use a rectangle instead of circle to modify search area
		// if any search parameter equals empty("" or 0), regarded as all.
		// longitude & latitude - user location
		// range - 1000m, 500m, 200m - if 0, set it default as 1000.
		// restType - restaurant type
		List<Restaurant> list = new ArrayList<Restaurant>();
		try {
			// Assemble sql string
			String sql = "from Restaurant";
			
			// range
			float earthRadius = 6370.856f;
			if (range==0) range = 1000;
			range = range/1000;
			float lat_incre = (float) (range*180/Math.PI/earthRadius);
			float long_incre = (float)(range*90*90/Math.PI/earthRadius/latitude);
			sql = sql + " where((longitude between "+(longitude-long_incre)+" and "+(longitude+long_incre)
					+") and (latitude between "+(latitude-lat_incre)+" and "+(latitude+lat_incre)+"))";
			
			List<Restaurant> rawList = new ArrayList<Restaurant>();
			DBConnection dbConnection = new DBConnection();
			rawList = dbConnection.ExecuteSelectQuery(sql);
			
			// restType
			Iterator<Restaurant> it = rawList.iterator();
			while (it.hasNext()) {
				Restaurant restaurant = (Restaurant)it.next();
				if (!restType.equals("")) {
					if (restaurant.getType().equals(restType)) {
						list.add(restaurant);
					}
				}
			}
			
			// Sort by distance
			DistanceCompare distanceCompare = new DistanceCompare(longitude, latitude);
			Collections.sort(list, distanceCompare.dlComparator);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	
	public static Map<Restaurant, Integer> getRestaurantsNearbyWaitingLeast(float longitude, float latitude, float range, String restType) {
		
		// Get nearby restaurants listed with lowest waiting numbers and returns a Map<Restaurant, Integer> (rest, queuenum)
		// Notice: I use a rectangle instead of circle to modify search area
		// if any search parameter equals empty("" or 0), regarded as all.
		// longitude & latitude - user location
		// range - 1000m, 500m, 200m - if 0, set it default as 1000.
		// restType - restaurant type		
		Map<Restaurant, Integer> map = new HashMap<Restaurant, Integer>();
		try {
			// Assemble sql string
			String sql = "from Restaurant";
			
			// range
			float earthRadius = 6370.856f;
			if (range==0) range = 1000;
			range = range/1000;
			float lat_incre = (float) (range*180/Math.PI/earthRadius);
			float long_incre = (float)(range*90*90/Math.PI/earthRadius/latitude);
			sql = sql + " where((longitude between "+(longitude-long_incre)+" and "+(longitude+long_incre)
					+") and (latitude between "+(latitude-lat_incre)+" and "+(latitude+lat_incre)+"))";
			
			List<Restaurant> rawList = new ArrayList<Restaurant>();
			DBConnection dbConnection = new DBConnection();
			rawList = dbConnection.ExecuteSelectQuery(sql);
			
			// restType
			List<Restaurant> list = new ArrayList<Restaurant>();
			Iterator<Restaurant> it = rawList.iterator();
			while (it.hasNext()) {
				Restaurant restaurant = (Restaurant)it.next();
				if (!restType.equals("")) {
					if (restaurant.getType().equals(restType)) {
						list.add(restaurant);
					}
				}
			}
			
			// Sort by waiting numbers
			DBQueueOperation queueOperation = new DBQueueOperation();
			map = queueOperation.GetQueueNumbersFromList(list);
			DistanceCompare distanceCompare = new DistanceCompare(longitude, latitude);
			List<Map.Entry<Restaurant, Integer>> mapList = new ArrayList<Map.Entry<Restaurant,Integer>>(map.entrySet());
			
			Collections.sort(mapList, distanceCompare.qwlComparator);
			map.clear();
			
			for (Map.Entry<Restaurant, Integer> entry:mapList) {
				map.put(entry.getKey(), entry.getValue());
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}
	
	
	public static int createRecord(Record record) {
		
		// Create a record
		// if returns 0, failed.
		int recordid = 0;
		try {
			DBRecordOperation recordOperation = new DBRecordOperation();
			recordid = recordOperation.CreateRecord(record);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return recordid;
	}
	
	public static List<Record> getAllRecords(int userid) {
		
		// get all records
		List<Record> recordList = new ArrayList<Record>();
		try {
			DBConnection dbConnection = new DBConnection();
			String sql = "from Record where userid="+userid;
			recordList = dbConnection.ExecuteSelectQuery(sql);
			if (recordList==null)
				return new ArrayList<Record>();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return recordList;
	}

//	public static String getUserName(int userid){
//		DBUserOperation userOperation = new DBUserOperation();
//		String name = userOperation.getUserName(userid);
//		return name;
//	}
}
