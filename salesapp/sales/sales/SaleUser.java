package com.b07.sales;

import com.b07.users.User;

public interface SaleUser {

  /**
   * get the Sale's User.
   * 
   * @return the User of the Sale.
   */
  public User getUser();

  /**
   * set the Sale's User.
   * 
   * @param user the Sale's User.
   */
  public void setUser(User user);
}
