package com.b07.users;

public interface UserInfo {

  /**
   * Get the name of the User.
   * 
   * @return the user's name.
   */
  public String getName();

  /**
   * Set a new name for the User.
   * 
   * @param name the new name.
   */
  public void setName(String name);

  /**
   * Get the age of the User.
   * 
   * @return user's age.
   */
  public int getAge();

  /**
   * Set the age of the User.
   * 
   * @param age user's age.
   */
  public void setAge(int age);

  /**
   * Get the address of the User.
   * 
   * @return user's address.
   */
  public String getAddress();

  /**
   * Set the address of the User.
   * 
   * @param address the user's address.
   */
  public void setAddress(String address);
}
