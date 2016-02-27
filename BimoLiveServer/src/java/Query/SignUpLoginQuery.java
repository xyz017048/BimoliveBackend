/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import DBConnector.Connector;
import Model.CheckResult;
import Model.RegisterRequestModel;
import Model.UserInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 *
 * @author Chonghuan
 */
public class SignUpLoginQuery 
{
    private String query = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private final String DEFAULTPROFILEPATH= "https://s3-us-west-2.amazonaws.com/bimolive-pictures/profile_pics/default_profile_pic.PNG";
    
    public void userRegister(RegisterRequestModel user)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return;
        
        try
        {
            query = "INSERT INTO UserBasic (email, username, password,roleLevel,lastLogin,regisDate,introWords, profile) VALUES(?,?,?,?,?,?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getEmail());
            stmt.setString(2,user.getUsername());
            stmt.setString(3,user.getPassword());
            stmt.setInt(4, 1);
            stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            stmt.setString(7, "");
            stmt.setString(8, DEFAULTPROFILEPATH);
            stmt.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }
    }
    
    public CheckResult checkUsername(String username)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        try
        {
            query = "SELECT username FROM UserBasic WHERE username = ? ";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (!rs.next())
            {
                result.setResult(1);
                return result;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }  
        return result;
    }
    
    public CheckResult checkEmail(String email)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        try
        {
            query = "SELECT email FROM UserBasic WHERE email = ? ";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (!rs.next())
            {
                result.setResult(1);
                return result;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }  
        return result;
    }
    
    public Object responseLogin(String email, String password)
    {
        int result = 0;
        UserInfo loginResponse = new UserInfo();
        CheckResult returnResult = new CheckResult(result);
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        try
        {
            query = "SELECT idUser,email,username, roleLevel, firstName, lastName, "
                    + "lastLogin, profile, introWords, regisDate,applyStatus FROM UserBasic"+
                    " WHERE email = ? and password = ? and active = 1";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                result = 1;
                loginResponse.setResult(result);
                loginResponse.setIdUser(rs.getInt("idUser"));
                loginResponse.setEmail(rs.getString("email"));
                loginResponse.setUsername(rs.getString("username"));
                loginResponse.setRoleLevel(rs.getInt("roleLevel"));
                loginResponse.setFirstName(rs.getString("firstName"));
                loginResponse.setLastName(rs.getString("lastName"));
                loginResponse.setLastLogin(rs.getString("lastLogin").substring(0,19));
                loginResponse.setProfile(rs.getString("profile"));
                loginResponse.setIntroWords(rs.getString("introWords"));
                loginResponse.setRegisDate(rs.getString("regisDate").substring(0,19));
                loginResponse.setApplyStatus(rs.getString("applyStatus"));
            }
            query = "UPDATE UserBasic set lastLogin = ?"+
                    " WHERE email = ?";
            stmt = conn.prepareStatement(query);
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setString(2, email);
            stmt.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        } 
        if (result == 1)
        {
            
            return loginResponse;
        }
        return returnResult;
    }
    
    public CheckResult applyToBeTeacher(UserInfo user)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        try
        {
            query = "UPDATE UserBasic set firstName = ?, lastName = ?, profile = ?, introWords = ?, resume = ?, company = ?, "
                    + "jobTitle = ?, applyStatus = ?, applyDate = ? WHERE idUser = ? ";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getProfile());
            stmt.setString(4, user.getIntroWords());
            stmt.setString(5, user.getResume());
            stmt.setString(6, user.getCompany());
            stmt.setString(7, user.getJobTitle());
            stmt.setString(8, "new");
            stmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(10, user.getIdUser());
            int rows = stmt.executeUpdate();
            if (rows != 0)
                result.setResult(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }  
        return result;
    }
    
    public CheckResult accountUpdate(UserInfo user)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        try
        {
            query = "UPDATE UserBasic set email = ?, username = ?, firstName = ?, lastName = ?, profile = ?, introWords = ?, resume = ?, company = ?, "
                    + "jobTitle = ? WHERE idUser = ? ";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getProfile());
            stmt.setString(6, user.getIntroWords());
            stmt.setString(7, user.getResume());
            stmt.setString(8, user.getCompany());
            stmt.setString(9, user.getJobTitle());
            stmt.setInt(10, user.getIdUser());
            int rows = stmt.executeUpdate();
            if (rows != 0)
                result.setResult(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }  
        return result;
    }
    
    public CheckResult changePassword(int idUser, String psw)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        try
        {
            query = "UPDATE UserBasic set password = ? WHERE idUser = ? ";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, psw);
            stmt.setInt(2, idUser);
            int rows = stmt.executeUpdate();
            if (rows != 0)
                result.setResult(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }  
        return result;
    }
}
