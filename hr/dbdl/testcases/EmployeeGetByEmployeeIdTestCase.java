import com.thinking.machine.hr.dl.dto.*;
import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import java.util.*;
import java.text.*;
public class EmployeeGetByEmployeeId
{
static public void main(String gg[])
{
String employeeId=gg[0];
try
{
SimpleDateFormat sdf;
sdf=new SimpleDateFormat("dd/MM/yyyy");
EmployeeDAOInterface employeeDAO;
EmployeeDTOInterface employeeDTO;
employeeDAO=new EmployeeDAO();
employeeDTO=employeeDAO.getByEmployeeId(employeeId);
System.out.println(employeeDTO.getEmployeeId());
System.out.println(employeeDTO.getName());
System.out.println(employeeDTO.getDesignationCode());
System.out.println(sdf.format(employeeDTO.getDateOfBirth()));
System.out.println(employeeDTO.getGender());
System.out.println(employeeDTO.getIsIndian());
System.out.println(employeeDTO.getBasicSalary());
System.out.println(employeeDTO.getPANNumber());
System.out.println(employeeDTO.getAadharCardNumber());
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}