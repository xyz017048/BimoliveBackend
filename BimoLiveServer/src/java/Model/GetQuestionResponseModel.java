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
public class GetQuestionResponseModel 
{
    private String lectureStatus;
    private int idQuestion;
    private String username;
    private String content;
    private String sendTime;
    private String changeTime;
    private String status;
    
    public GetQuestionResponseModel()
    {
        this.lectureStatus = "";
        this.idQuestion = 0;
        this.username = "";
        this.content = "";
        this.sendTime = "";
        this.status = "";
        this.changeTime= "";
    }

    public int getIdQuestion() 
    {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) 
    {
        this.idQuestion = idQuestion;
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

    public String getSendTime() 
    {
        return sendTime;
    }

    public void setSendTime(String sendTime) 
    {
        this.sendTime = sendTime;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getLectureStatus() {
        return lectureStatus;
    }

    public void setLectureStatus(String lectureStatus) {
        this.lectureStatus = lectureStatus;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }
}
