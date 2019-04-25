package cn.blcow.core.event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class EventBus implements ApplicationEventPublisherAware {

	public static String DOMAIN_EVENTS_KEY = "domain_events";

	protected static EventBus INSTANCE;

	private ApplicationEventPublisher applicationEventPublisher;

	public EventBus() {
		EventBus.INSTANCE = this;
	}

	@SuppressWarnings("unchecked")
	public static void post(ApplicationEvent e) {
		if (e instanceof AbstractDomainEvent) {
			List<ApplicationEvent> events = null;
			if (TransactionSynchronizationManager.hasResource(DOMAIN_EVENTS_KEY)) {
				events = (List<ApplicationEvent>) TransactionSynchronizationManager.getResource(DOMAIN_EVENTS_KEY);
				events.add(e);
			} else {
				events = new ArrayList<>();
				events.add(e);
				TransactionSynchronizationManager.bindResource(DOMAIN_EVENTS_KEY, events);
			}

		} else {
			INSTANCE.getApplicationEventPublisher().publishEvent(e);
		}
	}

	public ApplicationEventPublisher getApplicationEventPublisher() {
		return applicationEventPublisher;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

}
