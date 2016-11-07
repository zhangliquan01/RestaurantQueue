package Database;

import Database.Entity.Booking;

public class DBBookingOperation extends DBConnection {

	// Create booking method
		public int CreateBooking(Booking booking) {
			// returns bookid
			int bookid=0;
			try {
				this.DatabaseConnect();
				
				// Execute query
				// save user
				session.save(booking);
				// get bookid
				query = session.createQuery("select count(*) from Booking");
				bookid  = ((Long) query.iterate().next()).intValue();
				
				this.DatabaseClose();
				return bookid;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return bookid;
			}
		}
}
