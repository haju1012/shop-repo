package de.shop.util.rest;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import de.shop.util.NoMimeTypeException;
import de.shop.util.interceptor.Log;


/**
 * @author <a>Team 8</a>
 */
@Provider
@Log
public class NoMimeTypeExceptionMapper implements ExceptionMapper<NoMimeTypeException> {
	@Context
	private HttpHeaders headers;
	
	@Inject
	private Messages messages;
	
	@Override
	public Response toResponse(NoMimeTypeException e) {
		final String msg = messages.getMessage(headers, e.getMessageKey());
		final Response response = Response.status(BAD_REQUEST)
		                                  .type(TEXT_PLAIN)
		                                  .entity(msg)
		                                  .build();
		return response;
	}

}
