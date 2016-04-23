/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CheckResult;
import Model.ReadRequestData;
import Model.SendQuestionRequestModel;
import Query.QuestionQuery;
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
@WebServlet(name = "TeacherAnswerOwnQuestion", urlPatterns = {"/teacher/questionanswer"})
public class TeacherAnswerOwnQuestion extends HttpServlet 
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
        
        SendQuestionRequestModel question = gson.fromJson(requesString, SendQuestionRequestModel.class);
        if (question == null)
            return;
       
        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        PrintWriter out = response.getWriter();
        try 
        {
            QuestionQuery questionQuery = new QuestionQuery();
            CheckResult result = questionQuery.checkTeacherHoldThisLecture(question);
            if(result == null)
                response.setStatus(500);
            else if (result.getResult() != 1)
            {
                response.setStatus(403);
            }
            else
            {
                result = questionQuery.saveQuestion(question, false);
                if(result == null)
                    response.setStatus(500);
                else 
                    out.write(gson.toJson(result));
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
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}