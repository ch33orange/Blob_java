package com.agency;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author ch33orange
 */
@SpringBootApplication
public class DemoApplication {

	protected static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	/**
	 * 系统所有数据在此处初始化
	 * SysParamInitService中提供部分初始化方法
	 */
	public @PostConstruct void  init() {
		logger.info("=================初始化数据字典到缓存中===================");
	}

	@PreDestroy
	public void dostory() {
		logger.info("=================系统关闭注销===================");
	}

	public static void main(String[] args) {
		// 启动程序
		SpringApplication.run(DemoApplication.class, args);
	}
}
