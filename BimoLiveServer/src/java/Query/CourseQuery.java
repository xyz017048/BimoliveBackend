/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import DBConnector.Connector;
import Model.CheckResult;
import Model.CourseModel;
import Model.GetCategoryResponseModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chonghuan
 */
public class CourseQuery 
{
    private  String query = null;
    private  PreparedStatement stmt = null;
    private  ResultSet rs = null;
    
    public List<GetCategoryResponseModel> getCategory()
    {
        List<GetCategoryResponseModel> categoryList = new ArrayList<GetCategoryResponseModel>();
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        try
        {
            query = "SELECT abbreviation, fullName FROM CourseCategory order by abbreviation";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                GetCategoryResponseModel category = new GetCategoryResponseModel(rs.getString("abbreviation"), rs.getString("fullName"));
                categoryList.add(category);
            }  
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }
        return categoryList;
    }
    
    
    public CheckResult addNewCourse(CourseModel course)
    {
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        CheckResult result = new CheckResult();
        try
        {
            query = "INSERT INTO CourseInfo (category,idUser, levelNumber,name, createDate,intro,image,startDate, endDate, endFlag) VALUES(?,?,?,?,?,?,?,?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1,course.getCategory());
            stmt.setInt(2,course.getIdUser());
            stmt.setInt(3, course.getLevelNumber());
            stmt.setString(4, course.getName());
            stmt.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
            stmt.setString(6,course.getIntro());
            stmt.setString(7,course.getImage());
            stmt.setTimestamp(8,course.convertToTimestamp(course.getStartDate()));
            stmt.setTimestamp(9,course.convertToTimestamp(course.getEndDate()));
            stmt.setInt(10,course.getEndFlag());
            stmt.executeUpdate();
            
            result.setResult(1); 
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            Connector.CloseStmt(stmt);
            Connector.Close(conn);
        }
        return result;
    }
}
