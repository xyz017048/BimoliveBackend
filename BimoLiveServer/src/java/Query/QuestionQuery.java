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
import Model.IdModel;
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
    private final String READSTAUTS = "read";
    private final String BANSTRING = "ban";
    private final String FLAGSTRING = "flag";
    
    public CheckResult saveQuestion(SendQuestionRequestModel question, boolean flag)
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
            if (flag)
                stmt.setString(4, NEWSTATUS);
            else
                stmt.setString(4, FLAGSTRING);
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
    
    public CheckResult checkTeacherHoldThisLecture (SendQuestionRequestModel question)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        try
        {
            query = "SELECT * FROM Lecture L, CourseInfo C \n" +
                    "WHERE L.idLecture = ? and L.idCourse=C.idCourse and C.idUser = ? and L.status = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, question.getIdLecture());
            stmt.setInt(2, question.getIdUser());
            stmt.setString(3, "live");
            rs = stmt.executeQuery();
            if(rs.next())
            {
                result.setResult(1); // the teacher holds this lecture which is live
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
//            if (status.equals("solve"))
//                query = "UPDATE QuestionQueue set status = ?, solveTime = ? where idQuestion = ?";
//            else
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
    
    public List<GetQuestionResponseModel> getQuestions(int roleLevel, int idLecture, int idQuestion)
    {
        List<GetQuestionResponseModel> questions = new ArrayList<GetQuestionResponseModel>();
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        
        try
        {
            if(roleLevel == 1 && idQuestion == -1) //student, all
            {
                query = "SELECT idQuestion, username, content, sendTime, changeTime, Q.status, L.status "
                        + "FROM QuestionQueue Q, Lecture L "
                        + "WHERE L.idLecture = Q.idLecture and L.idLecture = ? and (Q.status = ? or Q.status = ? or Q.status = ?) order by changeTime";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, idLecture);
                stmt.setString(2, ANSWERSTRING);
                stmt.setString(3, BANSTRING);
                stmt.setString(4, FLAGSTRING);
            }
            else if (roleLevel == 2 && idQuestion == -1) //teacher, all
            {
                query = "SELECT idQuestion, username, content, sendTime, changeTime, Q.status, L.status "
                        + "FROM QuestionQueue Q, Lecture L "
                        + "WHERE L.idLecture = Q.idLecture and L.idLecture = ? order by idQuestion";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, idLecture);
            }
            
            else if (roleLevel == 1) //student, idQuestion
            {
                query = "SELECT idQuestion, username, content, sendTime, changeTime, Q.status, L.status\n"
                        + "FROM QuestionQueue Q, Lecture L\n" +
                        "WHERE L.idLecture = Q.idLecture and L.idLecture = ? and (Q.status = ? or Q.status = ? or Q.status = ?) and changeTime > (SELECT changeTime From QuestionQueue where idQuestion = ?)\n"
                        + "order by changeTime";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, idLecture);
                stmt.setString(2,ANSWERSTRING);
                stmt.setString(3,BANSTRING);
                stmt.setString(4, FLAGSTRING);
                stmt.setInt(5,idQuestion);
            }
            else if (roleLevel == 2) // teacher, idQuestion
            {
                query = "SELECT idQuestion, username, content, sendTime,changeTime, Q.status, L.status\n" +
                        "FROM QuestionQueue Q, Lecture L\n" +
                        "WHERE L.idLecture = Q.idLecture and L.idLecture = ? and \n" +
                        "Q.status = ? and idQuestion > ? order by idQuestion";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, idLecture);
                stmt.setString(2,NEWSTATUS);
                stmt.setInt(3, idQuestion);
            }
            else
            {
                return questions;
            }
            rs = stmt.executeQuery();
            int flag = 0;
            while (rs.next())
            {
                flag = 1;
                GetQuestionResponseModel question = new GetQuestionResponseModel();
                question.setLectureStatus(rs.getString("L.status"));
                question.setIdQuestion(rs.getInt("idQuestion"));
                question.setUsername(rs.getString("username"));
                question.setSendTime(rs.getString("sendTime").substring(0,19));
                question.setContent(rs.getString("content"));
                question.setStatus(rs.getString("Q.status"));
                question.setChangeTime(rs.getString("changeTime").substring(0,19));
                questions.add(question);
            }
            if (flag == 0)
            {
                query = "SELECT status FROM Lecture WHERE idLecture = ?";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, idLecture);
                rs = stmt.executeQuery();
                if (rs.next() && "finish".equals(rs.getString("status")))
                {
                    GetQuestionResponseModel question = new GetQuestionResponseModel();
                    question.setLectureStatus("finish");
                    questions.add(question);
                }
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
    
    public List<GetQuestionResponseModel> getMyQuestions(IdModel idModel)
    {
        List<GetQuestionResponseModel> questions = new ArrayList<GetQuestionResponseModel>();
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        
        try
        {
                
            query = "SELECT idQuestion, username, content, sendTime, Q.status, L.status "
                    + "FROM QuestionQueue Q, Lecture L "
                    + "WHERE L.idLecture = Q.idLecture and L.idLecture = ? and Q.idUser = ? order by sendTime";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idModel.getIdLecture());
            stmt.setInt(2, idModel.getIdUser());
         
            rs = stmt.executeQuery();
            while (rs.next())
            {
                GetQuestionResponseModel question = new GetQuestionResponseModel();
                question.setLectureStatus(rs.getString("L.status"));
                question.setIdQuestion(rs.getInt("idQuestion"));
                question.setUsername(rs.getString("username"));
                question.setSendTime(rs.getString("sendTime").substring(0,19));
                question.setContent(rs.getString("content"));
                question.setStatus(rs.getString("Q.status"));
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
