package Website;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import Database.Entity.Queue;
import Transaction.QueueOperation;
import Transaction.RestaurantOperation;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

public class QueueWebAction extends ActionSupport {
	

	public String getAllQueueList() {
		
		// Get all queue list
		// if returns SUCCESS, all executed successfully.
		// if returns LOGIN_FAILED, please login.
		// if returns FAILED, an error occurred.
		// "error" could be:
		// -2-system error.
		try {
			String restaccountid = ActionContext.getContext().getSession().get("userid").toString();
			String restidString = ActionContext.getContext().getSession().get("restid").toString();
			if (restaccountid==null || restaccountid.equals(""))
				return "LOGIN_FAILED";
			if (restidString==null || restidString.equals(""))
				return "LOGIN_FAILED";
			int accountid = Integer.valueOf(restaccountid).intValue();
			int restid = Integer.valueOf(restidString).intValue();
			
			// Get list
			// List should be separated by ',' and the first char should be ','
			List queueList = QueueOperation.getAllQueueRecordsWithRestid(restid);
			if (queueList==null) {
				ActionContext.getContext().getSession().put("queuelist", ",");
				return "SUCCESS";
			}
			String queuelistString = "";
			Queue queue = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
			Iterator it = queueList.iterator();
			if (!it.hasNext()) {
				queuelistString = ",";
			}
			while(it.hasNext()) {
				
				queue = (Queue)it.next();
				queuelistString += ","+queue.getQueueid()+","+dateFormat.format(queue.getTime())+","+queue.getState();
			}
			ActionContext.getContext().getSession().put("queuelist", queuelistString);
			
			return "SUCCESS";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ActionContext.getContext().getSession().put("error", "-2");
			return "FAILED";
		}
	}
	
	
	
	public String getCurrentQueueList() {
		
		// Get current queue list
		// if returns SUCCESS, all executed successfully.
		// if returns LOGIN_FAILED, please login.
		// if returns FAILED, an error occurred.
		// "error" could be:
		// -2-system error.
		try {
			String restaccountid = ActionContext.getContext().getSession().get("userid").toString();
			String restidString = ActionContext.getContext().getSession().get("restid").toString();
			if (restaccountid==null || restaccountid.equals(""))
				return "LOGIN_FAILED";
			if (restidString==null || restidString.equals(""))
				return "LOGIN_FAILED";
			int accountid = Integer.valueOf(restaccountid).intValue();
			int restid = Integer.valueOf(restidString).intValue();
			
			// Get list
			// List should be separated by ',' and the first char should be ','
			List queueList = QueueOperation.getCurrentQueueRecordsWithRestid(restid);
			if (queueList==null) {
				ActionContext.getContext().getSession().put("queuelist", ",");
				return "SUCCESS";
			}
			String queuelistString = "";
			Queue queue = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
			Iterator it = queueList.iterator();
			if (!it.hasNext()) {
				queuelistString = ",";
			}
			while(it.hasNext()) {
				
				queue = (Queue)it.next();
				queuelistString += ","+queue.getQueueid()+","+dateFormat.format(queue.getTime());
			}
			ActionContext.getContext().getSession().put("queuelist", queuelistString);
			
			return "SUCCESS";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ActionContext.getContext().getSession().put("error", "-2");
			return "FAILED";
		}
	}
	
	
	public String CallForANumber() {
		
		// Call for a new number
		// if returns LOAD_SUCCESS, successfully loaded the number
		// if returns SUCCESS, successfully called a nuw number
		// if returns LOGIN_FAILED, please login.
		// if returns FAILED, a system error occurred.
		try {
			String restaccountid = ActionContext.getContext().getSession().get("userid").toString();
			String restidString = ActionContext.getContext().getSession().get("restid").toString();
			if (restaccountid==null || restaccountid.equals(""))
				return "LOGIN_FAILED";
			if (restidString==null || restidString.equals(""))
				return "LOGIN_FAILED";
			int restid = Integer.valueOf(restidString).intValue();
			
			// All queue number
			int queuenumber = RestaurantOperation.getCurrentQueueNumber(restid);
			ActionContext.getContext().getSession().put("queuenumber", queuenumber);
			// check 
			Object obj= ActionContext.getContext().getSession().get("callnewnumber");
			if (obj==null) {
				System.out.println("asdf");
				// Load the page the first time
				int queueid = QueueOperation.getNewestQueueid(restid);
				ActionContext.getContext().getSession().put("NEWESTID", queueid);
				ActionContext.getContext().getSession().put("errorcode", "-1");
				return "LOAD_SUCCESS";
			}
			String callfornumberString = obj.toString();
			if (callfornumberString==null || callfornumberString.equals("")) {
				// Load the page the first time
				int queueid = QueueOperation.getNewestQueueid(restid);
				ActionContext.getContext().getSession().put("NEWESTID", queueid);
				return "LOAD_SUCCESS";
			} else {
				// Maybe calling a new number
				Object newestnumobj = ActionContext.getContext().getSession().get("NEWESTID");
				if (newestnumobj==null || newestnumobj.equals("")) {
					int queueid = QueueOperation.getNewestQueueid(restid);
					ActionContext.getContext().getSession().put("NEWESTID", queueid);
					return "LOAD_SUCCESS";
				}
				String newestnumString = newestnumobj.toString();
				
				// calling a new number
				int newestnum = Integer.valueOf(newestnumString).intValue();
				int queueid = QueueOperation.getNewestQueueid(restid);
				if (newestnum==queueid) {
					// Set its state as -1 - failed
					int result = QueueOperation.changeQueueState(queueid, -1);
					queuenumber = RestaurantOperation.getCurrentQueueNumber(restid);
					ActionContext.getContext().getSession().put("queuenumber", queuenumber);
					if (result==1) {
						queueid = QueueOperation.getNewestQueueid(restid);
						ActionContext.getContext().getSession().put("NEWESTID", queueid);
						ActionContext.getContext().getSession().put("callnewnumber", "");
						return "SUCCESS";
					} else {
						ActionContext.getContext().getSession().put("error", "-2");
						return "FAILED";
					}
					
				} else {
					// do nothing
					queueid = QueueOperation.getNewestQueueid(restid);
					ActionContext.getContext().getSession().put("NEWESTID", queueid);
					ActionContext.getContext().getSession().put("callnewnumber", "");
				}
				return "SUCCESS";
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ActionContext.getContext().getSession().put("error", "-2");
			return "FAILED";
		}
	}

}
