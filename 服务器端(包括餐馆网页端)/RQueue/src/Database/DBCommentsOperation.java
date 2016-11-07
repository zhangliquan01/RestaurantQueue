package Database;

import Database.Entity.Comments;

public class DBCommentsOperation extends DBConnection {

	// Create comments method
	public int CreateComment(Comments comments) {
		// returns commentsid
		int commentsid=0;
		try {
			this.DatabaseConnect();
			
			// Execute query
			// save comments
			session.save(comments);
			// get commentsid
			query = session.createQuery("select count(*) from Comments");
			commentsid  = ((Long) query.iterate().next()).intValue();
			
			this.DatabaseClose();
			return commentsid;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return commentsid;
		}
	}
}
