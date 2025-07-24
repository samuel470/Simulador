package com.pgbl.simulator.controller;

import com.pgbl.simulator.dto.SimulacaoRequest;
import com.pgbl.simulator.dto.SimulacaoResponse;
import com.pgbl.simulator.service.PgblSimuladorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller responsável pelos endpoints de simulação PGBL
 */
@RestController
@RequestMapping("/pgbl")
@CrossOrigin(origins = "*")
@Tag(name = "PGBL Simulator", description = "API para simulação de incentivo fiscal PGBL")
public class PgblController {

    @Autowired
    private PgblSimuladorService pgblSimuladorService;

    @Operation(
        summary = "Simular incentivo fiscal PGBL",
        description = "Realiza simulação completa de incentivo fiscal para PGBL com base nos dados fornecidos"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Simulação realizada com sucesso",
            content = @Content(schema = @Schema(implementation = SimulacaoResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados de entrada inválidos",
            content = @Content(schema = @Schema(implementation = com.pgbl.simulator.dto.ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno do servidor",
            content = @Content(schema = @Schema(implementation = com.pgbl.simulator.dto.ErrorResponse.class))
        )
    })
    @PostMapping("/simular")
    public ResponseEntity<SimulacaoResponse> simular(
        @Parameter(description = "Dados para simulação PGBL", required = true)
        @Valid @RequestBody SimulacaoRequest request) {
        
        SimulacaoResponse response = pgblSimuladorService.simular(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Health check da API",
        description = "Verifica se a API está funcionando corretamente"
    )
    @ApiResponse(
        responseCode = "200",
        description = "API funcionando corretamente"
    )
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("API PGBL Simulator está funcionando!");
    }

    @Operation(
        summary = "Informações da API",
        description = "Retorna informações básicas sobre a API"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Informações da API"
    )
    @GetMapping("/info")
    public ResponseEntity<Object> info() {
        return ResponseEntity.ok(new Object() {
            public final String nome = "PGBL Simulator API";
            public final String versao = "1.0.0";
            public final String descricao = "API para simulação de incentivo fiscal PGBL";
            public final String[] funcionalidades = {
                "Cálculo de IR com e sem PGBL",
                "Suporte a dependentes",
                "Cálculo de valor ideal de contribuição",
                "Simulação mensal e anual",
                "Validação de regras fiscais brasileiras"
            };
        });
    }
}

