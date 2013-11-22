package de.shop.util.concurrency;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.inject.Produces;

/**
 * @author <a>Team 8</a>
 */
public class ManagedExecutorServiceProducer {
	@Resource
	@Produces
	private ManagedExecutorService managedExecutorService;
	
	// fuer zeitlich versetzte oder periodische Ausfuehrungen
	//@Resource
	//@Produces
	//private ManagedScheduledExecutorService managedScheduledExecutorService;
}
