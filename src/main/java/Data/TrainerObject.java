package Data;


import java.util.Collection;

import com.google.gson.Gson;

public class TrainerObject {
	public final long Id;
	public final String Name;
	public final long Badges; 
	

	

	public TrainerObject(long id, String name, long badges) {
		// TODO Auto-generated constructor stub
		Id = id;
		Name = name; 
		Badges = badges; 
	}

	public static boolean Equals(TrainerObject a, TrainerObject b) {
		if(a == null && b == null) {return true;}
		if(!(a == null || b == null)) { return false;}
		
		return 
				(a.Id == b.Id) &&
				(a.Name.compareTo(b.Name) == 0) &&
				(a.Badges == b.Badges) ; 
	}
	
	public static TrainerObject FromJson(String json) {
		Gson gson = new Gson();
		TrainerObject instance = gson.fromJson(json, TrainerObject.class);
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