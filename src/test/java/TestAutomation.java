import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import io.qameta.allure.Step;
import util.ScreenshotUtil;
import java.io.File;

public class TestAutomation {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeSuite
    public void limpiarAllureResults() {
        File allureResults = new File("allure-results");

        // Si no existe, la crea automáticamente
        if (!allureResults.exists()) {
            if (allureResults.mkdirs()) {
                System.out.println("📁 Carpeta allure-results creada porque no existía");
            } else {
                System.out.println("⚠️ No se pudo crear la carpeta allure-results");
            }
            return;
        }

        // Si existe, elimina todo (archivos y subcarpetas)
        if (allureResults.isDirectory()) {
            eliminarContenido(allureResults);
            System.out.println("🧹 Carpeta allure-results limpiada antes de la ejecución");
        } else {
            System.out.println("⚠️ 'allure-results' existe pero no es un directorio");
        }
    }

    /**
     * Elimina archivos y carpetas dentro de un directorio de forma recursiva.
     */
    private void eliminarContenido(File carpeta) {
        File[] archivos = carpeta.listFiles();
        if (archivos == null) {
            return;
        }

        for (File archivo : archivos) {
            if (archivo.isDirectory()) {
                eliminarContenido(archivo); // 🔁 Llama recursivamente
            }

            try {
                boolean eliminado = archivo.delete();
                if (!eliminado) {
                    System.out.printf("⚠️ No se pudo eliminar: %s%n", archivo.getAbsolutePath());
                }
            } catch (SecurityException e) {
                System.err.printf("🚫 Error de permisos al eliminar %s: %s%n",
                        archivo.getAbsolutePath(), e.getMessage());
            }
        }
    }

    @BeforeClass
    public void setUp() {
        //  Silenciar logs de Selenium y WDM
        System.setProperty("webdriver.chrome.silentOutput", "true");
        System.setProperty("webdriver.chrome.verboseLogging", "false");
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "off");

