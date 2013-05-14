package org.mule.commons.jersey.exception;

import java.util.Arrays;

import com.sun.jersey.api.client.ClientResponse;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class InvalidResponseException extends RuntimeException {

	private static final long serialVersionUID = 564854801130718918L;
	
	private int status;
	private int[] expectedStatus;
	private ClientResponse response;
	
	public InvalidResponseException(int status, int[] expectedStatus, ClientResponse response) {
		super(String.format("Got status %d but was expecting one of [%s]", status, Arrays.toString(expectedStatus)));
		this.status = status;
		this.expectedStatus = expectedStatus;
		this.response = response;
	}

	public int getStatus() {
		return status;
	}

	public int[] getExpectedStatus() {
		return expectedStatus;
	}

	public ClientResponse getResponse() {
		return response;
	}
}
