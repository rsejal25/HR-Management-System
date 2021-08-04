import com.thinking.machine.hr.dl.dto.*;
import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import java.util.*;
import java.text.*;
public class EmployeePanNumberExistsTestCase
{
static public void main(String gg[])
{
String panNumber=gg[0];
try
{
boolean found;
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
found=employeeDAO.panNumberExists(panNumber);
if(found==true)
{
System.out.println("PAN number : "+panNumber+" exists");
}
else
{
System.out.println("PAN number : "+panNumber+" does not exists");
}
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}