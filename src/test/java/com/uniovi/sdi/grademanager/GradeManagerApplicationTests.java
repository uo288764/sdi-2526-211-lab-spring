package com.uniovi.sdi.grademanager;

import com.uniovi.sdi.grademanager.pageobjects.PO_HomeView;
import com.uniovi.sdi.grademanager.pageobjects.PO_Properties;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.jupiter.api.*;

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
    public void PR04() {
        PO_HomeView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish",
                PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
    }

    @Test
    @Order(6)
    void PR06() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(7)
    void PR07() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(8)
    void PR08() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(9)
    void PR09() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(10)
    void PR10() {
        Assertions.assertTrue(true);
    }
}