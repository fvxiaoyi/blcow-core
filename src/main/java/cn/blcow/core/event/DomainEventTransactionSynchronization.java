package cn.blcow.core.event;

import java.util.List;

import org.springframework.context.ApplicationEvent;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DomainEventTransactionSynchronization implements TransactionSynchronization {

	@Override
	public void beforeCommit(boolean readOnly) {
		//FIXME 持久化事件, 发送mq 失败回滚
		TransactionSynchronization.super.beforeCommit(readOnly);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterCommit() {
		if (TransactionSynchronizationManager.hasResource(EventBus.DOMAIN_EVENTS_KEY)) {
			List<ApplicationEvent> events = (List<ApplicationEvent>) TransactionSynchronizationManager
					.getResource(EventBus.DOMAIN_EVENTS_KEY);
			events.forEach(EventBus.INSTANCE.getApplicationEventPublisher()::publishEvent);
			TransactionSynchronizationManager.unbindResource(EventBus.DOMAIN_EVENTS_KEY);
		}
	}

}
