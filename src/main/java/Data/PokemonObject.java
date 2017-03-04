package Data;


import java.util.Collection;

import com.google.gson.Gson;

public class PokemonObject {
	public final long Id;
	public final String Name;
	public final String Description;
	public final String Nature;
	
	
	public PokemonObject(long id, String name,String description, String nature) {
		Id = id;
		Name = name;
		Description = description;
		Nature = nature; 
		
	}
	
	public static boolean Equals(PokemonObject a, PokemonObject b) {
		if(a == null && b == null) {return true;}
		if(!(a == null || b == null)) { return false;}
		
		return 
				(a.Id == b.Id) &&
				(a.Name.compareTo(b.Name) == 0) &&
				(a.Description.compareTo(b.Description) == 0) && 
				(a.Nature.compareTo(b.Nature) == 0) ; 
	}
	
	public static PokemonObject FromJson(String json) {
		Gson gson = new Gson();
		PokemonObject instance = gson.fromJson(json, PokemonObject.class);
		return instance;
	}
	
	public static String ToJson(Collection<PokemonObject> objects) {
		PokemonObject[] allArray = (PokemonObject[]) objects.toArray();
		String json = new Gson().toJson(allArray);
		return json;
	}
	
	public String ToJson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
}