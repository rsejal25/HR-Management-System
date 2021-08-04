import com.thinking.machine.hr.bl.pojo.*;
import com.thinking.machine.hr.bl.interfaces.pojo.*;
import com.thinking.machine.hr.bl.managers.*;
import com.thinking.machine.hr.bl.interfaces.managers.*;
import com.thinking.machine.hr.bl.exceptions.*;
import java.util.*;
public class DesignationManagerGetByDesignationCodeTestCase
{
public static void main(String gg[])
{
try
{
DesignationInterface designation;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
designation=designationManager.getDesignationByCode(20);
System.out.println(designation.getCode()+" "+designation.getTitle());
}catch(BLException blexception)
{
if(blexception.hasGenericException())
{
System.out.println(blexception.getGenericException());
}
List<String> properties=blexception.getProperties();
for(String property:properties)
{
System.out.println(blexception.getException(property));
}
}
}
}