/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import DBConnector.Connector;
import Model.CourseModel;
import Model.LectureModel;
import Model.StudentGetCourseInfoModel;
import Model.StudentGetLectureInfoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chonghuan
 */
public class StudentCourseQuery 
{
    private  String query = null;
    private  PreparedStatement stmt = null;
    private  ResultSet rs = null;
    
    public StudentGetLectureInfoModel getSingleLecture(int idUser, int idLecture)
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
            query = "SELECT * FROM FollowCourse FC, Lecture L where idUser = ? and FC.idCourse = L.idCourse and idLecture=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idUser);
            stmt.setInt(2, idLecture);
            rs = stmt.executeQuery();
            if(rs.next())
                lectureInfoModel.setFollowCourse(1);
            query = "SELECT FT.idUser FROM FollowTeacher FT, Lecture L, CourseInfo C where FT.idUser = ? and FT.idTeacher = C.idUser and C.idCourse = L.idCourse and idLecture=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idUser);
            stmt.setInt(2, idLecture);
            rs = stmt.executeQuery();
            if(rs.next())
                lectureInfoModel.setFollowTeacher(1);
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
    
    public List<StudentGetCourseInfoModel> getFollowedCourses(int idUser)
    {
        List<StudentGetCourseInfoModel> courses = new ArrayList<StudentGetCourseInfoModel>();
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        
        try
        {
            query = "SELECT * FROM CourseInfo C, FollowCourse FC, UserBasic U WHERE FC.idUser = ?"
                    + " and FC.idCourse = C.idCourse and C.idUser = U.idUser";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idUser);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                StudentGetCourseInfoModel courseInfoModel = new StudentGetCourseInfoModel();
                CourseModel course = new CourseModel();
                course.setIdCourse(rs.getInt("idCourse"));
                course.setCategory(rs.getString("category"));
                course.setLevelNumber(rs.getInt("levelNumber"));
                course.setName(rs.getString("name"));
                course.setIntro(rs.getString("intro"));
                course.setImage(rs.getString("image"));
                course.setCreateDate(rs.getString("createDate").substring(0,19));
                course.setStartDate(rs.getString("startDate").substring(0,19));
                course.setEndDate(rs.getString("endDate").substring(0,19));
                course.setEndFlag(rs.getInt("endFlag"));
                courseInfoModel.setCourseModel(course);
                courseInfoModel.setFollowCourse(1);
                courseInfoModel.setTeacherFirstName(rs.getString("firstName"));
                courseInfoModel.setTeacherLastName(rs.getString("lastName"));
                courses.add(courseInfoModel);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            courses = null;
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }
        return courses;
    }
}
