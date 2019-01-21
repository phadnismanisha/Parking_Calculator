package Parking_Calc_Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

public class TestCase_1 {
	private static StringBuffer errorString = new StringBuffer();
	WebDriver driver;
	
	@BeforeTest
	public void Initialize_Test() {
		
		// Opening the URL in the chrome browser
		System.setProperty("webdriver.chrome.driver",  "C:\\Users\\manis\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://adam.goucher.ca/parkcalc/");
	}
	
	// Testing for the scenario when user selects Short-Term Parking and enters valid Entry Date/Time & Leaving Date/Time
	@Test 
	public void ShortTermParking_WithValidDates()
	{
				
		// Select Short-Term Parking
		new Select(driver.findElement(By.cssSelector("#Lot"))).selectByValue("STP");
		
		// Enter 12:00AM as EntryTime
		driver.findElement(By.xpath("//*[@id='EntryTime']")).clear();
		driver.findElement(By.xpath("//*[@id='EntryTime']")).sendKeys("12:00");
		// Leave the EntryTime radio to AM so no change to the radio
		
		// Enter 1/1/2019 as Entry Date
		driver.findElement(By.id("EntryDate")).clear();
		driver.findElement(By.id("EntryDate")).sendKeys("1/1/2019");
		
		// Enter 12:00PM as LeavingTime
		driver.findElement(By.xpath("//*[@id='ExitTime']")).clear();
		driver.findElement(By.xpath("//*[@id='ExitTime']")).sendKeys("12:00");
		
		// Clicking the PM radio button of Leaving time to get the Leaving time as 12:00PM
		driver.findElement(By.xpath("/html/body/form/table/tbody/tr[3]/td[2]/font/input[3]")).click();
		
		// Enter 1/1/2019 as Leaving Date
		driver.findElement(By.id("ExitDate")).clear();
		driver.findElement(By.id("ExitDate")).sendKeys("1/1/2019");
		
		// Click on Calculate Button
		driver.findElement(By.name("Submit")).click();
		
		try {
			// Check if the COST is $24.00
			Assert.assertEquals(driver.findElement(By.xpath("/html/body/form/table/tbody/tr[4]/td[2]/span[1]/font/b")).getText(),"$ 24.00"); 
		}
		catch(Error e) {
			errorString.append(e.toString());
			
		}
			
		try {
		// Check if the Leaving Date/Time and Entry Date/Time difference is 12 hours
		Assert.assertEquals(driver.findElement(By.xpath("/html/body/form/table/tbody/tr[4]/td[2]/span[2]/font/b")).getText().trim(),"(0 Days, 12 Hours, 0 Minutes)");
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
