package selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class PrintTableValues {
	public static void main (String [] args) {
		System.setProperty("webdriver.chrome.driver","./drivers/chromedriver.exe");
		WebDriver wd = new ChromeDriver();
		wd.get("http://money.rediff.com/gainers/bsc/daily/groupa?");
		
		// heads
		WebElement tableHead = wd.findElement(By.xpath("//*[@id=\"leftcontainer\"]/table/thead/tr"));
		List < WebElement > heads = tableHead.findElements(By.tagName("th"));
		for (WebElement h : heads) {
			System.out.print(h.getText() + " ");
		}
		// content
		WebElement tableBody = wd.findElement(By.xpath("//*[@id=\"leftcontainer\"]/table/tbody"));
		//To locate rows of table. 
		List < WebElement > rows = tableBody.findElements(By.tagName("tr"));
		int numRows = rows.size();
		System.out.println("\n total number of rows = " + numRows);
		for (int rowNum = 0; rowNum < numRows ; rowNum++) {
			List < WebElement > columns = rows.get(rowNum).findElements(By.tagName("td"));
			int numColumns = columns.size();
//			System.out.println(numColumns);
			for (WebElement column : columns) {
				System.out.print(column.getText() + "\t");
			}
			System.out.println();
		}
	}
}
