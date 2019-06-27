package cn.blcow.core.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.TaskUtils;

@EnableScheduling
@EnableAsync
@Import(value = { JpaConfig.class, MybatisQueryServiceConfig.class, COSClientConfig.class })
public class BaseConfig implements SchedulingConfigurer {

	@Bean
	public ThreadPoolTaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler taskExecutor = new ThreadPoolTaskScheduler();
		taskExecutor.setThreadPriority(Thread.MAX_PRIORITY);
		taskExecutor.setThreadGroupName("DefaultCoreThreadGroup");
		taskExecutor.setThreadNamePrefix("DefaultCore");
		taskExecutor.setPoolSize(Runtime.getRuntime().availableProcessors() + 1);
		taskExecutor.setErrorHandler(TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		taskExecutor.setAwaitTerminationSeconds(60 * 2);
		taskExecutor.initialize();
		return taskExecutor;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskScheduler());
	}
}
