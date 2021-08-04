import com.thinking.machine.hr.bl.pojo.*;
import com.thinking.machine.hr.bl.interfaces.pojo.*;
import com.thinking.machine.hr.bl.exceptions.*;
import com.thinking.machine.hr.bl.managers.*;
import com.thinking.machine.hr.bl.interfaces.managers.*;
import java.math.*;
import java.util.*;
import java.text.*;
public class EmployeeManagerUpdateTestCase 
{
public static void main(String gg[])
{
String name="Mukesh Rawat";
DesignationInterface designation=new Designation();
designation.setCode(1);
Date dateOfBirth=new Date("2/01/1990");
boolean isIndian=false;
BigDecimal basicSalary=new BigDecimal("200000");
String panNumber="UDK822992";
String aadharCardNumber="A2921927";
try
{
EmployeeInterface employee=new Employee();
employee.setName(name);
employee.setEmployeeId("A10000001");
employee.setAadharCardNumber(aadharCardNumber);
employee.setPANNumber(panNumber);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setIsIndian(true);
employee.setBasicSalary(basicSalary);
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
if(employeeManager.employeePANNumberExists("UD2822992"))
{
System.out.println("yess");
}
employeeManager.updateEmployee(employee);
System.out.println("added");
}catch(BLException blexception)
{
if(blexception.hasGenericException())
{
System.out.println(blexception.getGenericException());
}
List<String> properties=blexception.getProperties();
for(String property:properties)
{
System.out.println(blexception.getException(property));
}
}
}
}