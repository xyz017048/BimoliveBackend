/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author Chonghuan
 */
public class PasswordCrypto 
{
    private final static String secretString = "y0U6nbhwYLAItcZezaMIQIzQupVI";
    
    public static String getEncrypt(String password)
    {
        String hash = "";
        try 
        {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretString.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKeySpec);
            
            hash = Hex.encodeHexString(sha256_HMAC.doFinal(password.getBytes()));
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return hash;
    } 
}
