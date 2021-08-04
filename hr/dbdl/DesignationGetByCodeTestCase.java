import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import com.thinking.machine.hr.dl.dto.*;
public class DesignationGetByCodeTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
DesignationDTOInterface designationDTO;
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDTO=designationDAO.getByCode(code);
System.out.println(designationDTO.getCode()+" "+designationDTO.getTitle());
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}