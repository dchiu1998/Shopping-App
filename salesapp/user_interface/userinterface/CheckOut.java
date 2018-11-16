package com.b07.userinterface;

import android.provider.ContactsContract;

import com.b07.database.DatabaseAndroidHelper;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.inventory.ItemImpl;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import android.support.v7.app.AppCompatActivity;

public class CheckOut extends AppCompatActivity {

  DatabaseAndroidHelper helper = new DatabaseAndroidHelper(this);
  private static final BigDecimal TAXRATE = new BigDecimal(1.13);

  public static BigDecimal getTotal(ShoppingCart shoppingCart) {
    return shoppingCart.total;
  }

  public static BigDecimal getTaxRate() {
    return TAXRATE;
  }

  /**
   * check out the shopping cart.
   * 
   * @param shoppingCart the shopping cart to be checked out
   * @return true if the shopping cart is checked out , false if not
   * @throws SQLException if something goes wrong
   * @throws DatabaseInsertException if something goes wrong
   */
  public boolean checkOut(ShoppingCart shoppingCart) throws SQLException, DatabaseInsertException {
    // check if shopping cart is null
    if (shoppingCart == null) {
      throw new IllegalArgumentException();
    } else {
      // get the items in cart
      List<ItemImpl> itemsInCart = shoppingCart.getItems();
      // get id of customer associated with this shopping cart
      int userId = shoppingCart.getCustomer().getId();
      // validate that the shopping cart has an associated customer
      if (shoppingCart.getCustomer() != null) {
        // check there is enough supply of each item in the cart
        for (int i = 0; i < itemsInCart.size(); i++) {
          ItemImpl itemInCart = itemsInCart.get(i);
          int itemId = itemInCart.getId();
          int quantityWanted = shoppingCart.getQuantity(itemInCart);
          int quantityAvailable = helper.getInventoryQuantityHelper(itemId);
          // return false if there is not enough supply
          if (quantityAvailable < quantityWanted) {
            return false;
          }
        }
        // check out the shopping cart
        BigDecimal totalAfterTax = shoppingCart.total.multiply(TAXRATE);
        // update tables in database
        // submit sale to database
        int saleId = helper.insertSaleHelper(userId, totalAfterTax);
        for (int i = 0; i < itemsInCart.size(); i++) {
          ItemImpl currentItem = itemsInCart.get(0);
          int itemId = currentItem.getId();
          int quantity = shoppingCart.getQuantity(currentItem);
          int quantityInInventory = helper.getInventoryQuantityHelper(itemId);
          int newQuantity = quantityInInventory - quantity;
          // update inventory
          helper.updateInventoryQuantityHelper(newQuantity, itemId);
          // submit itemized sale
          helper.insertItemizedSaleHelper(saleId, itemId, quantity);
        }
        // clear cart
        shoppingCart.clearCart();
        // decalre a variable for storing the account id of the 
        // account associated with this shopping cart
        int accountId = -1;
        // get the list of accounts that belong to this user
        List<Account> accounts = helper.getUserAccountsHelper(userId);
        // find the account associated with this shopping cart
        for (int i = 0; i < accounts.size();i++) {
          // get the current account in the list of accounts
          Account currentAccount = accounts.get(i);
          // get the shopping cart associated with the current account we are looking at
          ShoppingCart currentShoppingCart = currentAccount.getShoppingCart();
          // if this shopping cart matches the current
          // shopping cart then get the current account's id
          if (currentShoppingCart.equals(shoppingCart)) {
            // get the account id of the account associated with this shopping cart
            accountId = currentAccount.getAccountId();
          }
        }
        // update account status to inactive
        helper.updateAccountStatusHelper(accountId, false);
        return true;
      } else {
        return false;
      }
    }
  }
}
