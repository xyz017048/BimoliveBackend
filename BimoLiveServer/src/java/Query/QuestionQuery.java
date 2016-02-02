/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import DBConnector.Connector;
import Model.CheckResult;
import Model.SendQuestionRequestModel;
import Model.GetQuestionResponseModel;
import Model.TeacherQuestionActRequestModel;
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
    private final String NEWSTATUS = "new";
    private final String ANSWERSTRING = "answer";
    private final String SOLVESTRING = "solve";
    
    public CheckResult saveQuestion(SendQuestionRequestModel question)
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
            stmt.setString(4, NEWSTATUS);
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
    
    public CheckResult updateQuestion(TeacherQuestionActRequestModel action)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        String status = action.getStatus();
        int idQuestion = action.getIdQuestion();
        try
        {
            if (status.equals("solve"))
                query = "UPDATE QuestionQueue set status = ?, solveTime = ? where idQuestion = ?";
            else
                query = "UPDATE QuestionQueue set status = ?, changeTime = ? where idQuestion = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1,action.getStatus());
            stmt.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
            stmt.setInt(3,action.getIdQuestion());
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
    
    public List<GetQuestionResponseModel> getQuestions(int roleLevel, int idLecture, int interval)
    {
        List<GetQuestionResponseModel> questions = new ArrayList<GetQuestionResponseModel>();
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        
        try
        {
            if(roleLevel == 1 && interval == 0) //student, all
            {
                query = "SELECT idQuestion, username, content, sendTime, status FROM QuestionQueue WHERE idLecture = ? and (status = ? or status = ?) order by changeTime";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, idLecture);
                stmt.setString(2, ANSWERSTRING);
                stmt.setString(3, SOLVESTRING);
            }
            else if (roleLevel == 2 && interval == 0) //teacher, all
            {
                query = "SELECT idQuestion, username, content, sendTime, status FROM QuestionQueue WHERE idLecture = ? and status = ?";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, idLecture);
                stmt.setString(2, NEWSTATUS);
            }
            
            else if (roleLevel == 1)
            {
                Timestamp time = new Timestamp(System.currentTimeMillis()-interval*1000);
                query = "SELECT idQuestion, username, content, sendTime,status FROM QuestionQueue " +
                        "WHERE idLecture = ? and ((status = ? and changeTime > ? ) or (status = ? and solveTime > ?))"
                        + "order by changeTime";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, idLecture);
                stmt.setString(2,ANSWERSTRING);
                stmt.setTimestamp(3, time);
                stmt.setString(4, SOLVESTRING);
                stmt.setTimestamp(5, time);
            }
            else if (roleLevel == 2)
            {
                Timestamp time = new Timestamp(System.currentTimeMillis()-interval*1000);
                query = "SELECT idQuestion, username, content, sendTime,status FROM QuestionQueue " +
                        "WHERE idLecture = ? and status = ? and sendTime > ?";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, idLecture);
                stmt.setString(2,NEWSTATUS);
                stmt.setTimestamp(3, time);
            }
            rs = stmt.executeQuery();
            while (rs.next())
            {
                GetQuestionResponseModel question = new GetQuestionResponseModel();
                question.setIdQuestion(rs.getInt("idQuestion"));
                question.setUsername(rs.getString("username"));
                question.setSendTime(rs.getString("sendTime").substring(0,19));
                question.setContent(rs.getString("content"));
                question.setStatus(rs.getString("status"));
                questions.add(question);
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
        return questions;
    }
}
