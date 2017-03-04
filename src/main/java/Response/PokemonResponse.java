package Response;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

import com.google.gson.Gson;

import Data.PokemonObject;

public class PokemonResponse extends SimpleResponse {
	public final PokemonObject[] Pokemons;
	
	public static PokemonResponse Error(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		String stackTrace = sw.toString();
		return new PokemonResponse(false, e.getMessage(), stackTrace, new PokemonObject[0]);
	}
	
	public static PokemonResponse Error(String message) {
		return new PokemonResponse(false, message, "", new PokemonObject[0]);
	}
	
	public static PokemonResponse Success() {
		return new PokemonResponse(true, "", "", new PokemonObject[0]);
	}
	
	public static PokemonResponse Success(Collection<PokemonObject> pokemons) {
		PokemonObject[] array = new PokemonObject[pokemons.size()];
		int index = -1;
		for(PokemonObject c : pokemons) {
			array[++index] = c;
		}
		return Success(array);
	}
	
	public static PokemonResponse Success(PokemonObject[] pokemons) {
		return new PokemonResponse(true, "", "", pokemons);
	}
	
	public static PokemonResponse FromJson(String json) {
		Gson serializer = new Gson();
		PokemonResponse response = serializer.fromJson(json, PokemonResponse.class);
		return response;
	}

	private PokemonResponse(boolean success, String message, String trace, PokemonObject[] pokemons){
		super(success, message, trace);
		Pokemons = pokemons;
	}
	
	private PokemonResponse() {
		super();
		Pokemons = new PokemonObject[0];
	}
}