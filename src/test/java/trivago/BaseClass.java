package trivago;
 
import java.time.Duration;
 
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.edge.EdgeDriver;
 
import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;

import org.testng.annotations.Parameters;
 
public class BaseClass {

	 WebDriver driver;

	    @BeforeClass

	    @Parameters({"browser"})  // cross browsing

	    public void setUp(String br) throws InterruptedException {

	    	switch(br.toLowerCase())	    	{

	        	case "chrome": 

        		  driver = new ChromeDriver(); 

	        		System.out.println("\nChrome Succesfully Launched");

	        		break;

	        	case "edge":       		

	        		driver = new EdgeDriver(); 

	        		System.out.println("\nEdge Succesfully Launched");

	        		break;

	        	default : 

	        		System.out.println("Invalid Browser"); 

	        		return;

	    	}

	    	driver.get("https://www.trivago.in");

	    	driver.manage().window().maximize();

	        driver.manage().deleteAllCookies();   //remove cookies

	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	    }
 
	    @AfterClass

	    public void tearDown() {

	        driver.quit();

	    }
 
}

 