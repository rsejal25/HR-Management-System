import com.thinking.machine.hr.bl.pojo.*;
import com.thinking.machine.hr.bl.interfaces.pojo.*;
import com.thinking.machine.hr.bl.managers.*;
import com.thinking.machine.hr.bl.interfaces.managers.*;
import com.thinking.machine.hr.bl.exceptions.*;
import java.util.*;
class DesignationUpdateTestCase
{
public static void main(String gg[])
{
try
{
DesignationInterface designation=new Designation();
designation.setTitle("manager");
designation.setCode(2);
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
designationManager.updateDesignation(designation);
System.out.println("Updated");
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