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
public class LoginRequestModel 
{
    private String email;
    private String password;
    
    public LoginRequestModel()
    {
        this.email = "";
        this.password ="";
    }
    
    public LoginRequestModel(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void encrypt()
    {
        this.password = PasswordCrypto.getEncrypt(password);
    }
}
