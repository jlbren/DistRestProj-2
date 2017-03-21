package SampleService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import Retries.ExponentialBackoffRetryPolicy;
import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Client.PartnerClient;
import Client.PokemonClient;
import Client.TrainerClient;
import Data.PartnerObject;
import Data.PokemonObject;
import Data.TrainerObject;
import Service.Main;

import static org.junit.Assert.assertEquals;

public class ClientTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        String responseMsg = target.path("myresource").request().get(String.class);
        assertEquals("Got it!", responseMsg);
    }
    
    @Test
    public void testTrainer() throws Exception{
    	TrainerClient client = new TrainerClient("http://localhost:8080/myapp/trainer/", new ExponentialBackoffRetryPolicy());
		
		TrainerObject trainer = new TrainerObject(9, "firstname", 10);
		//create
		client.addTrainer(trainer);
		//read
		TrainerObject added = client.getTrainer(trainer.Id);
		
		//assertEquals(true,(added!=null)); // silly workaround to assert not null
		
		//update
		TrainerObject updated = new TrainerObject(added.Id, "firstname2", 11);
		
		client.updateTrainer(updated);
		
		added = client.getTrainer(trainer.Id); 
		
		//assertEquals(added.Name, "firstname2");
		
		//delete 
		client.deleteTrainer(trainer.Id);
		
		TrainerObject[] all = client.getAllTrainers();
		
		for(TrainerObject c : all) {
			if(c.Id == trainer.Id) {
				//assertEquals(true,false); // force failure to indicate delete did not happen
			}
		}
    
    }
    @Test
    public void testPokemon() throws Exception{
    	PokemonClient client = new PokemonClient("http://localhost:8080/myapp/pokemon/", new ExponentialBackoffRetryPolicy());
		
		PokemonObject pokemon = new PokemonObject(150, "Mewtwo", "Has science gone too far?!", "Hasty");
		//create
		client.addPokemon(pokemon);
		//read
		PokemonObject added = client.getPokemon(pokemon.Id);
		
		assertEquals(true,(added!=null)); // silly workaround to assert not null  
		
		//update
		PokemonObject updated = new PokemonObject(added.Id, "MegaMewtwo", "MEGA", "Lazy"); 
		
		client.updatePokemon(updated);
		
		added = client.getPokemon(pokemon.Id); 
		
		assertEquals(added.Name, "MegaMewtwo"); 
		
		//delete 
		client.deletePokemon(pokemon.Id);
		
		PokemonObject[] all = client.getAllPokemon();
		
		for(PokemonObject c : all) {
			if(c.Id == pokemon.Id) {
				assertEquals(true,false); // force failure to indicate delete did not happen 
			}
		}
    
    }
    
    @Test
    public void testPartners() throws Exception{
    	PartnerClient client = new PartnerClient("http://localhost:8080/myapp/partners/", new ExponentialBackoffRetryPolicy());
		
		PartnerObject partner = new PartnerObject(9,1,1,"TallGrass");
		//create
		client.addPartner(partner);
		//read
		PartnerObject added = client.getPartner(partner.Id);
		
		assertEquals(true,(added!=null)); // silly workaround to assert not null  
		
		//update
		PartnerObject updated = new PartnerObject(added.Id, 1,1,"Surfing");
		
		client.updatePartner(updated);
		
		added = client.getPartner(partner.Id); 
		
		assertEquals(added.Location, "Surfing"); 
		
		//delete 
		client.deletePartner(partner.Id);
		
		PartnerObject[] all = client.getAllPartners();
		
		for(PartnerObject c : all) {
			if(c.Id == partner.Id) {
				assertEquals(true,false); // force failure to indicate delete did not happen 
			}
		}
    
    }
}
