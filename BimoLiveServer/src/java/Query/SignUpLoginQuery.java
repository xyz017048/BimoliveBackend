/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import DBConnector.Connector;
import Model.CheckResult;
import Model.RegistrationModel;
import Model.ValidLoginResponse;
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
    
    public void userRegister(RegistrationModel user)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return;
        
        try
        {
            query = "INSERT INTO UserBasic (email, username, password,roleLevel,lastLogin,regisDate,introWords) VALUES(?,?,?,?,?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getEmail());
            stmt.setString(2,user.getUsername());
            stmt.setString(3,user.getPassword());
            stmt.setInt(4, 1);
            stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            stmt.setString(7, "");
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
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        try
        {
            query = "SELECT idUser,email,username, roleLevel, firstName, lastName, "
                    + "lastLogin, profile, introWords, regisDate FROM UserBasic"+
                    " WHERE email = ? and password = ? and active = 1";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                result = 1;
                ValidLoginResponse loginResponse = new ValidLoginResponse();
                loginResponse.setResult(result);
                loginResponse.setIdUser(rs.getInt("idUser"));
                loginResponse.setEmail(rs.getString("email"));
                loginResponse.setUsername(rs.getString("username"));
                loginResponse.setRoleLevel(rs.getInt("roleLevel"));
                loginResponse.setFirstName(rs.getString("firstName"));
                loginResponse.setLastName(rs.getString("lastName"));
                loginResponse.setLastLogin(rs.getString("lastLogin"));
                loginResponse.setProfile(rs.getString("profile"));
                loginResponse.setIntroWords(rs.getString("introWords"));
                loginResponse.setRegisDate(rs.getString("regisDate"));
                return loginResponse;
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
        CheckResult returnResult = new CheckResult(0);
        return returnResult;
    }
}
