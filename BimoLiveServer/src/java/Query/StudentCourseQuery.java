/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import DBConnector.Connector;
import Model.CheckResult;
import Model.CourseModel;
import Model.IdModel;
import Model.LectureModel;
import Model.SearchRequestModel;
import Model.SearchResponseModel;
import Model.StudentGetCourseInfoModel;
import Model.StudentGetLectureInfoModel;
import Model.StudentGetTeacherInfoModel;
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
public class StudentCourseQuery 
{
    private  String query = null;
    private  PreparedStatement stmt = null;
    private  ResultSet rs = null;
    private final String SEARCHALL = "all";
    private final String SEARCHCOURSE = "course";
    private final String SEARCHLECTURE = "lecture";
    private final String SEARCHTEACHER = "teacher";

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
                LectureModel lecture = getLecture(rs);
                lectureInfoModel.setLectureInfo(lecture);
                lectureInfoModel.setCourseName(rs.getString("name"));
                lectureInfoModel.setIdTeacher(rs.getInt("C.idUser"));
                lectureInfoModel.setTeacherFirstName(rs.getString("firstName"));
                lectureInfoModel.setTeacherLastName(rs.getString("lastName"));
                if (rs.getString("C.permissionCode").equals(""))
                    lectureInfoModel.setPermitStatus(1);
            }
            query = "SELECT * FROM PermissionStatus P, Lecture L where P.idUser = ? and P.idCourse = L.idCourse and idLecture=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idUser);
            stmt.setInt(2, idLecture);
            rs = stmt.executeQuery();
            if(rs.next())
                lectureInfoModel.setPermitStatus(1);
            else if(lectureInfoModel.getPermitStatus() !=1)
                return lectureInfoModel;
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
                CourseModel course = getCourse(rs);
                courseInfoModel.setCourseInfo(course);
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
    
    public List<UserInfo> getFollowedTeachers(int idUser)
    {
        List<UserInfo> teachers = new ArrayList<UserInfo>();
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        
        try
        {
            query = "SELECT * FROM FollowTeacher FT, UserBasic U WHERE FT.idUser = ?"
                    + " and FT.idTeacher = U.idUser";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idUser);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                UserInfo teacher = new UserInfo();
                teacher.setIdUser(rs.getInt("FT.idTeacher"));
                teacher.setEmail(rs.getString("email"));
                teacher.setUsername(rs.getString("username"));
                teacher.setFirstName(rs.getString("firstName"));
                teacher.setLastName(rs.getString("lastName"));
                teacher.setProfile(rs.getString("profile"));
                teacher.setIntroWords(rs.getString("introWords"));
                teacher.setResume(rs.getString("resume"));
                teacher.setCompany(rs.getString("company"));
                teacher.setJobTitle(rs.getString("jobTitle"));
                teachers.add(teacher);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            teachers = null;
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }
        return teachers;
    }
    
    public StudentGetTeacherInfoModel getTeacherInfo(IdModel idModel)
    {
        StudentGetTeacherInfoModel teacherInfoModel = new StudentGetTeacherInfoModel();
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        
        try
        {
            query = "SELECT * FROM UserBasic WHERE idUser = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idModel.getIdTeacher());
            rs = stmt.executeQuery();
            if(rs.next())
            {
                UserInfo teacher = getTeacher(rs);
                teacher.setResult(1);
                teacherInfoModel.setTeacherInfo(teacher);
            }
            query = "SELECT * FROM FollowTeacher WHERE idUser = ? and idTeacher = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idModel.getIdUser());
            stmt.setInt(2, idModel.getIdTeacher());
            rs = stmt.executeQuery();
            if(rs.next())
                teacherInfoModel.setFollowTeacher(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            teacherInfoModel = null;
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }
        return teacherInfoModel;
    }
    
    public StudentGetCourseInfoModel getSingleCourse(int idUser, int idCourse)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        StudentGetCourseInfoModel courseInfoModel = new StudentGetCourseInfoModel();
        try
        {
            query = "SELECT * FROM CourseInfo C, UserBasic U WHERE"
                    + " C.idCourse = ? and C.idUser = U.idUser";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idCourse);
            rs = stmt.executeQuery();
            if(rs.next())
            {
                CourseModel course = getCourse(rs);
                courseInfoModel.setCourseInfo(course);
                courseInfoModel.setTeacherFirstName(rs.getString("firstName"));
                courseInfoModel.setTeacherLastName(rs.getString("lastName"));
            }
            query = "SELECT * FROM FollowCourse FC where idUser = ? and idCourse = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idUser);
            stmt.setInt(2, idCourse);
            rs = stmt.executeQuery();
            if(rs.next())
                courseInfoModel.setFollowCourse(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            courseInfoModel = null;
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }
        return courseInfoModel;
    }
    
    public List<StudentGetCourseInfoModel> getCourses(int idUser)
    {
        List<StudentGetCourseInfoModel> courses = new ArrayList<StudentGetCourseInfoModel>();
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        
        try
        {
            query = "SELECT * FROM CourseInfo C, UserBasic U WHERE C.idUser = U.idUser order by C.createDate desc";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while(rs.next())
            {
                StudentGetCourseInfoModel courseInfoModel = new StudentGetCourseInfoModel();
                CourseModel course = getCourse(rs);
                courseInfoModel.setCourseInfo(course);
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
    
    public CheckResult studentInputPermissionCode (IdModel idModel)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        boolean flag = false;
        try
        {
            query = "SELECT * FROM CourseInfo C, Lecture L WHERE L.idCourse = C.idCourse and L.idLecture = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idModel.getIdLecture());
            rs = stmt.executeQuery();
            if (rs.next())
            {
                if (rs.getString("permissionCode") == "" || rs.getString("permissionCode").equals(idModel.getCode()))
                {
                    flag = true;
                }
            }
            if(!flag)
            {
                return result;
            }
            
            query = "INSERT INTO PermissionStatus (idUser, idCourse,date) VALUES(?, (SELECT idCourse FROM Lecture WHERE idLecture=?),?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,idModel.getIdUser());
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
    
    public SearchResponseModel getSearchResult(SearchRequestModel requestModel)
    {
        SearchResponseModel searchResponseModel = new SearchResponseModel();
        List<StudentGetCourseInfoModel> courses = new ArrayList<StudentGetCourseInfoModel>();
        List<StudentGetLectureInfoModel> lectures = new ArrayList<StudentGetLectureInfoModel>();
        List<UserInfo> teachers = new ArrayList<UserInfo>();
        
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        String type = requestModel.getType();
        String words = requestModel.getWords();
        try
        {
            if (type.equals(SEARCHALL))
            {
                courses = searchByCourse(conn, words);
                lectures =searchByLecture(conn, words);
                teachers = searchByTeacher(conn, words);
            }
            else if (type.equals(SEARCHCOURSE))
                courses = searchByCourse(conn, words);
            else if(type.equals(SEARCHLECTURE))
                lectures = searchByLecture(conn, words);
            else if (type.equals(SEARCHTEACHER))
                teachers = searchByTeacher(conn, words);
            searchResponseModel.setCourses(courses);
            searchResponseModel.setLectures(lectures);
            searchResponseModel.setTeachers(teachers);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            searchResponseModel = null;
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }
        return searchResponseModel;
    }
    
    private List<StudentGetCourseInfoModel> searchByCourse(Connection conn, String words) throws Exception
    {
        List<StudentGetCourseInfoModel> courses = new ArrayList<StudentGetCourseInfoModel>();
        query = "SELECT * FROM CourseInfo C, UserBasic U \n"
                + "WHERE C.idUser = U.idUser and (C.name like ? or C.intro like ? )\n"
                + "order by C.createDate desc";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, "%" +words+"%");
        stmt.setString(2, "%" +words+"%");
        rs = stmt.executeQuery();
        while(rs.next())
        {
            StudentGetCourseInfoModel courseInfoModel = new StudentGetCourseInfoModel();
            CourseModel course = getCourse(rs);
            courseInfoModel.setCourseInfo(course);
            courseInfoModel.setTeacherFirstName(rs.getString("firstName"));
            courseInfoModel.setTeacherLastName(rs.getString("lastName"));
            courses.add(courseInfoModel);
        }
        return courses;
    }
    
    private List<StudentGetLectureInfoModel> searchByLecture(Connection conn, String words) throws Exception
    {
        List<StudentGetLectureInfoModel> lectures = new ArrayList<StudentGetLectureInfoModel>();
        query = "SELECT * FROM UserBasic U, Lecture L, CourseInfo C \n" +
                        "WHERE U.idUser = C.idUser and L.idCourse = C.idCourse and (L.topic like ? or L.intro like ?) \n"
                        + "order by L.createDate desc";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, "%" +words+"%");
        stmt.setString(2, "%" +words+"%");
        rs = stmt.executeQuery();
        while(rs.next())
        {
            StudentGetLectureInfoModel lectureInfoModel = new StudentGetLectureInfoModel();
            LectureModel lecture = getLecture(rs);
            lectureInfoModel.setLectureInfo(lecture);
            lectureInfoModel.setTeacherFirstName(rs.getString("firstName"));
            lectureInfoModel.setTeacherLastName(rs.getString("lastName"));
            lectureInfoModel.setIdTeacher(rs.getInt("C.idUser"));
            lectures.add(lectureInfoModel);
        }
        return lectures;
    }
    
    private List<UserInfo> searchByTeacher(Connection conn, String words) throws Exception
    {
        List<UserInfo> teachers = new ArrayList<UserInfo>();
        query = "SELECT * FROM UserBasic WHERE firstName like ? or lastName like ? or username like ?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, "%" +words+"%");
        stmt.setString(2, "%" +words+"%");
        stmt.setString(3, "%" +words+"%");
        rs = stmt.executeQuery();
        while(rs.next())
        {
            UserInfo teacher = getTeacher(rs);
            teachers.add(teacher);
        }
        return teachers;
    }
    
    private LectureModel getLecture(ResultSet rs) throws Exception
    { 
        LectureModel lecture = new LectureModel();
        lecture.setIdCourse(rs.getInt("L.idCourse"));
        lecture.setIdLecture(rs.getInt("L.idLecture"));
        lecture.setLectureNum(rs.getInt("L.lectureNum"));
        lecture.setTopic(rs.getString("L.topic"));
        lecture.setIntro(rs.getString("L.intro"));
        lecture.setImage(rs.getString("L.image"));
        lecture.setCreateDate(rs.getString("L.createDate").substring(0,19));
        lecture.setScheduleDate(rs.getString("L.scheduleDate"));
        lecture.setStartTime(rs.getTime("L.startTime").toString().substring(0,5));
        lecture.setEndTime(rs.getTime("L.endTime").toString().substring(0,5));
        lecture.setStatus(rs.getString("L.status"));
        lecture.setUrl(rs.getString("L.url"));
        lecture.setRealStart(rs.getString("L.realStart"));
        lecture.setRealEnd(rs.getString("L.realEnd"));
        return lecture;
    }
 
    private CourseModel getCourse(ResultSet rs)throws Exception
    {
        CourseModel course = new CourseModel();
        course.setIdCourse(rs.getInt("idCourse"));
        course.setIdUser(rs.getInt("C.idUser"));
        course.setCategory(rs.getString("category"));
        course.setLevelNumber(rs.getInt("levelNumber"));
        course.setName(rs.getString("name"));
        course.setIntro(rs.getString("intro"));
        course.setImage(rs.getString("image"));
        course.setCreateDate(rs.getString("createDate").substring(0,19));
        course.setStartDate(rs.getString("startDate").substring(0,19));
        course.setEndDate(rs.getString("endDate").substring(0,19));
        course.setEndFlag(rs.getInt("endFlag"));
        return course;
    }
    
    private UserInfo getTeacher(ResultSet rs) throws Exception
    {
        UserInfo teacher = new UserInfo();
        teacher.setIdUser(rs.getInt("idUser"));
        teacher.setEmail(rs.getString("email"));
        teacher.setUsername(rs.getString("username"));
        teacher.setFirstName(rs.getString("firstName"));
        teacher.setLastName(rs.getString("lastName"));
        teacher.setProfile(rs.getString("profile"));
        teacher.setIntroWords(rs.getString("introWords"));
        teacher.setResume(rs.getString("resume"));
        teacher.setCompany(rs.getString("company"));
        teacher.setJobTitle(rs.getString("jobTitle"));
        return teacher;
    }
}
