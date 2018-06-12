package selenium;

import java.util.Iterator;		
import java.util.Set;		
import org.openqa.selenium.By;		
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MultipleWindows {
	public static void main(String[] args) throws InterruptedException {									
		System.setProperty("webdriver.chrome.driver","./drivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();			

		//Launching the site.				
		driver.get("http://demo.guru99.com/popup.php");			
//		driver.manage().window().maximize();		

		String MainWindow=driver.getWindowHandle();
		
		driver.findElement(By.xpath("//*[contains(@href,'popup.php')]")).click();			

//		String MainWindow=driver.getWindowHandle();		

		// To handle all new opened window.				
		Set<String> s1=driver.getWindowHandles();		
		Iterator<String> i1=s1.iterator();		

		while(i1.hasNext())			
		{		
			String ChildWindow=i1.next();		

			if(!MainWindow.equalsIgnoreCase(ChildWindow))			
			{    		

				// Switching to Child window
				driver.switchTo().window(ChildWindow);	                                                                                                           
				driver.findElement(By.name("emailid"))
				.sendKeys("gaurav.3n@gmail.com");                			

				driver.findElement(By.name("btnLogin")).click();			
	
				String xpath = "/html/body/table/tbody/tr[1]/td/h2";
				WebElement we = driver.findElement(By.xpath(xpath));
				String text = we.getText();
				if(text.toLowerCase().contains("access details to demo site")) {
					System.out.println("Passed");
					System.out.println(text);
				}else {
					System.out.println("Failed");
				}
								
				// Closing the Child Window.
				driver.close();		
			}		
		}		
		// Switching to Parent window i.e Main Window.
		driver.switchTo().window(MainWindow);	
		
		driver.quit();
	}

}
