# NutriFit

Sistema web acadêmico para acompanhamento nutricional, treinos, agendamentos e hidratação.

## Integrantes

- João Pedro Carneiro da Fonseca
- Gabriel Leão

## Tecnologias

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Thymeleaf
- H2
- MySQL
- Lombok
- Maven

## Funcionalidades entregues no MVP

- Cadastro de usuário
- Login e logout com sessão HTTP manual
- Dashboard com resumo do usuário logado
- CRUD de refeições
- CRUD de planos alimentares com seleção de refeições do próprio usuário
- CRUD de treinos
- CRUD de agendamentos com status
- Registro de hidratação por data com unicidade por usuário
- Layout base com fragments, alertas e navegação

## Arquitetura

O projeto está organizado em camadas:

- `controller`: recebe requisições, valida sessão e encaminha para a view
- `service`: concentra regras de negócio
- `repository`: acesso ao banco com Spring Data JPA
- `model`: entidades e enums
- `dto`: objetos de apoio para telas agregadas
- `templates`: páginas Thymeleaf
- `static`: CSS e JavaScript

## Como executar em desenvolvimento

O perfil padrão é `dev`, com H2 em memória.

```powershell
./mvnw spring-boot:run
```

Se quiser validar a compilação manualmente:

```powershell
./mvnw clean compile
```

## Produção

O perfil `prod` está configurado para MySQL. Ajuste usuário, senha e URL em `application-prod.properties` antes de usar.

## Observações técnicas

- O sistema usa `HttpSession` e salva o usuário logado em `usuarioLogado`.
- As rotas privadas sempre verificam a sessão antes de listar, editar ou excluir dados.
- A hidratação é protegida por uma restrição única de `usuario + data`.
- A autenticação do MVP foi mantida simples para fins acadêmicos.

## Interface

As páginas principais já existem em Thymeleaf para login, cadastro, dashboard e os CRUDs do MVP. As capturas de tela podem ser inseridas nesta seção quando o sistema for apresentado.

## Estrutura principal

```text
src/main/java/br/edu/nutrifit
├── controller
├── dto
├── model
├── repository
├── service
└── NutriFitApplication.java

src/main/resources
├── application.properties
├── application-dev.properties
├── application-prod.properties
├── static
└── templates
```
