# finance-api

Esse projeto é uma api de dados financeiros e tickers de ações.

## Guia do desenvolvedor
### Stack

* [Kotlin](https://kotlinlang.org)
* [Maven](https://maven.apache.org)
* [Spring Boot](https://spring.io/projects/spring-boot) 2.6.2
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html)
* [Spring MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#spring-web)
* [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)
* [Spring Cloud](https://spring.io/projects/spring-cloud) 2021.0.0
* [kotlin-logging](https://github.com/MicroUtils/kotlin-logging)
* [Bean Validation](https://beanvalidation.org)
* [Open Api 3 & Swagger](https://springdoc.org)
* [JUnit 5](https://junit.org/junit5/)
* [Java Faker](https://github.com/DiUS/java-faker) (para geração de dados fake data nos testes)
* [MockK](https://mockk.io) (para mocking)
* [SpringMockK](https://github.com/Ninja-Squad/springmockk) (para integração do mockk com Spring)
* [ArchUnit](https://www.archunit.org) (para validação das camadas da aplicação)
* [Pit Mutation Tests](https://pitest.org)
* [Testcontainers](https://www.testcontainers.org) (para testes de componente e integração)
* [jib-maven-plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin) (para gerar imagens do docker Distroless)
* [ktlint](https://github.com/pinterest/ktlint) (para kotlin code style)
* [JaCoCo](https://www.eclemma.org/jacoco/) (para relatório de cobertura de código)

### Construindo o projeto
```shell
./gradlew clean build 
```
### Rodando testes de integração
```shell
./gradlew integrationTests 
```
### Rodando testes de componente
```shell
./gradlew componentTests
```

### Instalando o ktlint
[ktlint](https://github.com/pinterest/ktlint) é um linter para Kotlin.

#### Linux
```
curl -sSLO https://github.com/pinterest/ktlint/releases/download/0.40.0/ktlint &&
chmod a+x ktlint &&
sudo mv ktlint /usr/local/bin/
```

Para usuários do Intellij, executar:
```
ktlint applyToIDEAProject
```

### Usando ktlint
```
# check the style of all Kotlin files inside the current dir (recursively)
# (hidden folders will be skipped)
$ ktlint

# print style violations grouped by file
$ ktlint --reporter=plain?group_by_file

# auto-correct style violations
# (if some errors cannot be fixed automatically they will be printed to stderr) 
$ ktlint -F
```

### Rodando o projeto
Para desenvolvimento local, executar o projeto com o Spring profile **local**.
Obs.: Precisa de uma ambiente com docker rodando

### Acessando o projeto
[http://localhost:8080](http://localhost:8080)

### Decisões arquiteturais
#### Arquitetura Hexagonal

### Backlog

* Criar user e user company
* Fazer conditions por userId
* Criar gráficos por company
* Criar favoritos numa nova coluna que será selecionável e ordenável
* Show/hide coluna no report
* Show/hide condição fora do filtro
* Eliminação de uma company, inclusão de uma nova company (stock code)
* Edição de tipo de company GROWING, CYCLIC e DIVIDEND
* Edição de tipo de company PRIVATE e STATE_OWNED
* Extração de dados de company e do NuInvest com background job. @Scheduled
* Report com % de dinheiro por tipos de company, e setor