# Baozi Store — API REST (UNINTER)

Projeto da disciplina Desenvolvimento Web Back-End. Objetivo: cadastrar, consultar, listar, atualizar e excluir **clientes, produtos e pedidos**. Cada pedido referencia 1 cliente e 1 produto, informando a quantidade comprada.

## Tecnologias

- Java 17
- Spring Boot 3.2.0
- Spring Web / MVC
- Spring Data JPA / Hibernate
- H2, banco relacional em arquivo (permitido pelo enunciado)
- JSON e Postman

## Abrir no Eclipse

1. Extraia o ZIP em uma pasta de sua preferência.
2. No Eclipse: **File > Import > Maven > Existing Maven Projects > Next**.
3. Em `Root Directory`, selecione a pasta `BaoziStore_API_REST` (a que contém o `pom.xml`).
4. Clique em **Finish** e aguarde as dependências Maven serem resolvidas.
5. Abra `src/main/java/br/com/baozistore/BaoziStoreApplication.java` e use **Run As > Java Application** (ou Spring Boot App, se disponível).
6. Espere aparecer `Started BaoziStoreApplication` no Console.
7. No navegador abra `http://localhost:8080/clientes`. Deve retornar `[]` quando não houver clientes cadastrados.

> O primeiro carregamento do Maven exige internet. A porta padrão é 8080.

## Postman

Importe o arquivo `BaoziStore_Postman_Collection.json`. Altere a variável da coleção `nomeClienteRU` para **seu primeiro nome imediatamente seguido do RU**, conforme o enunciado. Exemplo meramente fictício: `Aluno1234567`.

Rode nessa ordem para gravar IDs automaticamente nas variáveis da coleção:

1. `01 Clientes > POST Criar cliente`.
2. `02 Produtos > POST Criar produto`.
3. `03 Pedidos > POST Criar pedido`.
4. `GET listar todos` e `GET por ID` das três entidades.
5. Os PUT são opcionais. Caso use, execute antes da exclusão.
6. **Só depois dos prints**, faça `04 Exclusoes > DELETE pedido`, depois cliente, depois produto. O pedido deve ser excluído antes por causa das chaves estrangeiras.

## Endpoints

| Entidade | POST criar | GET todos | GET por ID | PUT atualizar (extra) | DELETE |
|---|---|---|---|---|---|
| Cliente | `/clientes` | `/clientes` | `/clientes/{id}` | `/clientes/{id}` | `/clientes/{id}` |
| Produto | `/produtos` | `/produtos` | `/produtos/{id}` | `/produtos/{id}` | `/produtos/{id}` |
| Pedido | `/pedidos` | `/pedidos` | `/pedidos/{id}` | `/pedidos/{id}` | `/pedidos/{id}` |

Exemplo de JSON POST Cliente:

```json
{"nome":"SEUNOME1234567","clienteDesde":"2026-11-17"}
```

Exemplo de JSON POST Produto:

```json
{"nome":"Baozi tradicional","preco":8.50,"estoque":true}
```

Exemplo de JSON POST Pedido (altere os IDs conforme os cadastros):

```json
{"clienteId":1,"produtoId":1,"quantidade":3}
```

O H2 é persistido em arquivo `baozi-store-db.mv.db` na pasta de execução. O campo `estoque` é **Boolean**, de acordo com o enunciado: `true` = disponível, `false` = indisponível. **Não é quantidade de estoque**.

Para visualizar tabelas com a aplicação em execução: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:file:./baozi-store-db;DB_CLOSE_ON_EXIT=FALSE`, usuário `sa`, senha vazia). O banco da aplicação pode manter conexão aberta; não é obrigatório acessar o console para obter nota.

## Diagrama e relatório

No PDF de entrega, use as seções do modelo oficial:

1. Situação fictícia (o nome do cliente deve ser **Nome+RU** e aparecer no print Postman).
2. Diagrama de casos de uso UML (ator **Usuário da API** e casos CRUD de Cliente, Produto, Pedido).
3. Especificação da API (entidades, campos e endpoints implementados).
4. Capturas reais do Postman (POST, GET geral, GET por ID, DELETE).
5. Link do GitHub com o código-fonte completo.

**Não gerar prints falsos**: os screenshots do Postman devem ser tirados durante a execução real no seu computador.

## Verificação

O projeto traz um teste de integração JUnit em `src/test` que percorre POST, GET e DELETE das três entidades. Ele pode ser executado com `mvn test` (Maven instalado) ou **Run As > JUnit Test** no Eclipse.

## Nota de implementação

O pedido guarda `clienteId`, `produtoId` e `quantidade` como no modelo da professora. Há relacionamentos JPA de leitura que criam chaves estrangeiras reais no banco, sem exigir JSON aninhado. O pacote `service` foi incluído como complemento, preservando os pacotes obrigatórios `model`, `repository` e `controller`.
