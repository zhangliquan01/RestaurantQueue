package Database.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Queue {
	private int queueid;
	private int userid;
	private int restid;
	private Date time;
	private int state;
	
	
	@Id
	public int getQueueid() {
		return queueid;
	}
	public void setQueueid(int queueid) {
		this.queueid = queueid;
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getState() {
		return state;
	}
}
