package Transaction;

import java.util.ArrayList;
import java.util.List;

import Database.DBCommentsOperation;
import Database.DBConnection;
import Database.Entity.Comments;

public class CommentsOperation {

	public static int createComments(Comments comment) {
		
		//create comments
		//if returns 0, failed.
		int commentsid = 0;
		try {
			DBCommentsOperation commentsOperation = new DBCommentsOperation();
			commentsid = commentsOperation.CreateComment(comment);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return commentsid;
	}
	
	public static List<Comments> getAllCommentsRecords(int userid) {
		
		// get all comments records
		List<Comments> commentsList = new ArrayList<Comments>();
		try {
			DBConnection dbConnection = new DBConnection();
			String sql = "from Comments where userid="+userid;
			commentsList = dbConnection.ExecuteSelectQuery(sql);
			if (commentsList==null)
				return new ArrayList<Comments>();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return commentsList;
	}
	
	public static List<Comments> getCommentsWithRestid(int restid) {
		
		// get all comments records with restid
		List<Comments> commentsList = new ArrayList<Comments>();
		try {
			DBConnection dbConnection = new DBConnection();
			String sql = "from Comments where restid="+restid;
			commentsList = dbConnection.ExecuteSelectQuery(sql);
			if (commentsList==null)
				return new ArrayList<Comments>();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return commentsList;
	}
}
