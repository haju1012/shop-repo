package de.shop.util.rest;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.UriInfo;

/**
 * @author <a>Team 8</a>
 */
@ApplicationScoped
public class UriHelper {
	public URI getUri(Class<?> clazz, UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder()
				      .path(clazz)
				      .build();
	}

	public URI getUri(Class<?> clazz, String methodName, Long id, UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder()
		              .path(clazz)
		              .path(clazz, methodName)
		              .build(id);
	}
}
