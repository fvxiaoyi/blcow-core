package cn.blcow.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.blcow.core.properties.SqlQueryServiceProperties;
import cn.blcow.core.query.IQueryParser;
import cn.blcow.core.query.IQueryService;
import cn.blcow.core.query.impl.JpaSqlQueryService;
import cn.blcow.core.query.impl.MyBatisQueryParser;

@Configuration
@EnableConfigurationProperties({ SqlQueryServiceProperties.class })
public class MybatisQueryServiceConfig {

	@Bean
	public IQueryParser myBatisQueryParser() {
		return new MyBatisQueryParser();
	}

	@Bean
	public IQueryService sqlQueryService() {
		return new JpaSqlQueryService();
	}

}
