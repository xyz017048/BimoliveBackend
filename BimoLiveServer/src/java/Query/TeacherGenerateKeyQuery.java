/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import DBConnector.Connector;
import Model.GenerateKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Chonghuan
 */
public class TeacherGenerateKeyQuery 
{
    private  String query = null;
    private  PreparedStatement stmt = null;
    private  ResultSet rs = null;
    
    public boolean checkKey(String key, int idUser)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return false;
        boolean result = false;
        try
        {
            query = "SELECT keyString FROM UserBasic WHERE keyString = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, key);
            rs = stmt.executeQuery();
            if (rs.next() )
            {
                return false;
            }
            else
            {
                result = true;
                query = "UPDATE UserBasic set keyString = ? WHERE idUser = ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, key);
                stmt.setInt(2,idUser);
                stmt.executeUpdate();
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
}
