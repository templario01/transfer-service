# Transfer Service

Microservicio reactivo para la gestión de transferencias financieras, construido con **Spring WebFlux**, base de datos relacional con **Flyway** para versionado de esquema, y generación automática de DTOs y contratos mediante **OpenAPI Generator**.

El proyecto sigue principios de **Arquitectura Hexagonal (Ports & Adapters)**, separando claramente dominio, aplicación y adaptadores externos.

---

# 🏗 Arquitectura

Estructura principal del proyecto:

src
└── main
├── java/io/templario01/transfer_service
│ ├── adapter # Adaptadores de entrada y salida (REST, DB, Kafka, etc.)
│ ├── application # Casos de uso (interactors) y puertos
│ ├── config # Configuración Spring (beans, seguridad, etc.)
│ ├── domain # Modelo de dominio y reglas de negocio
│ └── TransferServiceApplication
│
└── resources
├── db.migration # Scripts versionados de Flyway
├── postman # Colección de pruebas manuales
├── swagger.zconnect # Definición OpenAPI
├── application.yaml
└── application-dev.yaml

## Capas

- **domain** → Entidades y reglas puras (sin dependencias de framework)
- **application** → Casos de uso + puertos (interfaces)
- **adapter** → Implementaciones concretas (REST controllers, repositorios R2DBC, etc.)
- **config** → Configuración de infraestructura

---

# 🚀 Stack Tecnológico

- Java 17+
- Spring Boot
- Spring WebFlux (programación reactiva)
- R2DBC (acceso reactivo a base de datos)
- Flyway (migraciones versionadas)
- OpenAPI Generator (DTOs y contratos API)
- Maven

---

# ⚙️ Configuración

Los perfiles están definidos en:

- `application.yaml`
- `application-dev.yaml`
