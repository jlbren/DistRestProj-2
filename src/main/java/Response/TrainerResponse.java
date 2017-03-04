package Response;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

import com.google.gson.Gson;

import Data.TrainerObject;

public class TrainerResponse extends SimpleResponse {
	public final TrainerObject[] Trainers;
	
	public static TrainerResponse Error(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		String stackTrace = sw.toString();
		return new TrainerResponse(false, e.getMessage(), stackTrace, new TrainerObject[0]);
	}
	
	public static TrainerResponse Error(String message) {
		return new TrainerResponse(false, message, "", new TrainerObject[0]);
	}
	
	public static TrainerResponse Success() {
		return new TrainerResponse(true, "", "", new TrainerObject[0]);
	}
	
	public static TrainerResponse Success(Collection<TrainerObject> trainers) {
		TrainerObject[] array = new TrainerObject[trainers.size()];
		int index = -1;
		for(TrainerObject c : trainers) {
			array[++index] = c;
		}
		return Success(array);
	}
	
	public static TrainerResponse Success(TrainerObject[] trainers) {
		return new TrainerResponse(true, "", "", trainers);
	}
	
	public static TrainerResponse FromJson(String json) {
		Gson serializer = new Gson();
		TrainerResponse response = serializer.fromJson(json, TrainerResponse.class);
		return response;
	}

	private TrainerResponse(boolean success, String message, String trace, TrainerObject[] trainers){
		super(success, message, trace);
		Trainers = trainers; 
	}
	
	private TrainerResponse() {
		super();
		Trainers = new TrainerObject[0];
	}
}