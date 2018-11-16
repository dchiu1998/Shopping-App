package com.b07.userinterface;

import com.b07.database.DatabaseAndroidHelper;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.users.User;
import java.sql.SQLException;
import android.support.v7.app.AppCompatActivity;

public class Account extends AppCompatActivity {
  private int accountId;
  private int userId;
  private ShoppingCart shoppingCart;
  DatabaseAndroidHelper mydb = new DatabaseAndroidHelper(this);

  // create a new account
  /**
   * constructor.
   * 
   * @param userId the user's userId.
   * @throws DatabaseInsertException if something goes wrong with the insertion.
   * @throws SQLException thrown when something goes wrong with query.
   */
  public Account(int userId) throws DatabaseInsertException, SQLException {
    // check user id is a valid id
    boolean validId = userId > 0;
    // check that the userId is in the database
    boolean isInDatabase = mydb.getUserDetailsHelper(userId) instanceof User;
    if (isInDatabase == false || validId == false) {
      throw new DatabaseInsertException();
    }
    // insert account into database and store the account id
    accountId = mydb.insertAccountHelper(userId, false);
    // associate the user id to this account
    this.userId = userId;
  }

  /**
   * get the account id of this account.
   * 
   * @return the account id of this account
   */
  public int getAccountId() {
    return this.accountId;
  }

  /**
   * get the user id associated with this account.
   * 
   * @return the user id of the user associated with this account
   */
  public int getUserId() {
    return this.userId;
  }

  /**
   * restore the previous shopping cart of the given customer.
   * 
   * @param customer the customer to be assigned their previous shopping cart
   * @throws SQLException if something goes wrong
   */
  public void restoreShoppingCart(User customer) throws SQLException {
    // check that the input is valid and is in the database
    // get the id of the user
    int userId = customer.getId();
    // attempt to find the user in the database
    boolean isInDatabase = mydb.getUserDetailsHelper(userId) instanceof User;
    boolean isNotNull = customer != null;
    if (isInDatabase || isNotNull) {
      throw new IllegalArgumentException();
    } else {
      this.shoppingCart.setCustomer(customer);
    }
  }
  
  /**
   * get the shopping cart associated with this account.
   * @return the shopping cart of this account
   */
  protected ShoppingCart getShoppingCart() {
    return this.shoppingCart;
  }
}
