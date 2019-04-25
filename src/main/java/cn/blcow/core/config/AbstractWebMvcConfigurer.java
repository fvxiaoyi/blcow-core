package cn.blcow.core.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.blcow.core.mvc.MyHandlerExceptionResolver;
import cn.blcow.core.properties.CorsProperties;

@EnableConfigurationProperties({ CorsProperties.class })
public abstract class AbstractWebMvcConfigurer implements WebMvcConfigurer {

	@Autowired
	private CorsProperties corsProperties;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(corsProperties.getUrls()).allowedHeaders("*").allowedMethods(CorsConfiguration.ALL)
				.maxAge(3600);
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		MyHandlerExceptionResolver res = new MyHandlerExceptionResolver();
		res.setPreventResponseCaching(true);
		res.setOrder(Ordered.LOWEST_PRECEDENCE);
		resolvers.add(res);
	}
}
