package Service;

import java.sql.SQLException;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import Data.PartnerDataAccessObject;
import Data.PartnerObject;
import Response.PartnerResponse;
import Response.SimpleResponse;

 
@Path("/partners")
public class PartnerService {
	private final PartnerDataAccessObject _partnerDataAccess;
	public PartnerService(){
		_partnerDataAccess = new PartnerDataAccessObject();
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllPartners() {
		PartnerResponse response;
		try{
			Collection<PartnerObject> all = _partnerDataAccess.ReadAllPartners();
		    response = PartnerResponse.Success(all);
		} catch(Exception e) {
			response = PartnerResponse.Error(e);
		}
		return response.ToJson();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTrainer(@PathParam("id") long id) {
		PartnerResponse response;
		try{
			Collection<PartnerObject> all = _partnerDataAccess.ReadAllPartners();
		  
			Optional<PartnerObject> match = all
					.stream()
					.filter(c -> c.Id == id)
					.findFirst();
			
			if (match.isPresent()) {
				response = PartnerResponse.Success(new PartnerObject[]{match.get()});
			} else {
				response = PartnerResponse.Error("Partner not found");
			}
		} catch(Exception e){
			response = PartnerResponse.Error(e);
		}
		return response.ToJson();
	}

	
	@POST
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String addPartner(@PathParam("id") long id, String content) {
		SimpleResponse response;
		try {
			PartnerObject newObj = PartnerObject.FromJson(content);
			if(newObj.Id != id) {
				response = SimpleResponse.Error("Error, ID did not match");
			} else {
				 
				_partnerDataAccess.Insert(newObj);
				response = SimpleResponse.Success();
			}
		} catch(SQLException e) {
			if(e.getMessage().contains("Duplicate")){
				response = SimpleResponse.Error("Partner with id = " + id + " already exists");
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
	public String updatePartner(@PathParam("id") long id, String content) {
		SimpleResponse response;
		try {
			PartnerObject newObj = PartnerObject.FromJson(content);
			if(newObj.Id != id) {
				response = SimpleResponse.Error("Error, ID did not match");
			} else {
				_partnerDataAccess.Update(newObj);
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
	public String deletePartner(@PathParam("id") long id) {
		SimpleResponse response;
		try {
			_partnerDataAccess.Delete(id);
			response = SimpleResponse.Success();
		} catch(Exception e) {
			response = SimpleResponse.Error(e);
		}
		return response.ToJson();
	}
	
}
