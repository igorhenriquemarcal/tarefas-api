package br.com.igor.tarefas_api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.igor.tarefas_api.dto.TarefaRequest;
import br.com.igor.tarefas_api.modelo.Tarefa;
import br.com.igor.tarefas_api.servico.TarefaService;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService servico;

    public TarefaController(TarefaService servico) {
        this.servico = servico;
    }

    // GET /tarefas   ou   GET /tarefas?concluida=true   ou   ?concluida=false
    @GetMapping
    public List<Tarefa> listar(@RequestParam(required = false) Boolean concluida) {
        return servico.listar(concluida);
    }

    // GET /tarefas/1
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscar(@PathVariable Long id) {
        Tarefa tarefa = servico.buscar(id);
        if (tarefa == null) {
            return ResponseEntity.notFound().build();                  // 404
        }
        return ResponseEntity.ok(tarefa);                              // 200
    }

    // POST /tarefas
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody TarefaRequest dados) {
        try {
            Tarefa tarefa = servico.criar(dados);
            return ResponseEntity.status(HttpStatus.CREATED).body(tarefa);   // 201
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));   // 400
        }
    }

    // PUT /tarefas/1
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody TarefaRequest dados) {
        try {
            Tarefa tarefa = servico.atualizar(id, dados);
            if (tarefa == null) {
                return ResponseEntity.notFound().build();              // 404
            }
            return ResponseEntity.ok(tarefa);                          // 200
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));   // 400
        }
    }

    // PATCH /tarefas/1/concluir
    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Tarefa> concluir(@PathVariable Long id) {
        Tarefa tarefa = servico.concluir(id);
        if (tarefa == null) {
            return ResponseEntity.notFound().build();                  // 404
        }
        return ResponseEntity.ok(tarefa);                              // 200
    }

    // DELETE /tarefas/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!servico.remover(id)) {
            return ResponseEntity.notFound().build();                  // 404
        }
        return ResponseEntity.noContent().build();                     // 204
    }
}