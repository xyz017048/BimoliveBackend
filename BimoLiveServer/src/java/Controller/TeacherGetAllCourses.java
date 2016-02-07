/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CourseModel;
import Model.ReadRequestData;
import Model.IdModel;
import Query.TeacherCourseQuery;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Chonghuan
 */
@WebServlet(name = "TeacherGetAllCourses", urlPatterns = {"/teacher/courses"})
public class TeacherGetAllCourses extends HttpServlet 
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
        //        System.out.println("Request: received");
        String requesString = ReadRequestData.getData(request);
        Gson gson= new Gson();
        IdModel user = gson.fromJson(requesString, IdModel.class);
        if (user == null)
            return;
        
        int idUser = user.getIdUser();
        
        List<CourseModel> courses = new ArrayList<CourseModel>();
        
        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        PrintWriter out = response.getWriter();
        try 
        {
            TeacherCourseQuery courseQuery = new TeacherCourseQuery();
            courses = courseQuery.getCourses(idUser);
            if (courses != null)
            {
                out.write(gson.toJson(courses));
            }
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
