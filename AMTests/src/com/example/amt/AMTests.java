package com.example.amt;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
	
	
public class AMTests extends UiAutomatorTestCase{

		public void test_1() throws UiObjectNotFoundException {
			String test_barcode = "123456";
			String what_code = getParams().getString("to");
			if (what_code != null) {
				test_barcode = what_code.trim();
			}
		findAndRunApp();
		searchWithoutScan(test_barcode);
		exitToMainWindow();
		}
		
		private void findAndRunApp() throws UiObjectNotFoundException {
	        getUiDevice().pressHome();
	        UiObject allAppsButton = new UiObject(new UiSelector()
	        .description("All apps"));
	        allAppsButton.clickAndWaitForNewWindow();
	       /* UiObject appsTab = new UiObject(new UiSelector()
	        .text("All apps"));
	        appsTab.click();*/]
	        UiScrollable appViews = new UiScrollable(new UiSelector()
	        .scrollable(true));
	        appViews.setAsHorizontalList();
	        UiObject settingsApp = appViews.getChildByText(new UiSelector()
	        .className("android.widget.TextView"), "AntiMoskal_v001");
	        settingsApp.clickAndWaitForNewWindow();
	        
	    }
		
		private void searchWithoutScan(String barcode) throws UiObjectNotFoundException{
			UiObject SearchButton = new UiObject(new UiSelector()
	        .className("android.widget.ImageButton").index(3));
			SearchButton.waitForExists (10);
			SearchButton.clickAndWaitForNewWindow();
			UiObject newBarcode = new UiObject(new UiSelector()
	        .className("android.widget.EditText").text("Enter product number"));
			newBarcode.setText(barcode);
			UiObject Search = new UiObject(new UiSelector()
	        .className("android.widget.Button").text("Search"));
			Search.clickAndWaitForNewWindow();
			sleep(600);
		}
		
		private void exitToMainWindow() throws UiObjectNotFoundException{
				getUiDevice().pressBack();
				getUiDevice().pressBack();
	            UiScrollable appViews = new UiScrollable(new UiSelector()
		        .scrollable(true));
	            UiObject settingsApp = appViews.getChildByText(new UiSelector()
		        .className("android.widget.TextView"), "AntiMoskal_v001");
		        settingsApp.clickAndWaitForNewWindow();
		        UiObject ScanButton = new UiObject(new UiSelector()
		        .className("android.widget.ImageButton").index(4));
				ScanButton.waitForExists (10);
				ScanButton.clickAndWaitForNewWindow();
				UiObject QR = new UiObject(new UiSelector()
		        .className("android.widget.Button").index(1));
				QR.clickAndWaitForNewWindow();
	            	        
	    }

	}
