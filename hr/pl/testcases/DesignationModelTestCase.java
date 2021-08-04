import com.thinking.machine.hr.pl.model.*;
import com.thinking.machine.hr.bl.exceptions.*;
import java.awt.*;
import javax.swing.*;
public class DesignationModelTestCase extends JFrame
{
private DesignationModel designationModel;
private JTable table;
private JScrollPane jsp;
private Container container;
DesignationModelTestCase()
{
designationModel=new DesignationModel();
table=new JTable(designationModel);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int width=600;
int height=600;
setSize(width,height);
int x=(d.width/2)-(width/2);
int y=(d.height/2)-(height/2);
setLocation(x,y);
setVisible(true);
}
public static void main(String gg[])
{
DesignationModelTestCase de=new DesignationModelTestCase();
}
}