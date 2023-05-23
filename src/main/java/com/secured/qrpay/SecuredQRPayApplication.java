package com.secured.qrpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SecuredQRPayApplication {
	public static void main(String[] args) {
		SpringApplication.run(SecuredQRPayApplication.class, args);
		System.out.println("Application started successfully!");
	}
}
