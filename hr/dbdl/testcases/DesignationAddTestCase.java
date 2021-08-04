import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.dto.*;
public class DesignationAddTestCase
{
public static void main(String gg[])
{
String title=gg[0];
try
{
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
System.out.println("Designation :"+title+" added with code "+designationDTO.getCode());
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}