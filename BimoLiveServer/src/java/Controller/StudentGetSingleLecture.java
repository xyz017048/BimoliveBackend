/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.IdModel;
import Model.ReadRequestData;
import Model.StudentGetLectureInfoModel;
import Query.StudentCourseQuery;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Chonghuan
 */
@WebServlet(name = "StudentGetSingleLecture", urlPatterns = {"/student/singlelecture"})
public class StudentGetSingleLecture extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        String requesString = ReadRequestData.getData(request);
        Gson gson= new Gson();
        IdModel idModel = gson.fromJson(requesString, IdModel.class);
        if (idModel == null)
            return;
        
        int idLecture = idModel.getIdLecture();
        
        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        PrintWriter out = response.getWriter();
        try 
        {
            StudentCourseQuery courseQuery = new StudentCourseQuery();
            StudentGetLectureInfoModel lecture = courseQuery.getSingleLecture(idLecture);
            if (lecture != null)
            {
                if(lecture.getTeacherFirstName()!= "")
                    out.write(gson.toJson(lecture));
                else
                    response.setStatus(403);
            }
            else
                response.setStatus(500);
        } 
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally 
        {
            out.close();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
