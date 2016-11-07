package Database;

import Database.Entity.Record;

public class DBRecordOperation extends DBConnection {
	
	// Create record method
	public int CreateRecord(Record record) {
		// returns recordid
		int recordid=0;
		try {
			this.DatabaseConnect();
			
			// Execute query
			// save record
			session.save(record);
			// get recordid
			query = session.createQuery("select count(*) from Record");
			recordid  = ((Long) query.iterate().next()).intValue();
			
			this.DatabaseClose();
			return recordid;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return recordid;
		}
	}

}
