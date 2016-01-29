/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Chonghuan
 */
public class CourseModel 
{
    private int idCourse;
    private int idUser;
    private String category;
    private int levelNumber;
    private String name;
    private String intro;
    private String image;
    private String createDate;
    private String startDate;
    private String endDate;
    private int endFlag;
    
    public CourseModel ()
    {
        this.category = "";
        this.createDate = "";
        this.endDate = "";
        this.endFlag = 0;
        this.idCourse = 0;
        this.idUser = 0;
        this.image = "";
        this.intro = "";
        this.levelNumber = 0;
        this.name = "";
        this.startDate = "";
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getEndFlag() {
        return endFlag;
    }

    public void setEndFlag(int endFlag) {
        this.endFlag = endFlag;
    }
    
    public Timestamp convertToTimestamp(String date)
    {
        Timestamp timestamp;
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(date);
            timestamp = new Timestamp(parsedDate.getTime());
            return timestamp;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
