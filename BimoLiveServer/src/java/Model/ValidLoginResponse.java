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
public class ValidLoginResponse 
{
    private int result;
    private int idUser;
    private String email;
    private String username;
    private int roleLevel;
    private String firstName;
    private String lastName;
    private String lastLogin;
    private String profile;
    private String introWords;
    private String regisDate;
    
    public ValidLoginResponse()
    {
        result = 0;
        idUser = 0; 
        email = "";
        username = "";
        roleLevel = 0;
        firstName = "";
        lastName = "";
        lastLogin = "";
        profile = "";
        introWords = "";
        regisDate = "";
    }

    public int getResult() 
    {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(int roleLevel) {
        this.roleLevel = roleLevel;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getIntroWords() {
        return introWords;
    }

    public void setIntroWords(String introWords) {
        this.introWords = introWords;
    }

    public String getRegisDate() {
        return regisDate;
    }

    public void setRegisDate(String regisDate) {
        this.regisDate = regisDate;
    }
}
