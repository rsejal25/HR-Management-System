package com.thinking.machine.hr.dl.interfaces.dto;
import java.math.*;
import com.thinking.machine.enums.*;
import java.util.*;
public interface EmployeeDTOInterface extends Comparable<EmployeeDTOInterface>,java.io.Serializable
{
public void setEmployeeId(java.lang.String employeeId);
public java.lang.String getEmployeeId();
public void setName(java.lang.String name);
public java.lang.String getName();
public void setDesignationCode(int designationCode);
public int getDesignationCode();
public void setDateOfBirth(java.util.Date dateOfBirth);
public java.util.Date getDateOfBirth();
public void setGender(GENDER gender);
public char getGender();
public void setIsIndian(boolean isIndian);
public boolean getIsIndian();
public void setBasicSalary(java.math.BigDecimal basicSalary);
public java.math.BigDecimal getBasicSalary();
public void setPANNumber(java.lang.String panNumber);
public java.lang.String getPANNumber();
public void setAadharCardNumber(java.lang.String aadharCardNumber);
public java.lang.String getAadharCardNumber();
public boolean equals(Object other);
public int compareTo(EmployeeDTOInterface employeeDTO);
public int hashCode();
}
