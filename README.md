# Mercado Libre - Test Automation

## Sitio Web
[https://www.mercadolibre.com](https://www.mercadolibre.com)

## Lenguaje
Java 17

## Framework
Selenium WebDriver + TestNG + Allure Report

---

## Descripción

1. Entrar al sitio web.
2. Seleccionar **México** como país.
3. Buscar el término **“Playstation 5”**.
4. Filtrar por condición **“Nuevos”**.
5. Filtrar por ubicación **“CDMX”** *(actualmente no disponible)*.
6. Ordenar los resultados por **“Mayor a menor precio”**.
7. Obtener el **nombre y precio** de los primeros **5 productos**.
8. Imprimir los resultados en la consola.
9. Generar un **reporte con capturas** de cada paso.

---

## Cómo correr el programa

### Requerimientos
- Java 17 o superior
- Maven instalado
- Google Chrome y ChromeDriver (versión compatible)

---

### Pasos a ejecutar

#### 1️⃣ Clonar el repositorio:
```bash
git clone https://github.com/PBEV1999/Take-home-challenge.git
cd TestAutomation
```
---

### 🧩 Nota importante:

Si las dependencias de Maven no se descargan automáticamente después de clonar el proyecto, sigue estos pasos:

En IntelliJ IDEA, abre el panel de Maven (generalmente en el lado derecho del IDE).

Haz clic en el ícono Reload All Maven Projects 🔄 (las flechas circulares azules).

Espera a que las dependencias se descarguen completamente.

También puedes hacerlo desde la terminal:
```bash
mvn clean install -U
```

El parámetro -U fuerza a Maven a actualizar todas las dependencias desde los repositorios remotos.

#### 2️⃣ Ejecutar el test suite:
```bash
mvn test
```

#### 3️⃣ Generar el reporte de Allure:
```bash
allure serve allure-results
```
---
#### Ejemplo de resultado esperado

Total de productos encontrados: 50

Primeros 5 productos:

Sony Playstation 5 Digital Edición 30° Aniversario - $18,999 (5% OFF)

Playstation 5 Slim Edition 30° Aniversario - $24,000

Sony Playstation 5 Pro 2024 - $20,000

Playstation 5 Slim 1TB + Lector de Discos - $21,702

Playstation 5 Edición Limitada 30° Aniversario - $18,333
