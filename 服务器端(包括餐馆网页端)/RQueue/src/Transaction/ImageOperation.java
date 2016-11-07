package Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;

import Database.DBImageOperation;

public class ImageOperation {
	
	public static String insertImageUrl(int id, int type, String imgPath) {
		
		// insert a image for User, Restaurant and Dish
		// type:0-user 1-restaurant 2-dish
		String resultString = "failed";
		try {
			
			DBImageOperation imageOperation = new DBImageOperation();
			resultString = imageOperation.InsertUserImageUrl(id, type, imgPath);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultString;
	}
	
	
	public static String getImagePath(int id, int type, String imageType) {
		
		// generate image path
		// type:0-user 1-restaurant 2-dish
		// imageType:jpg, bmp, png, etc.
		String resultString = "failed";
		try {

			// select type
			switch(type) {
			case 0:resultString = "/users/Steven/Applications/MyEclipse/image/user/";break;
			case 1:resultString = "/users/Steven/Applications/MyEclipse/image/restaurant/";break;
			case 2:resultString = "/users/Steven/Applications/MyEclipse/image/dish/";break;
			default:break;
			}
			// name
			String imageName = "";
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");//可以方便地修改日期格式
			String datetime = dateFormat.format( date ); 
			imageName = id+"_"+ datetime+"."+imageType;
			resultString = resultString+imageName;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultString;
	}

}
