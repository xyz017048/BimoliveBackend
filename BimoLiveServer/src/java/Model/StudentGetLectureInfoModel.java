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
    
    public StudentGetLectureInfoModel()
    {
        teacherFirstName = "";
        teacherLastName = "";
        lectureModel = new LectureModel();
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
    
    
}
