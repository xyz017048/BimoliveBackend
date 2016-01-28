/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Chonghuan
 */
public class Connector 
{
    private final static String url = "bimolivedb.cvuflhnld9ts.us-west-2.rds.amazonaws.com:3306/BimoliveMasterDB";
    private final static String username = "thepros";
    private final static String password = "bimolive";
	
    public static Connection Get()
    {
        Connection conn = null;

        try 
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(String.format("jdbc:mysql://%s?user=%s&password=%s", url, username, password));
        } 
        catch (Exception ex) 
        {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return conn;
    }

    public static void Close(Connection conn)
    {
        try
        {
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void CloseStmt(PreparedStatement stmt)
    {
        try 
        {
            stmt.close();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    } 
}
