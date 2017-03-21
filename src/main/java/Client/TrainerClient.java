package Client;

import java.io.IOException;
import java.net.*;
import java.util.*;

import Data.TrainerObject;
import Response.PokemonResponse;
import Response.SimpleResponse;
import Response.TrainerResponse;
import Retries.IRetryPolicy;
import Retries.ISingleMethodPolicy;

public class TrainerClient {	
	private final String _baseAddress;
	private final IRetryPolicy _retry;
	
	public TrainerClient(String baseAddress, IRetryPolicy retry) {
				_baseAddress = baseAddress;
				_retry = retry;
	}

	public TrainerObject[] getAllTrainers() throws Exception {
		ISingleMethodPolicy method = _retry.NewMethod();
		TrainerResponse response;
		do{
			ClientAction action = new ClientAction();
			URL address = new URL(_baseAddress + "all");
			String json = action.Get(address, HttpContentType.JSON);
			response = TrainerResponse.FromJson(json);
			method.HadResponse(response);
			method.PerformWaitIfNeeded();
			method.ThrowErrorFromResponseIfAppropriate();
		} while(method.ShouldRetry());
		return response.Trainers;
	}
	
	public TrainerObject getTrainer(long id) throws Exception {
		ISingleMethodPolicy method = _retry.NewMethod();
		TrainerResponse response;
		do{
			ClientAction action = new ClientAction();
			URL address = new URL(_baseAddress + "all");
			String json = action.Get(address, HttpContentType.JSON);
			response = TrainerResponse.FromJson(json);
			method.HadResponse(response);
			method.PerformWaitIfNeeded();
			method.ThrowErrorFromResponseIfAppropriate();
		} while(method.ShouldRetry());
		ThrowFor(response);
		return response.Trainers[0];
	}
	
	public void addTrainer(TrainerObject trainer) throws Exception {
		ISingleMethodPolicy method = _retry.NewMethod();
		TrainerResponse response;
		do{
			ClientAction action = new ClientAction();
			URL address = new URL(_baseAddress + "all");
			String json = action.Get(address, HttpContentType.JSON);
			response = TrainerResponse.FromJson(json);
			method.HadResponse(response);
			method.PerformWaitIfNeeded();
			method.ThrowErrorFromResponseIfAppropriate();
		} while(method.ShouldRetry());
	}
	
	public void updateTrainer(TrainerObject trainer) throws Exception {
		ISingleMethodPolicy method = _retry.NewMethod();
		TrainerResponse response;
		do{
			ClientAction action = new ClientAction();
			URL address = new URL(_baseAddress + "all");
			String json = action.Get(address, HttpContentType.JSON);
			response = TrainerResponse.FromJson(json);
			method.HadResponse(response);
			method.PerformWaitIfNeeded();
			method.ThrowErrorFromResponseIfAppropriate();
		} while(method.ShouldRetry());
	}
	
	public void deleteTrainer(long id) throws Exception {
		ISingleMethodPolicy method = _retry.NewMethod();
		TrainerResponse response;
		do{
			ClientAction action = new ClientAction();
			URL address = new URL(_baseAddress + "all");
			String json = action.Get(address, HttpContentType.JSON);
			response = TrainerResponse.FromJson(json);
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