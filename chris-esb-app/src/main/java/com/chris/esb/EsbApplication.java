package com.chris.esb;

import com.chris.esb.dao.datasources.DynamicDataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@Import({DynamicDataSourceConfig.class})
@ComponentScan(basePackages={"com.chris.esb"})
public class EsbApplication {

    /**
     * Main method to start the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(EsbApplication.class, args);
    }

}
