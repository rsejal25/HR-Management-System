import com.thinking.machine.hr.bl.pojo.*;
import com.thinking.machine.hr.bl.interfaces.pojo.*;
import com.thinking.machine.hr.bl.managers.*;
import com.thinking.machine.hr.bl.interfaces.managers.*;
import com.thinking.machine.hr.bl.exceptions.*;
import java.util.*;
public class DesignationManagerGetDesignationsTestCase
{
public static void main(String gg[])
{
try
{
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
Set<DesignationInterface> designations=designationManager.getDesignations();
designations.forEach((designation)->{
System.out.println(designation.getCode()+" "+designation.getTitle());
});
System.out.println(designationManager.designationCodeExists(1));
}catch(BLException blexception)
{
if(blexception.hasGenericException())
{
System.out.println(blexception.getGenericException());
}
List<String>properties=blexception.getProperties();
}
}
}