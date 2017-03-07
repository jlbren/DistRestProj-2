package Response;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

import com.google.gson.Gson;


public class PartnerResponse extends SimpleResponse {
	public final PartnerObject[] Partners;
	
	public static PartnerResponse Error(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		String stackTrace = sw.toString();
		return new PartnerResponse(false, e.getMessage(), stackTrace, new PartnerObject[0]);
	}
	
	public static PartnerResponse Error(String message) {
		return new PartnerResponse(false, message, "", new PartnerObject[0]);
	}
	
	public static PartnerResponse Success() {
		return new PartnerResponse(true, "", "", new PartnerObject[0]);
	}
	
	public static PartnerResponse Success(Collection<PartnerObject> partners) {
		PartnerObject[] array = new PartnerObject[partners.size()];
		int index = -1;
		for(PartnerObject c : partners) {
			array[++index] = c;
		}
		return Success(array);
	}
	
	public static PartnerResponse Success(PartnerObject[] partners) {
		return new PartnerResponse(true, "", "", partners);
	}
	
	public static PartnerResponse FromJson(String json) {
		Gson serializer = new Gson();
		PartnerResponse response = serializer.fromJson(json, PartnerResponse.class);
		return response;
	}

	private PartnerResponse(boolean success, String message, String trace, PartnerObject[] partners){
		super(success, message, trace);
		Partners = partners; 
	}
	
	private PartnerResponse() {
		super();
		Partners = new PartnerObject[0];
	}
}