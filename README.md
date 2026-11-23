# API de Tarefas

API REST para gerenciar uma lista de tarefas, desenvolvida com **Java** e **Spring Boot** como parte dos meus estudos de back-end.

## Tecnologias

- Java 25
- Spring Boot 4.2.0 (Spring Web)
- Maven

## Arquitetura

O projeto segue a separação em camadas:

```
Controller  →  Service  →  Repository
(HTTP)         (regras)    (armazenamento)
```

- **Controller**: recebe as requisições HTTP e escolhe o código de status da resposta.
- **Service**: concentra as regras de negócio e as validações.
- **Repository**: guarda e busca as tarefas. Nesta versão, os dados ficam em memória.
- **DTO** (`TarefaRequest`): define exatamente quais campos a API aceita na entrada.

## Endpoints

| Método | Endpoint | Descrição | Respostas |
|---|---|---|---|
| GET | `/tarefas` | Lista todas as tarefas | 200 |
| GET | `/tarefas?concluida=true` | Filtra por status (`true` ou `false`) | 200 |
| GET | `/tarefas/{id}` | Busca uma tarefa | 200, 404 |
| POST | `/tarefas` | Cria uma tarefa | 201, 400 |
| PUT | `/tarefas/{id}` | Altera título e descrição | 200, 400, 404 |
| PATCH | `/tarefas/{id}/concluir` | Marca a tarefa como concluída | 200, 404 |
| DELETE | `/tarefas/{id}` | Remove uma tarefa | 204, 404 |

### Exemplo: criar uma tarefa

**Requisição** `POST /tarefas`

```json
{
  "titulo": "Estudar Spring Boot",
  "descricao": "Controller, Service e Repository"
}
```

**Resposta** `201 Created`

```json
{
  "id": 1,
  "titulo": "Estudar Spring Boot",
  "descricao": "Controller, Service e Repository",
  "concluida": false,
  "criadaEm": "2026-11-22T10:30:00"
}
```

### Exemplo: erro de validação

**Requisição** `POST /tarefas` sem título

**Resposta** `400 Bad Request`

```json
{
  "erro": "O título é obrigatório."
}
```

## Regras de negócio

- O título é obrigatório e pode ter no máximo 100 caracteres.
- A descrição é opcional.
- O `id`, a data de criação e o status são definidos pela própria API, nunca pelo cliente.
- Toda tarefa nasce pendente; a conclusão é feita por um endpoint próprio.

## Como rodar

Pré-requisito: JDK 25 instalado.

```bash
git clone https://github.com/igorhenriquemarcal/tarefas-api.git
cd tarefas-api
./mvnw spring-boot:run
```

No Windows, use `mvnw.cmd spring-boot:run`.

A API sobe em `http://localhost:8080`.

## O que pratiquei neste projeto

- Criação de uma API REST com Spring Boot
- Separação em camadas (Controller, Service e Repository)
- Injeção de dependência pelo construtor
- DTOs com `record`
- Uso dos métodos HTTP GET, POST, PUT, PATCH e DELETE
- Códigos de status adequados para cada situação (200, 201, 204, 400 e 404)
- Validação de dados antes de qualquer alteração
- Geração de id segura para acessos simultâneos (`AtomicLong` e `ConcurrentHashMap`)

## Próximos passos

- [ ] Persistência com Spring Data JPA e banco de dados
- [ ] Validação com Bean Validation (`@NotBlank`, `@Size`)
- [ ] Tratamento global de erros com `@ControllerAdvice`
- [ ] Testes automatizados com JUnit e MockMvc
- [ ] Documentação com Swagger/OpenAPI