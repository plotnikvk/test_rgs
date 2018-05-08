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
    WebDriverWait wait;

    //Константы для поиска объектов на странице
    private final By InsuranceItemPath = By.xpath("//li[@class='dropdown adv-analytics-" +
            "navigation-line1-link current']");
    private final By DmsItemPath = By.xpath("//*[contains(text(),'ДМС')]");
    private final By TitleElementPath = By.xpath("//span[@class = 'h1']");
    private final By RequestButtonPath = By.xpath("//a[contains(text(),'Отправить заявку')]");
    private final By RequestListPath = By.xpath("//h4[@class='modal-title']");
    private final By LastNameFieldPath = By.xpath("//input[@name='LastName']");
    private final By FirstNameFieldPath = By.xpath("//input[@name='FirstName']");
    private final By MiddleNameFieldPath = By.xpath("//input[@name='MiddleName']");
    private final By RegionFieldPath = By.xpath("//select[@name='Region']");
    private final By PhoneFieldPath = By.cssSelector("input[data-bind*='value: Phone']");
    private final By EmailFieldPath = By.cssSelector("input[data-bind*='value: Email']");
    private final By ContactDateFieldPath = By.cssSelector("input[data-bind*='value: ContactDate']");
    private final By CommentFieldPath = By.cssSelector("textarea[data-bind*='value: Comment']");
    private final By CheckBoxFieldPath = By.xpath("//input[@class='checkbox']");
    private final By ButtonPath = By.xpath("//button[@id='button-m']");
    private final By ErrorEmailPath = By.xpath("//span[@class='validation-error-text']");

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://www.rgs.ru");
    }

    @Test
    public void test ()throws  NullPointerException, IllegalStateException{
        wait = new WebDriverWait(driver,3);

        //Находим пункт меню "Страхование", двигаемся к нему, кликаем по нему
        WebElement webElement = driver.findElement(InsuranceItemPath);
        new Actions(driver).moveToElement(webElement);
        webElement.click();

        //Находим категорию "ДМС", двигаемся к ней, кликаем по ней
        WebElement dms = driver.findElement(DmsItemPath);
        new Actions(driver).moveToElement(dms);
        dms.click();

        //Находим элемент "Заголовок", проверяем соответствие названия
        WebElement title = driver.findElement(TitleElementPath);
        Assert.assertEquals("Заголовок\"Добровольное медицинское страхование\" присутствует",
                "Добровольное медицинское страхование (ДМС)", title.getText());

        //Находим кнопку "Отправить заявку", двигаемся к ней, кликаем по ней
        WebElement requestButton = driver.findElement(RequestButtonPath);
        new Actions(driver).moveToElement(requestButton);
        requestButton.click();

        //Находим элемент "Лист Заявки", ждем пока он загрузится, проверяем наличие текста названия
        WebElement requestList = driver.findElement(RequestListPath);
        wait.until(ExpectedConditions.visibilityOf(requestList));
        Assert.assertEquals("На странице отсутствует текст:" +
                        " \"Заявка на добровольное медицинское страхование\"",
                "Заявка на добровольное медицинское страхование", requestList.getText());

        //Находим поле "Фамилия", отправляем в него текст
        WebElement lastNameField = driver.findElement(LastNameFieldPath);
        lastNameField.sendKeys("Иванов");

        //Находим поле "Имя", отправляем в него текст
        WebElement firstNameField = driver.findElement(FirstNameFieldPath);
        firstNameField.sendKeys("Иван");

        //Находим поле "Отчество", отправляем в него текст
        WebElement middleNameField = driver.findElement(MiddleNameFieldPath);
        middleNameField.sendKeys("Иванович");

        //Находим поле "Регион", отправляем в него текст
        WebElement region = driver.findElement(RegionFieldPath);
        region.sendKeys("Москва");
        Select select = new Select(driver.findElement(RegionFieldPath));
        WebElement option = select.getFirstSelectedOption();
        String stringRegion = option.getText();

        //Находим поле "Телефон", отправляем в него текст
        WebElement phoneField = driver.findElement(PhoneFieldPath);
        phoneField.sendKeys("9999999999");

        //Находим поле "Email",отправляем в него текст
        WebElement emailField = driver.findElement(EmailFieldPath);
        emailField.sendKeys("qwertyqwerty");

        //Находим поле "Дата", отправляем туда текст
        WebElement contactDateField = driver.findElement(ContactDateFieldPath);
        contactDateField.sendKeys("01.09.2018");

        //Находим поле "Коментарий", отправляем туда текст
        WebElement commentField = driver.findElement(CommentFieldPath);
        commentField.sendKeys("Позвонить заранее");

        //Находим поле "Я согласен на обработку данных", кликаем на него, чтобы поставить галочку
        WebElement checkBoxField = driver.findElement(CheckBoxFieldPath);
        checkBoxField.click();

        //Находим кнопку "Отправить", кликаем, чтобы отправить лист заявки
        WebElement button = driver.findElement(ButtonPath);
        button.click();

        //Находим метку "Ошибка ввода Email"
        WebElement errorEmail = driver.findElement(ErrorEmailPath);

        //Делаем проверки: правильности заполнения полей, нажатого чекбокса, и видимости
        //метки "Ошибка ввода Email"
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
