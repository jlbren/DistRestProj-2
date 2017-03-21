package SampleService;


import Data.PartnerObject;
import Retries.ExponentialBackoffRetryPolicy;
import Service.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import Client.PartnerClient;


public class PartnerThread implements Runnable {


    public void run(){
            testPartners();
    }

    public void testPartners(){
        try {
            PartnerClient client = new PartnerClient("http://localhost:8080/myapp/partners/", new ExponentialBackoffRetryPolicy());

            PartnerObject partner = new PartnerObject(9, 1, 1, "TallGrass");
            //create
            client.addPartner(partner);
            //read
            PartnerObject added = client.getPartner(partner.Id);
            //update
            PartnerObject updated = new PartnerObject(added.Id, 1, 1, "Surfing");
            client.updatePartner(updated);
            added = client.getPartner(partner.Id);
            if(added == null) {
                throw new RuntimeException("unexpected null from getCustomer()");
            }

            System.out.println(added.toString());

            //delete
            client.deletePartner(partner.Id);

            PartnerObject[] all = client.getAllPartners();

            for (PartnerObject c : all) {
                if (c.Id == partner.Id) {
                    throw new RuntimeException("partner id " + partner.Id+" was not deleted");
                }
            }
            System.out.println("Success");
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
            threads[i] = new Thread(new PartnerThread());
            threads[i].start();
        }
        //block threads until all are finished before closing sever
        for(Thread t : threads)
            t.join();
        server.stop();
    }
}
