/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Time;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author Chonghuan
 */
public class LectureModel 
{
    private int idLecture;
    private int idCourse;
    private int lectureNum;
    private String topic;
    private String intro;
    private String image;
    private String scheduleDate;
    private String createDate;
    private String startTime;
    private String endTime;
    private String status;       // wait, live, replay, finish
    private String url;
    private String realStart;
    private String realEnd;
    
    
    public LectureModel()
    {
        this.idCourse = 0;
        this.idLecture = 0;
        this.lectureNum = 0;
        this.topic = "";
        this.intro = "";
        this.image = "";
        this.startTime = "";
        this.endTime = "";
        this.scheduleDate = "";
        this.createDate = "";
        this.status = "";
        this.url = "";
        this.realStart = "";
        this.realEnd = "";
    }

    public int getIdLecture() {
        return idLecture;
    }

    public void setIdLecture(int idLecture) {
        this.idLecture = idLecture;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public int getLectureNum() {
        return lectureNum;
    }

    public void setLectureNum(int lectureNumber) {
        this.lectureNum = lectureNumber;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getRealStart() {
        return realStart;
    }

    public void setRealStart(String realStart) {
        this.realStart = realStart;
    }

    public String getRealEnd() {
        return realEnd;
    }

    public void setRealEnd(String realEnd) {
        this.realEnd = realEnd;
    }
    
    public java.sql.Date getSqlDate(String datesString)
    {
        java.sql.Date sqlDate = null;
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = (java.util.Date)dateFormat.parse(datesString);
            sqlDate = new java.sql.Date(parsedDate.getTime());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return sqlDate;
    }
    
    public java.sql.Time getSqlTime(String timeString)
    {
        java.sql.Time sqlTime = null;
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
            sqlTime = new java.sql.Time( dateFormat.parse(timeString).getTime());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return sqlTime;
    }
}
