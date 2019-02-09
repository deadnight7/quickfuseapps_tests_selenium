package com.pilvo.dragndrop;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
/*
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
 */

public class DragnDrop_TC001 {
	WebDriver driver;
	
	public static void main (String args[]) 
	{
		try 
		{
			DragnDrop_TC001 t = new DragnDrop_TC001();
			t.check_action();
		}
		catch (Exception e) 
		{
			e.getMessage();
		}
	}
	
	@Test
	public void check_action() throws InterruptedException{
		
		try 
		{
			

			
			System.setProperty("webdriver.chrome.driver","chromedriver");
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			ChromeOptions option = new ChromeOptions();
			option.addArguments("test-type");
			option.addArguments("--disable-geolocation");
			option.addArguments("--disable-infobars");
			//option.addArguments("--disable-popup-blocking");
			option.setExperimentalOption("prefs",prefs);
			option.addArguments("--disable-popup");
			option.addArguments("--window-size=1280,768");//Set the size of the screen exact
			
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, option);
			
			driver = new ChromeDriver(capabilities);
			driver.manage().window().maximize();
			driver.get("http://quickfuseapps.com/");
			
			driver.findElement(By.xpath("//a[@id='link-create']")).click();
			WebDriverWait wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='intro-dialog-cont']//div[@class='ui-dialog-buttonpane ui-widget-content ui-helper-clearfix']")));
			
			driver.findElement(By.xpath("//div[@id='intro-dialog-cont']//div[@class='ui-dialog-buttonpane ui-widget-content ui-helper-clearfix']")).click();
			
			//Click Get Started
			driver.findElement(By.xpath("//a[@id='add-page']")).click();
			Thread.sleep(4000);
			driver.findElement(By.xpath("//form[@class='unsubmittable']//input[@name='name']")).sendKeys("TestPage");
			Thread.sleep(4000);
			driver.findElement(By.xpath("//div[@id='create-dialog']/following::button[1]")).click();
			Thread.sleep(4000);
			
			//Click Messaging
			driver.findElement(By.xpath("//*[@id='accordion']//a[contains (text(), 'Messaging')]")).click();
			TimeUnit.SECONDS.sleep(3);
			
			Point p = driver.findElement(By.xpath("//li[contains (., 'Send an SMS')]")).getLocation();
			System.out.println(p.getX()+","+p.getY());
			Point p1 = driver.findElement(By.xpath("//*[@id='module-1']//div[text()='Start']")).getLocation();
			System.out.println(p1.getX()+","+p1.getY());
			
			//Move Send an SMS 
			Actions actions = new Actions(driver);
			WebElement source=  driver.findElement(By.xpath("//li[contains (., 'Send an SMS')]//span")); //"//*[@id='accordion']/div[4]/ul/li[3]"));
			WebElement destination = driver.findElement(By.xpath("//*[@id='module-1']//div[text()='Start']")); ///"//*[@id='module-1']/div[1]/div[2]/div/div[2]"));
			
			//Drop Send an SMS to Board
			actions.clickAndHold(source).moveToElement(destination, -100, 110).release().build().perform();;
			
			//Wait for sec for module to be loaded on screen with an arrow on top
			TimeUnit.SECONDS.sleep(3);

			//Assign new Source and Destnation for connecting start and send an SMS module
			source = driver.findElement(By.xpath("//*[text()='Start']//ancestor::div[contains (@id, \"module-1\")]//div[contains (@class, 'draggable')]")); //Source -   //*[text()='Start']//ancestor::div[contains (@id, "module-1")]//div[contains (@class, 'draggable')]  --  //--//*[@id="module-1"]//div[contains (@class, 'draggable')]
			destination = driver.findElement(By.xpath("//div[text()='Send an SMS']//ancestor::div[contains (@id,\"module\")]//div[contains (@class, 'droppable')]")); //Destination - //div[text()='Send an SMS']//ancestor::div[contains (@id,"module")]//div[contains (@class, 'droppable')] 
			
			//Connect Start module > Send and SMS Module		
			actions.clickAndHold(source).moveToElement(destination).release().build().perform();
			
			//wait for a sec to join to get connected
			TimeUnit.SECONDS.sleep(1);
			
			//Put Send an Email Module on Board just next to Send an SMS Module
			source=  driver.findElement(By.xpath("//li[contains (., 'Send an Email')]//span"));
			destination = driver.findElement(By.xpath("//div[text()='Send an SMS']//ancestor::div[contains (@id,\"module\")]"));
			
			//Drag and Drop third Module : Sending an Email - on board
			actions.clickAndHold(source).moveToElement(destination, 290, 130).release().build().perform();
			
			//wait for module to get loaded on screen
			TimeUnit.SECONDS.sleep(3);
			
			//Assign new Source and Destnation for connecting 'Send an SMS' and 'Send an Email' module
			source = driver.findElement(By.xpath("//*[text()='Send an SMS']//ancestor::div[contains (@id, \"module\")]//div[contains (@class, 'draggable') and contains(@class,'syn-node-attached-e')]")); 
			destination = driver.findElement(By.xpath("//div[text()='Send an Email']//ancestor::div[contains (@id,\"module\")]//div[contains (@class, 'droppable')]")); 
			actions.clickAndHold(source).moveToElement(destination).release().build().perform();
			
