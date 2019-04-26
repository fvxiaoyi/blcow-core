package cn.blcow.core.event;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import cn.blcow.core.utils.Assert;

public class DomainEventJpaTransactionSynchronization implements TransactionSynchronization {

	/**
	 * FIXME 事务提交前事件也持久化
	 */
	@Override
	public void beforeCommit(boolean readOnly) {
		/*if (TransactionSynchronizationManager.hasResource(EventBus.DOMAIN_EVENTS_KEY)) {
			List<AbstractDomainEvent<?>> events = (List<AbstractDomainEvent<?>>) TransactionSynchronizationManager
					.getResource(EventBus.DOMAIN_EVENTS_KEY);
			if (!events.isEmpty()) {
				EntityManager entityManager = getEntityManager();
				String transactionId = UUID.randomUUID().toString();
				events.forEach(e -> {
					e.setTransactionId(transactionId);
					entityManager.persist(e);
				});
			}
		}*/
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterCommit() {
		if (TransactionSynchronizationManager.hasResource(EventBus.DOMAIN_EVENTS_KEY)) {
			List<AbstractDomainEvent<?>> events = (List<AbstractDomainEvent<?>>) TransactionSynchronizationManager
					.getResource(EventBus.DOMAIN_EVENTS_KEY);
			TransactionSynchronizationManager.unbindResource(EventBus.DOMAIN_EVENTS_KEY);
			events.forEach(event -> {
				if (!event.isDispatch()) {
					EventBus.INSTANCE.getApplicationEventPublisher().publishEvent(event);
					/*event.setDispatch(true);
					entityManager.merge(event);*/
				}
			});
		}
	}

	protected EntityManager getEntityManager() {
		Optional<Object> optional = TransactionSynchronizationManager.getResourceMap().values().stream()
				.filter(p -> p instanceof EntityManagerHolder).findFirst();
		Assert.requireTrue(optional.isPresent(), "current transaction no EntityManagerHolder.");
		return ((EntityManagerHolder) optional.get()).getEntityManager();
	}

}
