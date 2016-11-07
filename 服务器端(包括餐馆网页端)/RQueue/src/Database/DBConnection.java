package Database;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class DBConnection {
	protected SessionFactory sessionFactory;
	protected Session session;
	protected Query query;
	
	protected void DatabaseConnect() {
		try {
			// Create connection
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
			session = sessionFactory.openSession();
			session.getTransaction().begin();
			System.out.println("Database connected success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	protected void DatabaseClose() {
		try {
			// Close connection
			session.getTransaction().commit();
			session.close();
			System.out.println("Database closed success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public int ExecuteSaveQuery(Object object) {
		
		// notice: object must be specific types
		int result = 0;
		try {
			this.DatabaseConnect();
			session.save(object);
			this.DatabaseClose();
			result = 1;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public List ExecuteSelectQuery(String sql) {
		
		// execute select query only
		// returns list of tables
		List list = new ArrayList();
		try {
			this.DatabaseConnect();
			
			query = session.createQuery(sql);
			list = query.list();
			this.DatabaseClose();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public int ExecuteUpdateQuery(String sql) {
	
		// execute update sql/hql query without return parameters
		// only returns whether succeeded-1 or failed-0
		int result = 0;
		try {
			
			this.DatabaseConnect();
			
			query = session.createQuery(sql);
			query.executeUpdate();
			
			this.DatabaseClose();
			result = 1;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public int ExecuteDeleteQuery(Object object) {
		
		// notice: object must be specific types
		int result = 0;
		try {
			this.DatabaseConnect();
			session.delete(object);
			this.DatabaseClose();
			result = 1;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public int ExecuteQuery(String sql) {
		
		// notice: sql must be delete operation
		int result = 0;
		try {
			this.DatabaseConnect();
			query = session.createQuery(sql);
			this.DatabaseClose();
			result = 1;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public int ExecuteQueryToGetNumber(String sql) {
		
		// Notice: this method is used to get a number, like id or count.
		// Notice if returns -1, meaning there is an error occurred.
		int result = -1;
		try {
			this.DatabaseConnect();
			query = session.createQuery(sql);
			if (query.iterate().next()==null) {
				result = 0;
			} else {
				result = Integer.valueOf(query.iterate().next().toString()).intValue();
			}
			this.DatabaseClose();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
}
