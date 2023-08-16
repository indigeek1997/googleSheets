package com.satyam.interview;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class InterviewRupicardGoogleSheetsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext  context =
				SpringApplication.run(InterviewRupicardGoogleSheetsApplication.class, args);
		GoogleAuthorizeUtil util = new GoogleAuthorizeUtil();
		try {
			util.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
