package com.b07.users;

import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseAndroidHelper;

import java.sql.SQLException;

public class Customer extends User {

  private int id;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private boolean authenticated;

  public Customer(UserBuilderImpl builder) throws SQLException {
    super(builder);
    DatabaseAndroidHelper mydb = new DatabaseAndroidHelper(this);
    this.roleId = mydb.getRoleIdHelper("customer");
  }

}
