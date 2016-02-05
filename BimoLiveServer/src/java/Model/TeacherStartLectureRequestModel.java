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
public class TeacherStartLectureRequestModel 
{
    private int idUser;
    private int idLecture;
    
    public TeacherStartLectureRequestModel()
    {
        this.idLecture = 0;
    }
    public TeacherStartLectureRequestModel(int idUser, int idLecture)
    {
        this.idUser = idUser;
        this.idLecture = idLecture;
    }

    public int getIdUser() 
    {
        return idUser;
    }

    public void setIdUser(int idUser) 
    {
        this.idUser = idUser;
    }

    public int getIdLecture() 
    {
        return idLecture;
    }

    public void setIdLecture(int idLecture) 
    {
        this.idLecture = idLecture;
    }
}
