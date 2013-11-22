package de.shop.util.persistence;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author <a>Team 8</a>
 */
public class EntityManagerProducer {
	@PersistenceContext
	@Produces
	private EntityManager em;
}
