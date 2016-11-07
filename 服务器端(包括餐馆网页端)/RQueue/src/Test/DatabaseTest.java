package Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import Database.*;
import Database.Entity.*;
import Transaction.ImageOperation;

public class DatabaseTest {
	
	// test basic create method for db - test success
	public void database_test_1() {
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
		String datetime = dateFormat.format( date ); 
		System.out.println(datetime);
		
		// user
		User user = new User();
		user.setName("myname");
		user.setPassword("mypassword");
		user.setGender("男");
		user.setPhone("234543432");
		DBUserOperation userOperation = new DBUserOperation();
		int userid = userOperation.CreateUser(user);
		System.out.println("user:"+userid);
		
		// restaurant
		Restaurant restaurant = new Restaurant();
		restaurant.setName("心如意餐厅");
		restaurant.setType("中餐馆");
		restaurant.setAddress("曹安公路3454号");
		restaurant.setLongitude(123.33f);
		restaurant.setLatitude(34.45f);
		restaurant.setPhone("4123135");
		restaurant.setDepiction("该餐馆是一个很不错的餐馆");
		DBRestaurantOperation restaurantOperation = new DBRestaurantOperation();
		int restid = restaurantOperation.CreateRestaurant(restaurant);
		System.out.println("restid:"+restid);
		
		// dish
		Dish dish = new Dish();
		dish.setName("金师傅大馄饨");
		dish.setGroups("传统中餐");
		dish.setRestid(restid);
		dish.setDepiction("非常好吃");
		DBDishOperation dishOperation = new DBDishOperation();
		int dishid = dishOperation.CreateDish(dish);
		System.out.println("dishid:"+dishid);
		
		// record
		Record record = new Record();
		record.setUserid(userid);
		record.setRestid(restid);
		record.setTime(date);
		record.setCost(231.4f);
		DBRecordOperation recordOperation = new DBRecordOperation();
		int recordid = recordOperation.CreateRecord(record);
		System.out.println("recordid:"+recordid);
		
		// comments
		Comments comments = new Comments();
		comments.setRestid(restid);
		comments.setUserid(userid);
		comments.setDepiction("感觉还可以");
		DBCommentsOperation commentsOperation = new DBCommentsOperation();
		int commentsid = commentsOperation.CreateComment(comments);
		System.out.println("commentsid:"+commentsid);
		
		// like
		Like like = new Like();
		like.setRestid(restid);
		like.setUserid(userid);
		DBLikeOperation likeOperation = new DBLikeOperation();
		int likeint = likeOperation.ExecuteSaveQuery(like);
		System.out.println("like result: "+likeint);
		
		// booking
		Booking booking = new Booking();
		booking.setRestid(restid);
		booking.setUserid(userid);
		booking.setBooktime(date);
		booking.setDinning_time(date);
		DBBookingOperation bookingOperation = new DBBookingOperation();
		int bookid = bookingOperation.CreateBooking(booking);
		System.out.println("bookid:"+bookid);
		
		// queue
		Queue queue = new Queue();
		queue.setRestid(restid);
		queue.setUserid(userid);
		queue.setTime(date);
		DBQueueOperation queueOperation = new DBQueueOperation();
		int queueid = queueOperation.CreateQueue(queue);
		System.out.println("queueid:"+queueid);
	}

	// test image url insertion for db - test success
	public void database_test_2() {
		
		try {
			
			// public modifiers
			int id = 3;
			int type = 0;
			String imageType = "jpeg";
			String imgPath = "";
			DBImageOperation imageOperation = new DBImageOperation();
			String resultString = "";
			
			// user
			imgPath = ImageOperation.getImagePath(id, type, imageType);
			System.out.println(imgPath);
			resultString = imageOperation.InsertUserImageUrl(id, type, imgPath);
			System.out.println(resultString);
			
			// restaurant
			type = 1;
			imgPath = ImageOperation.getImagePath(id, type, imageType);
			System.out.println(imgPath);
			resultString = imageOperation.InsertUserImageUrl(id, type, imgPath);
			System.out.println(resultString);
			
			// dish
			type = 2;
			imgPath = ImageOperation.getImagePath(id, type, imageType);
			System.out.println(imgPath);
			resultString = imageOperation.InsertUserImageUrl(id, type, imgPath);
			System.out.println(resultString);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	//
}
