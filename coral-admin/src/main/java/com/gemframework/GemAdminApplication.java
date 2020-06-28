
package com.gemframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        DataSourceAutoConfiguration.class
})
public class GemAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(GemAdminApplication.class, args);
    }

}
