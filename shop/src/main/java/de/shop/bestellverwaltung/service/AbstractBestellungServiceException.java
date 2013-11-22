package de.shop.bestellverwaltung.service;

import de.shop.util.AbstractShopException;

/**
 * @author <a>Team 8</a>
 */
public abstract class AbstractBestellungServiceException extends AbstractShopException {
	private static final long serialVersionUID = -626920099480136224L;

	public AbstractBestellungServiceException(String msg) {
		super(msg);
	}
}
