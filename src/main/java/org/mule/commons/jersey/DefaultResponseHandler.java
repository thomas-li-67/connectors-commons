/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.commons.jersey;

import com.sun.jersey.api.client.ClientResponse;

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
	public <T> T onFailure(ClientResponse response, int status, int[] expectedStatus) {
		throw new RuntimeException(String.format("Got status %d but was expecting one of [%s]", status, expectedStatus.toString()));
	}
	
	@Override
	public <T> T onNoContent(ClientResponse response) {
		return null;
	}
}
