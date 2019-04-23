package cn.blcow.core.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class EventBus implements ApplicationEventPublisherAware {

	private static EventBus INSTANCE;

	private ApplicationEventPublisher applicationEventPublisher;

	@SuppressWarnings("static-access")
	public EventBus() {
		this.INSTANCE = this;
	}

	public static void post(ApplicationEvent e) {
		INSTANCE.getApplicationEventPublisher().publishEvent(e);
	}

	public ApplicationEventPublisher getApplicationEventPublisher() {
		return applicationEventPublisher;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

}
