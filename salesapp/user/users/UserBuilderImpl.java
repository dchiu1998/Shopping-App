package com.b07.users;

import java.sql.SQLException;

public class UserBuilderImpl implements UserBuilder {
  protected int userId;
  protected String name;
  protected int age;
  protected int roleId;
  protected String address;
  protected boolean authenticated;

  @Override
  public UserBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public UserBuilder age(int age) {
    this.age = age;
    return this;
  }

  @Override
  public UserBuilder address(String address) {
    this.address = address;
    return this;
  }

  @Override
  public UserBuilder authenticated(boolean authenticated) {
    this.authenticated = authenticated;
    return this;
  }

  @Override
  public User build(String roleName) throws SQLException {
    if (roleName.equalsIgnoreCase("admin")) {
      return new Admin(this);
    } else if (roleName.equalsIgnoreCase("customer")) {
      return new Customer(this);
    } else {
      throw new IllegalArgumentException("role not found");
    }
  }
}
