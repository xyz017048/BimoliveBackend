/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import DBConnector.Connector;
import Model.GetVideosResponseModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chonghuan
 */
public class VideoQuery 
{
    private  String query = null;
    private  PreparedStatement stmt = null;
    private  ResultSet rs = null;
    
    public List<GetVideosResponseModel> getVideos(String status)
    {
        List<GetVideosResponseModel> liveVideos = new ArrayList<GetVideosResponseModel>();
        Connection conn = Connector.Get();
        if (conn == null)
            return null;
        try
        {
            query = "SELECT firstName, lastName,C.name, L.lectureNum, L.idLecture,L.topic, \n" +
                    "L.intro,L.image, L.url FROM UserBasic U, Lecture L, CourseInfo C \n" +
                    "WHERE L.status = ? and U.idUser = C.idUser and L.idCourse = C.idCourse";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, status);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                GetVideosResponseModel liveVideo = new GetVideosResponseModel();
                liveVideo.setTeacherFirstName(rs.getString("firstName"));
                liveVideo.setTeacherLastName(rs.getString("lastName"));
                liveVideo.setCourseName(rs.getString("name"));
                liveVideo.setLectureNum(rs.getInt("lectureNum"));
                liveVideo.setIdLecture(rs.getInt("idLecture"));
                liveVideo.setTopic(rs.getString("topic"));
                liveVideo.setIntro(rs.getString("intro"));
                liveVideo.setImage(rs.getString("image"));
                liveVideo.setUrl(rs.getString("url"));
                liveVideos.add(liveVideo);
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
        return liveVideos;
    }
}
