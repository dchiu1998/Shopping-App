package com.b07.users;

import java.sql.SQLException;

public interface UserBuilder {
  /*
   * private boolean authenticated = false; private String userType; private String name; private
   * String address; private int age; private int id; private int roleId;
   */

  public UserBuilder name(String name);

  public UserBuilder age(int age);

  public UserBuilder address(String address);

  public UserBuilder authenticated(boolean authenticated);

  public User build(String roleName) throws SQLException;
}
