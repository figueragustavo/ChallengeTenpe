# ChallengeTenpe

Este proyecto es una aplicación de ejemplo que implementa un servicio REST para realizar operaciones de suma porcentual y paginación de transacciones.

## Tecnologías utilizadas

- Java
- Spring Boot
- Maven
- Feign
- Cache (Spring Cache)
- Validación (Jakarta Validation)
- Lombok

## Estructura del proyecto

- `src/main/java/com/challenge/tenpe/controller/impl/ControllerImpl.java`: Controlador REST que maneja las solicitudes HTTP.
- `src/main/java/com/challenge/tenpe/dto/Transaction.java`: DTO para las transacciones.
- `src/main/java/com/challenge/tenpe/service/impl/ServiceImpl.java`: Implementación del servicio que contiene la lógica de negocio.
- `src/main/java/com/challenge/tenpe/entity/TransactionEntity.java`: Entidad JPA para las transacciones.
- `src/main/java/com/challenge/tenpe/repository/TenpeRepository.java`: Repositorio JPA para acceder a la base de datos.

## Endpoints

### `POST /sumaPorcentual`

Realiza una suma porcentual.

#### Request

{
  "number1": 10,
  "number2": 20
}

#### Response

{
  "result": 33
}

### GET /pagingTransactions

Obtiene una lista paginada de transacciones.

#### Request

{
  "page": 0,
  "size": 10
}

#### Response

[
  {
    "startDate": "2023-01-01T00:00:00.000+00:00",
    "endDate": "2023-01-01T00:01:00.000+00:00",
    "endpoint": "/sumaPorcentual",
    "request": {...},
    "response": {...},
    "descriptionError": "OK"
  }
]


###  Ejecución del proyecto

Para ejecutar el proyecto, utiliza el siguiente comando de Maven:

mvn spring-boot:run

### Pruebas
Para ejecutar las pruebas, utiliza el siguiente comando de Maven:

mvn test

### Postman collection

ChallengeTenpe.postman_collection.json

### SWAGGER

http://localhost:8080/swagger-ui/index.html#

### Base de datos

postgresql-figueragustavo.alwaysdata.net:5432/figueragustavo_tenpe

### MOCK SERVICE GET /porcentaje

http://demo2008646.mockable.io/porcentaje


