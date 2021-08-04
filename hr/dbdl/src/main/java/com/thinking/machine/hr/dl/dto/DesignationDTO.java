package com.thinking.machine.hr.dl.dto;
import com.thinking.machine.hr.dl.interfaces.dto.*;
public class DesignationDTO implements DesignationDTOInterface
{
private int code;
private String title;
public DesignationDTO()
{
this.code=0;
this.title="";
}
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setTitle(String title)
{
this.title=title;
}
public String getTitle()
{
return this.title;
}
public boolean equals(Object other)
{
if(!(other instanceof DesignationDTO)) return false;
DesignationDTOInterface designationdto;
designationdto=(DesignationDTO)other;
return this.code==designationdto.getCode();
}
public int compareTo(DesignationDTOInterface other)
{
return this.code-other.getCode();
}
public int hashCode()
{
return this.code;
}
}