package com.paperpublish.model.DTO;


import com.paperpublish.model.users.Roles;
import com.paperpublish.model.users.User;

public class UserDTO {

    private String token;

    private String name;

    private String email;

    private String username;

    private String password;
    
    private String institution;

    private Roles roles;

    public UserDTO() {
        this.token = null;
    }

    public UserDTO(String token) {
        this.token = token;
    }

    public UserDTO(User user){
        this.name = user.getFullName();
        this.email = user.getEMail();
        this.username = user.getUsername();
        this.institution = user.getInstitution();
        this.roles = user.getRoles();
    }

    public UserDTO(User user, String token){
        this.name = user.getFullName();
        this.email = user.getEMail();
        this.username = user.getUsername();
        this.token = token;
        this.institution = user.getInstitution();
        this.roles = user.getRoles();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }
}
