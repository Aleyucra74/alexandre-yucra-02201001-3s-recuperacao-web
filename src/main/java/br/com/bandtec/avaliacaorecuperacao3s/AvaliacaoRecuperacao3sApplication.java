package br.com.bandtec.avaliacaorecuperacao3s;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AvaliacaoRecuperacao3sApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvaliacaoRecuperacao3sApplication.class, args);
	}

}
