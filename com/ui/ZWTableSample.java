package com.ui; 

import org.eclipse.swt.SWT;  
import org.eclipse.swt.custom.ControlEditor;  
import org.eclipse.swt.custom.TableCursor;  
import org.eclipse.swt.custom.TableEditor;  
import org.eclipse.swt.events.FocusAdapter;  
import org.eclipse.swt.events.FocusEvent;  
import org.eclipse.swt.events.KeyAdapter;  
import org.eclipse.swt.events.KeyEvent;  
import org.eclipse.swt.events.ModifyEvent;  
import org.eclipse.swt.events.ModifyListener;  
import org.eclipse.swt.events.MouseEvent;  
import org.eclipse.swt.events.MouseListener;  
import org.eclipse.swt.events.SelectionAdapter;  
import org.eclipse.swt.events.SelectionEvent;  
import org.eclipse.swt.layout.GridData;  
import org.eclipse.swt.widgets.Composite;  
import org.eclipse.swt.widgets.Menu;  
import org.eclipse.swt.widgets.Table;  
import org.eclipse.swt.widgets.TableColumn;  
import org.eclipse.swt.widgets.TableItem;  
import org.eclipse.swt.widgets.Text;  

public class ZWTableSample {
	private Menu menu = null;
	private Table table = null;
	private String[] tableHeader = null;
//	private int rowCount = 0;
	private int colCount = 0;

	public ZWTableSample(String[] tableHeader) {
		//this.rowCount = rowCount;
		this.colCount = tableHeader.length;
		this.tableHeader = tableHeader;
	}

	//创建表格  
	public void createTable(Composite composite, int style) {  
		//表格布局  
		GridData gridData = new org.eclipse.swt.layout.GridData();  
		gridData.horizontalAlignment = SWT.FILL;  
		gridData.grabExcessHorizontalSpace = true;  
		gridData.grabExcessVerticalSpace = true;  
		gridData.verticalAlignment = SWT.FILL;  

		//创建表格，使用SWT.FULL_SELECTION样式，可同时选中一行  
		table = new Table(composite, SWT.MULTI|SWT.FULL_SELECTION|SWT.CHECK); 
		//table = new Table(composite, style); 
		table.setHeaderVisible(true);//设置显示表头  
		table.setLayoutData(gridData);//设置表格布局  
		table.setLinesVisible(true);//设置显示表格线
		
		//创建表头的字符串数组  
		for (int i=0;i<tableHeader.length;i++){  
			TableColumn tableColumn = new TableColumn(table, SWT.NONE);  
			tableColumn.setText( tableHeader[i]);  
			//设置表头可移动，默认为false  
			tableColumn.setMoveable(true);  
		}  
		
		
		// *****************************************************/  
		// /***************************************************  
		//创建TableCursor对象，使用上下左右键可以控制表格  
		final TableCursor cursor = new TableCursor(table, SWT.NONE);  
		//创建可编辑的控件  
		final ControlEditor editor = new ControlEditor(cursor);  
		editor.grabHorizontal = true;  
		editor.grabVertical = true;  
		//为TableCursor对象注册事件  
		cursor.addSelectionListener( new SelectionAdapter() {  
			//但移动光标，在单元格上单击回车所触发的事件  
			public void widgetDefaultSelected(SelectionEvent e) {  
				//创建一个文本框控件  
				final Text text = new Text(cursor, SWT.NONE);  
				//获得当前光标所在的行TableItem对象  
				TableItem row = cursor.getRow();  
				//获得当前光标所在的列数  
				int column = cursor.getColumn();  
				//当前光标所在单元格的值赋给文本框  
				text.setText(row.getText(column));  
				//为文本框注册键盘事件  
				text.addKeyListener(new KeyAdapter() {  
					public void keyPressed(KeyEvent e) {  
						//此时在文本框上单击回车后，这是表格中的数据为修改后文本框中的数据  
						//然后释放文本框资源  
						if (e.character == SWT.CR) {
							TableItem row = cursor.getRow();
							int column = cursor.getColumn();  
							row.setText(column, text.getText());  
							text.dispose();  
						}  
						//如果在文本框中单击了ESC键，则并不对表格中的数据进行修改  
						if (e.character == SWT.ESC) {  
							text.dispose();  
						}  
					}  
				});  
				//注册焦点事件  
				text.addFocusListener(new FocusAdapter() {  
					//当该文本框失去焦点时，释放文本框资源  
					public void focusLost(FocusEvent e) {  
						text.dispose();  
					}  
				});  
				//将该文本框绑定到可编辑的控件上  
				editor.setEditor(text);  
				//设置文本框的焦点  
				text.setFocus();  
			}  
			//移动光标到一个单元格上所触发的事件  
			public void widgetSelected(SelectionEvent e) {  
				table.setSelection(new TableItem[] { cursor.getRow()});  
			}  
		});  
		cursor.addMouseListener(new MouseListener() {  

			@Override 
			public void mouseDoubleClick(MouseEvent e) {  
				// TODO Auto-generated method stub  

			}  

			@Override 
			public void mouseDown(MouseEvent e) {  
				if (e.button==3) { // 右键按下，显示右键菜单  
					menu.setVisible(true);  
				}  
			}  

			@Override 
			public void mouseUp(MouseEvent e) {  
				// TODO Auto-generated method stub  

			}  

		});  
		// ******************************************************/  
		//重新布局表格  
		for (int i=0; i<tableHeader.length; i++) {  
			table.getColumn (i).pack ();  
		}
		// ******************************************************/  
	}
	
	public void insertRow(int count) {
		//添加数据
		for (int i = 0; i < count; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			String[] strings = new String[colCount];
			item.setText(strings);
		}

		//添加可编辑的单元格  
		// /******************************************************  
		TableItem [] items = table.getItems ();  
		for (int i=0; i<items.length; i++) {
			for (int j = 0; j < colCount; j++) {
				//第一列设置，创建TableEditor对象  
				final TableEditor editor = new TableEditor (table);  
				//创建一个文本框，用于输入文字  
				final Text text = new Text (table, SWT.NONE);  
				//将文本框当前值，设置为表格中的值  
				text.setText(items[i].getText(0));  
				//设置编辑单元格水平填充  
				editor.grabHorizontal = true;  
				//关键方法，将编辑单元格与文本框绑定到表格的第j列  
				editor.setEditor(text, items[i], j);  
				//当文本框改变值时，注册文本框改变事件，该事件改变表格中的数据。  
				//否则即使改变的文本框的值，对表格中的数据也不会影响  
				text.addModifyListener( new ModifyListener(){  
					public void modifyText(ModifyEvent e) {  
						editor.getItem().setText(1,text.getText());  
					}
				});
			}
		}
	}
}  
