package Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import Transaction.RestaurantOperation;
import Transaction.UserOperation;

import Database.Entity.Dish;
import Database.Entity.Restaurant;

public class RestaurantAction {
	public void addRestaurant(){
		
	}
	public void getRestaurantsNearbyDistanceLeast(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		
		BaseAction baseAction = new BaseAction(request,response);
		JSONObject object = baseAction.getData();
		float longth =Float.parseFloat(object.get("longth").toString());
		float lati =Float.parseFloat(object.get("lati").toString());
		int length = object.getInt("length");
		System.out.println(longth);
		
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		restaurants = UserOperation.getRestaurantsNearbyDistanceLeast(longth,lati,length,"中餐馆");
		JSONArray jsonArray = JSONArray.fromObject(restaurants);
		System.out.println(jsonArray);
		
		baseAction.addReturnData("Rarray", jsonArray);
		baseAction.returnData();
	}
	public void getRestaurantsNearbyWaitingLeast(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		JSONArray array = new JSONArray();
		BaseAction baseAction = new BaseAction(request,response);
//		JSONObject object = baseAction.getData();
//		float longth =Float.parseFloat(object.get("longth").toString());
//		float lati =Float.parseFloat(object.get("lati").toString());
//		int length = object.getInt("length");
		
		Map<Restaurant, Integer> map = UserOperation.getRestaurantsNearbyWaitingLeast(121,31, 2000,"中餐馆");
		for (Entry<Restaurant, Integer> entry : map.entrySet()) {
			
			JSONObject object1 = JSONObject.fromObject(entry.getKey());
			object1.put("number", entry.getValue());
			array.add(object1);
			
		}
		
		baseAction.addReturnData("Rarray", array);
		baseAction.returnData();
	}
	
	public void getAllDishes(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");

		BaseAction baseAction = new BaseAction(request,response);
		JSONObject object = baseAction.getData();
		int restid = object.getInt("restid");
		
		List<Dish> dishList = RestaurantOperation.getAllDishes(restid);
		
		JSONArray jsonArray = JSONArray.fromObject(dishList);
		
		baseAction.addReturnData("dishlist", jsonArray);
		baseAction.returnData();
	}
}
