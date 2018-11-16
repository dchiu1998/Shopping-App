package com.b07.users;

import java.sql.SQLException;

public interface UserAuthenticate {

  /**
   * Check if the User is authenticated (i.e password is correct).
   * 
   * @param password the key to logging in
   * @return if password matches
   */
  public boolean authenticate(String password) throws SQLException;

  /**
   * Determine whether or not the User is currently authenticated.
   * 
   * @return if User is authenticated
   */
  public boolean isAuthenticated();

  /**
   * Set whether or not the User is currently authenticated.
   * 
   * @param authenticated if User is authenticated
   */
  public void setAuthenticated(boolean authenticated);
}
