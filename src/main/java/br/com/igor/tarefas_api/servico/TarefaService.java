package br.com.igor.tarefas_api.servico;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.igor.tarefas_api.dto.TarefaRequest;
import br.com.igor.tarefas_api.modelo.Tarefa;
import br.com.igor.tarefas_api.repositorio.TarefaRepository;

@Service
public class TarefaService {

    private final TarefaRepository repositorio;

    // o Spring entrega o repositório pronto aqui
    public TarefaService(TarefaRepository repositorio) {
        this.repositorio = repositorio;
    }

    // concluida == null: todas | true: só concluídas | false: só pendentes
    public List<Tarefa> listar(Boolean concluida) {
        List<Tarefa> todas = repositorio.listarTodas();
        if (concluida == null) {
            return todas;
        }

        List<Tarefa> filtradas = new ArrayList<>();
        for (Tarefa tarefa : todas) {
            if (tarefa.isConcluida() == concluida) {
                filtradas.add(tarefa);
            }
        }
        return filtradas;
    }

    // devolve null se não existir
    public Tarefa buscar(Long id) {
        return repositorio.buscarPorId(id);
    }

    public Tarefa criar(TarefaRequest dados) {
        validar(dados);
        Tarefa tarefa = new Tarefa(dados.titulo().trim(), limpar(dados.descricao()));
        return repositorio.salvar(tarefa);
    }

    // devolve a tarefa alterada, ou null se o id não existir
    public Tarefa atualizar(Long id, TarefaRequest dados) {
        Tarefa tarefa = repositorio.buscarPorId(id);
        if (tarefa == null) {
            return null;
        }

        validar(dados);   // valida antes de alterar
        tarefa.setTitulo(dados.titulo().trim());
        tarefa.setDescricao(limpar(dados.descricao()));
        return repositorio.salvar(tarefa);
    }

    // devolve a tarefa concluída, ou null se o id não existir
    public Tarefa concluir(Long id) {
        Tarefa tarefa = repositorio.buscarPorId(id);
        if (tarefa == null) {
            return null;
        }

        tarefa.concluir();
        return repositorio.salvar(tarefa);
    }

    // devolve true se removeu, false se o id não existia
    public boolean remover(Long id) {
        return repositorio.remover(id);
    }

    // ---------- regras de validação ----------

    private void validar(TarefaRequest dados) {
        if (dados.titulo() == null || dados.titulo().isBlank()) {
            throw new IllegalArgumentException("O título é obrigatório.");
        }
        if (dados.titulo().trim().length() > 100) {
            throw new IllegalArgumentException("O título pode ter no máximo 100 caracteres.");
        }
    }

    // a descrição é opcional: se não vier, fica vazia
    private String limpar(String descricao) {
        return descricao == null ? "" : descricao.trim();
    }
}