# Sistema de Registro de Vehículos

Registro de vehículos y sus propietarios (Java + MySQL).

## Funcionalidades

Listar vehículos, registrar vehículo (asociado a un propietario existente), buscar por placa (muestra datos del propietario), eliminar vehículo.

## Estructura

```
src/
├── dao/ConexionBD.java, VehiculoDAO.java
└── app/Main.java
```

## Base de datos

[`database/vehiculos.sql`](database/vehiculos.sql): `PROPIETARIOS`, `VEHICULOS`.

## Cómo ejecutarlo

```bash
mysql -u root -p < database/vehiculos.sql
javac -d bin -cp "lib/mysql-connector-j-9.5.0.jar" src/dao/*.java src/app/*.java
java -cp "bin;lib/mysql-connector-j-9.5.0.jar" app.Main
```

> Compilado y verificado con `javac` sin errores; conexión real a MySQL no probada en este entorno (sin servidor corriendo, como acordamos).

## Capturas

_Pendiente: agregar capturas en `capturas/`._

## Licencia

MIT — ver [LICENSE](LICENSE).
