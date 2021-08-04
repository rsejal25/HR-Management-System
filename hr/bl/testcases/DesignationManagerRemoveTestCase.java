import com.thinking.machine.hr.bl.pojo.*;
import com.thinking.machine.hr.bl.interfaces.pojo.*;
import com.thinking.machine.hr.bl.exceptions.*;
import com.thinking.machine.hr.bl.managers.*;
import com.thinking.machine.hr.bl.interfaces.managers.*;
import java.util.*;
public class DesignationManagerRemoveTestCase
{
public static void main(String gg[])
{
try
{
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
designationManager.removeDesignation(333);
System.out.println("removed");
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