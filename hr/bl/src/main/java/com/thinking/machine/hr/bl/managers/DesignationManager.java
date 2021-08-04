package com.thinking.machine.hr.bl.managers;
import com.thinking.machine.hr.bl.interfaces.pojo.*;
import com.thinking.machine.hr.bl.interfaces.managers.*;
import com.thinking.machine.hr.bl.exceptions.*;
import com.thinking.machine.hr.bl.pojo.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.dao.*;
import com.thinking.machine.hr.dl.dto.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationMap;
private Map<String,DesignationInterface> titleWiseDesignationMap;
private Set<DesignationInterface> designationsSet;
private static DesignationManager designationManager=null;
private DesignationManager() throws BLException
{
populateDataStructures();
}
private void populateDataStructures()
{
this.codeWiseDesignationMap=new HashMap<>();
this.titleWiseDesignationMap=new HashMap<>();
this.designationsSet=new TreeSet<>();
DesignationInterface designation;
try
{
Set<DesignationDTOInterface> dlDesignations;
dlDesignations=new DesignationDAO().getAll();
for(DesignationDTOInterface dlDesignation:dlDesignations)
{
designation=new Designation();
designation.setCode(dlDesignation.getCode());
designation.setTitle(dlDesignation.getTitle());
this.codeWiseDesignationMap.put(new Integer(designation.getCode()),designation);
this.titleWiseDesignationMap.put(designation.getTitle().toUpperCase(),designation);
this.designationsSet.add(designation);
}
}catch(DAOException daoException)
{
BLException blexception=null;
blexception.setGenericException(daoException.getMessage());
}
}
public static DesignationManager getDesignationManager() throws BLException
{
if(designationManager==null)
{
designationManager=new DesignationManager();
}
return designationManager;
}
DesignationInterface getDSDesignationByCode(int code)
{
DesignationInterface designation;
designation=this.codeWiseDesignationMap.get(code);
return designation;
}
public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blexception;
blexception=new BLException();
if(designation==null)
{
blexception.setGenericException("designation is null");
throw blexception;
}
int code=designation.getCode();
if(code!=0)
{
blexception.addException("code","Code should be zero");
}
String title=designation.getTitle();
if(title==null)
{
blexception.addException("title","title is null");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
blexception.addException("title","title length not be zero");
}
}
if(title.length()>0)
{
if(this.titleWiseDesignationMap.containsKey(title.toUpperCase()))
{
blexception.addException("title","designation already exists");
}
}
if(blexception.hasExceptions())
{
throw blexception;
}
try
{
DesignationInterface dsDesignation;
dsDesignation=new Designation();
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
code=designationDTO.getCode();
dsDesignation.setCode(code);
dsDesignation.setTitle(title);
codeWiseDesignationMap.put(code,dsDesignation);
titleWiseDesignationMap.put(title.toUpperCase(),dsDesignation);
designationsSet.add(dsDesignation);
}catch(DAOException daoexception)
{
blexception.setGenericException(daoexception.getMessage());
}
}
public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blexception;
blexception=new BLException();
if(designation==null)
{
blexception.setGenericException("designation is null");
throw blexception;
}
int code;
code=designation.getCode();
if(code==0)
{
blexception.addException("code","code cannot be zero");
}
if(!(this.codeWiseDesignationMap.containsKey(code)))
{
blexception.addException("code","code "+code+" does not exists");
}
String title;
title=designation.getTitle();
if(title==null)
{
blexception.addException("title","title cannot be null");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
blexception.addException("title","length of title is zero");
}
}
if(titleWiseDesignationMap.containsKey(title.toUpperCase()))
{
System.out.println("here");
}
if(title.length()>0)
{
DesignationInterface d;
d=titleWiseDesignationMap.get(title.toUpperCase());
if(d!=null&&d.getCode()!=code)
{
System.out.println("hrer");
blexception.addException("title","title "+title+" exists against another designation code");
}
}
if(blexception.hasExceptions())
{
throw blexception;
}
try
{
DesignationInterface dsDesignation=codeWiseDesignationMap.get(code);
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.update(designationDTO);
codeWiseDesignationMap.remove(code);
titleWiseDesignationMap.remove(dsDesignation.getTitle().toUpperCase());
designationsSet.remove(dsDesignation);
dsDesignation.setTitle(title);
dsDesignation.setCode(code);
codeWiseDesignationMap.put(code,dsDesignation);
titleWiseDesignationMap.put(title,dsDesignation);
designationsSet.add(dsDesignation);
}catch(DAOException daoexception)
{
blexception.setGenericException(daoexception.getMessage());
}
}
public void removeDesignation(int code) throws BLException
{
BLException blexception;
blexception=new BLException();
if(code<=0)
{
blexception.addException("code","code does not exists");
throw blexception;
}
if(code>0)
{
if(!(this.codeWiseDesignationMap.containsKey(code)))
{
blexception.addException("code","code does not exists");
throw blexception;
}
}
try
{
DesignationInterface dsDesignation=codeWiseDesignationMap.get(code);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.delete(code);
codeWiseDesignationMap.remove(code);
titleWiseDesignationMap.remove(dsDesignation.getTitle().toUpperCase());
designationsSet.remove(dsDesignation);

}catch(DAOException daoexception)
{
blexception.setGenericException(daoexception.getMessage());
}
}
public DesignationInterface getDesignationByCode(int code) throws BLException
{
DesignationInterface designation;
designation=new Designation();
DesignationInterface dsDesignation;
dsDesignation=codeWiseDesignationMap.get(code);
if(dsDesignation==null)
{
BLException blexception=new BLException();
blexception.addException("code","code does not exits");
throw blexception;
}
designation.setCode(code);
designation.setTitle(dsDesignation.getTitle());
return designation;
}

public DesignationInterface getDesignationByTitle(String title) throws BLException
{
DesignationInterface designation;
designation=new Designation();
DesignationInterface dsDesignation;
dsDesignation=titleWiseDesignationMap.get(title);
if(dsDesignation==null)
{
BLException blexception=new BLException();
blexception.addException("title","title does not exits");
throw blexception;
}
designation.setTitle(title);
designation.setCode(dsDesignation.getCode());
return designation;
}

public int getDesignationCount() throws BLException
{
return codeWiseDesignationMap.size();
}

public boolean designationCodeExists(int code) throws BLException
{
return codeWiseDesignationMap.containsKey(code);
}

public boolean designationTitleExists(String title) throws BLException
{
return titleWiseDesignationMap.containsKey(title);
}

public Set<DesignationInterface> getDesignations()
{
Set<DesignationInterface> designations;
designations=new TreeSet<>();
designationsSet.forEach((designation)->{
DesignationInterface dsDesignation=new Designation();
dsDesignation.setCode(designation.getCode());
dsDesignation.setTitle(designation.getTitle());
designations.add(dsDesignation);
});
return designations;
}
}