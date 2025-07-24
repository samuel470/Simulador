package com.pgbl.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;

/**
 * Classe principal da aplicação PGBL Simulator
 */
@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "PGBL Simulator API",
        version = "1.0.0",
        description = "API completa para simulação de incentivo fiscal PGBL com suporte a dependentes e todas as regras fiscais brasileiras",
        contact = @Contact(
            name = "PGBL Simulator Team",
            email = "contato@pgblsimulator.com"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    )
)
public class PgblSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PgblSimulatorApplication.class, args);
        System.out.println("\n=================================================");
        System.out.println("🚀 PGBL Simulator API iniciada com sucesso!");
        System.out.println("📖 Documentação: http://localhost:8080/api/swagger-ui.html");
        System.out.println("🔍 API Docs: http://localhost:8080/api/api-docs");
        System.out.println("❤️  Health Check: http://localhost:8080/api/pgbl/health");
        System.out.println("=================================================\n");
    }
}

