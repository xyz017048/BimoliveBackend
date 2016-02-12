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
    private int idQuestion;

    public GetQuestionRequestModel(int roleLevel, int idLecture, int idQuestion)
    {
        this.roleLevel = roleLevel;
        this.idLecture = idLecture;
        this.idQuestion = idQuestion;
    }

    public int getIdLecture() 
    {
        return idLecture;
    }

    public int getIdQuestion() 
    {
        return idQuestion;
    }

    public int getRoleLevel() 
    {
        return roleLevel;
    }
}
