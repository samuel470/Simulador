package com.pgbl.simulator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para resposta de erro da API
 */
@Schema(description = "Resposta de erro da API")
public class ErrorResponse {

    @Schema(description = "Timestamp do erro", example = "2025-01-20T10:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Código de status HTTP", example = "400")
    private int status;

    @Schema(description = "Mensagem de erro", example = "Dados de entrada inválidos")
    private String error;

    @Schema(description = "Mensagem detalhada", example = "Renda bruta é obrigatória")
    private String message;

    @Schema(description = "Caminho da requisição", example = "/api/pgbl/simular")
    private String path;

    @Schema(description = "Lista de erros de validação")
    private List<String> validationErrors;

    // Construtores
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(int status, String error, String message, String path) {
        this();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Getters e Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}

