package Client;
import java.io.*;
import java.net.*;

public class ClientAction {

	public String Get(URL url, String contentType) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		try{
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", contentType);

			
			if (conn.getResponseCode() < 200 || conn.getResponseCode() > 299) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
	
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
			StringBuffer buff = new StringBuffer();
			
			String output;
			while ((output = br.readLine()) != null) {
				buff.append(output);
				buff.append('\n');
			}
			
			return buff.toString();
		} finally {
			conn.disconnect();
		}
	}
	
	public String Delete(URL url, String contentType) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		try{
			conn.setDoOutput(true);
			conn.setRequestMethod("DELETE");
			conn.setRequestProperty("Content-Type", contentType);
	
			if (conn.getResponseCode() < 200 || conn.getResponseCode() > 299) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
	
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
			StringBuffer buff = new StringBuffer();
			
			String output;
			while ((output = br.readLine()) != null) {
				buff.append(output);
				buff.append('\n');
			}
			
			return buff.toString();
		} finally {
			conn.disconnect();
		}
	}
	
	public String Put(URL url, String contentType, String input) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		try{
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", contentType);
			
			OutputStream os = conn.getOutputStream();
			try{
				os.write(input.getBytes());
				os.flush();
			} finally {
				os.close();
			}

			
			if (conn.getResponseCode() < 200 || conn.getResponseCode() > 299) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
	
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
			StringBuffer buff = new StringBuffer();
			
			String output;
			while ((output = br.readLine()) != null) {
				buff.append(output);
				buff.append('\n');
			}
			
			return buff.toString();
		} finally {
			conn.disconnect();
		}
	}
	
	public String Post(URL url, String contentType, String input) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		try{
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", contentType);
			
			OutputStream os = conn.getOutputStream();
			try{
				os.write(input.getBytes());
				os.flush();
			} finally {
				os.close();
			}

			
			if (conn.getResponseCode() < 200 || conn.getResponseCode() > 299) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
	
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
			StringBuffer buff = new StringBuffer();
			
			String output;
			while ((output = br.readLine()) != null) {
				buff.append(output);
				buff.append('\n');
			}
			
			return buff.toString();
		} finally {
			conn.disconnect();
		}
	}
}