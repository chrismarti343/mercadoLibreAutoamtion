package mercadoLibre;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

public class ItemsList {
	WebDriver dv;

	@BeforeMethod
	public void beforeMethod() {
	
				String url = "https://www.mercadolibre.com.ec/";
			
		//Ignores Chrome certification
		ChromeOptions options = new ChromeOptions();
		options.addArguments("ignore-certificate-errors");
		options.addArguments("--incognito");
		options.addArguments("--remote-allow-origins=*");
		dv = new ChromeDriver(options); 
		dv.manage().window().maximize();
		dv.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		dv.get(url);

	}
	
	@Test
	public void testItemsList () throws InterruptedException, IOException {
		Thread.sleep(2000);
		dv.findElement(By.id("cb1-edit")).sendKeys("camisetas");
		dv.findElement(By.id("cb1-edit")).sendKeys(Keys.ENTER);
		

  
		List<Articulo> articulos = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
        	
        	// Wait till products load in the page
        	WebDriverWait wait = new WebDriverWait(dv, Duration.ofSeconds(10));
        	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='ui-search-layout__item']")));
        	
        	List<WebElement> elementos = dv.findElements(By.xpath("//li[@class='ui-search-layout__item']"));
       
	        for (WebElement elemento : elementos) {
	            String name = elemento.findElement(By.xpath(".//div[contains(@class,'ui-search-item__group ui-search-item__group--title shops__items-group')]")).getText();
	            String price = elemento.findElement(By.xpath(".//span[contains(@class,'andes-visually-hidden')]")).getText();
	            String link = elemento.findElement(By.xpath(".//a")).getAttribute("href");
	 
	            articulos.add(new Articulo(name, price, link));
	        }
        
		      // Scroll down to the bottom of the page
		      JavascriptExecutor js = (JavascriptExecutor) dv;
		      js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
	      
		      // Click on Siguiente
		      dv.findElement(By.xpath("//li[@class='andes-pagination__button andes-pagination__button--next shops__pagination-button']//a[@title='Siguiente']")).click();
		        
        }
        
       // Write the information into a text file
        File file = new File("marcadolibre-Items.txt");
        FileWriter writer = new FileWriter(file);
        int count = 1;
        for (Articulo articulo : articulos) {
            writer.write(count+ ") Producto: " + articulo.getNombre() + "\n" + "Precio: " + articulo.getPrecio() + "\n" + "Link:  " + articulo.getLink() + "\n");
            count++;
        }
        writer.close();
  
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws Exception {
		
		
		if(ITestResult.FAILURE==result.getStatus())
		{
			System.out.println("Failed: " + result.getMethod().getMethodName());
			
		} else {
			System.out.println("Successful: " + result.getMethod().getMethodName());
		}
			
		dv.quit();
	
	}

}

