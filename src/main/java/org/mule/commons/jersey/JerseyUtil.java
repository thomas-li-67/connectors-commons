/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.commons.jersey;

import java.util.ArrayList;
import java.util.List;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class JerseyUtil {

	public static class Builder {
		
		private JerseyUtil util;
		
		private Builder() {
			this.util = new JerseyUtil();
		}
		
		public Builder addRequestBehaviour(RequestBehaviour behaviour) {
			this.util.requestBehaviour.add(behaviour);
			return this;
		}
		
		public Builder setResponseHandler(ResponseHandler handler) {
			this.util.responseHandler = handler;
			return this;
		}
		
		public JerseyUtil build() {
			if (this.util.responseHandler == null) {
				this.util.responseHandler = new DefaultResponseHandler();
			}

			return this.util;
		}
	}
	
	private List<RequestBehaviour> requestBehaviour = new ArrayList<RequestBehaviour>();
	private ResponseHandler responseHandler;
	
	private JerseyUtil(){}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public <T> T get(WebResource resource, Class<T> entityClass, int... expectedStatus) {
		return this.execute("GET", resource, entityClass, expectedStatus);
	}
	
	public <T> T get(WebResource.Builder resource, Class<T> entityClass, int... expectedStatus) {
		return this.execute("GET", resource, entityClass, expectedStatus);
	}
	
	public <T> T get(WebResource resource, GenericType<T> type, int... expectedStatus) {
		return this.execute("GET", resource, type, expectedStatus);
	}
	
	public <T> T get(WebResource.Builder resource, GenericType<T> type, int... expectedStatus) {
		return this.execute("GET", resource, type, expectedStatus);
	}
	
	
	public <T> T post(WebResource resource, Class<T> entityClass, int... expectedStatus) {
		return this.execute("POST", resource, entityClass, expectedStatus);
	}
	
	public <T> T post(WebResource.Builder resource, Class<T> entityClass, int... expectedStatus) {
		return this.execute("POST", resource, entityClass, expectedStatus);
	}
	
	public <T> T post(WebResource resource, GenericType<T> type, int... expectedStatus) {
		return this.execute("POST", resource, type, expectedStatus);
	}
	
	public <T> T post(WebResource.Builder resource, GenericType<T> type, int... expectedStatus) {
		return this.execute("POST", resource, type, expectedStatus);
	}
	
	public <T> T put(WebResource resource, Class<T> entityClass, int... expectedStatus) {
		return this.execute("PUT", resource, entityClass, expectedStatus);
	}
	
	public <T> T put(WebResource.Builder resource, Class<T> entityClass, int... expectedStatus) {
		return this.execute("PUT", resource, entityClass, expectedStatus);
	}
	
	public <T> T put(WebResource resource, GenericType<T> type, int... expectedStatus) {
		return this.execute("PUT", resource, type, expectedStatus);
	}
	
	public <T> T put(WebResource.Builder resource,GenericType<T> type, int... expectedStatus) {
		return this.execute("PUT", resource, type, expectedStatus);
	}
	
	public <T> T delete(WebResource resource, Class<T> entityClass, int... expectedStatus) {
		return this.execute("DELETE", resource, entityClass, expectedStatus);
	}
	
	public <T> T delete(WebResource.Builder resource, Class<T> entityClass, int... expectedStatus) {
		return this.execute("DELETE", resource, entityClass, expectedStatus);
	}
	
	public <T> T delete(WebResource.Builder resource, GenericType<T> type, int... expectedStatus) {
		return this.execute("DELETE", resource, type, expectedStatus);
	}
	
	public <T> T delete(WebResource resource, GenericType<T> type, int... expectedStatus) {
		return this.execute("DELETE", resource, type, expectedStatus);
	}
	
	public <T> T options(WebResource resource, Class<T> entityClass, int... expectedStatus) {
		return this.execute("OPTIONS", resource.getRequestBuilder(), entityClass, expectedStatus);
	}
	public <T> T options(WebResource.Builder resource, GenericType<T> type, int... expectedStatus) {
		return this.execute("OPTIONS", resource, type, expectedStatus);
	}
	
	public <T> T options(WebResource resource, GenericType<T> type, int... expectedStatus) {
		return this.execute("OPTIONS", resource.getRequestBuilder(), type, expectedStatus);
	}
	public <T> T options(WebResource.Builder resource, Class<T> entityClass, int... expectedStatus) {
		return this.execute("OPTIONS", resource, entityClass, expectedStatus);
	}
	
	public <T> T execute(String method, WebResource resource, Class<T> entityClass, int... expectedStatus) {
		return this.execute(method, resource.getRequestBuilder(), entityClass, expectedStatus);
	}
	
	public <T> T execute(String method, WebResource resource, GenericType<T> type, int... expectedStatus) {
		return this.execute(method, resource.getRequestBuilder(), type, expectedStatus);
	}
	
	public <T> T execute(String method, WebResource.Builder builder, Class<T> entityClass, int... expectedStatus) {
    	
		for (RequestBehaviour b : this.requestBehaviour) {
    		builder = b.behave(builder, method, entityClass);
    	}
    	
    	ClientResponse response = builder.method(method, ClientResponse.class);
		int status = response.getStatus();
		 
		if (this.contains(expectedStatus, status)) {
			return status != Status.NO_CONTENT.getStatusCode() ? this.responseHandler.onSuccess(response, entityClass) : this.responseHandler.<T>onNoContent(response); 
		 } else {
			 return this.responseHandler.onFailure(response, status, expectedStatus);
		 }
    }
	
	public <T> T execute(String method, WebResource.Builder builder, GenericType<T> type, int... expectedStatus) {
    	
		for (RequestBehaviour b : this.requestBehaviour) {
    		builder = b.behave(builder, method, type);
    	}
    	
    	ClientResponse response = builder.method(method, ClientResponse.class);
		int status = response.getStatus();
		 
		if (this.contains(expectedStatus, status)) {
			return status != Status.NO_CONTENT.getStatusCode() ? this.responseHandler.onSuccess(response, type) : this.responseHandler.<T>onNoContent(response); 
		 } else {
			 return this.responseHandler.onFailure(response, status, expectedStatus);
		 }
    }
    
    private boolean contains(int[] expected, int obtained) {
    	if (expected == null || expected.length == 0) {
    		return true;
    	}
    	
    	for (int i : expected) {
    		if (i == obtained) {
    			return true;
    		}
    	}
    	
    	return false;
    }
	
}
