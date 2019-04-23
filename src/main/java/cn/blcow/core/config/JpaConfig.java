package cn.blcow.core.config;

import java.sql.Connection;

import javax.persistence.ValidationMode;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.blcow.core.domain.Repository;
import cn.blcow.core.event.EventBus;

@Configuration
@EntityScan("com.**.apps.**.model")
public class JpaConfig {

	@Bean
	public Repository repository() {
		return new Repository();
	}

	@Bean
	public EventBus eventBus() {
		return new EventBus();
	}

	@Bean
	public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
		return prop -> {
			prop.putIfAbsent(AvailableSettings.JPA_VALIDATION_MODE, ValidationMode.NONE);
			prop.putIfAbsent(AvailableSettings.ISOLATION, Connection.TRANSACTION_READ_COMMITTED);
			prop.putIfAbsent(AvailableSettings.STATEMENT_FETCH_SIZE, 64);
		};
	}
}
