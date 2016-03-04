/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chonghuan
 */
public class SearchResponseModel 
{
    private List<StudentGetCourseInfoModel> courses;
    private List<StudentGetLectureInfoModel> lectures;
    private List<UserInfo> teachers;
    
    public SearchResponseModel()
    {
        courses = new ArrayList<StudentGetCourseInfoModel>();
        lectures = new ArrayList<StudentGetLectureInfoModel>();
        teachers = new ArrayList<UserInfo>();
    }

    public List<StudentGetCourseInfoModel> getCourses() {
        return courses;
    }

    public void setCourses(List<StudentGetCourseInfoModel> courses) {
        this.courses = courses;
    }

    public List<StudentGetLectureInfoModel> getLectures() {
        return lectures;
    }

    public void setLectures(List<StudentGetLectureInfoModel> lectures) {
        this.lectures = lectures;
    }

    public List<UserInfo> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<UserInfo> teachers) {
        this.teachers = teachers;
    }
}
