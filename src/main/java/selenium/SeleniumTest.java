package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
//comment the above line and uncomment below line to use Chrome
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {

	public static void checkPageIsReady(WebDriver driver) {

		JavascriptExecutor js = (JavascriptExecutor)driver;

		//Initially bellow given if condition will check ready state of page.
		if (js.executeScript("return document.readyState").toString().equals("complete")){ 
			System.out.println("Page Is loaded.");
			return; 
		} 

		//This loop will rotate for 25 times to check If page Is ready after every 1 second.
		//You can replace your value with 25 If you wants to Increase or decrease wait time.
		for (int i=0; i<25; i++){ 
			try {
				Thread.sleep(1000);
			}catch (InterruptedException e) {} 
			//To check page ready state.
			if (js.executeScript("return document.readyState").toString().equals("complete")){ 
				break; 
			}   
		}
	}

	public static void validation(boolean contition, String message) {
		if(contition)
			System.out.println(message + " - Passed!");
		else
			System.out.println(message + " - Failed");
	}
	
	public static void getToHomePage(WebDriver driver, String baseUrl, String expectedHomeTitle) {
		driver.get(baseUrl);
		String actualTitle = driver.getTitle();
		validation(actualTitle.contentEquals(expectedHomeTitle), "getting to home page");
	}

	public static void main(String[] args) {
		// declaration and instantiation of objects/variables
//		System.setProperty("webdriver.firefox.marionette","./drivers/geckodriver.exe");
//		WebDriver driver = new FirefoxDriver();
		//comment the above 2 lines and uncomment below 2 lines to use Chrome
		System.setProperty("webdriver.chrome.driver","./drivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		String baseUrl = "http://demo.guru99.com/test/newtours/";
		String expectedHomeTitle = "Welcome: Mercury Tours";
		String actualTitle = "";
		
		getToHomePage(driver, baseUrl, expectedHomeTitle);	
		
		driver.findElement(By.linkText("SIGN-ON")).click();
		String expectedTitle = "Sign-on: Mercury Tours";
		actualTitle = driver.getTitle();
		
		validation(actualTitle.contentEquals(expectedTitle), "getting to Sign-on: Mercury Tours page");

		WebElement userName = driver.findElement(By.name("userName"));
		userName.sendKeys("vitaly");
		WebElement submit = driver.findElement(By.name("submit"));
		submit.click();

		checkPageIsReady(driver);
		
		String pageSource = driver.getPageSource();
		
		validation(pageSource.toLowerCase().contains("enter your username and password correct"), 
				"typed: enter your username and password correct");
		
		// return home:
		driver.findElement(By.linkText("Home")).click();
		actualTitle = driver.getTitle();
		
		validation(actualTitle.contentEquals(expectedHomeTitle), "link Home click");
			
		driver.findElement(By.linkText("Telecom Project")).click();
		actualTitle = driver.getTitle();
		expectedTitle = "Guru99 Telecom";
		
		validation(actualTitle.contentEquals(expectedTitle), "getting to Guru99 Telecom page");
		
		String text = "Guru99 telecom";
		
		validation(driver.findElement(By.linkText("Guru99 telecom")).getText().trim().contains(text),
				"link exists, named: Guru99 telecom");
		
		getToHomePage(driver, baseUrl, expectedHomeTitle);
		
		//close browser
		driver.close();
		System.exit(0);
	}
}
