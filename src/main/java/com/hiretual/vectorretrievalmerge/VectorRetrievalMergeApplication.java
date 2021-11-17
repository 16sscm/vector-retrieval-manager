package com.hiretual.vectorretrievalmerge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
public class VectorRetrievalMergeApplication {

	public static void main(String[] args) {
		SpringApplication.run(VectorRetrievalMergeApplication.class, args);
	}

}
