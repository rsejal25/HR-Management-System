import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.dto.*;
import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
public class DesignationTitleExistsTestCase
{
public static void main(String gg[])
{
String title=gg[0];
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
boolean f;
f=designationDAO.titleExists(title);
if(f)
{
System.out.println(title +" title exists ");
}
else
{
System.out.println("title does not exists");
}
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());		
}
}
}