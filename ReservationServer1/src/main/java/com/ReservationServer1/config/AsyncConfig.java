package com.ReservationServer1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
	
	@Bean(name = "threadPoolTaskExecutor")
    ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3); // 동시에 실행될 스레드 수
        executor.setMaxPoolSize(5); // 최대 스레드 수
        executor.setQueueCapacity(10); // 대기 큐 크기
        executor.setThreadNamePrefix("MyAsyncThread-");
        executor.initialize();
        return executor;
    }
	
}
