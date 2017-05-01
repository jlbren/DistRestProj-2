package SampleService;

import static org.junit.Assert.assertEquals;

import Client.PokemonClient;
import Data.PokemonObject;
import Retries.ExponentialBackoffRetryPolicy;

public class SimpleTest {

	public static void main(String[] args) {
    	PokemonClient client = new PokemonClient("http://localhost:8080/myapp/pokemon/", new ExponentialBackoffRetryPolicy());
    	
		PokemonObject[] all;
		try {
			all = client.getAllPokemon();
			System.out.println("Test results size: "+all.length); 
			for(PokemonObject pkmn : all){
				System.out.println(pkmn.ToJson());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
