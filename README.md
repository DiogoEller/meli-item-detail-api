# Meli Item Detail API

## Visão Geral

API RESTful para gerenciamento de produtos, incluindo busca por produtos relacionados via categoria. Persistência em arquivo JSON. Documentação automática via Swagger.

## Diagrama de fluxo (Mermaid)

Abaixo está um diagrama detalhando o fluxo para a página de detalhe de um produto no site do Mercado Livre, mostrando como ela consome nossa API e o que acontece internamente:

```mermaid
sequenceDiagram
    participant User as Usuário (Navegador)
    participant MLWeb as Mercado Livre (Frontend)
    participant API as Meli Item Detail API
    participant Service as ProductService
    participant Repo as ProductRepository
    participant JSON as products.json

    User->>MLWeb: Acessa página de detalhe do produto
    MLWeb->>API: GET /api/v1/products/{id}
    API->>Service: Chama getProductById(id)
    Service->>Repo: Busca produto por id
    Repo->>JSON: Lê arquivo products.json
    JSON-->>Repo: Retorna dados do produto
    Repo-->>Service: Retorna produto
    Service-->>API: Retorna ProductDto
    API-->>MLWeb: Retorna dados do produto (JSON)
    MLWeb-->>User: Exibe detalhes do produto

    Note over MLWeb,API: Se o usuário clicar em "Produtos relacionados"
    MLWeb->>API: GET /api/v1/products/related?category={categoria}
    API->>Service: Chama getRelatedItems(categoria)
    Service->>Repo: Busca produtos por categoria
    Repo->>JSON: Lê arquivo products.json
    JSON-->>Repo: Retorna lista de produtos da categoria
    Repo-->>Service: Retorna lista de produtos
    Service-->>API: Retorna lista de ProductDto
    API-->>MLWeb: Retorna produtos relacionados (JSON)
    MLWeb-->>User: Exibe produtos
```

## Endpoints Principais

| Método | Endpoint                        | Descrição                                 |
|--------|---------------------------------|-------------------------------------------|
| GET    | `/api/v1/products`              | Lista todos os produtos                   |
| GET    | `/api/v1/products/{id}`         | Busca produto por ID                      |
| POST   | `/api/v1/products`              | Cria um novo produto                      |
| PUT    | `/api/v1/products/{id}`         | Atualiza um produto                       |
| DELETE | `/api/v1/products/{id}`         | Remove um produto                         |
| GET    | `/api/v1/products/related`      | Lista produtos relacionados por categoria |

## Decisões Técnicas e Estratégias Arquiteturais

- **Stack:** Java 17, Spring Boot 3.x, Lombok, Jackson, SpringDoc OpenAPI, JUnit 5, Mockito
- **Persistência:** O "banco de dados" é um arquivo JSON configurável, facilitando testes, portabilidade e entendimento do fluxo de dados.
- **Arquitetura:** Separação por responsabilidade (MVC, DTO, Mapper, Exception, Interface), seguindo o padrão Model-View-Controller.
- **SOLID:** Princípios SOLID aplicados, com separação clara de responsabilidades, uso de interfaces para desacoplamento e facilidade de testes/mocks.
- **RESTful:** Endpoints seguem convenções REST, utilizando métodos HTTP adequados, URIs semânticas e respostas padronizadas.
- **Tratamento de Exceção:** Handler global (`@ControllerAdvice`) para padronizar respostas de erro e facilitar o diagnóstico de problemas.
- **Testes:** Unitários e integração cobrindo casos de sucesso e erro, utilizando JUnit 5 e Mockito.
- **Documentação:** Swagger/OpenAPI para documentação automática dos endpoints.
- **GenAI & Ferramentas Modernas:** O projeto foi acelerado com uso de ferramentas de IA (GitHub Copilot, ChatGPT, Mermaid) para geração de código, documentação e testes.

## Setup

Veja o arquivo `run.md` para instruções detalhadas de execução.