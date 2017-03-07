package Client;

import java.io.IOException;
import java.net.*;
import java.util.*;

import Data.TrainerObject;
import Response.SimpleResponse;
import Response.TrainerResponse;

public class PartnerClient {	
	private final String _baseAddress;
	
	public PartnerClient(String baseAddress) {
		_baseAddress = baseAddress;
	}

	public PartnerObject[] getAllPartjers() throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + "all");
		String json = action.Get(address, HttpContentType.JSON);
		PartnerResponse response = TrainerResponse.FromJson(json);
		ThrowFor(response);
		return response.Partners;
	}
	
	public TrainerObject getPartner(long id) throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + new Long(id).toString());
		String json = action.Get(address, HttpContentType.JSON);
		PartnerResponse response = PartnerResponse.FromJson(json);
		ThrowFor(response);
		return response.Partners[0];
	}
	
	public void addPartner(PartnerObject partner) throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + new Long(trainer.Id).toString());
		String partnerJson = partner.ToJson();
		String json = action.Post(address, HttpContentType.JSON, partnerJson);
		SimpleResponse response = SimpleResponse.FromJson(json);
		ThrowFor(response);
	}
	
	public void updatePartner(PartnerObject partner) throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + new Long(partner.Id).toString());
		String partnerJson = partner.ToJson();
		String json = action.Put(address, HttpContentType.JSON, partnerJson);
		SimpleResponse response = SimpleResponse.FromJson(json);
		ThrowFor(response);
	}
	
	public void deletePartner(long id) throws Exception {
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