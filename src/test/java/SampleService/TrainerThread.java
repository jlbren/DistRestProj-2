package SampleService;


import Data.PartnerObject;
import Data.PokemonObject;
import Data.TrainerObject;
import Retries.ExponentialBackoffRetryPolicy;
import Service.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import Client.TrainerClient;

import static org.junit.Assert.assertEquals;

public class TrainerThread implements Runnable {


    public void run(){
        testTrainer();
    }

    public void testTrainer(){
        try {

            TrainerClient client = new TrainerClient("http://localhost:8080/myapp/trainer/", new ExponentialBackoffRetryPolicy());

            TrainerObject trainer = new TrainerObject(9, "firstname", 10);
            //create
            client.addTrainer(trainer);
            //read
            TrainerObject added = client.getTrainer(trainer.Id);

          //  assertEquals(true,(added!=null)); // silly workaround to assert not null

            //update
            TrainerObject updated = new TrainerObject(added.Id, "firstname2", 11);

            client.updateTrainer(updated);

            added = client.getTrainer(trainer.Id);

           // assertEquals(added.Name, "firstname2");

            //delete
            client.deleteTrainer(trainer.Id);

            TrainerObject[] all = client.getAllTrainers();

            for(TrainerObject c : all) {
                if(c.Id == trainer.Id) {
                  //  assertEquals(true,false); // force failure to indicate delete did not happen
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
            threads[i] = new Thread(new TrainerThread());
            threads[i].start();
        }
        //block threads until all are finished before closing sever
        for(Thread t : threads)
            t.join();
        server.stop();
    }
}
