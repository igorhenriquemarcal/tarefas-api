package br.com.igor.tarefas_api.dto;

// a "ficha" que chega no POST e no PUT /tarefas
public record TarefaRequest(
        String titulo,
        String descricao
) {
}