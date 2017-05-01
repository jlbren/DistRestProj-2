package Client;


import java.io.IOException;
import java.net.*;
import java.util.*;

import Data.PokemonObject;
import Data.TrainerObject;
import Response.PartnerResponse;
import Response.PokemonResponse;
import Response.SimpleResponse;
import Response.TrainerResponse;
import Retries.IRetryPolicy;
import Retries.ISingleMethodPolicy;

public class PokemonClient {
	
	private final String _baseAddress;
	private final IRetryPolicy _retry;


	public PokemonClient(String baseAddress, IRetryPolicy retry) {
		_baseAddress = baseAddress;
		_retry = retry;
	}

	public PokemonObject[] getAllPokemon() throws Exception {
		ISingleMethodPolicy method = _retry.NewMethod();
		PokemonResponse response;
		do{
			ClientAction action = new ClientAction();
			URL address = new URL(_baseAddress + "all");
			String json = action.Get(address, HttpContentType.JSON);
			response = PokemonResponse.FromJson(json);
			method.HadResponse(response);
			//method.PerformWaitIfNeeded();
			method.ThrowErrorFromResponseIfAppropriate();
		} while(method.ShouldRetry());
		return response.Pokemons;
	}
	public PokemonObject getPokemon(long id) throws Exception {
		ISingleMethodPolicy method = _retry.NewMethod();
		PokemonResponse response;
		do{
			ClientAction action = new ClientAction();
			URL address = new URL(_baseAddress + new Long(id).toString());
			String json = action.Get(address, HttpContentType.JSON);
			response = PokemonResponse.FromJson(json);
			method.HadResponse(response);
			method.PerformWaitIfNeeded();
			method.ThrowErrorFromResponseIfAppropriate();
		} while(method.ShouldRetry());
		ThrowFor(response);
		return response.Pokemons[0];
	}
	
	public void addPokemon(PokemonObject pokemon) throws Exception {
		ISingleMethodPolicy method = _retry.NewMethod();
		PokemonResponse response;
		do{
			ClientAction action = new ClientAction();
			URL address = new URL(_baseAddress + new Long(pokemon.Id).toString());
			String json = action.Get(address, HttpContentType.JSON);
			response = PokemonResponse.FromJson(json);
			method.HadResponse(response);
			method.PerformWaitIfNeeded();
			method.ThrowErrorFromResponseIfAppropriate();
		} while(method.ShouldRetry());
	}
	
	public void updatePokemon(PokemonObject pokemon) throws Exception {
		ISingleMethodPolicy method = _retry.NewMethod();
		PokemonResponse response;
		do{
			ClientAction action = new ClientAction();
			URL address = new URL(_baseAddress + new Long(pokemon.Id).toString());
			String json = action.Get(address, HttpContentType.JSON);
			response = PokemonResponse.FromJson(json);
			method.HadResponse(response);
			method.PerformWaitIfNeeded();
			method.ThrowErrorFromResponseIfAppropriate();
		} while(method.ShouldRetry());
	}
	
	public void deletePokemon(long id) throws Exception {
		ISingleMethodPolicy method = _retry.NewMethod();
		PokemonResponse response;
		do{
			ClientAction action = new ClientAction();
			URL address = new URL(_baseAddress + new Long(id).toString());
			String json = action.Get(address, HttpContentType.JSON);
			response = PokemonResponse.FromJson(json);
			method.HadResponse(response);
			method.PerformWaitIfNeeded();
			method.ThrowErrorFromResponseIfAppropriate();
		} while(method.ShouldRetry());
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
	