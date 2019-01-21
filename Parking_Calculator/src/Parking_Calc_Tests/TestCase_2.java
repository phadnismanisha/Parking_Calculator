package Parking_Calc_Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase_2 {

	private static StringBuffer errorString = new StringBuffer();
	WebDriver driver;
	
	@BeforeTest
	public void Initialize_Test() {
		
		// Opening the URL in the chrome browser
		System.setProperty("webdriver.chrome.driver",  "C:\\Users\\manis\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://adam.goucher.ca/parkcalc/");
	}
	
		// Testing for scenario when user selects Valet Parking and valid Entry Date/time & Leaving Date/time
		@Test 
		public void ValetParking_WithValidDates()
		{
					
			// Select Valet Parking
			new Select(driver.findElement(By.cssSelector("#Lot"))).selectByValue("VP");
			
			// Enter 10:00AM as EntryTime
			driver.findElement(By.xpath("//*[@id='EntryTime']")).clear();
			driver.findElement(By.xpath("//*[@id='EntryTime']")).sendKeys("10:00");
			// Leave the EntryTime radio to AM so no change to the radio
			
			// Enter 1/1/2019 as Entry Date
			driver.findElement(By.id("EntryDate")).clear();
			driver.findElement(By.id("EntryDate")).sendKeys("1/1/2019");
			
			// Enter 3:00PM as LeavingTime
			driver.findElement(By.xpath("//*[@id='ExitTime']")).clear();
			driver.findElement(By.xpath("//*[@id='ExitTime']")).sendKeys("3:00");
			
			// Clicking the PM radio button to get the Leaving time as 3:00PM
			driver.findElement(By.xpath("/html/body/form/table/tbody/tr[3]/td[2]/font/input[3]")).click();
			
			// Enter 1/1/2019 as Leaving Date
			driver.findElement(By.id("ExitDate")).clear();
			driver.findElement(By.id("ExitDate")).sendKeys("1/1/2019");
			
			// Click on Calculate Button
			driver.findElement(By.name("Submit")).click();
			
			try {
				// Check if the COST is $24.00
				Assert.assertEquals(driver.findElement(By.xpath("/html/body/form/table/tbody/tr[4]/td[2]/span[1]/font/b")).getText(),"$ 12.00"); 
			}
			catch(Error e) {
				errorString.append(e.toString());
				
			}
				
			try {
			// Check if the Leaving Date/Time and Entry Date/Time difference is 5 hours
			Assert.assertEquals(driver.findElement(By.xpath("/html/body/form/table/tbody/tr[4]/td[2]/span[2]/font/b")).getText().trim(),"(0 Days, 5 Hours, 0 Minutes)");
			}
			catch(Error e) {
				errorString.append(" " + e.toString());
			}
			
			String verifyErrorString = errorString.toString();
			
			if(!"".equals(verifyErrorString))
			{
				org.testng.Assert.fail(verifyErrorString);
				
			}
		}
		
		@AfterTest
		public void Close_Driver() throws Exception
		{
			// Closing the browser
			driver.quit();
					
		}

}
