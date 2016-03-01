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
public class StudentGetTeacherInfoModel 
{
    private int followTeacher;
    private UserInfo teacherInfo;
    
    public StudentGetTeacherInfoModel()
    {
        followTeacher = 0;
        teacherInfo = new UserInfo();
    }

    public int getFollowTeacher() {
        return followTeacher;
    }

    public void setFollowTeacher(int followTeacher) {
        this.followTeacher = followTeacher;
    }

    public UserInfo getTeacherInfo() {
        return teacherInfo;
    }

    public void setTeacherInfo(UserInfo teacherInfo) {
        this.teacherInfo = teacherInfo;
    }
    
}
