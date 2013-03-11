/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.commons.jersey;

import com.sun.jersey.api.client.WebResource;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public interface RequestBehaviour {
	
	public <T> WebResource.Builder behave(WebResource.Builder builder, String method, Class<T> entityClass);

}
