package de.shop.util.web;

import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;


/**
 * @author <a>Team 8</a>
 */
public class Producers {
	@Produces
	@RequestScoped
	// http://docs.oracle.com/javaee/6/api/index.html?javax/faces/context/FacesContext.html
	private static FacesContext facesContext() {
		final FacesContext ctx = FacesContext.getCurrentInstance();
		if (ctx == null) {
			throw new ContextNotActiveException("FacesContext is not active");
		}
		return ctx;
	}
	
	@Produces
	@RequestScoped
	// http://docs.oracle.com/javaee/6/api/index.html?javax/faces/context/Flash.html
	private static Flash flash(final FacesContext ctx) {
		return ctx.getExternalContext().getFlash();
	}
}
