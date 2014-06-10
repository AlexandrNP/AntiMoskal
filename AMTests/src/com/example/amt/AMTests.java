package com.example.amt;

import android.util.Log;
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
			Log.i("AntiMoskalTest", "Start App");
		findAndRunApp();
		searchWithoutScan(test_barcode);
		Log.i("AntiMoskalTest", "Runing App");
		exitToMainWindow();
			//Log.i("AntiMoskalTest", "End App");
		}
		
		private void findAndRunApp() throws UiObjectNotFoundException {
	        // Go to main screen
	        getUiDevice().pressHome();
	        // Find menu button
	        UiObject allAppsButton = new UiObject(new UiSelector()
	        .description("All apps"));
	        // Click on menu button and wait new window
	        allAppsButton.clickAndWaitForNewWindow();
	        // Find App tab
	       /* UiObject appsTab = new UiObject(new UiSelector()
	        .text("All apps"));
	        // Click on app tab
	        appsTab.click();*/
	        // Find scroll object (menu scroll)
	        UiScrollable appViews = new UiScrollable(new UiSelector()
	        .scrollable(true));
	        // Set the swiping mode to horizontal (the default is vertical)
	        appViews.setAsHorizontalList();
	        // Find AntiMoskal application
	        UiObject settingsApp = appViews.getChildByText(new UiSelector()
	        .className("android.widget.TextView"), "AntiMoskal_v001");
	        // Open AntiMoskal application
	        settingsApp.clickAndWaitForNewWindow();
	        
	    }
		
		private void searchWithoutScan(String barcode) throws UiObjectNotFoundException{
			// Find and click New message button
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
