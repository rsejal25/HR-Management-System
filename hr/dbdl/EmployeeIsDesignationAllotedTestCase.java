import com.thinking.machine.hr.dl.dto.*;
import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import java.util.*;
import java.text.*;
public class EmployeeIsDesignationAllotedTestCase
{
static public void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
boolean found;
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
found=employeeDAO.isDesignationAlloted(code);
if(found==true)
{
System.out.println("Designation Code : "+code+" is alloted");
}
else
{
System.out.println("Designation Code : "+code+" is not alloted");
}
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}