import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.dto.*;
import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import java.util.*;
public class DesignationGetAllTestCase
{
public static void main(String gg[])
{
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
Set<DesignationDTOInterface>designations;
designations=designationDAO.getAll();
designations.forEach((designationDTO)->{
System.out.printf("code %d ,title %s\n",designationDTO.getCode(),designationDTO.getTitle());
});
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());		
}
}
}