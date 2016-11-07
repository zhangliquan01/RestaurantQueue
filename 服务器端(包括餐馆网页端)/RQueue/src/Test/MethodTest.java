package Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Database.DBQueueOperation;
import Database.Entity.*;
import Transaction.CommentsOperation;
import Transaction.ImageOperation;
import Transaction.QueueOperation;
import Transaction.RestaurantOperation;
import Transaction.UserOperation;

public class MethodTest {

	// test basic methods for users(create, login) and images(create) - test success
	public void method_test_1() {
		
		// user creation
		User user = new User();
		user.setName("myname");
		user.setPassword("mypassword");
		user.setGender("男");
		user.setPhone("234543432");
		int userid = UserOperation.createUser(user);
		System.out.println("userid:"+userid);
		
		// user login
		String userphone = "132311";
		String password = "mypassword";
		int usernewid = UserOperation.loginCheck(userphone, password).getUserid();
		System.out.println("usernewid:"+usernewid);
		
		// imageurl insertion
		String imgPath = ImageOperation.getImagePath(usernewid, 0, "png");
		System.out.println(imgPath);
		String resultString = ImageOperation.insertImageUrl(usernewid, 0, imgPath);
		System.out.println(resultString);
	
	}

	// user-change password restaurant&dish- create - test success
	public void method_test_2() {
		
		try {
			
			// user-change password
			int userid = 2;
			int result = UserOperation.changePassword(userid, "thisisapassword");
			System.out.println("user:"+result);
			
			// create restaurant & dish
			// restaurant
			Restaurant restaurant = new Restaurant();
			restaurant.setName("如意餐厅");
			restaurant.setType("中餐馆");
			restaurant.setAddress("曹安公路3454号");
			restaurant.setLongitude(123.33f);
			restaurant.setLatitude(34.45f);
			restaurant.setPhone("4123135");
			restaurant.setDepiction("该餐馆是一个很不错的餐馆");
			result = RestaurantOperation.createRestaurant(restaurant);
			System.out.println("restaurant:"+result);
			
			// dish
			Dish dish = new Dish();
			dish.setName("三鲜水饺");
			dish.setGroups("传统中餐");
			dish.setRestid(2);
			dish.setDepiction("非常好吃");
			result = RestaurantOperation.createDish(dish);
			System.out.println("dish:"+result);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// user-like insert or delete - test success
	public void method_test_3() {
		
		try {
			// Like insert
			Like like = new Like();
			like.setUserid(2);
			like.setRestid(4);
			int result = UserOperation.addLike(like);
			System.out.println("like insert:"+result);
			
			// delete
			like.setUserid(2);
			like.setRestid(3);
			result = UserOperation.deletelike(like);
			System.out.println("like delete:"+result);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// queue-create, all records, current records － test success
	public void method_test_4() {
		
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
			String datetime = dateFormat.format( date ); 
			System.out.println(datetime);
			
			// Create a queue
			Queue queue = new Queue();
			queue.setRestid(1);
			queue.setUserid(6);
			queue.setTime(date);
			int result = QueueOperation.createQueue(queue);
			System.out.println("queue create:"+result);
			
			// get all records
			List list = QueueOperation.getAllQueueRecords(1);
			Iterator it = list.iterator();

			 while (it.hasNext()) {
				 queue = (Queue)it.next();
				 datetime = dateFormat.format(queue.getTime()); 
				 System.out.println("Queue all:"+queue.getUserid()+" "+queue.getRestid()+" "+datetime);
			 }
			 
			 // get current records
			 list = QueueOperation.getCurrentQueueRecords(1);
			 it = list.iterator();
			 while (it.hasNext()) {
				 queue = (Queue)it.next();
				 datetime = dateFormat.format(queue.getTime()); 
				 System.out.println("Queue current:"+queue.getUserid()+" "+queue.getRestid()+" "+datetime);
			 }
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// restaurant nearby test - test success
	public void method_test_5() {
		try {
			List list = new ArrayList<Restaurant>();
			list = UserOperation.getRestaurantsNearbyDistanceLeast(50.0f,50.0f, 500,"本帮");
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Restaurant restaurant = (Restaurant)it.next();
				System.out.println(restaurant.getName());
				System.out.println(restaurant.getAddress());
				System.out.println(restaurant.getType());
				System.out.println(restaurant.getPhone());
				System.out.println(restaurant.getDepiction());
			}
			
			System.out.println("################################");
			Map<Restaurant, Integer> map = UserOperation.getRestaurantsNearbyWaitingLeast(50.0f,50.0f, 500,"本帮");
			for (Entry<Restaurant, Integer> entry : map.entrySet()) {
				Restaurant restaurant = (Restaurant)entry.getKey();
				System.out.println(restaurant.getName());
				System.out.println(restaurant.getAddress());
				System.out.println(restaurant.getType());
				System.out.println(restaurant.getPhone());
				System.out.println(restaurant.getDepiction());
				System.out.println(entry.getValue());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// comments test
	public void method_test_6() {
		
		try {
			// get all comments
			List<Comments> list = CommentsOperation.getAllCommentsRecords(1);
			Iterator<Comments> it = list.iterator();
			while(it.hasNext()) {
				Comments comments = (Comments)it.next();
				System.out.println(comments.getCommentid());
				System.out.println(comments.getRestid());
				System.out.println(comments.getUserid());
				System.out.println(comments.getDepiction());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
