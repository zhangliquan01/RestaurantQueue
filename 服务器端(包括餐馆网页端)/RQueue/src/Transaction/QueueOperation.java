package Transaction;

import java.util.ArrayList;
import java.util.List;
import Database.DBConnection;
import Database.DBQueueOperation;
import Database.Entity.Queue;

public class QueueOperation {
	
	public static int createQueue(Queue queue) {
		
		// create queue
		// if returns 0, failed
		int result = 0;
		try {
			DBQueueOperation queueOperation = new DBQueueOperation();
			result = queueOperation.CreateQueue(queue);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public static List<Queue> getAllQueueRecords(int userid) {
		
		// get all queue records
		List<Queue> list = new ArrayList<Queue>(); 
		try {
			
			DBQueueOperation queueOperation = new DBQueueOperation();
			list = queueOperation.ExecuteSelectQuery("from Queue where userid="+userid);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Queue> getCurrentQueueRecords(int userid) {
		
		// get current queue records
		List<Queue> list = new ArrayList<Queue>(); 
		try {
			
			DBQueueOperation queueOperation = new DBQueueOperation();
			list = queueOperation.ExecuteSelectQuery("from Queue where and userid="+userid + " and state=0");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Queue> getAllQueueRecordsWithRestid(int restid) {
		
		// get all queue records with restid
		List<Queue> list = new ArrayList<Queue>(); 
		try {
			
			DBQueueOperation queueOperation = new DBQueueOperation();
			list = queueOperation.ExecuteSelectQuery("from Queue where restid="+restid);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Queue> getCurrentQueueRecordsWithRestid(int restid) {
		
		// get current queue records with restid
		List<Queue> list = new ArrayList<Queue>(); 
		try {
			
			DBQueueOperation queueOperation = new DBQueueOperation();
			list = queueOperation.ExecuteSelectQuery("from Queue where restid="+restid + " and state=0");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public static int getNewestQueueid(int restid) {
		
		// Get newest queueid from a restid
		// returns queueid
		// if returns 0, no user
		// if returns -2, a system error.
		int queueid = 0;
		try {
			DBConnection dbConnection = new DBConnection();
			String sql = "select max(queueid) from Queue where restid="+restid+" and state=0";
			queueid = dbConnection.ExecuteQueryToGetNumber(sql);
			if (queueid==-1)
				return -2;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			queueid = -2;
		}
		return queueid;
	}
	
	public static int changeQueueState(int queueid, int state) {
		
		// change queue state
		// if succeeded, returns 1. If failed, returns 0.
		// state can be changed as 1-success, 2-be overdue
		int result = 0;
		try {
			
			DBConnection dbConnection = new DBConnection();
			String sql = "update Queue set state="+state+" where queueid="+queueid;
			//result = dbConnection.ExecuteQuery(sql);
			result = dbConnection.ExecuteUpdateQuery(sql);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

}
