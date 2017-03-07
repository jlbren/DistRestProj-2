package Data;


import java.util.Collection;

import com.google.gson.Gson;

public class PartnerObject {
	public final long Id;
	public final long pkmnId;
	public final long trainerId;
	public final String Location;
	
	

	

	public PartnerObject(long id, long pId, long tId, String location) {
		Id = id;
		pkmnId = pId; 
		trainerId = tId;
		Location = location; 
	}

	public static boolean Equals(PartnerObject a, PartnerObject b) {
		if(a == null && b == null) {return true;}
		if(!(a == null || b == null)) { return false;}
		
		return 
				(a.Id == b.Id) &&
				(a.pkmnId == b.pkmnId) &&
				(a.trainerId == b.trainerId) &&
				(a.Location.compareTo(b.Location) == 0); 
	}
	
	public static PartnerObject FromJson(String json) {
		Gson gson = new Gson();
		PartnerObject instance = gson.fromJson(json, PartnerObject.class);
		return instance;
	}
	
	public static String ToJson(Collection<TrainerObject> objects) {
		TrainerObject[] allArray = (TrainerObject[]) objects.toArray();
		String json = new Gson().toJson(allArray);
		return json;
	}
	
	public String ToJson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
}