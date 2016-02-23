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
    private LectureModel lectureModel;
    private int followCourse;
    private int followTeacher;
    
    public StudentGetLectureInfoModel()
    {
        this.teacherFirstName = "";
        this.teacherLastName = "";
        this.lectureModel = new LectureModel();
        this.followCourse = 0;
        this.followTeacher = 0;
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

    public LectureModel getLectureModel() {
        return lectureModel;
    }

    public void setLectureModel(LectureModel lectureModel) {
        this.lectureModel = lectureModel;
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
}
