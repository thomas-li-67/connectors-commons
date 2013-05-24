/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.commons.jersey;

import org.mule.commons.jersey.exception.InvalidResponseException;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class DefaultResponseHandler implements ResponseHandler {

	@Override
	public <T> T onSuccess(ClientResponse response, Class<T> entityType) {
		return response.getEntity(entityType);
	}
	
	@Override
	public <T> T onSuccess(ClientResponse response, GenericType<T> type) {
		return response.getEntity(type);
	}

	@Override
	public <T> T onFailure(ClientResponse response, int status, int[] expectedStatus) {
		throw new InvalidResponseException(status, expectedStatus, response); 
	}
	
	@Override
	public <T> T onNoContent(ClientResponse response) {
		return null;
	}
}
