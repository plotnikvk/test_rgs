import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.reflect.generics.tree.Tree;

/**
 * Created by plotnikvk  .
 */

public class rgsTest {

    WebDriver driver;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://www.rgs.ru");
    }

    @Test
    public void test ()throws InterruptedException, NullPointerException, IllegalStateException{
        WebDriverWait wait = new WebDriverWait(driver,3);

        WebElement webElement = driver.findElement(By.xpath("//li[@class='dropdown adv-analytics-navigation-line1-link" +
                " current']"));
        new Actions(driver).moveToElement(webElement);
        webElement.click();

        WebElement dms = driver.findElement(By.xpath("//*[contains(text(),'ДМС')]"));
        new Actions(driver).moveToElement(dms);
        dms.click();

        WebElement title = driver.findElement(By.xpath("//span[@class = 'h1']"));
        Assert.assertEquals("Заголовок\"Добровольное медицинское страхование\" присутствует",
                "Добровольное медицинское страхование (ДМС)", title.getText());

        WebElement requestButton = driver.findElement(By.xpath("//a[contains(text(),'Отправить заявку')]"));
        new Actions(driver).moveToElement(requestButton);
        requestButton.click();

        WebElement requestList = driver.findElement(By.xpath("//h4[@class='modal-title']"));
        wait.until(ExpectedConditions.visibilityOf(requestList));
        Assert.assertEquals("На странице присутствует текст: \"Заявка на добровольное медицинское страхование\"",
                "Заявка на добровольное медицинское страхование", requestList.getText());

        WebElement lastNameField = driver.findElement(By.xpath("//input[@name='LastName']"));
        lastNameField.sendKeys("Иванов");

        WebElement firstNameField = driver.findElement(By.xpath("//input[@name='FirstName']"));
        firstNameField.sendKeys("Иван");

        WebElement middleNameField = driver.findElement(By.xpath("//input[@name='MiddleName']"));
        middleNameField.sendKeys("Иванович");

        WebElement region = driver.findElement(By.xpath("//select[@name='Region']"));
        region.sendKeys("Москва");
        Select select = new Select(driver.findElement(By.xpath("//select[@name='Region']")));
        WebElement option = select.getFirstSelectedOption();
        String stringRegion = option.getText();

        WebElement phoneField = driver.findElement(By.cssSelector("input[data-bind*='value: Phone']"));
        phoneField.sendKeys("9999999999");

        WebElement emailField = driver.findElement(By.cssSelector("input[data-bind*='value: Email']"));
        emailField.sendKeys("qwertyqwerty");

        WebElement contactDateField = driver.findElement(By.cssSelector("input[data-bind*='value: ContactDate']"));
        contactDateField.sendKeys("01.09.2018");

        WebElement commentField = driver.findElement(By.cssSelector("textarea[data-bind*='value: Comment']"));
        commentField.sendKeys("Позвонить заранее");

        WebElement checkBoxField = driver.findElement(By.xpath("//input[@class='checkbox']"));
        checkBoxField.click();
        WebElement button = driver.findElement(By.xpath("//button[@id='button-m']"));
        button.click();

        WebElement errorEmail = driver.findElement(By.xpath("//span[@class='validation-error-text']"));

        Assert.assertEquals("Иванов", lastNameField.getAttribute("value"));
        Assert.assertEquals("Иван", firstNameField.getAttribute("value"));
        Assert.assertEquals("Иванович", middleNameField.getAttribute("value"));
        Assert.assertEquals("Москва", stringRegion );
        Assert.assertEquals("+7 (999) 999-99-99", phoneField.getAttribute("value"));
        Assert.assertEquals("qwertyqwerty", emailField.getAttribute("value"));
        Assert.assertEquals("01.09.2018", contactDateField.getAttribute("value"));
        Assert.assertEquals("Позвонить заранее", commentField.getAttribute("value"));
        Assert.assertTrue(checkBoxField.isSelected());
        Assert.assertTrue(errorEmail.isDisplayed());


    }

    @After
    public void tearDown(){
        driver.quit();
    }
}
