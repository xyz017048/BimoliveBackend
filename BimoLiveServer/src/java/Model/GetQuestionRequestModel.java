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
public class GetQuestionRequestModel 
{
    private int roleLevel;
    private int idLecture;
    private int interval;

    public GetQuestionRequestModel(int roleLevel, int idLecture, int interval)
    {
        this.roleLevel = roleLevel;
        this.idLecture = idLecture;
        this.interval = interval;
    }

    public int getIdLecture() 
    {
        return idLecture;
    }

    public int getInterval() 
    {
        return interval;
    }

    public int getRoleLevel() 
    {
        return roleLevel;
    }
}
