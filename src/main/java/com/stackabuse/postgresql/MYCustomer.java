package com.stackabuse.postgresql;

public class MYCustomer {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    // Constructor, getters and setters...

    @Override
    public String toString() {
        return "MYCustomer["
                + "id=" + id
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", email=" + email
                + ']';
    }

	public MYCustomer(Integer id, String firstName, String lastName, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public MYCustomer(String firstName, String lastName, String email) {
		super();
		this.id = null;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	public MYCustomer(String firstName, String lastName) {
		super();
		this.id = null;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = null;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
