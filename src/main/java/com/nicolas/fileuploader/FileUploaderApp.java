package com.nicolas.fileuploader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableConfigurationProperties
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class FileUploaderApp {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(FileUploaderApp.class, args);
  }
}
