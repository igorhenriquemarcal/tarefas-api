package br.com.igor.tarefas_api.repositorio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.com.igor.tarefas_api.modelo.Tarefa;

// guarda as tarefas na memória (por enquanto, sem banco de dados)
@Repository
public class TarefaRepository {

    private final Map<Long, Tarefa> tarefas = new ConcurrentHashMap<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    // se a tarefa ainda não tem id, gera um novo; depois guarda
    public Tarefa salvar(Tarefa tarefa) {
        if (tarefa.getId() == null) {
            tarefa.setId(proximoId.getAndIncrement());
        }
        tarefas.put(tarefa.getId(), tarefa);
        return tarefa;
    }

    // devolve null se não existir
    public Tarefa buscarPorId(Long id) {
        return tarefas.get(id);
    }

    // devolve todas, em ordem de id
    public List<Tarefa> listarTodas() {
        List<Tarefa> lista = new ArrayList<>(tarefas.values());
        lista.sort(Comparator.comparing(Tarefa::getId));
        return lista;
    }

    // devolve true se removeu, false se o id não existia
    public boolean remover(Long id) {
        return tarefas.remove(id) != null;
    }
}