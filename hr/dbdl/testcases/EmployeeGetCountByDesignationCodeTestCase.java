import com.thinking.machine.hr.dl.dto.*;
import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import java.util.*;
import java.text.*;
public class EmployeeGetCountByDesignationCodeTestCase
{
static public void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
int count;
count=employeeDAO.getCountByDesignation(code);
System.out.println("Employees with designationCode : "+code+" are" +count);
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}