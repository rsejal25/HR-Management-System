import com.thinking.machine.hr.bl.pojo.*;
import com.thinking.machine.hr.bl.interfaces.pojo.*;
import com.thinking.machine.hr.bl.exceptions.*;
import com.thinking.machine.hr.bl.managers.*;
import com.thinking.machine.hr.bl.interfaces.managers.*;
import java.math.*;
import java.util.*;
import java.text.*;
public class EmployeeManagerAddTestCase 
{
public static void main(String gg[])
{
String name="Veena Jain";
DesignationInterface designation=new Designation();
designation.setCode(2);
Date dateOfBirth=new Date("20/11/1990");
boolean isIndian=true;
BigDecimal basicSalary=new BigDecimal("200000");
String aadharCardNumber="UD22822992";
String panNumber="A29219223";
try
{
EmployeeInterface employee=new Employee();
employee.setName(name);
employee.setAadharCardNumber(aadharCardNumber);
employee.setPANNumber(panNumber);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setIsIndian(isIndian);
employee.setBasicSalary(basicSalary);
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.addEmployee(employee);
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