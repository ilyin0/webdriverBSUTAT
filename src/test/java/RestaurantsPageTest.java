import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class RestaurantsPageTest {

    WebDriver driver;

    @BeforeTest
    public void setupDriverAndBrowserAndSite(){
        driver = new ChromeDriver();
        driver.get("https://dominos.by/restaurants");
        driver.manage().window().fullscreen();
        WebElement btnCloseAds = new WebDriverWait(driver, 5).until(d->driver.findElement(By.className("modal__close")));
        btnCloseAds.click();
    }

    @AfterTest
    public void quitDriver() {
        driver.quit();
    }

    @Test
    public void testIsAvailableDeliveryAddress() throws InterruptedException {
        WebElement form = driver.findElement(By.xpath("//form[@class=\"store-locator__form\"]"));

        WebElement streetInputDiv = form.findElement(By.xpath("//div[./div[1]/text()=\"Улица\"]"));
        streetInputDiv.click();

        driver.findElement(By.xpath("//div[@class=\"search-street__modal-content\"]//input[@class=\"custom-field-text__input\"]")).sendKeys("УМАНСКАЯ УЛ.");
        new WebDriverWait(driver, 5).until(d->driver.findElement(By.xpath("//li/button[./div[1]/text()=\"УМАНСКАЯ УЛ.\" and ./div[2]/text()=\"МИНСК\"]"))).click();

        WebElement houseNumberInputDiv = form.findElement(By.xpath("//div[./div[1]/text()=\"Номер дома\"]"));
        houseNumberInputDiv.findElement(By.tagName("input")).sendKeys("37");

        Thread.sleep(2000);

        WebElement checkAvailability = driver.findElement(By.xpath("//button[text()=\"Проверить\"]"));
        checkAvailability.click();

        Thread.sleep(5000);

        Assert.assertEquals(checkAvailability.getText(), "Адрес в зоне доставки");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class=\"notification\"]/div[@class=\"notification__title\"]")).getText(), "Вы находитесь в зоне доставки");

    }
}
