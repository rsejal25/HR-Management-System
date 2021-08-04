package com.thinking.machine.hr.dl.dao;
import com.thinking.machine.hr.dl.interfaces.dao.*;
import com.thinking.machine.hr.dl.interfaces.dto.*;
import com.thinking.machine.hr.dl.exceptions.*;
import com.thinking.machine.hr.dl.dto.*;
import java.util.*;
import java.io.*;
import java.sql.*;
public class DesignationDAO implements DesignationDAOInterface
{
private final static String FILE_NAME="designation.data";
public void add(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null)
{
throw new DAOException("designation is null");
}
String title;
title=designationDTO.getTitle();
if(title==null)
{
throw new  DAOException("designation is null");
}
title=title.trim();
if(title.length()==0) throw new DAOException("length of title should be zero");
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("designation already exists");
}
int code;
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into designation (title) values(?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,title);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
code=resultSet.getInt(1);
designationDTO.setCode(code);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException exception)
{
throw new DAOException(exception.getMessage());
}
}
public void update(DesignationDTOInterface designationDTO) throws DAOException
{
int code;
String title;
code=designationDTO.getCode();
title=designationDTO.getTitle();
if(code<=0)
{
throw new DAOException("invalid code");
}
if(title==null)
{
throw new DAOException("title is null");
}
title=title.trim();
if(title.length()==0)
{
throw new DAOException("Length of title is zero");
}
Connection connection;
try
{
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select* from designation where code=?");
preparedStatement.setInt(1,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("designation code does not exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select code from designation where title=? and code<>?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("designation already exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update designation set title=? where code=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void delete(int code) throws DAOException
{
if(code<=0)
{
throw new DAOException("invalid code");
}
Connection connection;
try
{
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation");
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid designation code");
}
resultSet.close();
preparedStatement.close();
connection.close();
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("delete from designation where code=?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public Set<DesignationDTOInterface>getAll() throws DAOException
{
Set<DesignationDTOInterface> designations=new TreeSet<>();
try
{
Connection connection;
connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select * from designation");
int code;
String title;
while(resultSet.next())
{
code=resultSet.getInt("code");
title=resultSet.getString("title");
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
designations.add(designationDTO);
}
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return designations;
}
public DesignationDTOInterface getByCode(int code) throws DAOException
{
String title;
if(code<=0)
{
throw new DAOException("invalid code");
}
Connection connection;
try
{
connection=DAOConnection.getConnection();
PreparedStatement prepareStatement;
prepareStatement=connection.prepareStatement("select title from designation where code=?");
prepareStatement.setInt(1,code);
ResultSet resultSet;
resultSet=prepareStatement.executeQuery();
if(resultSet.next())
{
title=resultSet.getString("title").trim();
}
else
{
resultSet.close();
prepareStatement.close();
connection.close();
throw new DAOException("invalid code");
}
resultSet.close();
prepareStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
return designationDTO;
}
public DesignationDTOInterface getByTitle(String title) throws DAOException
{
if(title==null)
{
throw new DAOException("designation is null");
}
title=title.trim();
if(title.length()==0)
{
throw new DAOException("designation length is zero");
}
Connection connection;
int code;
try
{
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("designation does not exists");
}
code=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
designationDTO.setCode(code);
return designationDTO;
}
public boolean codeExists(int code) throws DAOException
{
if(code<=0)
{
return false;
}
Connection connection;
try
{
connection=DAOConnection.getConnection();
PreparedStatement prepareStatement;
prepareStatement=connection.prepareStatement("select title from designation where code=?");
prepareStatement.setInt(1,code);
ResultSet resultSet;
resultSet=prepareStatement.executeQuery();
if(resultSet.next()==false)
{
return false;
}
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return true;
}
public boolean titleExists(String title) throws DAOException
{
if(title==null) return false;
title=title.trim();
if(title.length()==0) return false;
Connection connection;
try
{
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
return false;
}
resultSet.close();
preparedStatement.close();
connection.close();
return true;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public int getCount() throws DAOException
{
Connection connection;
int count;
try
{
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(*) from designation");
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
count=resultSet.getInt("count");
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return count;
}
}