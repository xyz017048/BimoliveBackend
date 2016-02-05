/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.UUID;

/**
 *
 * @author Chonghuan
 */
public class GenerateKey 
{
    private String keyString;
    
    public GenerateKey ()
    {
        this.keyString = "";
    }

    public String getKey() 
    {
        return keyString;
    }

    public void generateKey()
    {
        UUID uuid = UUID.randomUUID();
        this.keyString = uuid.toString();
    }
}