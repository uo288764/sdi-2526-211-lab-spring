package com.uniovi.sdi.grademanager;

import com.uniovi.sdi.grademanager.pageobjects.*;
import com.uniovi.sdi.grademanager.pageobjects.PO_HomeView;
import com.uniovi.sdi.grademanager.pageobjects.PO_Properties;
import com.uniovi.sdi.grademanager.pageobjects.PO_SignUpView;
import com.uniovi.sdi.grademanager.pageobjects.PO_View;
import jakarta.transaction.Transactional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.jupiter.api.*;
import org.springframework.test.annotation.DirtiesContext;
import com.uniovi.sdi.grademanager.util.SeleniumUtils;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GradeManagerApplicationTests {
        static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
        //static String Geckodriver = "C:\\Path\\geckodriver-v0.36.0-win64.exe";
        //static String Geckodriver = "C:\\Dev\\tools\\selenium\\geckodriver-v0.36.0-win64.exe";
        static String Geckodriver = "C:\\Users\\bmene\\Documents\\4o\\SDI repetido\\AA Software\\geckodriver-v0.30.0-win64.exe";


    //static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
        //static String Geckodriver = "/Users/USUARIO/selenium/geckodriver-v0.30.0-macos";
        // Para la versión de Firefox 121 en adelante la ruta de firefo en MAC es
        //static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox";
        //Común a Windows y a MACOSX
        static WebDriver driver = getDriver(PathFirefox, Geckodriver);
        static String URL = "http://localhost:8090";
        public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp(){
        driver.navigate().to(URL);
    }
    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown(){
        driver.manage().deleteAllCookies();
    }
    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {}
    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
//Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }

    @Test
    @Order(1)
    void PR01A() {
        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
    }

    @Test
    @Order(2)
    void PR01B() {
        List<WebElement> welcomeMessageElement = PO_HomeView.getWelcomeMessageText(driver,
                PO_Properties.getSPANISH());
        Assertions.assertEquals(welcomeMessageElement.getFirst().getText(),
                PO_HomeView.getP().getString("welcome.message", PO_Properties.getSPANISH()));
    }

    //PR02. Opción de navegación. Pinchar en el enlace Registro en la página home
    @Test
    @Order(3)
    public void PR02() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
    }
    //PR03. Opción de navegación. Pinchar en el enlace Identifícate en la página home
    @Test
    @Order(4)
    public void PR03() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
    }

    //PR04. Opción de navegación. Cambio de idioma de Español a Inglés y vuelta a Español
    @Test
    @Order(5)
    @DirtiesContext
    public void PR04() {
        PO_HomeView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish",
                PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
    }

    //PR05. Prueba del formulario de registro. registro con datos correctos
    @Test
    @Order(6)
    public void PR05() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "77777778A", "Josefo", "Perez", "77777", "77777");
        //Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.getFirst().getText());
    }

    //PR06A. Prueba del formulario de registro. DNI repetido en la BD
// Propiedad: Error.signup.dni.duplicate
    @Test
    @Order(7)
    public void PR06A() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.dni.duplicate",
                PO_Properties.getSPANISH());
        //Comprobamos el error de DNI repetido.
        String checkText = PO_HomeView.getP().getString("Error.signup.dni.duplicate",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.getFirst().getText());
    }


    //PR06B. Prueba del formulario de registro. Nombre corto.
// Propiedad: Error.signup.dni.length
    //PR06B. Prueba del formulario de registro. Nombre corto.
