package Database.Entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Booking {
	private int bookid;
	private int userid;
	private int restid;
	private Date booktime;
	private Date dinning_time;
	
	
	@Id
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getRestid() {
		return restid;
	}
	public void setRestid(int restid) {
		this.restid = restid;
	}
	public Date getBooktime() {
		return booktime;
	}
	public void setBooktime(Date booktime) {
		this.booktime = booktime;
	}
	public Date getDinning_time() {
		return dinning_time;
	}
	public void setDinning_time(Date dinning_time) {
		this.dinning_time = dinning_time;
	}
}
