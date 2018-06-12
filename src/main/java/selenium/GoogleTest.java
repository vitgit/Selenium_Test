package selenium;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;


public class GoogleTest {

	public static String getToHomePage(String baseUrl, String homeTitle, WebDriver driver) {
		driver.get(baseUrl);
		if(driver.getTitle().toLowerCase().equals(homeTitle.toLowerCase()))
			return "getToHomePage passed";
		else
			return "getToHomePage failed";
	}
	
	public static void main(String[] args) {
		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface, 
		// not the implementation.
		System.setProperty("webdriver.firefox.marionette","./drivers/geckodriver.exe");
		System.setProperty("webdriver.chrome.driver","./drivers/chromedriver.exe");
		
//		WebDriver driver = new FirefoxDriver();
		WebDriver driver = new ChromeDriver();
		
		String baseUrl = "http://www.google.com";

		// And now use this to visit Google
		driver.get(baseUrl);
		// Alternatively the same thing can be done like this
		// driver.navigate().to("http://www.google.com");

		String homeTitle = driver.getTitle();
		
		// Find the text input element by its name
		WebElement element = driver.findElement(By.name("q"));

		// Enter something to search for
		element.sendKeys("Cheese!");

		// Now submit the form. WebDriver will find the form for us from the element
		element.submit();

		// Check the title of the page
		System.out.println("Page title is: " + driver.getTitle());

		// Google's search is rendered dynamically with JavaScript.
		// Wait for the page to load, timeout after 10 seconds
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("cheese!");
			}
		});

		// Should see: "cheese! - Google Search"
		System.out.println("Page title is: " + driver.getTitle());
		
		System.out.println(getToHomePage(baseUrl, homeTitle, driver));
		
		element = driver.findElement(By.name("q"));
		element.sendKeys("Cheese!");
		element.submit();
		// check cheese appeared many times on page
		String pageSource = driver.getPageSource().toLowerCase();
		Matcher matcher = Pattern.compile("cheese", Pattern.CASE_INSENSITIVE).matcher(pageSource);
		int count = 0;
        while (matcher.find())
            count++;
        System.out.println("cheese appeared in search " + count + " times");
		
        //get first 2 listings link
        //<cite class=...>(http...)</cite>
        matcher = Pattern.compile("<cite\\s+class=.+?>(http.+?)<\\/cite", Pattern.CASE_INSENSITIVE).matcher(pageSource);
        int ii = 0;
        String [] links = new String [2];
        while (matcher.find()) {
        	links[ii] = matcher.group(1);
        	ii++;
        	if(ii == 2)
        		break;
        }
        System.out.println(links[0] + "\n" + links[1]);
        //TBD click on these links. Make sure new page appears. Get back
        driver.get(links[0]);
        if(! homeTitle.equals(driver.getTitle()))
        	System.out.println("getting to " + links[0] + " passed");
        else
        	System.out.println("getting to " + links[0] + " failed");
        System.out.println(getToHomePage(baseUrl, homeTitle, driver));
        
		//Close the browser
		driver.quit();
	}

}
