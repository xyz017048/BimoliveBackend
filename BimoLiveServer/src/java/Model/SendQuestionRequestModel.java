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
public class SendQuestionRequestModel 
{
    private int idUser;
    private String username;
    private int idLecture;
    private String content;
    
    public SendQuestionRequestModel()
    {
        this.idUser = 0;
        this.idLecture = 0;
        this.username = "";
        this.content = "";
    }

    public String getUsername() 
    {
        return username;
    }

    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getContent() 
    {
        return content;
    }

    public void setContent(String content) 
    {
        this.content = content;
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
