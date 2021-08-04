import com.thinking.machine.hr.dl.exceptions.*;
import java.text.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import com.thinking.machine.hr.dl.dto.*;
import com.thinking.machine.enums.*;
import com.thinking.machine.hr.dl.dao.*;
import java.math.*;
import java.util.*;
public class EmployeeGetCountTestCase
{
public static void main(String gg[])
{
try
{
int recordCount;
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
recordCount=employeeDAO.getCount();
System.out.println("Total number of Employees Added are : "+recordCount);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}