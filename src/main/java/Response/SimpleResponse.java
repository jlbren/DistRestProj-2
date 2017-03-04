package Response; 
import java.io.*;

import com.google.gson.Gson;

public class SimpleResponse {
	public final boolean Success;
	public final String Message;
	public final String StackTrace;
	
	public static SimpleResponse Error(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		String stackTrace = sw.toString();
		return new SimpleResponse(false, e.getMessage(), stackTrace);
	}
	
	public static SimpleResponse Error(String message) {
		return new SimpleResponse(false, message, "");
	}
	
	public static SimpleResponse Success() {
		return new SimpleResponse(true, "", "");
	}
	
	protected SimpleResponse(boolean success, String message, String stackTrace) {
		Success = success;
		Message = message;
		StackTrace = stackTrace;
	}
	
	protected SimpleResponse() {
		Success = true;
		Message = "";
		StackTrace = "";
	}
	
	public String ToJson() {
		Gson serializer = new Gson();
		String json = serializer.toJson(this);
		return json;
	}
	
	public static SimpleResponse FromJson(String json) {
		Gson serializer = new Gson();
		SimpleResponse response = serializer.fromJson(json, SimpleResponse.class);
		return response;
	}
}