package Client;

import java.io.IOException;
import java.net.*;
import java.util.*;

import Data.TrainerObject;
import Response.SimpleResponse;
import Response.TrainerResponse;

public class TrainerClient {	
	private final String _baseAddress;
	
	public TrainerClient(String baseAddress) {
		_baseAddress = baseAddress;
	}

	public TrainerObject[] getAllTrainers() throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + "all");
		String json = action.Get(address, HttpContentType.JSON);
		TrainerResponse response = TrainerResponse.FromJson(json);
		ThrowFor(response);
		return response.Trainers;
	}
	
	public TrainerObject getTrainer(long id) throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + new Long(id).toString());
		String json = action.Get(address, HttpContentType.JSON);
		TrainerResponse response = TrainerResponse.FromJson(json);
		ThrowFor(response);
		return response.Trainers[0];
	}
	
	public void addTrainer(TrainerObject trainer) throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + new Long(trainer.Id).toString());
		String trainerJson = trainer.ToJson();
		String json = action.Post(address, HttpContentType.JSON, trainerJson);
		SimpleResponse response = SimpleResponse.FromJson(json);
		ThrowFor(response);
	}
	
	public void updateTrainer(TrainerObject trainer) throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + new Long(trainer.Id).toString());
		String trainerJson = trainer.ToJson();
		String json = action.Put(address, HttpContentType.JSON, trainerJson);
		SimpleResponse response = SimpleResponse.FromJson(json);
		ThrowFor(response);
	}
	
	public void deleteTrainer(long id) throws Exception {
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