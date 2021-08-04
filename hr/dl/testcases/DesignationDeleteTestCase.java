import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.dto.*;
public class DesignationDelete TestCase
{
public static void main(String gg[])
{
int code;
code=Integer.parseInt(gg[0]);
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.delete(code);
System.out.println("title deleted at code :"+code);
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}