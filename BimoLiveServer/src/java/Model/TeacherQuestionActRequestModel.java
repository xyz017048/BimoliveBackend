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
public class TeacherQuestionActRequestModel 
{
    private int idQuestion;
    private String status;
    
    public TeacherQuestionActRequestModel(int idQuestion, String status)
    {
        this.idQuestion = idQuestion;
        this.status = status;
    }

    public int getIdQuestion() 
    {
        return idQuestion;
    }

    public String getStatus() 
    {
        return status;
    }
}
