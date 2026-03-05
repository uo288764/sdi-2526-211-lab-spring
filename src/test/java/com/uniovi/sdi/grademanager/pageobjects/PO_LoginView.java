package com.uniovi.sdi.grademanager.pageobjects;

import com.uniovi.sdi.grademanager.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_LoginView extends PO_NavView {
    static public void fillLoginForm(WebDriver driver, String dnip, String passwordp) {
        // Intenta con "username" en lugar de "dni"
        WebElement dni = driver.findElement(By.name("username"));
        dni.click();
        dni.clear();
        dni.sendKeys(dnip);

        WebElement password = driver.findElement(By.name("password"));
        password.click();
        password.clear();
        password.sendKeys(passwordp);

        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    static public void login(WebDriver driver, String dni, String password) {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        fillLoginForm(driver, dni, password);
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.getFirst().getText());
    }
}