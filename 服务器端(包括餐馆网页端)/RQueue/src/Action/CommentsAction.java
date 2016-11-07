package Action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import Transaction.CommentsOperation;

import Database.Entity.Comments;

public class CommentsAction {
	public void createComments() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		BaseAction baseAction = new BaseAction(request, response);
		
		JSONObject comObject = baseAction.getData();
		System.out.println(comObject);
		
		Comments comments = new Comments();
		comments.setRestid(comObject.getInt("restid"));
		comments.setDepiction(comObject.getString("depiction"));
		comments.setUserid(comObject.getInt("userid"));
		//comments.setUserid(comObject.getInt("recordid"));
		
		int commentid = CommentsOperation.createComments(comments);
		baseAction.addReturnData("commentid", commentid);
		baseAction.returnData();
		
	}
	public void getAllCommentsRecords() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		BaseAction baseAction = new BaseAction(request, response);
		JSONObject jsonObject = baseAction.getData();
		int userid = jsonObject.getInt("userid");
		
		List<Comments> comList = CommentsOperation.getAllCommentsRecords(userid);
		JSONArray array = JSONArray.fromObject(comList);
		baseAction.addReturnData("allComments", array);
		baseAction.returnData();
	}
	
	public void getRestCommentsRecords(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		BaseAction baseAction = new BaseAction(request, response);
		
		JSONObject comObject = baseAction.getData();
		int restid = comObject.getInt("restid");
		
		List<Comments> comList = CommentsOperation.getCommentsWithRestid(restid);
		JSONArray array = new JSONArray().fromObject(comList);
		baseAction.addReturnData("restComments", array);
		System.out.println(array);
		baseAction.returnData();
	}
}
