package com.thinking.machine.hr.bl.managers;
import com.thinking.machine.hr.bl.interfaces.pojo.*;
import com.thinking.machine.hr.bl.pojo.*;
import com.thinking.machine.hr.bl.interfaces.managers.*;
import com.thinking.machine.hr.bl.exceptions.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.dto.*;
import com.thinking.machine.hr.dl.dao.*;
import java.util.*;
import java.math.*;
import java.text.*;
import com.thinking.machine.enums.*;
public class EmployeeManager implements EmployeeManagerInterface,Cloneable
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeesMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeesMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeesMap;
private Set<EmployeeInterface> employeesSet;
private static EmployeeManager employeeManager=null;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeesMap;
private void populateDataStructures() throws BLException
{
this.employeeIdWiseEmployeesMap=new HashMap<>();
this.panNumberWiseEmployeesMap=new HashMap<>();
this.aadharCardNumberWiseEmployeesMap=new HashMap<>();
this.employeesSet=new TreeSet<>();
this.designationCodeWiseEmployeesMap=new HashMap<>();
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
Set<EmployeeDTOInterface> employees;
employees=employeeDAO.getAll();
EmployeeInterface employee;
DesignationInterface designation;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
Set<EmployeeInterface> ets;
for(EmployeeDTOInterface dlEmployee:employees)
{
employee=new Employee();
employee.setName(dlEmployee.getName());
employee.setEmployeeId(dlEmployee.getEmployeeId());
designation=designationManager.getDesignationByCode(dlEmployee.getDesignationCode());
employee.setDesignation(designation);
employee.setPANNumber(dlEmployee.getPANNumber());
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
employee.setIsIndian(dlEmployee.getIsIndian());
if(dlEmployee.getGender()=='M')
{
employee.setGender(GENDER.MALE);
}
else
{
employee.setGender(GENDER.FEMALE);
}
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.setDateOfBirth((Date)((dlEmployee.getDateOfBirth()).clone()));
this.employeeIdWiseEmployeesMap.put(dlEmployee.getEmployeeId().toUpperCase(),employee);
this.panNumberWiseEmployeesMap.put(dlEmployee.getPANNumber().toUpperCase(),employee);
this.aadharCardNumberWiseEmployeesMap.put(dlEmployee.getAadharCardNumber().toUpperCase(),employee);
this.employeesSet.add(employee);
ets=designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(employee);
designationCodeWiseEmployeesMap.put(designation.getCode(),ets);
}
else
{
ets.add(employee);
}
}
}catch(DAOException daoexception)
{
BLException blexception=new BLException();
blexception.setGenericException(daoexception.getMessage());
throw blexception;
}
}
public static EmployeeManager getEmployeeManager() throws BLException
{
if(employeeManager==null) employeeManager=new EmployeeManager();
return employeeManager;
}
private EmployeeManager() throws BLException
{
populateDataStructures();
}
public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blexception;
blexception=new BLException();
String employeeId=employee.getEmployeeId();
String name=employee.getName();
DesignationInterface designation=employee.getDesignation();
Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
boolean isIndian=employee.getIsIndian();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
BigDecimal basicSalary=employee.getBasicSalary();
if(employeeId!=null)
{
employeeId=employeeId.trim();
if(employeeId.length()>0)
{
blexception.addException("employeeId","Employee Id "+employeeId+"should ne null");
}
}
if(name==null)
{
blexception.addException("name","Name should not be null");
}
else
{
name=name.trim();
if(name.length()==0)
{
blexception.addException("name","length of name should not be zero");
}
}
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(designation==null)
{
blexception.addException("designation","designation should not be null");
}
else
{
System.out.println(designation.getCode());
if(designationManager.designationCodeExists(designation.getCode())==false)
{
blexception.addException("code","designation Code"+designation.getCode()+" does not exists");
}
}
if(dateOfBirth==null)
{
blexception.addException("dateOfBirth","Date Of Birth is null");
}
if(gender==' ')
{
blexception.addException("gender","gender required");
}
if(basicSalary==null)
{
blexception.addException("basicSalary","Basic Salary is null");
}
else
{
if(basicSalary.signum()==-1)
{
blexception.addException("basicSalary","Basic Salary cannot be negative");
}
}
if(panNumber==null)
{
blexception.addException("panNumber","PAN Number is null");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blexception.addException("panNumber","PAN Number length is zero");
}
}
if(aadharCardNumber==null)
{
blexception.addException("aadharCardNumber","Aadhar Card Number is null");
}
else
{
if(aadharCardNumber.length()==0)
{
blexception.addException("aadharCardNumber","Aadhar Card Number length is zero");
}
}
if(aadharCardNumber!=null&&aadharCardNumber.length()>0)
{
if(aadharCardNumberWiseEmployeesMap.containsKey(panNumber))
{
blexception.addException("panNumber","PAN Number already exists");
}
}
if(panNumber!=null&&panNumber.length()>0)
{
if(panNumberWiseEmployeesMap.containsKey(panNumber))
{
blexception.addException("aadharCardNumber","Aadhar Card Number exists");
}
}
if(blexception.hasExceptions())
{
throw blexception;
}
try
{
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setName(name);
if(gender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
else
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setDateOfBirth((Date)dateOfBirth.clone());
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setDesignationCode(designation.getCode());
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.add(employeeDTO);
EmployeeInterface dsEmployees;
dsEmployees=new Employee();
dsEmployees.setName(name);
dsEmployees.setEmployeeId(employeeDTO.getEmployeeId());
dsEmployees.setPANNumber(employeeDTO.getPANNumber());
dsEmployees.setAadharCardNumber(employeeDTO.getAadharCardNumber());
dsEmployees.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployees.setIsIndian(employeeDTO.getIsIndian());
if(employeeDTO.getGender()=='M')
{
dsEmployees.setGender(GENDER.MALE);
}
else
{
dsEmployees.setGender(GENDER.FEMALE);
}
Set<EmployeeInterface>ets;
dsEmployees.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployees.setBasicSalary(employeeDTO.getBasicSalary());
employeeIdWiseEmployeesMap.put(employeeDTO.getEmployeeId().toUpperCase(),dsEmployees);
panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployees);
aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployees);
employeesSet.add(dsEmployees);
ets=designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployees);
designationCodeWiseEmployeesMap.put(designation.getCode(),ets);
}
else
{
ets.add(dsEmployees);
}
}catch(DAOException daoexception)
{
blexception.setGenericException(daoexception.getMessage());
throw blexception;
}
}
public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blexception;
blexception=new BLException();
String name=employee.getName();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
BigDecimal basicSalary=employee.getBasicSalary();
boolean isIndian=employee.getIsIndian();
String employeeId=employee.getEmployeeId();
Date dateOfBirth=employee.getDateOfBirth();
DesignationInterface designation=employee.getDesignation();
char gender=employee.getGender();
if(employeeId==null)
{
blexception.addException("employeeId","Employee Id is null");
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0)
{
blexception.addException("employeeId","Employee Id is of zero length");
}
else
{
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blexception.addException("employeeId","Employee Id does not exists");
}
}
}
if(name==null)
{
blexception.addException("name","name is null");
}
else
{
name=name.trim();
if(name.length()==0)
{
blexception.addException("name","name is of zero length");
}
}
if(designation==null)
{
blexception.addException("designation","Designation is null");
}
if(dateOfBirth==null)
{
blexception.addException("dateOfBirth","Date Of Birth is null");
}
if(basicSalary==null)
{
blexception.addException("basicSalary","Basic Salary is null");
}
if(gender==' ')
{
blexception.addException("gender","gender is required");
}
if(panNumber==null)
{
blexception.addException("panNumber","PAN Number is null");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blexception.addException("panNumber","PAN Number length is zero");
}
}
if(aadharCardNumber==null)
{
blexception.addException("aadharCardNumber","Aadhar Card Number is null");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blexception.addException("aadharCardNumber","Aadhar Card Number length is zero");
}
}
if(panNumber!=null&&panNumber.length()>0)
{
System.out.println("hed0");
EmployeeInterface ee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(ee!=null&&ee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
{
System.out.println("heppeidd0");
blexception.addException("panNumber","PAN Number already exists");
}
}
if(aadharCardNumber!=null&&aadharCardNumber.length()>0)
{
System.out.println("heppe3838d0");
EmployeeInterface ee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(ee!=null&&ee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
{
blexception.addException("aadharCardNumber","Aadhar Card Number already exists");
}
}
if(blexception.hasExceptions())
{
throw blexception;
}
try
{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();

EmployeeInterface dsEmployee;
dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
String oldPANNumber=dsEmployee.getPANNumber();
String oldAadharCardNumber=dsEmployee.getAadharCardNumber();
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designation.getCode());
if(gender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
else
{
employeeDTO.setGender(GENDER.FEMALE);
}
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.update(employeeDTO);
EmployeeInterface dlEmployee=new Employee();
dlEmployee.setName(name);
dlEmployee.setEmployeeId(employeeId);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dlEmployee.setBasicSalary(basicSalary);
if(gender=='M')
{
dlEmployee.setGender(GENDER.MALE);
}
else
{
dlEmployee.setGender(GENDER.FEMALE);
}
dlEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
employeeIdWiseEmployeesMap.remove(employeeId.toUpperCase());
panNumberWiseEmployeesMap.remove(oldPANNumber.toUpperCase());
aadharCardNumberWiseEmployeesMap.remove(oldAadharCardNumber.toUpperCase());
designationCodeWiseEmployeesMap.remove(designation.getCode());
Set<EmployeeInterface>ets;
ets=designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dlEmployee);
designationCodeWiseEmployeesMap.put(designation.getCode(),ets);
}
else
{
ets.add(dlEmployee);
}
employeeIdWiseEmployeesMap.put(employeeId.toUpperCase(),dlEmployee);
panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dlEmployee);
aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dlEmployee);
employeesSet.add(dlEmployee);
}catch(DAOException daoexception)
{
blexception.setGenericException(daoexception.getMessage());
throw blexception;
}
}
public void removeEmployee(String employeeId) throws BLException
{
BLException blexception;
blexception=new BLException();
if(employeeId==null)
{
blexception.addException("employeeId","Employee Id  does not exits");
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0)
{
blexception.addException("employeeId","Length of Employee Id is zero");
}
}
if(employeeId!=null&&employeeId.length()>0)
{
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blexception.addException("employeeId","Employee Id "+employeeId+" does not exists");
}
}
if(blexception.hasExceptions())
{
throw blexception;
}
try
{
EmployeeInterface employee;
employee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.delete(employee.getEmployeeId());
employeesSet.remove(employee);
designationCodeWiseEmployeesMap.remove(employee.getDesignation().getCode());
panNumberWiseEmployeesMap.remove(employee.getPANNumber());
aadharCardNumberWiseEmployeesMap.remove(employee.getAadharCardNumber());
}catch(DAOException daoexception)
{
blexception.setGenericException(daoexception.getMessage());
}
}
public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
{
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
BLException blexception;
blexception=new BLException();
blexception.setGenericException("Employee Id "+employeeId+" not found");
throw blexception;
}
EmployeeInterface dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
EmployeeInterface employee=new Employee();
employee.setName(dsEmployee.getName());
employee.setEmployeeId(employeeId);
DesignationInterface designation;
designation=dsEmployee.getDesignation();
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
employee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));

employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
if(dsEmployee.getGender()=='M')
{
employee.setGender(GENDER.MALE);
}
else
{
employee.setGender(GENDER.FEMALE);
}
return employee;
}
public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException
{
EmployeeInterface employee=new Employee();
EmployeeInterface dsEmployee=null;
if(panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase())==false)
{
BLException blexception;
blexception=new BLException();
blexception.setGenericException("PAN Number not found");
throw blexception;
}
dsEmployee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
employee.setName(dsEmployee.getName());
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation;
designation=dsEmployee.getDesignation();
employee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
if(dsEmployee.getGender()=='M')
{
employee.setGender(GENDER.MALE);
}
else
{
employee.setGender(GENDER.FEMALE);
}
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setBasicSalary(dsEmployee.getBasicSalary());
return employee;

}

public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
{
EmployeeInterface employee=new Employee();
EmployeeInterface dsEmployee=null;
if(aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase())==false)
{
BLException blexception;
blexception=new BLException();
blexception.setGenericException("Aadhar Card Number not found");
throw blexception;
}
dsEmployee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
employee.setName(dsEmployee.getName());
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation;
designation=dsEmployee.getDesignation();
employee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
if(dsEmployee.getGender()=='M')
{
employee.setGender(GENDER.MALE);
}
else
{
employee.setGender(GENDER.FEMALE);
}
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setBasicSalary(dsEmployee.getBasicSalary());
return employee;
}
public int getEmployeeCount() throws BLException
{
return this.employeeIdWiseEmployeesMap.size();
}

