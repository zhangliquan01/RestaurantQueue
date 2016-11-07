package Action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.action.internal.QueuedOperationCollectionAction;

import Transaction.QueueOperation;

import Database.Entity.Queue;

public class QueueAction {
	public void addQueue(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		
		BaseAction baseAction = new BaseAction(request,response);
		JSONObject jsonObject = baseAction.getData();
		Queue queue = new Queue();
		queue.setRestid(jsonObject.getInt("restid"));
		queue.setUserid(jsonObject.getInt("userid"));
		queue.setTime(new Date());
		
		int result = QueueOperation.createQueue(queue);
		baseAction.addReturnData("result", result);
		baseAction.returnData();
	}
	public void getAllQueueRecords(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		
		BaseAction baseAction = new BaseAction(request,response);
		JSONObject jsonObject = baseAction.getData();
		int userid = jsonObject.getInt("userid");
		List<Queue> allQList = QueueOperation.getAllQueueRecords(userid);
		
		JSONArray jsonArray = new JSONArray();
		for(int i=0;i<allQList.size();i++){
			Queue queue = allQList.get(i);
			jsonArray.add(queue);
		}
		baseAction.addReturnData("allQueueList", jsonArray);
		baseAction.returnData();
	}
	public void getCurrentQueueRecords(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		
		BaseAction baseAction = new BaseAction(request,response);
		JSONObject jsonObject = baseAction.getData();
		int userid = jsonObject.getInt("userid");
		
		List<Queue> allQList = QueueOperation.getCurrentQueueRecords(1);
		
		JSONArray jsonArray = new JSONArray();
		for(int i=0;i<allQList.size();i++){
			Queue queue = allQList.get(i);
			jsonArray.add(queue);
		}
		baseAction.addReturnData("currentQueueList", jsonArray);
		baseAction.returnData();
	}
	public void getCurrentQueueRecordsWithRestid(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		
		BaseAction baseAction = new BaseAction(request,response);
		JSONObject jsonObject = baseAction.getData();
		int restid = jsonObject.getInt("restid");
		
		List<Queue> allQList = QueueOperation.getCurrentQueueRecordsWithRestid(restid);
		
		
		baseAction.addReturnData("queuenum", allQList.size());
		baseAction.returnData();
	}
}
