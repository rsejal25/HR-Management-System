import com.thinking.machine.hr.dl.dto.*;
import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import java.util.*;
import java.text.*;
public class EmployeeAadharCardNumberExistsTestCase
{
static public void main(String gg[])
{
String aadharCardNumber=gg[0];
try
{
boolean found;
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
found=employeeDAO.aadharCardNumberExists(aadharCardNumber);
if(found==true)
{
System.out.println("Aadhar card number : "+aadharCardNumber+" exists");
}
else
{
System.out.println("Aadhar card number : "+aadharCardNumber+" does not exists");
}
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}