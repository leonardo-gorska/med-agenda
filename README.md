# MedAgenda Enterprise 🩺

> **Plataforma Corporativa Full-Stack para Agendamento Médico Inteligente.**

O **MedAgenda** é uma solução robusta arquitetada para unificar o gerenciamento impecável de agendas clínicas, construído com foco 100% em **Zero Conflitos de Horários** e segurança via **criptografia JWT Stateless**. Este projeto reflete as melhores práticas da indústria para escalabilidade e design premium de front-end.

![Design UI Showcase](https://img.shields.io/badge/Layout-Glassmorphism-black?style=for-the-badge) ![Spring Boot](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white) ![React](https://img.shields.io/badge/React_SPA-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)

---

## 🏗 Arquitetura do Sistema

O repositório é segmentado estrategicamente:

- **`/` (Raiz / Backend):** Construído sobre **Java 21 e Spring Boot 3**, lidando com camadas estritas de Serviço (`AppointmentService`), mapeamentos de persistência com `Spring Data JPA` e segurança de JWT blindando a API REST. Inclui **Flyway** para migrations de banco SQL confiáveis.
- **`/frontend`:** SPA minimalista de altíssima performance estruturada com **Vite, React (TypeScript) e Chakra UI v3**, oferecendo `Feedback Toasters` integrados ativamente à API anti-sobrescrita de banco de dados.

## 🚀 Como Funciona (Core Business Logic)
A "Joia da Coroa" sistêmica é a **Validação Anti-Overlap**.

Se dois pacientes, ou secretárias, tentarem reservar ao mesmo tempo o limite temporal do `Dr. House` (Ex: `10:00 - 11:00`), o Back-end aciona cálculos matemáticos estritos nas consultas subjacentes via JPA Repository (`endTime > newStartTime && startTime < newEndTime`). O servidor aborta na própria camada de dados em frações de segundo, retornando um status `409 Conflict`, interpretado dinamicamente para o usuário da Web não sofrer dupla cobrança.

## 🛠️ Tecnologias Adotadas

**Backend:**
- Java 21 LTS 
- Spring Boot 3.2.4 (Security, Web, Data JPA, Validation)
- Auth0 Java-JWT
- Flyway (Versionamento SQL seguro via `V1__create_initial_tables.sql`)
- Banco de Dados H2 em Memória / Configurado para PostegreSQL
- OpenAPI (Automated Swagger Docs via `springdoc`)
- JUnit 5 & Mockito (Testabilidade em Camadas de Exceção)

**Frontend:**
- Vite & React (TS)
- Chakra UI v3 (Tokens de cores `teal` hospitalar & Glassmorphism design)
- Axios (Interceptação estrita de Tokens Locais)
- React Router DOM e Lucide-Icons

## 🏁 Inicialização Rápida

### 1. Iniciar a API Segura (Backend)
Na pasta raiz (`/med-agenda`):
```bash
# As migrations Flyway rodarão automaticamente ao ligar
mvn spring-boot:run
```
*(Swagger Documentação Automática disponível em `http://localhost:8080/swagger-ui.html`)*

### 2. Iniciar a Interface de Usuário (Frontend)
Entre na pasta do client web e execute:
```bash
cd frontend
npm install
npm run dev
```
*(Painel Administrativo interativo carregará instantaneamente em `http://localhost:5173/`)*

---
*Projetado sistematicamente para portfólio de engenharia de software moderno.*
