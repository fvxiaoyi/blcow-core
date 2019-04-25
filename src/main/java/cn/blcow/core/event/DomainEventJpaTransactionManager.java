package cn.blcow.core.event;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DomainEventJpaTransactionManager extends JpaTransactionManager {

	private static final long serialVersionUID = -4248067576725210194L;

	@Override
	protected void prepareSynchronization(DefaultTransactionStatus status, TransactionDefinition definition) {
		super.prepareSynchronization(status, definition);
		if (!TransactionSynchronizationManager.getSynchronizations().stream()
				.anyMatch(p -> p instanceof DomainEventTransactionSynchronization)) {
			TransactionSynchronizationManager.registerSynchronization(new DomainEventTransactionSynchronization());
		}
	}

}
