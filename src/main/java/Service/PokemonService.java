package Service;

import java.sql.SQLException;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import Data.PokemonDataAccessObject;
import Data.PokemonObject;
import Data.TrainerObject;
import Response.PokemonResponse;
import Response.SimpleResponse;
import Response.TrainerResponse;

 
@Path("/pokemon")
public class PokemonService {
	private final PokemonDataAccessObject _pokemonDataAccess;
	public PokemonService(){
		_pokemonDataAccess = new PokemonDataAccessObject();
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllPokemon() {
		PokemonResponse response;
		try{
			Collection<PokemonObject> all = _pokemonDataAccess.ReadAllPokemon();
		    response = PokemonResponse.Success(all);
		} catch(Exception e) {
			response = PokemonResponse.Error(e);
		}
		return response.ToJson();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTrainer(@PathParam("id") long id) {
		PokemonResponse response;
		try{
			Collection<PokemonObject> all = _pokemonDataAccess.ReadAllPokemon();
		  
			Optional<PokemonObject> match = all
					.stream()
					.filter(c -> c.Id == id)
					.findFirst();
			
			if (match.isPresent()) {
				response = PokemonResponse.Success(new PokemonObject[]{match.get()});
			} else {
				response = PokemonResponse.Error("Pokemon not found");
			}
		} catch(Exception e){
			response = PokemonResponse.Error(e);
		}
		return response.ToJson();
	}

	
	@POST
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String addPokemon(@PathParam("id") long id, String content) {
		SimpleResponse response;
		try {
			PokemonObject newObj = PokemonObject.FromJson(content);
			if(newObj.Id != id) {
				response = SimpleResponse.Error("Error, ID did not match");
			} else {
				 
				_pokemonDataAccess.Insert(newObj);
				response = SimpleResponse.Success();
			}
		} catch(SQLException e) {
			if(e.getMessage().contains("Duplicate")){
				response = SimpleResponse.Error("Pokemon with id = " + id + " already exists");
			} else {
				System.out.println(content); 
				response = SimpleResponse.Error(e);
			}
		} catch(Exception e) {
			response = SimpleResponse.Error(e);
		}
		return response.ToJson();
	}
	

	@PUT
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePokemon(@PathParam("id") long id, String content) {
		SimpleResponse response;
		try {
			PokemonObject newObj = PokemonObject.FromJson(content);
			if(newObj.Id != id) {
				response = SimpleResponse.Error("Error, ID did not match");
			} else {
				_pokemonDataAccess.Update(newObj);
				response = SimpleResponse.Success();
			}
		} catch(Exception e) {
			response = SimpleResponse.Error(e);
		}
		return response.ToJson();
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePokemon(@PathParam("id") long id) {
		SimpleResponse response;
		try {
			_pokemonDataAccess.Delete(id);
			response = SimpleResponse.Success();
		} catch(Exception e) {
			response = SimpleResponse.Error(e);
		}
		return response.ToJson();
	}


	
	
}
