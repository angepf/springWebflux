# 📦 Spring WebFlux API – Clean Architecture

Este proyecto es una API reactiva desarrollada con **Spring Boot + WebFlux**, usando **PostgreSQL** como base de datos y aplicando el enfoque de **arquitectura limpia**. La configuración del sistema se gestiona de forma centralizada mediante un repositorio Git, y los servicios se despliegan con **Docker Compose**.

---

## 🧱 Tecnologías principales

- ⚡ Spring Boot (WebFlux)
- 🧼 Arquitectura limpia (Clean Architecture)
- 🐘 PostgreSQL (conectividad reactiva)
- 🔐 Spring Cloud Config Server con repositorio Git
- 🐳 Docker & Docker Compose
- 🛠️ Gradle

---

## 🚀 Instrucciones para levantar el entorno

### 1. Construir el proyecto

```bash
./gradlew build
```

> Asegúrate de tener permisos de ejecución en el archivo `gradlew` (`chmod +x gradlew` si estás en Linux/MacOS).

---

### 2. Configurar las variables de entorno

Antes de iniciar los servicios, exporta las siguientes variables:

```bash
export POSTGRES_PASS=****
export AWS_ACCESS_KEY_SQS==****
export AWS_SECRET_KEY_SQS==****
export GIT_USER_NAME==****
export GIT_USER_PASSWORD==****
export GIT_CONFIG_URI==****
```

> ⚠️ **Advertencia:** No publiques claves reales en repositorios públicos. Considera usar `.env`, Docker secrets o herramientas de gestión de secretos como Vault.

---

### 3. Levantar los servicios con Docker Compose

```bash
docker compose up -d
```

Esto iniciará todos los contenedores en segundo plano.

---

### 4. Verificar estado de los contenedores

```bash
docker compose ps
```

---

### 5. Ver logs de la aplicación principal

```bash
docker logs -f msa-account-management
```

---

### 6. Reiniciar servicios si es necesario

```bash
docker compose start
```

---

## 🧪 Pruebas

Ejecuta las pruebas con:

```bash
./gradlew test
```

---

## 📁 Estructura del proyecto

```
src/
 └── main/
     ├── java/
     │   └── com/tuempresa/
     │       ├── application/
     │       ├── domain/
     │       ├── infrastructure/
     │       └── config/
     └── resources/
         └── application.yml
```

La arquitectura limpia permite separar claramente las responsabilidades del sistema.

---

## 📌 Notas adicionales

- Asegúrate de tener Docker y Docker Compose instalados correctamente.
- La configuración externa se toma del repositorio Git indicado.
- Considera añadir herramientas de monitoreo/logs (p.ej., Grafana, ELK) en entornos productivos.

---

## ✨ Autor

**Angepf**  
Repositorio de configuración: [springWebflux](https://github.com/angepf/springWebflux)

---

## 🛡️ Licencia

Este proyecto está licenciado bajo los términos de [MIT].
