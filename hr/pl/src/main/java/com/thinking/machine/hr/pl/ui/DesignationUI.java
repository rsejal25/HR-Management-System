package com.thinking.machine.hr.pl.ui;
import com.thinking.machine.hr.pl.model.*;
import com.thinking.machine.hr.bl.exceptions.*;
import com.thinking.machine.hr.bl.pojo.*;
import com.thinking.machine.hr.bl.managers.*;
import com.thinking.machine.hr.bl.interfaces.pojo.*;
import com.thinking.machine.hr.bl.interfaces.managers.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.table.*;
import java.io.*;
public class DesignationUI extends JFrame implements DocumentListener,ListSelectionListener
{
private DesignationModel designationModel;
private JTable table;
private ImageIcon addIcon;
private ImageIcon editIcon;
private ImageIcon saveIcon;
private ImageIcon cancelIcon;
private ImageIcon pdfIcon;
private ImageIcon deleteIcon;
private JScrollPane jsp;
private Container container;
private JLabel searchLabel;
private JLabel titleLabel;
private JLabel searchErrorLabel;
private JTextField searchTextField;
private DesignationPanel designationPanel;
private JButton clearSearchTextFieldButton ;
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;
public DesignationUI()
{
initComponents();
setAppearence();
addListeners();
setViewMode();
designationPanel.setViewMode();
}
public void setViewMode()
{
this.mode=MODE.VIEW;
if(designationModel.getRowCount()==0)
{
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
table.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
clearSearchTextFieldButton.setEnabled(true);
table.setEnabled(true); 
}
}
public void setAddMode()
{
this.mode=MODE.ADD;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
table.setEnabled(true);
}
public void setDeleteMode()
{
this.mode=MODE.DELETE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
table.setEnabled(false);
}
public void setEditMode()
{
this.mode=MODE.EDIT;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
table.setEnabled(false);
}
public void setExportToPDFMode()
{
this.mode=MODE.EXPORT_TO_PDF;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
table.setEnabled(false);
}
public void initComponents()
{
designationModel=new DesignationModel();
addIcon=new ImageIcon(this.getClass().getResource("/icons/addIcon.jpg"));
deleteIcon=new ImageIcon(this.getClass().getResource("/icons/deleteIcon.jpg"));
saveIcon=new ImageIcon(this.getClass().getResource("/icons/saveIcon.jpg"));
cancelIcon=new ImageIcon(this.getClass().getResource("/icons/cancel.jpg"));
pdfIcon=new ImageIcon(this.getClass().getResource("/icons/PDFIcon.jpg"));
editIcon=new ImageIcon(this.getClass().getResource("/icons/editIcon.jpg"));
searchLabel=new JLabel("Search");
titleLabel=new JLabel("Designation");
searchErrorLabel=new JLabel("");
searchTextField=new JTextField();
designationPanel=new DesignationPanel();
clearSearchTextFieldButton=new JButton("X");
table=new JTable(designationModel);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(null);
}
public void setAppearence()
{
int lm,tm;
lm=0;
tm=0;
Font titleFont=new Font("Verdana",Font.BOLD,15);
Font searchFont=new Font("Verdana",Font.BOLD,60);
Font columnHeaderFont=new Font("Verdana",Font.BOLD,15);
titleLabel.setBounds(lm+10,tm+10,500,80);
searchLabel.setBounds(lm+10,tm+10+60+10,100,30);
searchTextField.setBounds(lm+10+30+40,tm+10+60+15,400,20);
searchErrorLabel.setBounds(lm+10+30+40+400+30,tm+10+60,80,15);
searchErrorLabel.setForeground(Color.red);
table.setRowHeight(30);
table.getColumnModel().getColumn(0).setPreferredWidth(55);
table.getColumnModel().getColumn(1).setPreferredWidth(100);
JTableHeader header=table.getTableHeader();
header.setFont(columnHeaderFont);
header.setReorderingAllowed(false);
header.setResizingAllowed(false);
table.setRowSelectionAllowed(true);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
clearSearchTextFieldButton.setBounds(lm+10+30+40+400+30,tm+10+60+15,30,20);
jsp.setBounds(lm+10,tm+10+60+15+30,560,200);
designationPanel.setBounds(lm+10,tm+10+60+15+30+200+20,560,200);
container.add(titleLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(searchErrorLabel);
container.add(clearSearchTextFieldButton);
container.add(jsp);
container.add(designationPanel);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int width=600;
int height=600;
setSize(width,height);
int x=(d.width/2)-(width/2);
int y=(d.height/2)-(height/2);
setLocation(x,y);
}
public void searchDesignation()
{
searchErrorLabel.setText("");
String title=searchTextField.getText().trim();
if(title.length()==0) return;
int rowIndex;
try
{
rowIndex=designationModel.indexOfTitle(title,true);
}catch(BLException blexception)
{
searchErrorLabel.setText("Not Found");
return;
}
table.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rect=table.getCellRect(rowIndex,0,true);
table.scrollRectToVisible(rect);	
}
public void addListeners()
{
searchTextField.getDocument().addDocumentListener(this);
clearSearchTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
table.getSelectionModel().addListSelectionListener(this);
}
public void valueChanged(ListSelectionEvent ev)
{
int rowIndex;
rowIndex=table.getSelectedRow();
try
{
DesignationInterface designation=designationModel.getDesignationAt(rowIndex);
designationPanel.setDesignation(designation);
}catch(BLException blexception)
{
designationPanel.clearDesignation();
return;
}
}
public void changedUpdate(DocumentEvent de)
{
searchDesignation();
}
public void removeUpdate(DocumentEvent de) 
{
searchDesignation();
}
public void insertUpdate(DocumentEvent de)
{
searchDesignation();
}
class DesignationPanel extends JPanel
{
private JLabel titleCaptionLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton clearTitleTextButton;
private JButton addButton;
private JButton editButton;
private JButton deleteButton;
private JButton exportToPDFButton;
private JButton cancelButton;
private JPanel buttonPanel;
private DesignationInterface designation;
DesignationPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
initComponents();
setAppereance();
addListeners();
}
private void initComponents()
{
designation=null;
titleCaptionLabel=new JLabel("Designation");
titleLabel=new JLabel("");
titleTextField=new JTextField();
clearTitleTextButton=new JButton();
buttonPanel=new JPanel();
addButton=new JButton(addIcon);
editButton=new JButton(editIcon);
cancelButton=new JButton(cancelIcon);
deleteButton=new JButton(deleteIcon);
exportToPDFButton=new JButton(pdfIcon);
}
private void setAppereance()
{
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
titleCaptionLabel.setFont(captionFont);
titleLabel.setFont(dataFont);
titleTextField.setFont(dataFont);
setLayout(null);
int lm,tm;
lm=0;
tm=0;
titleCaptionLabel.setBounds(lm+10,tm+20,110,30);
titleLabel.setBounds(lm+110+15,tm+20,400,30);
titleTextField.setBounds(lm+110+15,tm+20,400,30);
clearTitleTextButton.setBounds(lm+10+110+350+10,tm+20,30,30);
add(titleCaptionLabel);
add(titleLabel);
add(titleTextField);
buttonPanel.setBounds(50,tm+20+30+30,465,75);
buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
addButton.setBounds(70,12,50,50);
editButton.setBounds(70+50+20,12,50,50);
deleteButton.setBounds(70+50+20+50+20,12,50,50);
cancelButton.setBounds(70+50+20+50+20+50+20,12,50,50);
exportToPDFButton.setBounds(70+50+20+50+20+50+20+50+20,12,50,50);
buttonPanel.setLayout(null);
buttonPanel.add(addButton);
buttonPanel.add(editButton);
buttonPanel.add(deleteButton);
buttonPanel.add(cancelButton);
buttonPanel.add(exportToPDFButton);
add(clearTitleTextButton);
add(buttonPanel);
}
public void setDesignation(DesignationInterface designation)
{
this.designation=designation;
titleLabel.setText(designation.getTitle());
}
public void clearDesignation()
{
this.designation=null;
titleLabel.setText("");
}
public boolean addDesignation()
{
String title=titleTextField.getText();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation required");
titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setTitle(title);
int index=0;
try
{
designationModel.add(d);
}catch(BLException blexception)
{
if(blexception.hasGenericException())
{
JOptionPane.showMessageDialog(this,blexception.getGenericException());
}
else
{
if(blexception.hasException("title"))
{
JOptionPane.showMessageDialog(this,blexception.getException("title"));
}
titleTextField.requestFocus();
}
return false;
}
try
{
index=designationModel.getIndexOfDesignation(d);
}catch(BLException blexception)
{
}
table.setRowSelectionInterval(index,index);
Rectangle rect=table.getCellRect(index,0,true);
table.scrollRectToVisible(rect);
return true;
}
public boolean updateDesignation()
{
String title=titleTextField.getText();
title=title.trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation required");
titleTextField.requestFocus();
return false;
}
DesignationInterface d=null;
try
{
d=new Designation();
d.setTitle(title);
d.setCode(this.designation.getCode());
designationModel.update(d);
}catch(BLException blexception)
{
if(blexception.hasGenericException())
{
JOptionPane.showMessageDialog(this,blexception.getGenericException());
}
else
{
if(blexception.hasException("title"))
{
JOptionPane.showMessageDialog(this,blexception.getException("title"));
}
}
titleTextField.requestFocus();
return false;
}
int rowIndex=0;
try
{
rowIndex=designationModel.getIndexOfDesignation(d);
}catch(BLException blexception)
{
}
table.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rect=table.getCellRect(rowIndex,0,true);
table.scrollRectToVisible(rect);
return false;
}
private void deleteDesignation()
{
String title=this.designation.getTitle();
int selectedOption=JOptionPane.showConfirmDialog(this,"Delete "+title+" ?","Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION) return;
int code;
try
{
code=this.designation.getCode();
designationModel.delete(code);
JOptionPane.showMessageDialog(this,title+" deleted");
this.clearDesignation();
}catch(BLException blexception)
{
if(blexception.hasGenericException())
{
JOptionPane.showMessageDialog(this,blexception.getGenericException());
}
else
{
if(blexception.hasException("title"))
{
JOptionPane.showMessageDialog(this,blexception.getException("title"));
}
}
}
}
private void addListeners()
{
this.addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)
{
setAddMode();
}
else
{
if(addDesignation())
{
setViewMode();
}
}
}
});
this.editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)
{
setEditMode();
}
else
{
if(updateDesignation())
{
setViewMode();
}
}
}
});
this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setViewMode();
}
});
this.deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setDeleteMode();
}
});
this.exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
JFileChooser jfc=new JFileChooser();
jfc.setCurrentDirectory(new File("."));
int selectedOption=jfc.showSaveDialog(DesignationUI.this);
if(selectedOption==JFileChooser.APPROVE_OPTION)
{
try
{
File selectedFile=jfc.getSelectedFile();
String pdfFile=selectedFile.getAbsolutePath();
if(pdfFile.endsWith(".")) pdfFile+="pdf";
else if(pdfFile.endsWith(".pdf")==false) pdfFile+=".pdf";
File file=new File(pdfFile);
File parent=new File(file.getParent());
if(parent.exists()==false||parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(DesignationUI.this,"Incorrect path :"+file.getAbsolutePath());
return;
}
designationModel.exportToPDF(file);
}catch(BLException blexception)
{
if(blexception.hasGenericException())
{
JOptionPane.showMessageDialog(DesignationUI.this,blexception.getGenericException());
return;
}
}
catch(Exception e)
{
System.out.println(e);
}
}
}
});
}
void setViewMode()
{
DesignationUI.this.setViewMode();
this.addButton.setText("A"); 
this.editButton.setText("U");
this.titleTextField.setVisible(false);
this.addButton.setEnabled(true);
this.titleLabel.setVisible(true);
this.cancelButton.setEnabled(false);
if(designationModel.getRowCount()>0)
{
this.editButton.setEnabled(true);
this.deleteButton.setEnabled(true);
this.exportToPDFButton.setEnabled(true);
this.clearTitleTextButton.setVisible(false);
}
else
{
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
}
void setAddMode()
{
DesignationUI.this.setAddMode();
this.titleTextField.setText("");
this.titleTextField.setVisible(true);
this.titleLabel.setVisible(false);
addButton.setIcon(saveIcon);
deleteButton.setEnabled(false);
editButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
cancelButton.setEnabled(true);
this.clearTitleTextButton.setVisible(true);
}
void setEditMode()
{
if(table.getSelectedRow()<0||table.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to edit");
return;
}
DesignationUI.this.setEditMode();
this.titleTextField.setText(designation.getTitle());
this.titleTextField.setVisible(true);
this.titleLabel.setVisible(false);
deleteButton.setEnabled(false);
cancelButton.setEnabled(false);
addButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
this.clearTitleTextButton.setVisible(true);
editButton.setText("U");
}
void setDeleteMode()
{
if(table.getSelectedRow()<0||table.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to delete");
return;
}
DesignationUI.this.setDeleteMode();
addButton.setEnabled(false);
cancelButton.setEnabled(true);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
deleteDesignation();
DesignationUI.this.setViewMode();
setViewMode();
}
void setExportToPDFMode()
{
DesignationUI.this.setExportToPDFMode();
addButton.setEnabled(false);
cancelButton.setEnabled(true);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}	
}
}


