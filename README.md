# NutriFit

Aplicação web acadêmica desenvolvida em dupla para organizar dados de acompanhamento nutricional, treinos, agendamentos e hidratação.

## Equipe

- João Pedro Carneiro da Fonseca
- Gabriel Leão

## Funcionalidades

- Cadastro, login e logout
- Dashboard associado ao usuário autenticado
- CRUD de refeições e planos alimentares
- CRUD de treinos e agendamentos
- Registro de hidratação por usuário e data
- Perfis de acesso para usuário e administrador
- Banco H2 em memória para desenvolvimento e configuração MySQL para execução local em perfil de produção

## Tecnologias

Java 17, Spring Boot, Spring Security, Spring Data JPA, Thymeleaf, H2, MySQL, Lombok e Maven.

## Arquitetura

O código segue uma organização em camadas:

- `controller`: endpoints web e integração com as páginas
- `service`: regras de negócio
- `repository`: persistência com Spring Data JPA
- `model` e `enums`: entidades e tipos de domínio
- `dto`: dados agregados para as telas
- `templates`: páginas Thymeleaf
- `static`: CSS e JavaScript

## Executar em desenvolvimento

Pré-requisitos: Java 17.

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

O perfil padrão é `dev`, que usa H2 em memória. O script `import.sql` cria contas de demonstração locais:

- Usuário: `user@example.com` / `senha`
- Administrador: `admin@example.com` / `adminpass`

Essas contas e o banco H2 servem apenas para desenvolvimento local; não as reutilize em ambientes externos.

## Perfil MySQL

O perfil `prod` exige as variáveis `DB_URL`, `DB_USERNAME` e `DB_PASSWORD`. A configuração não contém credenciais versionadas.

Linux/macOS:

```bash
export PROFILE=prod
export DB_URL='jdbc:mysql://localhost:3306/nutriFitDB?useSSL=false&serverTimezone=America/Sao_Paulo'
export DB_USERNAME='seu_usuario'
export DB_PASSWORD='sua_senha'
./mvnw spring-boot:run
```

Windows PowerShell:

```powershell
$env:PROFILE='prod'
$env:DB_URL='jdbc:mysql://localhost:3306/nutriFitDB?useSSL=false&serverTimezone=America/Sao_Paulo'
$env:DB_USERNAME='seu_usuario'
$env:DB_PASSWORD='sua_senha'
.\mvnw.cmd spring-boot:run
```

O perfil MySQL usa `ddl-auto=validate`: crie e atualize o esquema do banco antes de iniciar a aplicação. O repositório ainda não inclui migrações versionadas para o esquema.

## Testes

Execute `./mvnw test` ou `.\mvnw.cmd test`. Atualmente há um teste de inicialização do contexto Spring; a cobertura de regras de negócio e fluxos web ainda pode ser ampliada.

## Limitações conhecidas

Este é um MVP acadêmico. A configuração de produção exige um banco preparado e credenciais fornecidas pelo ambiente. O projeto não inclui migrações de banco nem cobertura automatizada abrangente dos fluxos.

## Estrutura principal

```text
src/main/java/br/edu/nutrifit/
├── config/
├── controller/
├── dto/
├── model/
├── repository/
└── service/

src/main/resources/
├── application*.properties
├── static/
└── templates/
```
