# PerfDog API Tests

Suite de automatización de pruebas de API para la tienda de mascotas **PerfDog**,
construida con **Rest Assured + TestNG** contra la API pública de
[Swagger Petstore](https://petstore.swagger.io).

## Historia de usuario

> La tienda de mascotas "PerfDog" se encuentra desarrollando una nueva versión
> para su sitio web. Dado que el front end no está terminado, se decide
> comenzar a probar las funcionalidades utilizando la API de la aplicación.
>
> **Como usuario**, quiero poder loguearme y obtener productos de la tienda,
> para tener todo listo y agilizar mi compra.

## Funcionalidades cubiertas

| # | Funcionalidad | Endpoint | Clase de test |
|---|---|---|---|
| 1 | Crear un usuario | `POST /user` | `CreateUserTests` |
| 2 | Login con usuario recién creado | `GET /user/login` | `LoginTests` |
| 3 | Listar mascotas con status "disponible" | `GET /pet/findByStatus` | `ListAvailablePetsTests` |
| 4 | Consultar una mascota específica | `GET /pet/{petId}` | `GetPetByIdTests` |
| 5 | Crear una orden (compra) para una mascota | `POST /store/order` | `CreateOrderTests` |
| 6 | Logout | `GET /user/logout` | `LogoutTests` |

## Stack técnico

- **Java 17** (compatible desde Java 8+)
- **Maven** como gestor de dependencias
- **Rest Assured 5.3.2** como cliente HTTP
- **TestNG 7.8.0** como test runner
- **Lombok** para reducir boilerplate en los POJOs (`@Data`, `@Builder`)
- **Jackson** para (de)serialización POJO ↔ JSON
- **Logback + SLF4J** para logging

## Estructura del proyecto

```
src/test/
├── java/com/perfdog/automation/
│   ├── config/
│   │   └── TestRunner.java        # Carga config.properties, expone getBaseUrl()
│   ├── model/                     # POJOs (User, Pet, Category, Order, ApiResponse)
│   ├── request/
│   │   └── RequestBuilder.java    # Métodos estáticos GET/POST con Rest Assured
│   └── test/                      # Una clase por funcionalidad
└── resources/
    └── config.properties          # url.base=https://petstore.swagger.io/v2
```

## Cómo correr los tests

**Desde IntelliJ:** click derecho sobre `testng.xml` (raíz del proyecto) → **Run 'testng.xml'**.

**Desde terminal (Maven):**
```bash
mvn test
```

Al finalizar deberías ver:
```
Total tests run: 6, Passes: 6, Failures: 0, Skips: 0
```

## Buenas prácticas aplicadas

- **Tests independientes:** cada test genera sus propios datos (usernames/passwords únicos vía `UUID`), sin depender de que otro test haya corrido antes. La suite corre con `parallel="methods"` en `testng.xml` para validar esta independencia real.
- **Sin datos hardcodeados:** los `petId` usados en `GetPetByIdTests` y `CreateOrderTests` se obtienen dinámicamente consultando `/pet/findByStatus`, nunca un id fijo.
- **POJOs en vez de Strings JSON:** toda comunicación con la API usa objetos Java (`User`, `Pet`, `Order`), serializados/deserializados automáticamente por Jackson.
- **Separación de responsabilidades:** `RequestBuilder` solo ejecuta requests (sin aserciones); las validaciones quedan siempre a cargo de los tests.
- **Configuración externalizada:** la URL base vive en `config.properties`, cargada una única vez en `@BeforeSuite`.
- **Mensajes de error descriptivos:** cada aserción incluye un mensaje que explica qué se esperaba, evitando el típico "expected X but was Y" sin contexto.
- **Logging con SLF4J/Logback:** nunca `System.out`; los requests/responses se loguean automáticamente vía `RequestLoggingFilter`/`ResponseLoggingFilter`.

## Flujo de ramas (Git)

```
main ── develop ──┬── feature/create-user
                   ├── feature/login
                   ├── feature/logout
                   ├── feature/list-available-pets
                   ├── feature/get-pet-by-id
                   ├── feature/create-order
                   └── chore/code-documentation
```

Cada funcionalidad se desarrolló en su propia rama `feature/*`, partiendo de `develop`
y mergeándose de vuelta ahí. Al cierre de la entrega, `develop` se mergea a `main`.
