package org.mule.commons.jersey.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 * 
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GsonProvider implements MessageBodyWriter<Object>, MessageBodyReader<Object> {

	private static final String UTF_8 = "UTF-8";
	private Gson gson;
	
	public GsonProvider(Gson gson) {
		this.gson = gson;
	}

	@Override
	public boolean isReadable(Class<?> type, java.lang.reflect.Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}
	
	@Override
	public Object readFrom(Class<Object> type,
			java.lang.reflect.Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream) throws IOException,
			WebApplicationException {
		
		Type jsonType;
		if (type.equals(genericType)) {
			jsonType = type;
		} else {
			jsonType = genericType;
		}
			
		if (jsonType.equals(String.class)) {
			return IOUtils.toString(entityStream);
		} else {
			InputStreamReader streamReader = new InputStreamReader(entityStream, UTF_8);
			try {
				return this.gson.fromJson(streamReader, jsonType);
			} finally {
				IOUtils.closeQuietly(entityStream);
			}
		}
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public long getSize(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(Object object, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		
		OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8);
		try {
			Type jsonType;
			if (type.equals(genericType)) {
				jsonType = type;
			} else {
				jsonType = genericType;
			}
			this.gson.toJson(object, jsonType, writer);
		} finally {
			writer.close();
		}
	}

}
