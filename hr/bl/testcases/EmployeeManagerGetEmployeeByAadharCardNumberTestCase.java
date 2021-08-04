import com.thinking.machine.hr.bl.exceptions.*;
import com.thinking.machine.hr.bl.pojo.*;
import com.thinking.machine.hr.bl.interfaces.pojo.*;
import com.thinking.machine.hr.bl.managers.*;
import com.thinking.machine.hr.bl.interfaces.managers.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManagerGetEmployeeByAadharCardNumberTestCase
{
public static void main(String gg[])
{
try
{
EmployeeManager employeeManager=EmployeeManager.getEmployeeManager();
EmployeeInterface employee;
SimpleDateFormat sdf;
sdf=new SimpleDateFormat("dd/MM/yyyy");
employee=employeeManager.getEmployeeByAadharCardNumber("A2922");
System.out.println(employee.getEmployeeId());
System.out.println(employee.getName());
System.out.println(employee.getDesignation().getCode());
System.out.println(employee.getPANNumber());
System.out.println(employee.getAadharCardNumber());
System.out.println(sdf.format(employee.getDateOfBirth()));
System.out.println(employee.getIsIndian());
System.out.println(employee.getBasicSalary());
System.out.println(employee.getGender());
}catch(BLException blexception)
{
System.out.println("hef");
if(blexception.hasGenericException())
{
System.out.println("herjf");
System.out.println(blexception.getGenericException());
}
List<String>properties=blexception.getProperties();
for(String property:properties)
{
System.out.println(blexception.getException(property));
}
}
}
}