			//Wait for connection to be succesful
			TimeUnit.SECONDS.sleep(1);
			
			//Click Basic Tab for getting Hang Up or exit - //*[@id='accordion']//a[contains (text(), 'Basic')]
			driver.findElement(By.xpath("//*[@id='accordion']//a[contains (text(), 'Basic')]")).click();
			
			//Wait for animation to complete
			TimeUnit.SECONDS.sleep(1);
			
			//Put Hang up or Exit button on board - //li[contains (., 'Hang Up or Exit')]//span
			source = driver.findElement(By.xpath("//li[contains (., 'Hang Up or Exit')]//span")); 
			destination = driver.findElement(By.xpath("//div[text()='Send an SMS']//ancestor::div[contains (@id,\"module\")]//div[contains (@class, 'draggable') and contains(@class,'syn-node-attached-w')]"));  
			actions.clickAndHold(source).moveToElement(destination, -100, 50).release().build().perform();
			TimeUnit.SECONDS.sleep(3);
			
			//Connect - Send an SMS with Exit App (Module-4)
			source = driver.findElement(By.xpath("//div[text()='Send an SMS']//ancestor::div[contains (@id,\"module\")]//div[contains (@class, 'draggable') and contains(@class,'syn-node-attached-w')]"));// //Source - //div[text()='Send an SMS']//ancestor::div[contains (@id,"module")]//div[contains (@class, 'draggable') and contains(@class,'syn-node-attached-w')] 
			destination = driver.findElement(By.xpath("//div[text()='Exit App']//ancestor::div[contains (@id,\"module-4\")]//div[contains (@class, 'droppable')]"));//		//Destination with - //div[text()='Exit App']//ancestor::div[contains (@id,"module-4")]//div[contains (@class, 'droppable')]  
			actions.clickAndHold(source).moveToElement(destination).release().build().perform();
			TimeUnit.SECONDS.sleep(1);
			
			//Put Hang up or Exit button on board - //li[contains (., 'Hang Up or Exit')]//span
			source = driver.findElement(By.xpath("//li[contains (., 'Hang Up or Exit')]//span")); 
			destination = driver.findElement(By.xpath("//div[text()='Send an Email']//ancestor::div[contains (@id,\"module\")]//div[contains (@class, 'draggable') and contains(@class,'syn-node-attached-w')]"));  
			actions.clickAndHold(source).moveToElement(destination, -100, -100).release().build().perform();
			TimeUnit.SECONDS.sleep(1);
					
			//Connect - Send an SMS with Exit App (Module-4)
			source = driver.findElement(By.xpath("//div[text()='Send an Email']//ancestor::div[contains (@id,\"module\")]//div[contains (@class, 'draggable') and contains(@class,'syn-node-attached-w')]"));// //Source - //div[text()='Send an SMS']//ancestor::div[contains (@id,"module")]//div[contains (@class, 'draggable') and contains(@class,'syn-node-attached-w')] 
			destination = driver.findElement(By.xpath("//div[text()='Exit App']//ancestor::div[contains (@id,\"module-5\")]//div[contains (@class, 'droppable')]"));//		//Destination with - //div[text()='Exit App']//ancestor::div[contains (@id,"module-4")]//div[contains (@class, 'droppable')]  
			actions.clickAndHold(source).moveToElement(destination).release().build().perform();
			TimeUnit.SECONDS.sleep(1);
			
			//Put Hang up or Exit button on board - //li[contains (., 'Hang Up or Exit')]//span
			source = driver.findElement(By.xpath("//li[contains (., 'Hang Up or Exit')]//span")); 
			destination = driver.findElement(By.xpath("//div[text()='Send an Email']//ancestor::div[contains (@id,\"module\")]//div[contains (@class, 'draggable') and contains(@class,'syn-node-attached-e')]"));  
			actions.clickAndHold(source).moveToElement(destination, +100, -100).release().build().perform();
			TimeUnit.SECONDS.sleep(1);
			
			//Connect - Send an SMS with Exit App (Module-4)
			source = driver.findElement(By.xpath("//div[text()='Send an Email']//ancestor::div[contains (@id,\"module\")]//div[contains (@class, 'draggable') and contains(@class,'syn-node-attached-e')]"));// //Source - //div[text()='Send an SMS']//ancestor::div[contains (@id,"module")]//div[contains (@class, 'draggable') and contains(@class,'syn-node-attached-w')] 
			destination = driver.findElement(By.xpath("//div[text()='Exit App']//ancestor::div[contains (@id,\"module-6\")]//div[contains (@class, 'droppable')]"));//		//Destination with - //div[text()='Exit App']//ancestor::div[contains (@id,"module-4")]//div[contains (@class, 'droppable')]  
			actions.clickAndHold(source).moveToElement(destination).release().build().perform();
			TimeUnit.SECONDS.sleep(1);
			
		}
		catch (Exception e) 
		{
			e.getMessage();
		}
	}



	public void teardown()
	{
		driver.quit();
	}

}