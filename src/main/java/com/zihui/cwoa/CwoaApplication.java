package com.zihui.cwoa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

@MapperScan("com.zihui.cwoa.*.dao")
//@EnableCaching//开启redis
@SpringBootApplication
@ImportResource(locations={"classpath:myKaptcha.xml"})
public class CwoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CwoaApplication.class, args);
	}

}
