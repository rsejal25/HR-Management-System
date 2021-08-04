import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import com.thinking.machine.hr.dl.dto.*;
import com.thinking.machine.hr.dl.dao.*;
public class DesignationGetCountTestCase
{
public static void main(String gg[])
{
try
{
int count;
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
count=designationDAO.getCount();
System.out.println("Total number of records added are "+count);
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}