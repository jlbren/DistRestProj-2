package SampleService;


import Data.PartnerObject;
import Data.PokemonObject;
import Retries.ExponentialBackoffRetryPolicy;
import Service.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import Client.PokemonClient;

import static org.junit.Assert.assertEquals;

public class PokemonThread implements Runnable {


    public void run(){
        testPokemon();
    }

    public void testPokemon(){
        try {

            PokemonClient client = new PokemonClient("http://localhost:8080/myapp/pokemon/", new ExponentialBackoffRetryPolicy());

            PokemonObject pokemon = new PokemonObject(150, "Mewtwo", "Has science gone too far?!", "Hasty");
            //create
            client.addPokemon(pokemon);
            //read
            PokemonObject added = client.getPokemon(pokemon.Id);

          //  assertEquals(true,(added!=null)); // silly workaround to assert not null

            //update
            PokemonObject updated = new PokemonObject(added.Id, "MegaMewtwo", "MEGA", "Lazy");

            client.updatePokemon(updated);

            added = client.getPokemon(pokemon.Id);


            //delete
            client.deletePokemon(pokemon.Id);

            PokemonObject[] all = client.getAllPokemon();

            for(PokemonObject c : all) {
                if(c.Id == pokemon.Id) {
                   // assertEquals(true,false); // force failure to indicate delete did not happen
                }
            }

        }catch(Exception e){
            System.err.println(e.getMessage());
        }

    }


    public static void main(String [] args) throws Exception{
        // start the server
        HttpServer server;
        WebTarget target;
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();
        //thread count
        int tMax = 25;
        Thread [] threads = new Thread [tMax];

        target = c.target(Main.BASE_URI);
        for(int i = 0; i < tMax; ++i) {
            threads[i] = new Thread(new PokemonThread());
            threads[i].start();
        }
        //block threads until all are finished before closing sever
        for(Thread t : threads)
            t.join();
        server.stop();
    }
}
