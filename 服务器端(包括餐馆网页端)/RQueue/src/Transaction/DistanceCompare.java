package Transaction;

import java.util.Comparator;
import java.util.Map;
import java.math.*;
import java.util.Map.Entry;

import Database.Entity.Restaurant;




// This class is only used to be called by other transaction packet classes.
// Express layer classes should not call it.
public class DistanceCompare {
	
	private static float userLongitude;
	private static float userLatitude;
	
	public DistanceCompare(float longitude, float latitude) {
		this.userLongitude = longitude;
		this.userLatitude = latitude;
	}
	
	// Distance least sorting
	public final static Comparator<Restaurant> dlComparator = new Comparator<Restaurant>() {
		@Override
		public int compare(Restaurant rest1, Restaurant rest2) {
			int cr = 0;
			// cr=-1 <, cr=0 =, cr=1 >
			float rest1Distance = Math.abs((rest1.getLongitude()-userLongitude))+ Math.abs((rest1.getLatitude()-userLatitude));
			float rest2Distance = Math.abs((rest2.getLongitude()-userLongitude))+ Math.abs((rest2.getLatitude()-userLatitude));
			if (rest1Distance>rest2Distance) {
				// Restaurant with least distance should rank lower
				cr = 1;
			} else {
				cr = -1;
			}
			return cr;
		}
	};
	
	// Queue waiting least sorting
	public final static Comparator<Map.Entry<Restaurant, Integer>> qwlComparator = new Comparator<Map.Entry<Restaurant,Integer>>() {
		@Override
		public int compare(Map.Entry<Restaurant, Integer> entry1, Map.Entry<Restaurant, Integer> entry2) {
			int cr = 0;
			if (entry1.getValue()>entry2.getValue()) {
				cr = 1;
			} else {
				cr = -1;
			}
			return cr;
		}
	};

}
