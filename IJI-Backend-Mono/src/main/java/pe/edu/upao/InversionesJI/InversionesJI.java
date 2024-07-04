package pe.edu.upao.InversionesJI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties
@EnableFeignClients(basePackages = "pe.edu.upao.InversionesJI.Feign")
public class InversionesJI {

	public static void main(String[] args) {
		SpringApplication.run(InversionesJI.class, args);
	}

}
