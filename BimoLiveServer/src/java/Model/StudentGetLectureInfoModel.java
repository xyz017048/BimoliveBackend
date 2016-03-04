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
public class StudentGetLectureInfoModel 
{
    private String teacherFirstName;
    private String teacherLastName;
    private int idTeacher;
    private LectureModel lectureInfo;
    private int followCourse;
    private int followTeacher;
    private int permitStatus;
    
    public StudentGetLectureInfoModel()
    {
        this.teacherFirstName = "";
        this.teacherLastName = "";
        this.idTeacher = 0;
        this.lectureInfo = new LectureModel();
        this.followCourse = 0;
        this.followTeacher = 0;
        this.permitStatus = 0;
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

    public int getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(int idTeacher) {
        this.idTeacher = idTeacher;
    }

    public LectureModel getLectureInfo() {
        return lectureInfo;
    }

    public void setLectureInfo(LectureModel lectureInfo) {
        this.lectureInfo = lectureInfo;
    }

    public int getFollowCourse() {
        return followCourse;
    }

    public void setFollowCourse(int followCourse) {
        this.followCourse = followCourse;
    }

    public int getFollowTeacher() {
        return followTeacher;
    }

    public void setFollowTeacher(int followTeacher) {
        this.followTeacher = followTeacher;
    }

    public int getPermitStatus() {
        return permitStatus;
    }

    public void setPermitStatus(int permitStatus) {
        this.permitStatus = permitStatus;
    }
}