        // Desactivar loggers comunes
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.openqa.selenium.remote").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.openqa.selenium.devtools").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("io.github.bonigarcia.wdm").setLevel(java.util.logging.Level.OFF);

        //  Redirigir System.out y System.err temporalmente
        java.io.PrintStream dummy = new java.io.PrintStream(new java.io.OutputStream() {
            public void write(int b) {}
        });
        System.setOut(dummy);
        System.setErr(dummy);

        //  Configurar y abrir navegador
        WebDriverManager.chromedriver().browserVersion("140").setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        //  Espera explícita
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        //  Restaurar salidas estándar para tus propios prints
        System.setOut(new java.io.PrintStream(new java.io.FileOutputStream(java.io.FileDescriptor.out)));
        System.setErr(new java.io.PrintStream(new java.io.FileOutputStream(java.io.FileDescriptor.err)));
    }

    @Test
    public void busquedaPlaystation5() {
        try {
            abrirSitio();
            seleccionarMexico();
            buscarProducto();
            aplicarFiltros();
            ordenarPorMayorPrecio();
            imprimirProductos();

        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
            ScreenshotUtil.takeScreenshot(driver, "Error durante la ejecución");
        }
    }

    @Step("Entrar al sitio MercadoLibre")
    private void abrirSitio() {
        driver.get("https://www.mercadolibre.com/");
        ScreenshotUtil.takeScreenshot(driver, "1 - Entrar al sitio");
    }

    @Step("Seleccionar México")
    private void seleccionarMexico() {
        WebElement mexico = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href*='mercadolibre.com.mx']")));
        mexico.click();
        ScreenshotUtil.takeScreenshot(driver, "2 - Seleccionar México");
    }

    @Step("Buscar 'PlayStation 5'")
    private void buscarProducto() {
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("as_word")));
        searchBox.sendKeys("playstation 5");
        searchBox.submit();
        ScreenshotUtil.takeScreenshot(driver, "3 - Buscar producto");
    }

    @Step("Aplicar filtros (Nuevo + CDMX)")
    private void aplicarFiltros() throws InterruptedException {

        // Espera y selecciona el filtro "Nuevo"
        WebElement filtroNuevo = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(),'Nuevo')]")));
        filtroNuevo.click();

        // Espera y selecciona el filtro "Ciudad de México" o "CDMX"
        WebElement filtroCdmx = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(),'Ciudad de México') or contains(text(),'CDMX')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filtroCdmx);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", filtroCdmx);

        // Espera a que se actualicen los resultados filtrados
        Thread.sleep(2500);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        //  Forzar scroll al inicio (esto soluciona que la imagen salga en medio)
        js.executeScript("window.scrollTo(0, 0);");
        Thread.sleep(1000);

        //  Toma una captura principal mostrando los filtros arriba
        ScreenshotUtil.takeScreenshot(driver, "4 - Filtros aplicados");
    }



    @Step("Ordenar por 'Mayor precio'")
    private void ordenarPorMayorPrecio() {
        WebElement menuOrdenar = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.andes-dropdown__trigger")));
        menuOrdenar.click();

        WebElement mayorMenor = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(),'Mayor precio')]")));
        mayorMenor.click();

        ScreenshotUtil.takeScreenshot(driver, "5 - Ordenar por Mayor precio");
    }

    @Step("Mostrar los primeros 5 productos en consola")
    private void imprimirProductos() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("li.ui-search-layout__item")));

        List<WebElement> productos = driver.findElements(By.cssSelector("li.ui-search-layout__item"));

        System.out.println("\n Total de productos encontrados: " + productos.size());
        System.out.println(" Primeros 5 productos:\n");

        // Recorre los primeros 5 productos
        for (int i = 0; i < Math.min(5, productos.size()); i++) {
            String nombreProducto = obtenerNombreProducto(i);
            String precioProducto = obtenerPrecioProducto(i);

            System.out.println((i + 1) + ". " + nombreProducto + " - $" + precioProducto);

            //  Mueve el producto a la vista
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productos.get(i));

            // Espera breve sin usar Thread.sleep
            new WebDriverWait(driver, Duration.ofMillis(700))
                    .until(ExpectedConditions.jsReturnsValue("return true;"));

            //  Toma screenshot de cada producto
            ScreenshotUtil.takeScreenshot(driver, "Producto " + (i + 1) + " - " + nombreProducto);
        }
    }

    private String obtenerNombreProducto(int index) {
        try {
            WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Encontrar el producto individual
            WebElement producto = localWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("(//li[contains(@class,'ui-search-layout__item')])[" + (index + 1) + "]")
            ));

            // Buscar título dentro del producto
            WebElement titulo = producto.findElement(By.xpath(".//h2 | .//h3"));

            // Intentar varios métodos de obtención de texto
            String attrTitle = titulo.getDomAttribute("title");
            if (attrTitle != null && !attrTitle.isEmpty()) return attrTitle;

            String ariaLabel = titulo.getDomAttribute("aria-label");
            if (ariaLabel != null && !ariaLabel.isEmpty()) return ariaLabel;

            String text = titulo.getText();
            if (!text.isEmpty()) return text;

            WebElement link = titulo.findElement(By.xpath(".//a[@title]"));
            String linkTitle = link.getDomAttribute("title");
            return (linkTitle != null && !linkTitle.isEmpty()) ? linkTitle : "Nombre no disponible";

        } catch (Exception e) {
            return "Nombre no disponible";
        }
    }


    private String obtenerPrecioProducto(int index) {
        try {
            WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Localiza el producto específico por índice
            WebElement producto = localWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("(//li[contains(@class,'ui-search-layout__item')])[" + (index + 1) + "]")
            ));

            //  1. Busca si hay un precio con descuento (preferido)
            List<WebElement> preciosConDescuento = producto.findElements(
                    By.xpath(".//span[contains(@class,'andes-money-amount__fraction') and not(contains(@class,'previous'))]")
            );

            //  2. Busca el precio original (tachado)
            List<WebElement> preciosTachados = producto.findElements(
                    By.xpath(".//span[contains(@class,'andes-money-amount__fraction') and contains(@class,'previous')]")
            );

            //  3. Busca etiqueta de descuento (% OFF)
            List<WebElement> descuentos = producto.findElements(
                    By.xpath(".//span[contains(@class,'andes-money-amount__discount')]")
            );

            String precioFinal = "Precio no disponible";

            if (!preciosConDescuento.isEmpty()) {
                precioFinal = preciosConDescuento.get(0).getText();
            } else if (!preciosTachados.isEmpty()) {
                precioFinal = preciosTachados.get(0).getText();
            }

            // Añadir decimales si existen
            try {
                WebElement precioDecimal = producto.findElement(By.xpath(".//span[contains(@class,'andes-money-amount__cents')]"));
                precioFinal += "." + precioDecimal.getText();
            } catch (NoSuchElementException ignored) {}

            // Agregar el porcentaje OFF si está presente
            if (!descuentos.isEmpty()) {
                precioFinal += " (" + descuentos.get(0).getText() + ")";
            }

            return precioFinal;

        } catch (Exception e) {
            return "Precio no disponible";
        }
    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
