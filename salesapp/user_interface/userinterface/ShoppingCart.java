package com.b07.userinterface;

import com.b07.database.DatabaseAndroidHelper;
import com.b07.inventory.ItemImpl;
import com.b07.users.User;
import java.math.BigDecimal;
import java.security.AccessControlException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import android.support.v7.app.AppCompatActivity;

public class ShoppingCart extends AppCompatActivity {

  private HashMap<ItemImpl, Integer> items;
  private User customer;
  protected BigDecimal total;
  DatabaseAndroidHelper mydb = new DatabaseAndroidHelper(this);

  /**
   * create a new shopping cart with given user.
   * 
   * @param customer the customer with this shopping cart
   */
  public ShoppingCart(User customer) {
    this.items = new HashMap<ItemImpl, Integer>();
    // check that customer is not null
    if (customer == null) {
      throw new IllegalArgumentException("customer cannot be null");
    } else {
      // check customer is logged in
      if (customer.isAuthenticated()) {
        this.customer = customer;
      } else {
        // If they are not authenticated, throw an exception
        throw new AccessControlException("Not currently authenticated.");
      }
    }
  }

  /**
   * add item to this shopping cart.
   * 
   * @param item the item to add to the shopping cart
   * @param quantity the amount of the item to add to the shopping cart
   * @throws SQLException if something goes wrong
   */
  public void addItem(ItemImpl item, int quantity) throws SQLException {
    // check item is in the database
    boolean itemInDatabase = mydb.getItemHelper(item.getId()) != null;
    // check item is not null and quantity greater than zero
    if (item == null || quantity < 0 || itemInDatabase) {
      throw new IllegalArgumentException();
    } else {
      // if item already in shopping cart, update the quantity only
      if (items.containsKey(item)) {
        items.put(item, items.get(item) + quantity);
      } else {
        items.put(item, quantity);
      }
      // update total of this shopping cart
      total.add(item.getPrice().multiply(new BigDecimal(quantity)));
    }
  }

  /**
   * remove item from shopping cart.
   * 
   * @param item the item to be removed
   * @param quantity the amount of the item to be removed
   * @throws NoSuchElementException if item is not in the shopping cart
   */
  public void removeItem(ItemImpl item, int quantity) throws NoSuchElementException {
    // check that item is not null and quantity greater than zero
    if (item == null || quantity < 0) {
      throw new IllegalArgumentException();
    } else {
      // check item is in this shopping cart
      if (items.containsKey(item) != true) {
        throw new NoSuchElementException();
      }
      items.remove(item, quantity);
      // if quantity becomes zero then remove item from items
      if (items.get(item) == 0) {
        items.remove(item);
      }
      // update total of this shopping cart
      total.subtract(item.getPrice().multiply(new BigDecimal(quantity)));
    }
  }

  public boolean isEmpty() {
    return items.isEmpty();
  }

  /**
   * get the items in the shopping cart.
   * 
   * @return the list of items in the shopping cart
   */
  @SuppressWarnings("unchecked")
  public List<ItemImpl> getItems() {
    // create new list to store items
    List<ItemImpl> listOfItems = new ArrayList<ItemImpl>();
    // get the items
    listOfItems = (List<ItemImpl>) items.keySet();
    return listOfItems;
  }

  /**
   * Get the current customer.
   * 
   * @return customer
   */
  public User getCustomer() {
    return this.customer;
  }

  /**
   * Return the amount of a specific amount currently in the cart.
   * 
   * @param item the given item
   * @return amount of given item
   */
  protected int getQuantity(ItemImpl item) {
    // check item is not null
    if (item == null) {
      throw new IllegalArgumentException();
    } else {
      return this.items.get(item);
    }
  }

  /**
   * set the customer for this shopping cart.
   * 
   * @param customer the customer to be associated to this shopping cart
   */
  protected void setCustomer(User customer) {
    this.customer = customer;
  }

  /**
   * Completely clear the current cart.
   */
  public void clearCart() {
    this.items.clear();
  }
}
