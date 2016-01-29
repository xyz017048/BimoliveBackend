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
public class GetCategoryResponseModel 
{
    private String abbreviation;
    private String fullName;
    
    public GetCategoryResponseModel(String abbreviation, String fullName)
    {
        this.abbreviation = abbreviation;
        this.fullName = fullName;
    }

    public String getAbbreviation() 
    {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) 
    {
        this.abbreviation = abbreviation;
    }

    public String getFullName() 
    {
        return fullName;
    }

    public void setFullName(String fullName) 
    {
        this.fullName = fullName;
    }
}
