package org.mule.commons.jersey;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource.Builder;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class DefaultRequestBehaviour implements RequestBehaviour {
	
	@Override
	public <T> Builder behave(Builder builder, String method, Class<T> entityClass) {
		return builder;
	}
	
	@Override
	public <T> Builder behave(Builder builder, String method, GenericType<T> type) {
		return builder;
	}

}
