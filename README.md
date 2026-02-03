# nk-order-process-ms

Microserviço responsável pelo processamento e consulta de pedidos, desenvolvido como parte de um teste técnico com foco em **arquitetura, boas práticas, escalabilidade e idempotência**.

O serviço recebe pedidos via API, calcula valores de forma simples (valor unitário × quantidade), evita duplicidades e disponibiliza os dados para consumo por outros sistemas.

---

## Stack Tecnológica

- Java 21
- Spring Boot 3.4.x
- Spring Web
- Spring Data MongoDB
- Jakarta Validation
- Spring Actuator
- Springdoc OpenAPI (Swagger)
- Docker + Docker Compose
- Maven

---

## Arquitetura

O projeto segue uma abordagem inspirada em **Clean Architecture / Hexagonal Architecture**, separando claramente responsabilidades:

```
domain
 ├─ model
 └─ exception

application
 ├─ port
 │   ├─ in
 │   └─ out
 └─ usecase

infrastructure
 ├─ persistence
 │   └─ mongo
 ├─ web
 │   ├─ controller
 │   ├─ dto
 │   └─ handler
 └─ config

bootstrap
```

### Princípios aplicados
- Domínio independente de frameworks
- Inversão de dependência via ports
- Regras de negócio isoladas
- Camada web apenas como adaptadora
- Persistência desacoplada do domínio
- Código preparado para evolução e escala

---

## Modelagem do Pedido

- Um pedido sempre contém **todos os seus itens**
- Cada item possui:
    - SKU
    - Nome
    - Valor unitário
    - Quantidade
- O valor total do pedido é a soma de:
  ```
  unitPrice × quantity
  ```
- O total de itens é a soma das quantidades

---

## Idempotência e Duplicidade

Para evitar pedidos duplicados, o serviço utiliza **idempotência por chave externa**:

- Cada pedido deve conter:
    - `sourceSystem`
    - `externalOrderId`

Existe um **índice único composto** no MongoDB:

```
(sourceSystem, externalOrderId)
```

Fluxo:
- Se um pedido com a mesma chave externa já existir, o serviço retorna o pedido existente
- Caso contrário, um novo pedido é criado

---

## Volume e Escalabilidade

O serviço foi pensado para cenários de alto volume (ex.: centenas de milhares de pedidos/dia):

- MongoDB como banco documental
- Escrita eficiente e indexada
- Aplicação stateless
- Escala horizontal simples

---

## Endpoints Disponíveis

### Criar pedido
`POST /orders`

### Buscar pedido por ID
`GET /orders/{id}`

### Buscar pedido por chave externa
`GET /orders/by-external?sourceSystem=ECOMMERCE&externalOrderId=ORDER-123`

---

## Swagger / OpenAPI

```
http://localhost:8080/swagger
```

---

## Healthcheck

```
GET /actuator/health
```

---

## Executar o Projeto

### Com Docker
```bash
docker compose up --build
```

### Local
```bash
./mvnw spring-boot:run
```

---

## Testes

```bash
./mvnw clean test
```

---

## Git Flow

- main
- develop
- feature/*

---

## Considerações Finais

Projeto desenvolvido com foco em arquitetura, clareza, manutenibilidade e escalabilidade.
