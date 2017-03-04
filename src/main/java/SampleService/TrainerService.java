package SampleService;

import java.sql.SQLException;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import Data.TrainerDataAccessObject;
import Data.TrainerObject;
import Response.SimpleResponse;
import Response.TrainerResponse;
 
@Path("/trainer")
public class TrainerService {
	private final TrainerDataAccessObject _trainerDataAccess;
	public TrainerService(){
		_trainerDataAccess = new TrainerDataAccessObject();
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllTrainers() {
		TrainerResponse response;
		try{
			Collection<TrainerObject> all = _trainerDataAccess.ReadAllTrainers();
		    response = TrainerResponse.Success(all);
		} catch(Exception e) {
			response = TrainerResponse.Error(e);
		}
		return response.ToJson();
	}
	

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTrainer(@PathParam("id") long id) {
		TrainerResponse response;
		try{
			Collection<TrainerObject> all = _trainerDataAccess.ReadAllTrainers();
		  
			Optional<TrainerObject> match = all
					.stream()
					.filter(c -> c.Id == id)
					.findFirst();
			
			if (match.isPresent()) {
				response = TrainerResponse.Success(new TrainerObject[]{match.get()});
			} else {
				response = TrainerResponse.Error("Trainer not found");
			}
		} catch(Exception e){
			response = TrainerResponse.Error(e);
		}
		return response.ToJson();
	}

	
	@POST
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String addTrainer(@PathParam("id") long id, String content) {
		SimpleResponse response;
		try {
			TrainerObject newObj = TrainerObject.FromJson(content);
			if(newObj.Id != id) {
				response = SimpleResponse.Error("Error, ID did not match");
			} else {
				System.out.println("gucci"); 
				_trainerDataAccess.Insert(newObj);
				response = SimpleResponse.Success();
			}
		} catch(SQLException e) {
			if(e.getMessage().contains("Duplicate")){
				response = SimpleResponse.Error("Trainer with id = " + id + " already exists");
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
	public String updateTrainer(@PathParam("id") long id, String content) {
		SimpleResponse response;
		try {
			TrainerObject newObj = TrainerObject.FromJson(content);
			if(newObj.Id != id) {
				response = SimpleResponse.Error("Error, ID did not match");
			} else {
				_trainerDataAccess.Update(newObj);
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
	public String deleteTrainer(@PathParam("id") long id) {
		SimpleResponse response;
		try {
			_trainerDataAccess.Delete(id);
			response = SimpleResponse.Success();
		} catch(Exception e) {
			response = SimpleResponse.Error(e);
		}
		return response.ToJson();
	}

	
	
}
