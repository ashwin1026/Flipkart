package bizobjects.flipkart.cases;

/*
 * @author - Ajujgar
 * @Date - 21st Mar 2021
 * @Task - Item Purchase flow
 */

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import bizobjects.flipkart.screens.ScrCart;
import bizobjects.flipkart.screens.ScrDeliveryAddress;
import bizobjects.flipkart.screens.ScrHome;
import bizobjects.flipkart.screens.ScrItemDetails;
import bizobjects.flipkart.screens.ScrLogin;
import bizobjects.flipkart.screens.ScrOppoMobilePhones;
import commonutils.CommonBusiness;

public class UCItemPurchase extends CommonBusiness {


	/**
	 * This method will complete the flow of item purchase.
	 * 
	 * The processItemPurchase method is a method that is in charge of orchestrating the events necessary to access the 'Item Purchase' flow.
	 * @param driver (WebDriver)		- The instance of the WebDriver created at the time of launching the browser.
	 * @param prop (Properties)			- Properties object with the test data read while launching the browser
	 * @TASK  Complete the item purchase flow to buy Oppo mobile
	 */
	public void processItemPurchase(WebDriver driver, Properties prop) {

		ScrHome scrh = new ScrHome();
		ScrOppoMobilePhones scromp = new ScrOppoMobilePhones();
		ScrItemDetails scrid = new ScrItemDetails();
		ScrCart scrc = new ScrCart();
		ScrLogin scrl = new ScrLogin();
		ScrDeliveryAddress scrda = new ScrDeliveryAddress();
		String methodName = getCallingMethod(0);
		
		logStartMethod(methodName);
		try {

			driver.findElement(By.xpath("//body/div[2]/div/div/button")).click(); //Closes pop up

			scrh.clickOnButton_Link(driver, "Mobiles");
		
			scrh.clickOnButton_Link(driver, "Electronics");
			scrh.clickOnButton_Link(driver, "OPPO");
			scromp.clickOnButton_Link(driver, "View All");
			scromp.pageScrollDown(driver, "OppoA3sRed16GB");
			scromp.clickOnButton_Link(driver, "OppoA3sRed16GB");
			switchToChildWindow(driver);
			scrid.clickOnButton_Link(driver, "AddToCart");
			scrh.clickOnButton_Link(driver, "FlipkartLogo");

			driver.findElement(By.xpath("//body/div[3]/div/div/button")).click(); //Closes pop up

			scrh.clickOnButton_Link(driver, "Cart");
			scrc.isElementVisible(driver, "OppoA3sRed16GB");
			scrc.clickOnButton_Link(driver, "PlaceOrder");
			String userName = prop.getProperty("username");
			String pass = prop.getProperty("password");

			scrl.fillText(driver, "Email_phone", userName);
			scrl.clickOnButton_Link(driver, "Continue");
			scrl.fillText(driver, "Password", pass);
			scrl.clickOnButton_Link(driver, "Login");
			scrda.clickOnButton_Link(driver, "DeliverHere");

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}