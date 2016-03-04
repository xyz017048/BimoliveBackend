/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import DBConnector.Connector;
import Model.CheckResult;
import Model.CourseModel;
import Model.CourseCategoryModel;
import Model.LectureModel;
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
public class TeacherCourseQuery 
{
    private  String query = null;
    private  PreparedStatement stmt = null;
    private  ResultSet rs = null;
    private final String WAITSTATUS = "wait";
    private final String LIVESTATUS = "live";
    private final String REPLAYSTATUS = "replay";
    private final String FINISHSTATUS = "finish";
    private final String DEFAULTCOURSEIMAGE= "https://s3-us-west-2.amazonaws.com/bimolive-pictures/course_pics/course_default_pic.png";
    private final String DEFAULTLECTUREIMAGE= "https://s3-us-west-2.amazonaws.com/bimolive-pictures/course_pics/lecture_default_pic.png";
    
    public List<CourseCategoryModel> getCategory()
    {
        List<CourseCategoryModel> categoryList = new ArrayList<CourseCategoryModel>();
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        try
        {
            query = "SELECT abbreviation, fullName FROM CourseCategory order by abbreviation";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                CourseCategoryModel category = new CourseCategoryModel(rs.getString("abbreviation"), rs.getString("fullName"));
                categoryList.add(category);
            }  
        }
        catch (Exception e)
        {
            e.printStackTrace();
            categoryList = null;
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }
        return categoryList;
    }
    
    
    public CheckResult addNewCourse(CourseModel course)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        try
        {
            query = "INSERT INTO CourseInfo (category,idUser, levelNumber,name, createDate,intro,image,startDate, endDate, endFlag, permissionCode) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1,course.getCategory());
            stmt.setInt(2,course.getIdUser());
            stmt.setInt(3, course.getLevelNumber());
            stmt.setString(4, course.getName());
            stmt.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
            stmt.setString(6,course.getIntro());
            if(course.getImage().isEmpty())
                stmt.setString(7, DEFAULTCOURSEIMAGE);
            else
                stmt.setString(7,course.getImage());
            stmt.setTimestamp(8,course.convertToTimestamp(course.getStartDate()));
            stmt.setTimestamp(9,course.convertToTimestamp(course.getEndDate()));
            stmt.setInt(10,course.getEndFlag());
            stmt.setString(11, course.getPermissionCode());
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
    
    
    public CheckResult updateCourse(CourseModel course)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        try
        {
            query = "UPDATE CourseInfo set category=?,levelNumber=?,name=?,intro=?,image=?,startDate=?, endDate=?, endFlag=?, permissionCode=? where idCourse = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1,course.getCategory());
            stmt.setInt(2, course.getLevelNumber());
            stmt.setString(3, course.getName());
            stmt.setString(4,course.getIntro());
            if(course.getImage().isEmpty())
                stmt.setString(5, DEFAULTCOURSEIMAGE);
            else
                stmt.setString(5,course.getImage());
            stmt.setTimestamp(6,course.convertToTimestamp(course.getStartDate()));
            stmt.setTimestamp(7,course.convertToTimestamp(course.getEndDate()));
            stmt.setInt(8,course.getEndFlag());
            stmt.setString(9, course.getPermissionCode());
            stmt.setInt(10, course.getIdCourse());
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
    
    
    public CheckResult addNewLecture(LectureModel lecture)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        try
        {
            query = "INSERT INTO Lecture (idCourse,lectureNum, topic,intro,image, status, url, createDate,scheduleDate, startTime, endTime) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,lecture.getIdCourse());
            stmt.setInt(2,lecture.getLectureNum());
            stmt.setString(3, lecture.getTopic());
            stmt.setString(4, lecture.getIntro());
            if(lecture.getImage().isEmpty())
                stmt.setString(5, DEFAULTLECTUREIMAGE);
            else
                stmt.setString(5,lecture.getImage());
            stmt.setString(6,WAITSTATUS);
            stmt.setString(7,"");
            stmt.setTimestamp(8,new Timestamp(System.currentTimeMillis()));
            stmt.setDate(9,lecture.getSqlDate(lecture.getScheduleDate()));
            stmt.setTime(10,lecture.getSqlTime(lecture.getStartTime()));
            stmt.setTime(11,lecture.getSqlTime(lecture.getEndTime()));
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
    
    public CheckResult updateLecture(LectureModel lecture)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        try
        {
            query = "UPDATE Lecture set topic=?,intro=?,image=?,scheduleDate=?, startTime=?, endTime=? where idLecture=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, lecture.getTopic());
            stmt.setString(2, lecture.getIntro());
            if(lecture.getImage().isEmpty())
                stmt.setString(3, DEFAULTLECTUREIMAGE);
            else
                stmt.setString(3,lecture.getImage());
            stmt.setDate(4,lecture.getSqlDate(lecture.getScheduleDate()));
            stmt.setTime(5,lecture.getSqlTime(lecture.getStartTime()));
            stmt.setTime(6,lecture.getSqlTime(lecture.getEndTime()));
            stmt.setInt(7, lecture.getIdLecture());
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
    
    
    public List<CourseModel> getCourses(int idUser)
    {
        List<CourseModel> courses = new ArrayList<CourseModel>();
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        
        try
        {
            query = "SELECT * FROM CourseInfo WHERE idUser = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idUser);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                CourseModel course = new CourseModel();
                course.setIdCourse(rs.getInt("idCourse"));
                course.setIdUser(rs.getInt("idUser"));
                course.setCategory(rs.getString("category"));
                course.setLevelNumber(rs.getInt("levelNumber"));
                course.setName(rs.getString("name"));
                course.setIntro(rs.getString("intro"));
                course.setImage(rs.getString("image"));
                course.setCreateDate(rs.getString("createDate").substring(0,19));
                course.setStartDate(rs.getString("startDate").substring(0,19));
                course.setEndDate(rs.getString("endDate").substring(0,19));
                course.setEndFlag(rs.getInt("endFlag"));
                course.setPermissionCode(rs.getString("permissionCode"));
                courses.add(course);
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
    
    public CourseModel getSingleCourse(int idUser, int idCourse)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CourseModel course = new CourseModel();
        try
        {
            query = "SELECT * FROM CourseInfo WHERE idCourse = ? and idUser = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idCourse);
            stmt.setInt(2, idUser);
            rs = stmt.executeQuery();
            if(rs.next())
            {
                course.setIdCourse(rs.getInt("idCourse"));
                course.setIdUser(rs.getInt("idUser"));
                course.setCategory(rs.getString("category"));
                course.setLevelNumber(rs.getInt("levelNumber"));
                course.setName(rs.getString("name"));
                course.setIntro(rs.getString("intro"));
                course.setImage(rs.getString("image"));
                course.setCreateDate(rs.getString("createDate").substring(0,19));
                course.setStartDate(rs.getString("startDate").substring(0,19));
                course.setEndDate(rs.getString("endDate").substring(0,19));
                course.setEndFlag(rs.getInt("endFlag"));
                course.setPermissionCode(rs.getString("permissionCode"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            course = null;
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }
        return course;
    }
    
    
    public List<LectureModel> getLectures(int idCourse)
    {
        List<LectureModel> lectures = new ArrayList<LectureModel>();
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        
        try
        {
            query = "SELECT * FROM Lecture WHERE idCourse = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idCourse);
            rs = stmt.executeQuery();
            while(rs.next())
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
                lectures.add(lecture);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            lectures = null;
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }
        return lectures;
    }
    
    public LectureModel getSingleLecture(int idUser, int idLecture)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        LectureModel lecture = new LectureModel();
        try
        {
            query = "SELECT * FROM Lecture L, CourseInfo C \n" +
                    "WHERE L.idLecture = ? and L.idCourse=C.idCourse and C.idUser = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idLecture);
            stmt.setInt(2, idUser);
            rs = stmt.executeQuery();
            if(rs.next())
            {
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
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            lecture = null;
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }
        return lecture;
    }
    
    
    public CheckResult startLecture(int idUser, int idLecture)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        String key = "";
        try
        {
            // get the key
            query = "SELECT keyString FROM UserBasic WHERE idUser = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idUser);
            rs = stmt.executeQuery();
            if (rs.next() )
            {
                if (rs.getString("keyString").isEmpty())
                    key = Integer.toString(idUser);
                else
                    key = rs.getString("keyString");
            }
            else
            {
                result.setResult(2); // user doesnot exit.
                return result;
            }
            query = "SELECT * FROM Lecture L, CourseInfo C \n" +
                    "WHERE L.idLecture = ? and L.idCourse=C.idCourse and C.idUser = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idLecture);
            stmt.setInt(2, idUser);
            rs = stmt.executeQuery();
            if(!rs.next())
            {
                result.setResult(3); // the teacher does not hold this lecture
                return result;
            }
            query = "UPDATE Lecture set status = ?, url = ?, realStart = ? WHERE idLecture = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, LIVESTATUS);
            stmt.setString(2, key);
            stmt.setTime(3,new java.sql.Time((new java.util.Date()).getTime()));
            stmt.setInt(4,idLecture);
            stmt.executeUpdate();
            
            result.setResult(1); 
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = null;
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }
        return result;
    }
    
    public CheckResult endLecture(int idUser, int idLecture)
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
            stmt.setInt(1, idLecture);
            stmt.setInt(2, idUser);
            stmt.setString(3, LIVESTATUS);
            rs = stmt.executeQuery();
            if(!rs.next())
            {
                result.setResult(2); // the teacher does not hold this live lecture
                return result;
            }
            query = "UPDATE Lecture set status = ?, realEnd = ? WHERE idLecture = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, FINISHSTATUS);
            stmt.setTime(2,new java.sql.Time((new java.util.Date()).getTime()));
            stmt.setInt(3,idLecture);
            stmt.executeUpdate();
            
            result.setResult(1); 
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = null;
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }
        return result;
    }
}
