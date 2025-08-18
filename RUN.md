# Como rodar e testar o projeto

## Pré-requisitos

- Java 17+
- Maven 3.6.0 ou superior
- Docker (opcional, para rodar em container)

---

## Instalando o JDK e Maven

1. **Baixe o JDK 17**  
   - [Adoptium Temurin](https://adoptium.net/temurin/releases/?version=17)
   - [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)

2. **Configure JAVA_HOME e adicione `%JAVA_HOME%\bin` ao PATH**  
   - Veja instruções detalhadas [aqui](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html).

3. **Baixe o Maven**  
   - [Download Maven](https://maven.apache.org/download.cgi)
   - Configure MAVEN_HOME e adicione `bin` ao PATH.

4. **Verifique as instalações:**
   ```sh
   java -version
   mvn -version
   ```

---

## Configuração do Projeto

Clone o repositório:

```sh
git clone https://github.com/DiogoEller/meli-item-detail-api.git
cd meli-item-detail-api
```

---

## Build e Execução

### Usando Maven

1. **Build do projeto:**
   ```sh
   mvn clean install
   ```

2. **Executar a aplicação:**
   ```sh
   mvn spring-boot:run
   ```

---

### Usando Docker (passo a passo sucinto)

1. **Build do jar:**
   ```sh
   mvn clean package
   ```

2. **Build da imagem Docker:**
   ```sh
   docker build -t meli-item-detail-api .
   ```

3. **Rodar o container:**
   ```sh
   docker run -p 8080:8080 meli-item-detail-api
   ```

---

## Testes Automatizados

Execute todos os testes unitários e de integração:

```sh
mvn test
```

---

## Testando os Endpoints

Após rodar a aplicação, acesse:

- **Swagger UI:**  
  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- **Exemplo de requisição:**  
  ```sh
  curl -X GET http://localhost:8080/api/v1/products
  ```

---

## Observações

- O arquivo de produtos é configurável via `application.properties`.
- Para ambiente de testes, é usado `src/test/resources/test-products.json`.
- Todos os endpoints e modelos estão documentados via Swagger/OpenAPI.