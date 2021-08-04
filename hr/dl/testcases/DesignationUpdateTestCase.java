import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.dto.*;
public class DesignationUpdateTestCase
{
public static void main(String gg[])
{
int code;
code=Integer.parseInt(gg[0]);
String title=gg[1];
try
{
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.update(designationDTO);
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}