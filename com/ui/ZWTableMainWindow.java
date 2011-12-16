package com.ui; 

import org.eclipse.swt.SWT;  
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.FillLayout;  
import org.eclipse.swt.layout.GridLayout;  
import org.eclipse.swt.widgets.Composite;  
import org.eclipse.swt.widgets.Shell;  
import org.eclipse.swt.widgets.ToolBar;

public class ZWTableMainWindow {
	public Shell sShell = null;
	private ViewForm viewForm = null;  
	private ToolBar toolBar = null;  
	private Composite composite = null;  

	//创建主窗口  
	public void createSShell() {  
		sShell = new Shell();  
		sShell.setText("表格窗口");  
		sShell.setLayout(new FillLayout());  
		createViewForm();   
		sShell.setSize(new org.eclipse.swt.graphics.Point(307,218));  
		sShell.pack();  
	}
	
	//创建ViewForm面板放置工具栏和表格  
	private void createViewForm() {  
		viewForm = new ViewForm(sShell, SWT.NONE);  
		viewForm.setTopCenterSeparate(true);  
		//createToolBar();
		viewForm.setTopLeft(toolBar);  
		createComposite();  
		viewForm.setContent(composite);  
	}  

	//创建放置表格的面板  
	private void createComposite() {
		GridLayout gridLayout = new GridLayout();  
		gridLayout.numColumns = 1;  
		composite = new Composite(viewForm, SWT.NONE);  
		composite.setLayout(gridLayout); 
		
		/* 主要用法：
		 * 1. 新建表头数组
		 * 2. new 出ZWTableSample
		 * 3. 调用createTable方法，传入composite参数
		 * 4. 调用插入行数方法insertRow，可以通过其他事件触发
		 */ 
		String[] tableHeader = {"姓名","性别","电话","电子邮件"};  
		ZWTableSample myTable = new ZWTableSample(tableHeader);
		myTable.createTable(composite, SWT.NULL);
		myTable.insertRow(3);
	} 
}  
