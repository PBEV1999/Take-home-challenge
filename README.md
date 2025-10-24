#  Mercado Libre - Test Automation 

##  SitioWeb
[https://www.mercadolibre.com](https://www.mercadolibre.com)

##  Lenguaje
Java 17

##  Framework
Selenium WebDriver + TestNG + Allure Report

---

##  Descripcion

1. Enter the website
2. Select **México** as the country
3. Search for the term **“Playstation 5”**
4. Filter by condition **“Nuevos”**
5. Filter by location **“CDMX”**// no es algo que actualmente se puede filtrar
6. Order results by **“Mayor a menor precio”**
7. Obtain the **name and price** of the first **5 products**
8. Print the results in the console
9. Generate a report with screenshots for each step

---

##  Como correr el programa

### Requerimientos
- Java 17 or higher
- Maven installed
- Google Chrome and ChromeDriver (matching version)

### Pasos a ejecutar

1.Clonar el repositorio:
   ```bash
   git clone https://github.com/<your_user>/TestAutomation.git
   cd TestAutomation

-> 🧩 **Nota:**
> Si las dependencias de **Maven** no se descargan automáticamente después de clonar el proyecto, sigue estos pasos:
>
> 1. En **IntelliJ IDEA**, abre el panel de **Maven** (ubicado generalmente en el lado derecho del IDE).
> 2. Da clic en el ícono **Reload All Maven Projects** 🔄 (las flechas circulares azules).
> 3. Espera a que todas las dependencias se descarguen y finalice el proceso de construcción.
>
> También puedes hacerlo desde la terminal con el siguiente comando:
> ```bash
> mvn clean install -U
> ```
> El parámetro `-U` fuerza a Maven a **actualizar todas las dependencias** desde los repositorios remotos, asegurando que el proyecto se ejecute correctamente.

2.Correr el  test suite:

mvn test

3.Genrar el  Allure Report:

allure serve allure-results


Ejemplo de resultado esperado.

Total de productos encontrados: 50
Primeros 5 productos:

1. Sony Playstation 5 Digital Edición 30° Aniversario - $18,999 (5% OFF)
2. Playstation 5 Slim Edition 30 Aniversario - $24,000
3. Sony Playstation 5 Pro 2024 - $20,000
4. Playstation 5 Slim 1TB + Lector de Discos - $21,702
5. Playstation 5 Edición Limitada 30° Aniversario - $18,333
