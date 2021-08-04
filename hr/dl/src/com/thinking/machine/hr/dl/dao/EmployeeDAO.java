package com.thinking.machine.hr.dl.dao;
import java.io.*;
import java.text.*;
import java.util.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.dto.*;
import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.enums.*;
import java.math.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private static final String FILE_NAME="employee.data";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId;
String name;
int designationCode;
String aadharCardNumber;
String panNumber;
boolean isIndian;
char gender;
Date dateOfBirth;
BigDecimal basicSalary;
name=employeeDTO.getName();
if(name==null) throw new DAOException("name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of name is zero");
designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code");
boolean isDesignationCodeValid=false;
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
isDesignationCodeValid=designationDAO.codeExists(designationCode);
if(isDesignationCodeValid==false) throw new DAOException("Invalid designation code "+designationCode);
dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("Date of birth is null");
gender=employeeDTO.getGender();
isIndian=employeeDTO.getIsIndian();
basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("Basic salary is null");
if(basicSalary.signum()==-1) throw new DAOException("Salary is negative");
panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("PAN Number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length of PAN Number is zero");
aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Length of Aadhar card number is zero");
try
{
int lastGeneratedEmployeeId=10000000;
String lastGeneratedEmployeeIdString="";
int recordCount=0;
String recordCountString="";
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
lastGeneratedEmployeeIdString=String.format("%-10s","10000000");
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
recordCountString=String.format("%-10s","0");
randomAccessFile.writeBytes(recordCountString+"\n");
}
else
{
lastGeneratedEmployeeId=Integer.parseInt(randomAccessFile.readLine().trim());
recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
}
boolean panNumberExists=false;
boolean aadharCardNumberExists=false;
String fAadharCardNumber;
String fPANNumber;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(x=1;x<=7;x++) randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(panNumberExists==false&&fPANNumber.equalsIgnoreCase(panNumber))
{
panNumberExists=true;
}
if(aadharCardNumberExists==false&&fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
aadharCardNumberExists=true;
}
if(aadharCardNumberExists&&panNumberExists) break;
}
if(panNumberExists&&aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN number ("+panNumber+") and Aadhar card number ("+aadharCardNumber+") exists");
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN number ("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("Aadhar card number exists");
}
lastGeneratedEmployeeId++;
employeeId="A"+lastGeneratedEmployeeId;
recordCount++;
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
SimpleDateFormat sdf;
sdf=new SimpleDateFormat("dd/MM/yyyy");
randomAccessFile.writeBytes(sdf.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
randomAccessFile.seek(0);
lastGeneratedEmployeeIdString=String.format("%-10d",lastGeneratedEmployeeId);
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
employeeDTO.setEmployeeId(employeeId);
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
String employeeId;
String name;
String panNumber;
String aadharCardNumber;
BigDecimal basicSalary;
Date dateOfBirth;
int designationCode;
boolean isIndian;
char gender;
if(employeeDTO==null) throw new DAOException("Employee is null");
employeeId=employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOException("Invalid employee id");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Invalid Employee id : employee id is of zero length");
name=employeeDTO.getName();
if(name==null) throw new DAOException("Name of employee is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Invalid name of employee : length of name is zero");
designationCode=employeeDTO.getDesignationCode();
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
if(designationDAO.codeExists(designationCode)==false) throw new DAOException("Invalid designation code");
dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null)throw new DAOException("Invalid date of birth");
gender=employeeDTO.getGender();
isIndian=employeeDTO.getIsIndian();
basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("Invalid Basic Salary");
if(basicSalary.signum()==-1) throw new DAOException("Invalid basic salary : salary is negative");
aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Invalid Aadhar Card Number : aadhar card number is negative");
panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("Invalid PAN number : PAN number is null"); 
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid Employee id : "+employeeId);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) throw new DAOException("Invalid Employee id : "+employeeId);
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
String fPanNumber;
String fAadharCardNumber;
long foundAt=0;
boolean employeeIdFound=false;
boolean panNumberFound=false;
boolean aadharCardNumberFound=false;
System.out.println("hello!!");
int x;
String panNumberFoundAtEmployeeId=null;
String aadharCardNumberFoundAtEmployeeId=null;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
if(employeeIdFound==false)foundAt=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(x=1;x<=6;x++) randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
if(employeeIdFound==false&&fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound=true;
}
if(panNumberFound==false&&fPanNumber.equalsIgnoreCase(panNumber))
{
panNumberFound=true;
panNumberFoundAtEmployeeId=fEmployeeId;
}
if(aadharCardNumberFound==false&&fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
aadharCardNumberFound=true;
aadharCardNumberFoundAtEmployeeId=fEmployeeId;
}
if(panNumberFound&&aadharCardNumberFound&&employeeIdFound) break;
}
if(employeeIdFound==false)
{
randomAccessFile.close();
throw new DAOException("Invalid employee id");
}
boolean aadharCardNumberExists=false;
boolean panNumberExists=false;
if(panNumberFound&&employeeId.equalsIgnoreCase(panNumberFoundAtEmployeeId)==false)
{
panNumberExists=true;
}
if(aadharCardNumberFound&&aadharCardNumber.equalsIgnoreCase(aadharCardNumberFoundAtEmployeeId)==false)
{
aadharCardNumberExists=true;
}
if(panNumberExists&&aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN number ("+panNumber+") and Aadhar card number ("+aadharCardNumber+") exists");
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN number ("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("Aadhar card number ("+aadharCardNumber+") exists");
}
randomAccessFile.seek(foundAt);
for(x=1;x<=9;x++) randomAccessFile.readLine();
File tmpFile;
tmpFile=new File("tp.data");
if(tmpFile.exists()==false) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
randomAccessFile.seek(foundAt);
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
randomAccessFile.writeBytes(sdf.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
randomAccessFile.close();
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public void delete(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("Employee id is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("EmployeeId is of zero length");
try
{
File file;
file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid employee id");
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid Employee id");
}
int x;
long foundAt=0;
randomAccessFile.readLine();
randomAccessFile.readLine();
boolean employeeIdExists=false;
String fEmployeeId;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
foundAt=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
if(employeeId.equalsIgnoreCase(fEmployeeId)) 
{
employeeIdExists=true;
break;
}
for(x=1;x<=8;x++) randomAccessFile.readLine();
}
if(employeeIdExists==false)
{
randomAccessFile.close();
throw new DAOException("Invalid employee id");
}
randomAccessFile.seek(foundAt);
for(x=1;x<=9;x++)randomAccessFile.readLine();
File tmpFile;
tmpFile=new File("tp.data");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
tmpRandomAccessFile.seek(0);
randomAccessFile.seek(foundAt);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
randomAccessFile.close();
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface>employees=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return employees;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
return employees;
}
String employeeId;
String name;
int designationCode;
Date dateOfBirth=null;
BigDecimal basicSalary;
char gender;
boolean isIndian;
String panNumber;
String aadharCardNumber;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employeeId=randomAccessFile.readLine();
name=randomAccessFile.readLine();
designationCode=Integer.parseInt(randomAccessFile.readLine());
SimpleDateFormat sdf;
try
{
sdf=new SimpleDateFormat("dd/MM/yyyy");
dateOfBirth=sdf.parse(randomAccessFile.readLine());
}catch(ParseException pe)
{
}
gender=randomAccessFile.readLine().charAt(0);
isIndian=Boolean.parseBoolean(randomAccessFile.readLine());
basicSalary=new BigDecimal(randomAccessFile.readLine());
panNumber=randomAccessFile.readLine();
aadharCardNumber=randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
if(gender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
else
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employees.add(employeeDTO);
}
randomAccessFile.close();
return employees;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
Set<EmployeeDTOInterface>employees=new TreeSet<>();
try
{
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fAadharCardNumber;
String fPanNumber;
File file;
file=new File(FILE_NAME);
if(file.exists()==false) return employees;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return employees;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try
{
SimpleDateFormat sdf;
sdf=new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth=sdf.parse(randomAccessFile.readLine());
}catch(ParseException pe)
{
}
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean((randomAccessFile.readLine()));
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fAadharCardNumber=randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
if(fDesignationCode==designationCode)
{
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
if(fGender=='F')
{
employeeDTO.setGender(GENDER.FEMALE);
}
else
{
employeeDTO.setGender(GENDER.MALE);
}
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
employeeDTO.setPANNumber(fPanNumber);
employees.add(employeeDTO);
}
}
randomAccessFile.close();
return employees;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public boolean isDesignationAlloted(int designationCode)throws DAOException
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
if(designationDAO.codeExists(designationCode)==false) return false;
try
{
File file;
file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) return false;
randomAccessFile.readLine();
randomAccessFile.readLine();
int fDesignationCode,x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
if(Integer.parseInt(randomAccessFile.readLine())==designationCode)
{
randomAccessFile.close();
return true;
}
for(x=1;x<=6;x++) randomAccessFile.readLine();
}
randomAccessFile.readLine();
return false;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber==null) return false;
panNumber=panNumber.trim();
if(panNumber.length()==0) return false;
try
{
String fPanNumber;
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(x=1;x<=8;x++) randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
if(panNumber.equalsIgnoreCase(fPanNumber))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) return false;
try
{
int x;
String fAadharCardNumber;
File file;
file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(x=1;x<=7;x++) randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)) 
{
randomAccessFile.close();
return true;
}
randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public boolean employeeIdExists(String employeeId) throws DAOException
{ 
if(employeeId==null) return false;
employeeId=employeeId.trim();
if(employeeId.length()==0) return false;
try
{
int x;
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
randomAccessFile.close();
return true;
}
for(x=1;x<=8;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public int getCount() throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
int recordCount;
randomAccessFile.readLine();
recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
return recordCount;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public int getCountByDesignation(int designationCode) throws DAOException
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
if(designationDAO.codeExists(designationCode)==false) 
{
throw new DAOException("Invalid Designation code : "+designationCode);
}
try
{
File file;
file=new File(FILE_NAME);
if(file.exists()==false)
{
throw new DAOException("Invalid Designation code");
}
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid Designation code : "+designationCode);
}
int x;
int count=0;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
if(Integer.parseInt(randomAccessFile.readLine())==designationCode)
{
count++;
}
for(x=1;x<=6;x++)randomAccessFile.readLine();
}
randomAccessFile.close();
return count;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public EmployeeDTOInterface getByEmployeeId(String employeeId)throws DAOException
{
try
{
if(employeeId==null) throw new DAOException("Invalid employee Id :"+employeeId);
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Invalid employeeId Id :employee id is of zero length");
File file=new File(FILE_NAME); 
if(file.exists()==false) throw new DAOException("Invalid employee id :"+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid employee id : "+employeeId);
}
char gender;
String fEmployeeId;
int x;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
employeeDTO.setDateOfBirth(sdf.parse(randomAccessFile.readLine()));
}catch(ParseException pe)
{
}
gender=randomAccessFile.readLine().charAt(0);
if(gender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
else
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employeeDTO.setPANNumber(randomAccessFile.readLine());
return employeeDTO;
}
else
{
for(x=1;x<=8;x++) randomAccessFile.readLine();
}
}
throw new DAOException("Invalid employee id: "+employeeId);	
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public EmployeeDTOInterface getByPANNumber(String panNumber)throws DAOException
{
if(panNumber==null) throw new DAOException("Invalid PAN number :"+panNumber);
if(panNumber.length()==0) throw new DAOException("Invalid PAN number : PAN number is of zero length");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid PAN number : "+panNumber);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid PAN number : "+panNumber);
}
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
boolean fIsIndian;
BigDecimal fBasicSalary;
char fGender;
String fPanNumber;
String fAadharCardNumber;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try
{
SimpleDateFormat sdf;
sdf=new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth=sdf.parse(randomAccessFile.readLine());
}catch(ParseException pe)
{
}
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fAadharCardNumber=randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
if(fPanNumber.equalsIgnoreCase(panNumber))
{
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
if(fGender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
else
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
employeeDTO.setPANNumber(fPanNumber);
return employeeDTO;
}
}
throw new DAOException("Invalid PAN number : "+panNumber);
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber)throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("Invalid Aadhar Card  number :"+aadharCardNumber);
if(aadharCardNumber.length()==0) throw new DAOException("Invalid PAN number : Aadhar card number is of zero length");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid Aadhar card number : "+aadharCardNumber);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid Aadhar card number : "+aadharCardNumber);
}
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
boolean fIsIndian;
BigDecimal fBasicSalary;
char fGender;
String fPanNumber;
String fAadharCardNumber;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try
{
SimpleDateFormat sdf;
sdf=new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth=sdf.parse(randomAccessFile.readLine());
}catch(ParseException pe)
{
}
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fAadharCardNumber=randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
if(fGender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
else
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
employeeDTO.setPANNumber(fPanNumber);
return employeeDTO;
}
}
throw new DAOException("Invalid Aadhar card number : "+aadharCardNumber);
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}
}