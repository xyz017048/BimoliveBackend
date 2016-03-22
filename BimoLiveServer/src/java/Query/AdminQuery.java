/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import DBConnector.Connector;
import Model.CheckResult;
import Model.UserInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chonghuan
 */
public class AdminQuery 
{
    private  String query = null;
    private  PreparedStatement stmt = null;
    private  ResultSet rs = null;
    private final String NEWSTATUS = "new";
    private final String READSTAUTS = "read";
    private final String APPROVALSTRING = "approval";
    private final String DECLINESTRING = "decline";
    
    public List<UserInfo> getApplications(int idAdmin, int idUser)
    {
        List<UserInfo> applications = new ArrayList<UserInfo>();
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        
        try
        {
            query = "SELECT roleLevel FROM UserBasic WHERE idUser = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idAdmin);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                if (rs.getInt("roleLevel") != 0)
                    return applications;
            }
            else
                return applications;
            if(idUser == -1) // all
            {
                query = "SELECT * FROM UserBasic "
                        + "WHERE  applyStatus = ? or applyStatus = ? order by applyDate";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, READSTAUTS);
                stmt.setString(2, NEWSTATUS);
            }
            else
            {
                query = "SELECT * FROM UserBasic "
                        + "WHERE  idUser = ? and (applyStatus = ? or applyStatus = ?)";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, idUser);
                stmt.setString(2, NEWSTATUS);
                stmt.setString(3, READSTAUTS);
            }
            rs = stmt.executeQuery();
            while (rs.next())
            {
                UserInfo applicant = new UserInfo();
                applicant.setIdUser(rs.getInt("idUser"));
                applicant.setEmail(rs.getString("email"));
                applicant.setUsername(rs.getString("username"));
                applicant.setRoleLevel(rs.getInt("roleLevel"));
                applicant.setFirstName(rs.getString("firstName"));
                applicant.setLastName(rs.getString("lastName"));
                applicant.setProfile(rs.getString("profile"));
                applicant.setIntroWords(rs.getString("introWords"));
                applicant.setRegisDate(rs.getString("regisDate").substring(0,19));
                applicant.setApplyStatus(rs.getString("applyStatus"));
                applicant.setResume(rs.getString("resume"));
                applicant.setCompany(rs.getString("company"));
                applicant.setJobTitle(rs.getString("jobTitle"));
                applications.add(applicant);
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
        return applications;
    }
    
    public CheckResult updateApplication(UserInfo action)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        try
        {
            if(action.getApplyStatus().equals("approve"))
                action.setRoleLevel(2);
            else
                action.setRoleLevel(1);
            query = "UPDATE UserBasic set applyStatus = ?, idAdmin = ?, changeDate = ?, roleLevel = ? where idUser = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1,action.getApplyStatus());
            stmt.setInt(2,action.getIdAdmin());
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(4, action.getRoleLevel());
            stmt.setInt(5,action.getIdUser());
            stmt.executeUpdate();
            
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
