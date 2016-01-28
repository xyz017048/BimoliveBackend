/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import DBConnector.Connector;
import Model.CheckResult;
import Model.QuestionSend;
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
public class QuestionQuery 
{
    private  String query = null;
    private  PreparedStatement stmt = null;
    private  ResultSet rs = null;
    public CheckResult saveQuestion(QuestionSend question)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        try
        {
            query = "INSERT INTO QuestionQueue (idUser, idLecture,sendTime,status, username, content) VALUES(?,?,?,?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,question.getIdUser());
            stmt.setInt(2,question.getIdLecture());
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stmt.setString(4, "new");
            stmt.setString(5,question.getUsername());
            stmt.setString(6,question.getContent());
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
    
    public static List<QuestionSend> getQuestions()
    {
        List<QuestionSend> questions = new ArrayList<QuestionSend>();
//        Connection conn = Connector.Get();
//        if (conn == null)
//            return questions;
//        try
//        {
//            query = "SELECT username, content FROM StudentSendQuestion";
//            stmt = conn.prepareStatement(query);
//            rs = stmt.executeQuery();
//            while (rs.next())
//            {
//                String username = rs.getString("username");
//                String content = rs.getString("content");
//
//                questions.add(new QuestionSend(username, content));
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            Connector.CloseStmt(stmt);
//            Connector.Close(conn);
//        }     
        return questions;
    }
}
