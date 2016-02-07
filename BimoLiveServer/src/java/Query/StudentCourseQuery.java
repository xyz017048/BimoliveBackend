/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import DBConnector.Connector;
import Model.LectureModel;
import Model.StudentGetLectureInfoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Chonghuan
 */
public class StudentCourseQuery 
{
    private  String query = null;
    private  PreparedStatement stmt = null;
    private  ResultSet rs = null;
    
    public StudentGetLectureInfoModel getSingleLecture(int idLecture)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        StudentGetLectureInfoModel lectureInfoModel = new StudentGetLectureInfoModel();
        try
        {
            query = "SELECT * FROM UserBasic U, Lecture L, CourseInfo C \n" +
                    "WHERE L.idLecture = ? and U.idUser = C.idUser and L.idCourse = C.idCourse";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idLecture);
            rs = stmt.executeQuery();
            if(rs.next())
            {
                LectureModel lecture = new LectureModel();
                lecture.setIdCourse(rs.getInt("idCourse"));
                lecture.setIdLecture(rs.getInt("idLecture"));
                lecture.setLectureNum(rs.getInt("lectureNum"));
                lecture.setTopic(rs.getString("topic"));
                lecture.setIntro(rs.getString("intro"));
                lecture.setImage(rs.getString("image"));
                lecture.setCreateDate(rs.getString("createDate").substring(0,19));
                lecture.setScheduleDate(rs.getString("scheduleDate"));
                lecture.setStartTime(rs.getTime("startTime").toString().substring(0,5));
                lecture.setEndTime(rs.getTime("endTime").toString().substring(0,5));
                lecture.setStatus(rs.getString("status"));
                lecture.setUrl(rs.getString("url"));
                lectureInfoModel.setLectureModel(lecture);
                lectureInfoModel.setTeacherFirstName(rs.getString("firstName"));
                lectureInfoModel.setTeacherLastName(rs.getString("lastName"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            lectureInfoModel = null;
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }
        return lectureInfoModel;
    }
}
