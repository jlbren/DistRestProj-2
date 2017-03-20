package Client;

import java.io.IOException;
import java.net.*;
import java.util.*;
import Retries.*;

import Data.PartnerObject;
import Response.PartnerResponse;
import Response.SimpleResponse;
import Retries.IRetryPolicy;

public class PartnerClient {	
	private final String _baseAddress;
	private final IRetryPolicy _retry;
	public PartnerClient(String baseAddress, IRetryPolicy retry) {
		_baseAddress = baseAddress;
		_retry = retry;
	}

	public PartnerObject[] getAllPartners() throws Exception {
		ISingleMethodPolicy method = _retry.NewMethod();
		PartnerResponse response;
		do{
			ClientAction action = new ClientAction();
			URL address = new URL(_baseAddress + "all");
			String json = action.Get(address, HttpContentType.JSON);
			response = PartnerResponse.FromJson(json);

			method.HadResponse(response);
			method.PerformWaitIfNeeded();
			method.ThrowErrorFromResponseIfAppropriate();
		} while(method.ShouldRetry());

		return response.Partners;
	}
	
	public PartnerObject getPartner(long id) throws Exception {
		ISingleMethodPolicy method = _retry.NewMethod();
		PartnerResponse response;
		do{
			ClientAction action = new ClientAction();
			URL address = new URL(_baseAddress + new Long(id).toString());
			String json = action.Get(address, HttpContentType.JSON);
			response = PartnerResponse.FromJson(json);

			method.HadResponse(response);
			method.PerformWaitIfNeeded();
			method.ThrowErrorFromResponseIfAppropriate();
		} while(method.ShouldRetry());

		return response.Partners[0];
	}
	
	public void addPartner(PartnerObject partner) throws Exception {
		ISingleMethodPolicy method = _retry.NewMethod();
		PartnerResponse response;
		do{
			ClientAction action = new ClientAction();
			URL address = new URL(_baseAddress + new Long(partner.Id).toString());
			String json = action.Get(address, HttpContentType.JSON);
			response = PartnerResponse.FromJson(json);

			method.HadResponse(response);
			method.PerformWaitIfNeeded();
			method.ThrowErrorFromResponseIfAppropriate();
		} while(method.ShouldRetry());
	}
	
	public void updatePartner(PartnerObject partner) throws Exception {
		ISingleMethodPolicy method = _retry.NewMethod();
		PartnerResponse response;
		do{
			ClientAction action = new ClientAction();
			URL address = new URL(_baseAddress + new Long(partner.Id).toString());
			String json = action.Get(address, HttpContentType.JSON);
			response = PartnerResponse.FromJson(json);

			method.HadResponse(response);
			method.PerformWaitIfNeeded();
			method.ThrowErrorFromResponseIfAppropriate();
		} while(method.ShouldRetry());
	}
	
	public void deletePartner(long id) throws Exception {
		ISingleMethodPolicy method = _retry.NewMethod();
		PartnerResponse response;
		do{
			ClientAction action = new ClientAction();
			URL address = new URL(_baseAddress + new Long(id).toString());
			String json = action.Get(address, HttpContentType.JSON);
			response = PartnerResponse.FromJson(json);

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