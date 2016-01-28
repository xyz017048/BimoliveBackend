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
public class CheckResult 
{
    private int result;
    
    public CheckResult()
    {
        this.result = 0;
    }
    
    public CheckResult(int result)
    {
        this.result = result;
    }

    public int getResult()
    {
        return result;
    }

    public void setResult(int result) 
    {
        this.result = result;
    }
}
