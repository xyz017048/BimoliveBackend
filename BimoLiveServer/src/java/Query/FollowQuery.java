/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import DBConnector.Connector;
import Model.CheckResult;
import Model.IdModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 *
 * @author Chonghuan
 */
public class FollowQuery 
{
    private  String query = null;
    private  PreparedStatement stmt = null;
    private  ResultSet rs = null;
    
    public CheckResult followCourse (IdModel idModel)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        int idCourse = idModel.getIdCourse();
        CheckResult result = new CheckResult();
        try
        {
            if (idCourse != -1)
            {
                query = "INSERT INTO FollowCourse (idUser, idCourse,followTime) VALUES(?,?,?)";
            }
            else
            {
                query = "INSERT INTO FollowCourse (idUser, idCourse,followTime) VALUES(?, (SELECT idCourse FROM Lecture WHERE idLecture=?),?)";
            }
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,idModel.getIdUser());
            if (idCourse != -1)
                stmt.setInt(2,idCourse);
            else
                stmt.setInt(2,idModel.getIdLecture());
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
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
    
    public CheckResult followTeacher (IdModel idModel)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        int idTeacher = idModel.getIdTeacher();
        int idCourse = idModel.getIdCourse();
        CheckResult result = new CheckResult();
        try
        {
            if (idTeacher != -1)
            {
                query = "INSERT INTO FollowTeacher (idUser, idTeacher,followTime) VALUES(?,?,?)";
            }
            else if (idCourse != -1)
            {
                query = "INSERT INTO FollowTeacher (idUser, idTeacher,followTime) VALUES(?, (SELECT idUser FROM CourseInfo WHERE idCourse=?),?)";
            }
            else
            {
                query = "INSERT INTO FollowTeacher (idUser, idTeacher,followTime) VALUES(?, (SELECT idUser FROM CourseInfo C, Lecture L WHERE C.idCourse = L.idCourse and L.idLecture=?),?)";
            }
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,idModel.getIdUser());
            if (idTeacher != -1)
                stmt.setInt(2, idTeacher);
            else if (idCourse != -1)
                stmt.setInt(2,idCourse);
            else
                stmt.setInt(2,idModel.getIdLecture());
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
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
    
    
    public CheckResult unfollowCourse (IdModel idModel)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        int idCourse = idModel.getIdCourse();
        CheckResult result = new CheckResult();
        try
        {
            if (idCourse != -1)
            {
                query = "DELETE FROM FollowCourse WHERE idUser = ? and idCourse = ?";
            }
            else
            {
                query = "DELETE FROM FollowCourse WHERE idUser =? and idCourse = (SELECT idCourse FROM Lecture WHERE idLecture=?)";
            }
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,idModel.getIdUser());
            if (idCourse != -1)
                stmt.setInt(2,idCourse);
            else
                stmt.setInt(2,idModel.getIdLecture());
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
    
    public CheckResult unfollowTeacher (IdModel idModel)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        int idTeacher = idModel.getIdTeacher();
        int idCourse = idModel.getIdCourse();
        CheckResult result = new CheckResult();
        try
        {
            if (idTeacher != -1)
            {
                query = "DELETE FROM FollowTeacher WHERE idUser = ? AND idTeacher= ?";
            }
            else if (idCourse != -1)
            {
                query = "DELETE FROM FollowTeacher WHERE idUser = ? and idTeacher = (SELECT idUser FROM CourseInfo WHERE idCourse = ?)";
            }
            else
            {
                query = "DELETE FROM FollowTeacher WHERE idUser= ? AND idTeacher=(SELECT idUser FROM CourseInfo C, Lecture L WHERE C.idCourse = L.idCourse and L.idLecture=?)";
            }
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,idModel.getIdUser());
            if (idTeacher != -1)
                stmt.setInt(2, idTeacher);
            else if (idCourse != -1)
                stmt.setInt(2,idCourse);
            else
                stmt.setInt(2,idModel.getIdLecture());
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
