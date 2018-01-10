package testCases;

import java.io.IOException;
import java.util.Properties;

import operation.ReadObject;
import operation.UIOperation;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import excelExportAndFileIO.ReadGuru99ExcelFile;

public class HybridExecuteTest {
	
	WebDriver webdriver = null;
	String Browser_name=null;
	@Parameters({ "browser" })
    @Test(priority=0)
	public void GetBrowserName(String browser )
	{
		System.out.println("Browser Name is "+browser);
		Browser_name=browser;
	}
	
	
	
	@Test(dataProvider="hybridData")
	public void testLogin(String testcaseName,String keyword,String objectName,String objectType,String value/*String browser*/ ) throws Exception {
		// TODO Auto-generated method stub
    	System.out.println("Executing Script");
    	System.out.println("Value of testcaseName" + testcaseName);
    	System.out.println("Value of keyword" + keyword);
    	System.out.println("Value of objectName" + objectName);
    	System.out.println("Value of objectType" + objectType);
    	System.out.println("Value of value" + value);
    	
    	
    	if(testcaseName!=null&&testcaseName.length()!=0){
    	if(Browser_name.equalsIgnoreCase("Firefox"))
    	{
    		System.out.println("Browser Name is "+Browser_name);
    		System.setProperty("webdriver.gecko.driver", "C:\\Workspace\\Custom Installations\\geckodriver-v0.19.1-win64\\geckodriver.exe"); 	
    		webdriver=new FirefoxDriver();
    	}
    	
    	else if(Browser_name.equalsIgnoreCase("Chrome"))
    	{
    		System.out.println("Browser Name is "+Browser_name);
    		System.setProperty("webdriver.chrome.driver", "C:\\Workspace\\Custom Installations\\chromedriver_win32_2.34\\chromedriver.exe");   //
			
    		webdriver = new ChromeDriver();
    	}
    	}
        ReadObject object = new ReadObject();
        Properties allObjects =  object.getObjectRepository();
        UIOperation operation = new UIOperation(webdriver);
      	//Call perform function to perform operation on UI
    			operation.perform(allObjects, keyword, objectName,
    				objectType, value);
    	  
	
	}
	
	@AfterTest()
	public void afterTest()
	{
		webdriver.close();
	}

    
    @DataProvider(name="hybridData")
	public Object[][] getDataFromDataprovider() throws IOException{
    	Object[][] object = null; 
    	ReadGuru99ExcelFile file = new ReadGuru99ExcelFile();
        
         //Read keyword sheet
         Sheet guru99Sheet = file.readExcel(System.getProperty("user.dir")+"\\","TestCase.xlsx" , "KeywordFramework");
       //Find number of rows in excel file
     	int rowCount = guru99Sheet.getLastRowNum()-guru99Sheet.getFirstRowNum();
     	object = new Object[rowCount][5];
     	for (int i = 0; i < rowCount; i++) 
     	{
    		//Loop over all the rows
    		Row row = guru99Sheet.getRow(i+1);
    		//Create a loop to print cell values in a row
    		for (int j = 0; j < row.getLastCellNum(); j++) {
    			//Print excel data in console
    			object[i][j] = row.getCell(j).toString();
    		    // Debug Logger System.out.println(object[i][j]+"Value of i"+i+"Value of J"+j);
    		}
         
    		
     	}
     	System.out.println("");
     	  return object;	 
	}
}
