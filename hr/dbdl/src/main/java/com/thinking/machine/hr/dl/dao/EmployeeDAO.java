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
import java.sql.*;	
public class EmployeeDAO implements EmployeeDAOInterface
{
private static final String FILE_NAME="employee.data";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("employee is null");
String name;
String panNumber;
String aadharCardNumber;
int designationCode;
String employeeId;
boolean isIndian;
BigDecimal basicSalary;
java.util.Date dateOfBirth;
char gender;
name=employeeDTO.getName();
if(name==null) throw new DAOException("name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("length of name is zero");
designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("invalid designation code");
boolean designationCodeValid=false;
DesignationDAOInterface designationDAO=new DesignationDAO();
designationCodeValid=designationDAO.codeExists(designationCode);
if(designationCodeValid==false) throw new DAOException("designation code does not exists");
dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("date of birth is null");
gender=employeeDTO.getGender();
if(gender==' ')
{
throw new DAOException("gender not set Male/Female");
}
isIndian=employeeDTO.getIsIndian();
basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("basic salary is null");
if(basicSalary.signum()==-1) throw new DAOException("basic salary is negative");
panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("PAN number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("PAN Number is of zero length");
aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Aadhar card number is of zero length");
Connection connection;
try
{
boolean panNumberExists=false;
boolean aadharCardNumberExists=false;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
ResultSet resultSet;
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
if(aadharCardNumberExists&&panNumberExists)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("PAN number and Aadhar Card number already exists");
}
if(panNumberExists)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("PAN number already exists");
}
if(aadharCardNumberExists)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Aadhar card number already exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,basic_salary,gender,is_indian,pan_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int generatedEmployeeId=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
employeeDTO.setEmployeeId("A"+1000000+generatedEmployeeId);
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("employee is null");
String name;
String employeeId;
String panNumber;
String aadharCardNumber;
int designationCode;
boolean isIndian;
BigDecimal basicSalary;
java.util.Date dateOfBirth;
char gender;
employeeId=employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOException("EmployeeId is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("EmployeeId length is zero");
name=employeeDTO.getName();
if(name==null) throw new DAOException("name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("length of name is zero");
designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("invalid designation code");
boolean designationCodeValid=false;
DesignationDAOInterface designationDAO=new DesignationDAO();
designationCodeValid=designationDAO.codeExists(designationCode);
if(designationCodeValid==false) throw new DAOException("designation code does not exists");
dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("date of birth is null");
gender=employeeDTO.getGender();
if(gender==' ')
{
throw new DAOException("gender not set Male/Female");
}
isIndian=employeeDTO.getIsIndian();
basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("basic salary is null");
if(basicSalary.signum()==-1) throw new DAOException("basic salary is negative");
panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("PAN number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("PAN Number is of zero length");
aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Aadhar card number is of zero length");
Connection connection;
try
{
boolean employeeIdExists=false;
boolean panNumberExists=false;
boolean aadharCardNumberExists=false;
int actualEmployeeId;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-10000000;
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Employee Id does not exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
if(aadharCardNumberExists&&panNumberExists)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("PAN number and Aadhar Card number already exists");
}
if(panNumberExists)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("PAN number already exists");
}
if(aadharCardNumberExists)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Aadhar card number already exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,basic_salary=?,gender=?,is_indian=?,pan_number=?,aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void delete(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("Employee id is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("EmployeeId is of zero length");
Connection connection;
int actualEmployeeId;
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-10000000;
try
{
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Employee Id does not exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface>employees=new TreeSet<>();
String employeeId;
String name;
int designationCode;
java.util.Date utilDateOfBirth=null;
java.sql.Date sqlDateOfBirth;
BigDecimal basicSalary;
char gender;
boolean isIndian;
String panNumber;
String aadharCardNumber;
Connection connection;
int actualEmployeeId;
EmployeeDTOInterface employeeDTO;
try
{
ResultSet resultSet;
PreparedStatement preparedStatement;
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select * from employee");
resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
actualEmployeeId=resultSet.getInt("employee_id");
employeeId="A"+10000000+actualEmployeeId;
name=resultSet.getString("name").trim();
designationCode=resultSet.getInt("designation_code");
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
gender=resultSet.getString("gender");
if(gender.equals("M"))
{
employeeDTO.setGender(GENDER.MALE);
}
else
{
employeeDTO.setGender(GENDER.FEMALE);
}
basicSalary=resultSet.getBigDecimal("basic_salary");
panNumber=resultSet.getString("pan_number").trim();
aadharCardNumber=resultSet.getString("aadhar_card_number").trim();
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setDateOfBirth(utilDateOfBirth);
employeeDTO.setBasicSalary(basicSalary);
employees.add(employeeDTO);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employees;
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
throw new DAOException("not yet implemented");
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
java.util.Date fDateOfBirth=null;
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
java.util.Date fDateOfBirth=null;
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