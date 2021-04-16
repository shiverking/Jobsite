package project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @author ：闫崇傲
 * @description：启动类
 * @date ：2021/4/15 23:56
 */
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
public class JobSiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobSiteApplication.class, args);
    }
}
