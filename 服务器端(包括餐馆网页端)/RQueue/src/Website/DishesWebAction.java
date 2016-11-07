package Website;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import Database.Entity.Dish;
import Transaction.RestaurantOperation;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DishesWebAction extends ActionSupport {
	private String name;
	private String groups;
	private String depiction;
	private float price;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getDepiction() {
		return depiction;
	}

	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String createNewDish() {
		
		// create a new dish
		// if returns SUCCESS, create success
		// if returns LOGIN_FAILED, please login.
		// if returns FAILED, an error occured.
		// "error" could be:
		// -1-create failed.
		// -2-system error.
		// -3-cannot be empty.
		try {
			String restaccountid = ActionContext.getContext().getSession().get("userid").toString();
			String restidString = ActionContext.getContext().getSession().get("restid").toString();
			if (restaccountid==null || restaccountid.equals(""))
				return "LOGIN_FAILED";
			if (restidString==null || restidString.equals(""))
				return "LOGIN_FAILED";
			int restid = Integer.valueOf(restidString).intValue();
			
			// Get form
			String nameString = this.getName();
			String groupsString = this.getGroups();
			String depictionString = this.getDepiction();
			float price = this.getPrice();
			if (nameString==null || nameString.equals("") ||
				groupsString==null || groupsString.equals("") ||
				depictionString==null || depictionString.equals("")) {
				
				ActionContext.getContext().getSession().put("error", "-3");
				return "FAILED";
			}
			
			// Create Dish object
			Dish dish = new Dish();
			dish.setRestid(restid);
			dish.setName(nameString);
			dish.setGroups(groupsString);
			dish.setDepiction(depictionString);
			dish.setPrice(price);
			
			int dbResult = RestaurantOperation.createDish(dish);
			if (dbResult>0) {
				ActionContext.getContext().getSession().put("error", "1");
				return "SUCCESS";
			} else {
				ActionContext.getContext().getSession().put("error", "-1");
				return "FAILED";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ActionContext.getContext().getSession().put("error", "-2");
			return "FAILED";
		}
	}
	
	public String getAllDishList() {
		
		// Get all dishes
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
			List dishList = RestaurantOperation.getAllDishes(restid);
			if (dishList==null) {
				ActionContext.getContext().getSession().put("dishlist", ",");
				return "SUCCESS";
			}
			String dishlistString = "";
			Dish dish = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
			Iterator it = dishList.iterator();
			if (!it.hasNext()) {
				dishlistString = ",";
			}
			while(it.hasNext()) {
				
				dish = (Dish)it.next();
				dishlistString += ","+dish.getDishid()+","+dish.getName()+","+dish.getDepiction()+","+dish.getGroups()+","+dish.getPrice();
			}
			ActionContext.getContext().getSession().put("dishlist", dishlistString);
			
			return "SUCCESS";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ActionContext.getContext().getSession().put("error", "-2");
			return "FAILED";
		}
	}

}
