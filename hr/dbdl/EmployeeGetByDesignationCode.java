import com.thinking.machine.hr.dl.dto.*;
import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import java.util.*;
import java.text.*;
public class EmployeeGetByDesignationCode
{
static public void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
SimpleDateFormat sdf;
sdf=new SimpleDateFormat("dd/MM/yyyy");
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
Set<EmployeeDTOInterface>employees;
employees=employeeDAO.getByDesignationCode(code);
for(EmployeeDTOInterface employeeDTO:employees)
{
System.out.println(employeeDTO.getEmployeeId());
System.out.println(employeeDTO.getName());
System.out.println(employeeDTO.getDesignationCode());
System.out.println(sdf.format(employeeDTO.getDateOfBirth()));
System.out.println(employeeDTO.getGender());
System.out.println(employeeDTO.getIsIndian());
System.out.println(employeeDTO.getBasicSalary());
System.out.println(employeeDTO.getPANNumber());
System.out.println(employeeDTO.getAadharCardNumber());
System.out.println("*****************");
}
}catch(DAOException daoexception)
{
System.out.println(daoexception.getMessage());
}
}
}