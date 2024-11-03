package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

	//DataProvider 1
	
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path=".\\testData\\opencartLoginData.xlsx";//taking excel file from testData
		
		ExcelUtility xlutils=new ExcelUtility(path);//creating an object for XLUtility
		
		int totalRows=xlutils.getRowCount("Sheet1");
		int totalCols=xlutils.getCellCount("Sheet1",1);
				
		String loginData[][]=new String[totalRows][totalCols];//created for two dimension array which can store the data user and password
		
		for(int i=1;i<=totalRows;i++)  //1   //read the data from xl storing in two-dimensional array
		{		
			for(int j=0;j<totalCols;j++)  //0    i is rows j is col
			{
				loginData[i-1][j]= xlutils.getCellData("Sheet1",i, j);  //1,0
			}
		}
	return loginData;//returning two dimension array
				
	}

}