public boolean employeePANNumberExists(String panNumber) throws BLException
{
return this.panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase());
}

public boolean employeeAadharCardNumberExists(String aadharCardNumber) throws BLException
{
return this.aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase());
}

public boolean employeeEmployeeIdExists(String employeeId) throws BLException
{
return this.employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase());
}

public Set<EmployeeInterface> getEmployees() throws BLException
{

Set<EmployeeInterface>employees=new TreeSet<>();
employeesSet.forEach((dsEmployee)->{
EmployeeInterface employee=new Employee();
employee.setName(dsEmployee.getName());
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
DesignationManagerInterface designationManager=null;
try
{
designationManager=DesignationManager.getDesignationManager();
}catch(BLException blexception)
{
}
DesignationInterface designation;
designation=dsEmployee.getDesignation();
employee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
if(dsEmployee.getGender()=='M')
{
employee.setGender(GENDER.MALE);
}
else
{
employee.setGender(GENDER.FEMALE);
}
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employees.add(employee);
});
return employees;
}
public Set<EmployeeInterface> getEmployeesByDesignationCode(int designationCode) throws BLException
{
if(designationCodeWiseEmployeesMap.containsKey(designationCode)==false)
{
BLException blexception;
blexception=new BLException();
blexception.setGenericException("Designation Code "+designationCode+" does not exists");
throw blexception;
}
Set<EmployeeInterface>employees;
employees=new TreeSet<>();
Set<EmployeeInterface>dsEmployees;
dsEmployees=designationCodeWiseEmployeesMap.get(designationCode);
for(EmployeeInterface dsEmployee:dsEmployees)
{
EmployeeInterface employee=new Employee();
employee.setName(dsEmployee.getName());
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
DesignationManagerInterface designationManager=null;
designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation;
designation=dsEmployee.getDesignation();
employee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
if(dsEmployee.getGender()=='M')
{
employee.setGender(GENDER.MALE);
}
else
{
employee.setGender(GENDER.FEMALE);
}
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employees.add(employee);
}
return employees;
}
public int getEmployeeCountByDesignationCode(int designationCode) throws BLException
{
return designationCodeWiseEmployeesMap.get(designationCode).size();
}
public boolean designationAlloted(int designationCode) throws BLException
{
return designationCodeWiseEmployeesMap.containsKey(designationCode);
}
}