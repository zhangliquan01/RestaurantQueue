package Database;


public class DBImageOperation extends DBConnection {
	
	public String InsertUserImageUrl(int id, int type, String imgPath) {
		
		// insert a image for User, Restaurant and Dish
		// type:0-user 1-restaurant 2-dish
		String resultString = "failed";
		try {
			
			String sql = "";
			// select type
			switch (type) {
			case 0:sql="update User set imageurl='"+imgPath+"' where userid='"+id+"'";break;
			case 1:sql="update Restaurant set imageurl='"+imgPath+"' where restid='"+id+"'";break;
			case 2:sql="update Dish set imageurl='"+imgPath+"' where dishid='"+id+"'";break;
			default:return "failed";
			}
			System.out.println(sql);
			// execute update
			int result = this.ExecuteUpdateQuery(sql);
			if(result>1)
				resultString = "success";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultString;
	}

}
