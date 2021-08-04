import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.dto.*;
import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
public class DesignationCodeExistsTestCase
{
public static void main(String gg[])
{
int code;
code=Integer.parseInt(gg[0]);
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
boolean f;
f=designationDAO.codeExists(code);
if(f)
{
System.out.println(code +" code exists ");
}
else
{
System.out.println("code does not exists");
}
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());		
}
}
}