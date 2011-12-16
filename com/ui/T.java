package com.ui;

import org.eclipse.swt.widgets.Display;

public class T {

	/**
	 * @param args
	 */
	public static void main(String[] args) {  
		//调用主窗口  
		Display display = Display.getDefault();
		ZWTableMainWindow mainWindow = new ZWTableMainWindow();
		mainWindow.createSShell();
		mainWindow.sShell.open();
		while (!mainWindow.sShell.isDisposed()) {  
			if (!display.readAndDispatch())  
				display.sleep();  
		}  
		//     ImageFactory.dispose();  
		display.dispose();  
	}  

}
