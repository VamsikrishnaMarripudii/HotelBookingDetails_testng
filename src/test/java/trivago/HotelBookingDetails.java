 package trivago;
 
import java.io.File;
import java.io.IOException;
 
import java.text.SimpleDateFormat;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
 
import org.testng.Assert;
import org.testng.annotations.Test;
 
 
public class HotelBookingDetails extends BaseClass {
		
	     //Verify Title
	      @Test
		  public void verifyTitle() throws InterruptedException
		  {
			  String title = driver.getTitle();
			 
			  System.out.println("\nThe Title of the trivago is: "+title);
			  Assert.assertEquals(title,"trivago.in - Compare hotel prices worldwide");  
			  
		  }
	
	    
	    //CaptureScreenshot
	    public static void captureScreenshot(WebDriver driver, String baseName) throws IOException {
	        
	         String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	         String fileName = baseName + "_" + timestamp + ".png";
 
	         File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	         FileUtils.copyFile(screenshot, new File("C:\\Users\\2332922\\eclipse-workspace\\Test\\src\\Screenshots\\" + fileName));
	         System.out.println("\nScreenshot Captured Successfully: " + fileName);
	     }
	    
	    @Test
	    public void capturePage()
	    {
	    	 try {
	             captureScreenshot(driver, "Trivago_page");
	         } catch (IOException e) {
	             e.printStackTrace();
	         }
	
	    }
	    
