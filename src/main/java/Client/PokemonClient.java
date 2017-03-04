package Client;


import java.io.IOException;
import java.net.*;
import java.util.*;

import Data.PokemonObject;
import Data.TrainerObject;
import Response.PokemonResponse;
import Response.SimpleResponse;
import Response.TrainerResponse;

public class PokemonClient {
	
	private final String _baseAddress;
	
	public PokemonClient(String baseAddress) {
		_baseAddress = baseAddress;
	}

	public PokemonObject[] getAllPokemon() throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + "all");
		String json = action.Get(address, HttpContentType.JSON); 
		PokemonResponse response = PokemonResponse.FromJson(json);
		ThrowFor(response);
		return response.Pokemons;
	}
	public PokemonObject getPokemon(long id) throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + new Long(id).toString());
		String json = action.Get(address, HttpContentType.JSON);
		PokemonResponse response = PokemonResponse.FromJson(json);
		ThrowFor(response);
		return response.Pokemons[0];
	}
	
	public void addPokemon(PokemonObject pokemon) throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + new Long(pokemon.Id).toString());
		String pokemonJson = pokemon.ToJson();
		String json = action.Post(address, HttpContentType.JSON, pokemonJson);
		SimpleResponse response = SimpleResponse.FromJson(json);
		ThrowFor(response);
	}
	
	public void updatePokemon(PokemonObject pokemon) throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + new Long(pokemon.Id).toString());
		String pokemonJson = pokemon.ToJson();
		String json = action.Put(address, HttpContentType.JSON, pokemonJson);
		SimpleResponse response = SimpleResponse.FromJson(json);
		ThrowFor(response);
	}
	
	public void deletePokemon(long id) throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + new Long(id).toString());
		String json = action.Delete(address, HttpContentType.JSON);
		SimpleResponse response = SimpleResponse.FromJson(json);
		ThrowFor(response);
	}
	
	private static void ThrowFor(SimpleResponse response) throws Exception {
		if(!response.Success) {
			if(response.StackTrace.length() == 0) {
				throw new RuntimeException(response.Message);
			} else {
				throw new RuntimeException(response.Message + "\r\n" + response.StackTrace);
			}
		}
	}
}
	