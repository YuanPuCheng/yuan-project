package com.zihui.cwoa;

import com.zihui.cwoa.system.thread.DelRedisThread;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@MapperScan("com.zihui.cwoa.*.dao")
@EnableCaching//开启redis
@SpringBootApplication
@ImportResource(locations={"classpath:myKaptcha.xml"})
public class CwoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CwoaApplication.class, args);
		ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(1);
			/**
			 * 第一个参数  工作线程。
			 * 第二个参数几秒后开始运行，
			 * 第三个参数几秒循环执行
			 * 第四个参数，时间类型
			 */
			System.out.println("redis线程已启动------------");
			schedule.scheduleAtFixedRate(new DelRedisThread(),
					0, 3, TimeUnit.HOURS);
	}

}