	  //Scroll the  page by Pixel Number
	    @Test(priority = 1)
	    public void scrollByPixels() {
	        try
	        {
	            // Scroll down by 500 pixels
	            JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("window.scrollBy(0,150)");
	            Thread.sleep(2000);
	            
	        }
	        catch (Exception e)
	        {
	            System.out.println("An error occurred: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
 
	    @Test(priority = 1)
	    public void testUsingExcelData() throws IOException, InterruptedException {
	    	
	        String filePath = "C:\\Users\\2332922\\Downloads\\City.xlsx";
	        ExcelUtility excelUtility = new ExcelUtility();
	        String city = excelUtility.getCellValue(filePath, 0, 1, 1); // Sheet 0, Row 1, Cell 1
 
	        System.out.println("\nCity Successfully captured From Excel Utility - City Name : "+city);
	       
	        // Pass data into application
	        WebElement destination = driver.findElement(By.id("input-auto-complete"));
	        destination.sendKeys(city);
	        Thread.sleep(2000);
            destination.click();
            Thread.sleep(2000);
	    }
	   
	    
	    @Test(priority = 2)
	    public void selectDate() throws InterruptedException {
	    	
	        // Select Date
	        WebElement datee = driver.findElement(By.xpath("//div[@class = '_3axGO1 r5zBu4 c_L5el']//fieldset[@class = 'MGDNki']//button//span[@class = 'Ji89fk _61wiWy']"));
	        datee.click();
	        Thread.sleep(2000);
	        WebElement dateFix = driver.findElement(By.xpath("//time[@datetime = '2024-10-25']"));
	        dateFix.click();
	        Thread.sleep(2000);
	    }
	
	    
	    @Test(priority = 3)
	    public void selectGuests() throws InterruptedException {
	    	
	        // Click guest icon
	        WebElement guesticon = driver.findElement(By.xpath("//button[@data-testid='search-form-guest-selector']"));
	        guesticon.click();
	        Thread.sleep(1000);
	
	        // Reduce adults by 1
	        WebElement adult = driver.findElement(By.xpath("//button[@data-testid='adults-amount-minus-button']"));
	        adult.click();
	        Thread.sleep(1000);
	
	        // Click apply button
	        WebElement applyButton = driver.findElement(By.xpath("//button[@data-testid='guest-selector-apply']"));
	        applyButton.click();
	        Thread.sleep(2000);
	    }
	
	    
	    @Test(priority = 4)
	    public void searchHotels() throws InterruptedException {
	        // Click search button
	        WebElement search = driver.findElement(By.xpath("//button[@data-testid ='search-button-with-loader']"));
	        search.click();
	        Thread.sleep(5000);
	    }
	
	    @Test(priority = 5)
	    public void sortHotelsByRating() throws InterruptedException {
	    	
	        // Sort by rating
	        WebElement sort = driver.findElement(By.id("sorting-selector"));
	        Select s = new Select(sort);
	        s.selectByIndex(4); // Select "Rating"
	        Thread.sleep(2000);
	        
	        // Print confirmation message
	        System.out.println();
	        System.out.println("Hotels sorted successfully by rating.");
	    }
	
	    
	    //Scroll the  page by Pixel Number
	    @Test(priority = 6,dependsOnMethods={"sortHotelsByRating"})
	    public void testScrollByPixels() {
	        try {
	            // Scroll down by 500 pixels
	            JavascriptExecutor js = (JavascriptExecutor) driver;
	            Thread.sleep(2000);
	            
	            js.executeScript("window.scrollBy(0,200)");
	            Thread.sleep(2000);
	            
	            js.executeScript("window.scrollBy(201,500)");
	            Thread.sleep(2000);
	                      
 
	        }
	        catch (Exception e)
	        {
	            System.out.println("An error occurred: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
 
	    
	    @Test(priority = 7)
	    public void verifyHotelNamesAndPrices() throws InterruptedException {
	    	
	        // Get first 5 hotels and their prices
	        List<WebElement> hotels = driver.findElements(By.xpath("//*[contains(@data-testid, 'item-name')]"));
	        List<WebElement> prices = driver.findElements(By.xpath("//span[@class='_1yAX4_ XMAEyU WRSws_']"));
	
	        Assert.assertTrue(hotels.size() >= 5 && prices.size() >= 5, "Less than 5 hotels found!");
	
	        // Store hotel names and prices in a list
	        List<Hotel> hotelList = new ArrayList<>();
	        
	        for (int i = 0; i < Math.min(5, hotels.size()); i++)
	        {
	            String hotelName = hotels.get(i).getText();
	            String priceText = prices.get(i).getText().replace("₹", "").replace(",", "").trim();
	            int price = Integer.parseInt(priceText);
	
	            hotelList.add(new Hotel(hotelName, price));
	        }
	
	        // Print hotel names with prices
	        System.out.println("\nFirst 5 hotels with prices:");
	        System.out.println("-----------------------------");
	        
	        for (Hotel hotel : hotelList)
	        {
	            System.out.println("Hotel: " + hotel.getName() + " - Price: ₹" + hotel.getPrice());
	        }
	
	        // Sort prices in descending order
	        List<Integer> sortedPrices = new ArrayList<>();
	        
	        for (Hotel hotel : hotelList)
	        {
	            sortedPrices.add(hotel.getPrice());
	        }
	        
	        Collections.sort(sortedPrices, Collections.reverseOrder());
	
	        // Print sorted prices
	        System.out.println("\nSorted prices in descending order:");
	        System.out.println("-------------------------------------");
	        
	        for (int price : sortedPrices)
	        {
	            System.out.println("Price: ₹" + price);
	        }
	        
	        System.out.println("\nPrices Successfully sorted in Descending Order");
	
	        // Print hotel names with sorted prices
	        System.out.println("\nHotels with prices in sorted order:");
	        System.out.println("-------------------------------------");
	        
	        Map<Integer, String> priceHotelMap = new HashMap<>();
	        
	        for (Hotel hotel : hotelList)
	        {
	            priceHotelMap.put(hotel.getPrice(), hotel.getName());
	        }
	
	        for (int price : sortedPrices)
	        {
	            String hotelName = priceHotelMap.get(price);
	            System.out.println("Hotel: " + hotelName + " - Price: ₹" + price);
	        }
	        
	        System.out.println("-------------------------------------");
	        
	    }
		    public void verifyHotelRatingsAreSorted() throws InterruptedException {
		        // Verify if hotel ratings are sorted in descending order
//		        List<WebElement> rating = driver.findElements(By.xpath("//span[@itemprop='ratingValue']"));
		        List<WebElement> rating = driver.findElements(By.xpath("//strong//span[@itemprop='ratingValue']"));
		        List<String> original = new ArrayList<>();
		        for (WebElement str : rating)
		        {
		            original.add(str.getText());
		        }
		
		        List<String> duplicate = new ArrayList<>(original);
		        Collections.sort(duplicate);
		        Collections.reverse(duplicate);
		
		        Assert.assertEquals(original, duplicate, "Hotel ratings are not sorted in descending order!");
		    }
		
 
	    
	
//	    @Test(priority = 8)
//	    public void verifyHotelRatingsAreSorted() throws InterruptedException {
//	        // Verify if hotel ratings are sorted in descending order
////	        List<WebElement> rating = driver.findElements(By.xpath("//span[@itemprop='ratingValue']"));
//	        List<WebElement> rating = driver.findElements(By.xpath("//strong//span[@itemprop='ratingValue']"));
//	        List<String> original = new ArrayList<>();
//	        for (WebElement str : rating)
//	        {
//	            original.add(str.getText());
//	        }
//	
//	        List<String> duplicate = new ArrayList<>(original);
//	        Collections.sort(duplicate);
//	        Collections.reverse(duplicate);
//	
//	        Assert.assertEquals(original, duplicate, "Hotel ratings are not sorted in descending order!");
//	    }
//	
 
	    // Custom class to store hotel name and price
	    class Hotel {
	        private String name;
	        private int price;
	
	        public Hotel(String name, int price) {
	            this.name = name;
	            this.price = price;
	        }
	
	        public String getName() {
	            return name;
	        }
	
	        public int getPrice() {
	            return price;
	        }
	    }
	}
 
	
 