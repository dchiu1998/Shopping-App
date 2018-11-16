package com.b07.users;

import com.b07.database.DatabaseAndroidHelper;
import com.b07.security.PasswordHelpers;
import java.sql.SQLException;
import android.support.v7.app.AppCompatActivity;

public abstract class User extends AppCompatActivity implements UserInfo, UserRoleId, UserId, UserAuthenticate {
  DatabaseAndroidHelper mydb = new DatabaseAndroidHelper(this);
  private int userId;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private boolean authenticated = false;

  /**
   * constructor.
   * 
   * @param builder the builder passed to User.
   */
  public User(UserBuilderImpl builder) {
    this.userId = builder.userId;
    this.name = builder.name;
    this.age = builder.age;
    this.address = builder.address;
  }

  /**
   * get the userId of this user.
   * 
   * @return the id of this user
   */
  @Override
  public int getId() {
    return this.userId;
  }

  /**
   * set the userId of this user.
   * 
   * @param userId the id of this user
   */
  @Override
  public void setId(int userId) {
    // check id is a valid id
    if (userId < 0) {
      throw new IllegalArgumentException("id cannot be less than 0");
    }
    this.userId = userId;
  }

  /**
   * get the name of this user.
   * 
   * @return name the name of this user
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * set the name of this user.
   * 
   * @param name the name of this user
   */
  @Override
  public void setName(String name) {
    // check name is a valid name
    if (name == null || name.trim() == "") {
      throw new IllegalArgumentException();
    }
    this.name = name;
  }

  /**
   * get the age of this user.
   * 
   * @return age the age of this uer
   */
  @Override
  public int getAge() {
    return age;
  }

  /**
   * set the age of this user.
   * 
   * @param age the age of this user
   */
  @Override
  public void setAge(int age) {
    // check age is a valid age
    if (age < 0) {
      throw new IllegalArgumentException();
    } else {
      this.age = age;
    }
  }

  /**
   * get the role id of this user.
   * 
   * @return RoleId the role id of this user
   */
  @Override
  public int getRoleId() {
    return roleId;
  }

  @Override
  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

  public String getAddress() {
    return this.address;
  }

  @Override
  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public boolean isAuthenticated() {
    return this.authenticated;
  }

  public void setAuthenticated(boolean authenticated) {
    this.authenticated = authenticated;
  }

  /**
   * Take in a string password and match it with this user's password, return true of password
   * matches, else return false.
   * 
   * @param password the password to compare with this user's password
   * @return true if password match and return false if password does not match
   * @throws SQLException when something goes wrong
   */
  @Override
  public final boolean authenticate(String password) throws SQLException {
    // check password is a valid password
    if (password == null || password == "") {
      throw new IllegalArgumentException();
    } else {
      // Get the user's set password
      String userPassword = mydb.getPasswordHelper(this.userId);
      // Compare it to the password he/she is currently entering
      if (PasswordHelpers.comparePassword(userPassword, password)) {
        // Set authenticated to be true is passwords match
        this.authenticated = true;
      }
    }
    // Since authenticated is initially set to false, false will be returned if passwords
    // do not match
    return this.authenticated;
  }


  /**
   * check that given inputs are valid inputs.
   * 
   * @param id the id to be checked
   * @param name the name to be checked
   * @param age the age to be checked
   * @param address the address to be checked
   */
  protected void inputChecker(int id, String name, int age, String address) {
    // check for invalid inputs
    boolean nullString = address == null || name == null;
    boolean emptyString = address == "" || name == "";
    boolean negativeInt = id < 0 || age < 0;
    if (nullString || emptyString || negativeInt) {
      throw new IllegalArgumentException();
    }
  }
}
