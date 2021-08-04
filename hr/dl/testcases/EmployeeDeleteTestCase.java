import com.thinking.machine.hr.dl.dto.*;
import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import java.util.*;
import java.text.*;
public class EmployeeDeleteTestCase
{
static public void main(String gg[])
{
String employeeId=gg[0];
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.delete(employeeId);
System.out.println("EmployeeId : "+employeeId+" deleted");
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}