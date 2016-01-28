/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Chonghuan
 */
public class ReadRequestData 
{
    public static String getData(HttpServletRequest request) throws ServletException, IOException 
    {
        BufferedReader in  = request.getReader();
        StringBuilder requestBuilder = new StringBuilder();
        String str = in.readLine();
        while (str != null)
        {
            requestBuilder.append(str);
            str = in.readLine();
        }
        return requestBuilder.toString();
    }
}
