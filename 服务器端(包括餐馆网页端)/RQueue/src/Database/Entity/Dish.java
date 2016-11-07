package Database.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Dish {
	private int dishid;
	private int restid;
	private String name;
	private String groups;
	private float price;
	private String depiction;
	private String imageurl;
	
	
	@Id
	public int getDishid() {
		return dishid;
	}
	public void setDishid(int dishid) {
		this.dishid = dishid;
	}
	public int getRestid() {
		return restid;
	}
	public void setRestid(int restid) {
		this.restid = restid;
	}
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
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getDepiction() {
		return depiction;
	}
	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
}
