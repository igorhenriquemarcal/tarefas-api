package br.com.igor.tarefas_api.modelo;

import java.time.LocalDateTime;

public class Tarefa {

    private Long id;
    private String titulo;
    private String descricao;
    private boolean concluida;
    private LocalDateTime criadaEm;

    // toda tarefa nasce pendente e com a data de agora;
    // o id é definido depois, por quem guarda a tarefa
    public Tarefa(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = false;
        this.criadaEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public LocalDateTime getCriadaEm() {
        return criadaEm;
    }

    // em vez de um setConcluida(true), um método com nome de ação
    public void concluir() {
        this.concluida = true;
    }
}