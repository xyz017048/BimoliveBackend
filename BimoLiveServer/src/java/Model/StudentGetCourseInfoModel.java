/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Chonghuan
 */
public class StudentGetCourseInfoModel 
{
    private String teacherFirstName;
    private String teacherLastName;
    private CourseModel courseInfo;
    private int followCourse;
//    private int followTeacher;
    
    public StudentGetCourseInfoModel()
    {
        this.teacherFirstName = "";
        this.teacherLastName = "";
        this.courseInfo = new CourseModel();
        this.followCourse = 0;
    }

    public String getTeacherFirstName() {
        return teacherFirstName;
    }

    public void setTeacherFirstName(String teacherFirstName) {
        this.teacherFirstName = teacherFirstName;
    }

    public String getTeacherLastName() {
        return teacherLastName;
    }

    public void setTeacherLastName(String teacherLastName) {
        this.teacherLastName = teacherLastName;
    }

    public CourseModel getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseModel courseInfo) {
        this.courseInfo = courseInfo;
    }

    public int getFollowCourse() {
        return followCourse;
    }

    public void setFollowCourse(int followCourse) {
        this.followCourse = followCourse;
    }
    
}