// Propiedad: Error.signup.dni.length
    @Test
    @Order(8)
    public void PR06B() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Jose", "Perez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.name.length",
                PO_Properties.getSPANISH());
        //Comprobamos el error de Nombre corto de nombre corto .
        String checkText = PO_HomeView.getP().getString("Error.signup.name.length",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.getFirst().getText());
    }

    //PR07: Identificación válida con usuario de ROL usuario (99999990A/123456)
    @Test
    @Order(9)
    public void PR07() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        //Comprobamos que entramos en la pagina privada de Alumno
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.getFirst().getText());
    }

    //PR08: Identificación válida con usuario de ROL profesor (99999993D/123456)
    @Test
    @Order(10)
    public void PR08() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999993D", "123456");
        //Comprobamos que entramos en la pagina privada de Profesor
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.getFirst().getText());
    }

    //PR09: Identificación válida con usuario de ROL Administrador (99999988F/123456)
    @Test
    @Order(11)
    public void PR09() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999988F", "123456");
        //Comprobamos que entramos en la pagina privada de Administrador
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.getFirst().getText());
    }

    //PR10: Identificación inválida con usuario de ROL alumno (contraseña incorrecta)
    @Test
    @Order(12)
    public void PR10() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario con contraseña incorrecta
        PO_LoginView.fillLoginForm(driver, "99999990A", "incorrect");
        //Comprobamos que sigue en la página de login (no ha entrado)
        List<WebElement> result = PO_View.checkElementBy(driver, "class", "btn btn-primary");
        Assertions.assertTrue(result.size() > 0);
    }

    //PR11: Identificación válida y desconexión con usuario de ROL usuario (99999990A/123456)
    @Test
    @Order(13)
    public void PR11() {
        //Logueamos como estudiante y verificamos la página privada
        PO_LoginView.login(driver, "99999990A", "123456");
        //Ahora nos desconectamos y comprobamos que aparece el menú de registro
        PO_PrivateView.logout(driver);
    }


    @Test
    @Order(14)
    public void PR12() {
        //Logueamos como estudiante y verificamos la página privada
        PO_LoginView.login(driver, "99999990A", "123456");
        //Contamos el número de filas de notas
        List<WebElement> marksList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        Assertions.assertEquals(1, marksList.size());
        //Desconectamos
        PO_PrivateView.logout(driver);
    }

    //PR13. Loguearse como estudiante y ver los detalles de la nota con Descripcion = Nota A2.
    //PR13. Loguearse como estudiante y ver los detalles de la nota con Descripcion = Nota A2.
    @Test
    @Order(15)
    public void PR13() {
        //Comprobamos que entramos en la pagina privada de Alumno
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.login(driver, "99999990A", "123456");

        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        //Contamos las notas
        By enlace = By.xpath("//td[contains(text(), 'Nota A1')]/following-sibling::*[2]");
        driver.findElement(enlace).click();
        //Esperamos por la ventana de detalle
        checkText = "Detalles de la nota";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.getFirst().getText());
        //Ahora nos desconectamos y comprobamos que aparece el menú de registro
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());

        PO_PrivateView.logout(driver);
    }

    //P14. Loguearse como profesor y Agregar Nota A2.
    @Test
    @Order(16)
    public void PR14() {
        //Logueamos como profesor y verificamos la página privada
        PO_LoginView.login(driver, "99999993D", "123456");
        //Navegamos al menú de Notas y pinchamos en Añadir nota
        PO_PrivateView.navigateToNavMenu(driver, "//*[@id=\"myNavbar\"]/ul[1]/li[2]/div","//*[@id=\"myNavbar\"]/ul[1]/li[2]/div/a");
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'mark/add')]");
        elements.getFirst().click();
        //Rellenamos el formulario de nota
        String checkText = "Nota sistemas distribuidos";
        PO_PrivateView.fillFormAddMark(driver, 3, checkText, "8");
        //Vamos a la última página y comprobamos que aparece la nota
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        elements.getLast().click();
        elements = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, elements.getFirst().getText());
        //Desconectamos
        PO_PrivateView.logout(driver);
    }

    @Test
    @Order(17)
    public void PR15() {
        //Logueamos como profesor y verificamos la página privada
        PO_LoginView.login(driver, "99999993D", "123456");
        //Navegamos al menú de Notas y pinchamos en Lista de notas
        PO_PrivateView.navigateToNavMenu(driver, "//*[@id=\"myNavbar\"]/ul[1]/div","//*[@id=\"myNavbar\"]/ul[1]/li[2]/div/a");
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'mark/list')]");
        elements.getFirst().click();
        //Vamos a la última página y borramos "Nota sistemas distribuidos"
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        elements.getLast().click();
        elements = PO_View.checkElementBy(driver, "free", "//td[contains(text(), 'Nota sistemas distribuidos')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
        elements.getFirst().click();
        //Volvemos a la última página y verificamos que ya no aparece
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        elements.getLast().click();
        SeleniumUtils.waitTextIsNotPresentOnPage(driver, "Nota sistemas distribuidos",
                PO_View.getTimeout());
        //Desconectamos
        PO_PrivateView.logout(driver);
    }
}