import com.thinking.machine.hr.dl.dto.*;
import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import java.util.*;
import java.text.*;
public class EmployeeEmployeeIdExistsTestCase
{
static public void main(String gg[])
{
String employeeId=gg[0];
try
{
boolean found;
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
found=employeeDAO.employeeIdExists(employeeId);
if(found==true)
{
System.out.println("EmployeeId : "+employeeId+" exists");
}
else
{
System.out.println("EmployeeId : "+employeeId+" does not exists");
}
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}