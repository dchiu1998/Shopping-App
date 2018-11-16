package com.b07.userinterface;

import android.support.v7.app.AppCompatActivity;

import com.b07.database.DatabaseAndroidHelper;
import com.b07.exceptions.CreateUserFailedException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.ItemImpl;
import com.b07.inventory.ItemTypes;
import com.b07.sales.SaleImpl;
import com.b07.sales.SalesLogImpl;
import com.b07.users.Admin;
import com.b07.users.User;
import java.math.BigDecimal;
import java.security.AccessControlException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminInterface extends AppCompatActivity{

  DatabaseAndroidHelper mydb = new DatabaseAndroidHelper(this);
  private User currentAdmin;
  private InventoryImpl inventory;

  /**
   * create a new administrator interface with administrator and inventory input.
   * 
   * @param admin the administrator to be added to the interface
   * @param inventory the inventory to be added to the interface
   * @throws SQLException if something goes wrong
   * @throws AccessControlException if user is not authenticated
   */
  public AdminInterface(User admin, InventoryImpl inventory)
      throws SQLException, AccessControlException {
    // check inputs are not null
    if (admin == null || inventory == null) {
      throw new IllegalArgumentException("inputs cannot be null");
    }
    // If user is not authenticated, throw an exception
    if (!admin.isAuthenticated()) {
      throw new AccessControlException("Not currently authenticated.");
    }
    this.currentAdmin = admin;
    this.inventory = inventory;
  }

  /**
   * create a new admin interface with inventory input.
   * 
   * @param inventory the inventory to be added to the interface
   */
  public AdminInterface(InventoryImpl inventory) {
    // check inventory is not null
    if (inventory == null) {
      throw new IllegalArgumentException("inventory cannot be null");
    }
    this.inventory = inventory;
  }

  /**
   * set the current admin of this interface.
   * 
   * @param admin the admin to be set as current admin
   * @throws SQLException if something goes wrong with the query.
   */
  public void setCurrentAdmin(Admin admin) throws SQLException {
    // check employee is not null
    if (admin == null) {
      throw new IllegalArgumentException("admin cannot be null");
    } else {
      // check if employee is authenticated
      // get password of employee
      if (admin.isAuthenticated()) {
        this.currentAdmin = admin;
      } else {
        // Throw an exception saying Admin is not authenticated
        throw new AccessControlException("Not currently authenticated.");
      }
    }
  }

  /**
   * check if this administrator interface has an administrator.
   * 
   * @return true if there is a current employee , false if not
   */
  public boolean hasCurrentAdmin() {
    return this.currentAdmin != null;
  }

  /**
   * Re-stocks a quantity of an item.
   * 
   * @param item the item to be re-stocked
   * @param quantity the amount of the item to be re-stocked
   * @throws SQLException if something goes wrong with the query.
   */
  public void restockInventory(ItemImpl item, int quantity) throws SQLException {
    // check item is not null and quantity is not zero or negative
    if (quantity <= 0) {
      throw new IllegalArgumentException("quantity cannot be less than 1");
    } else if (item == null) {
      throw new IllegalArgumentException("item cannot be null");
    } else {
      inventory.updateMap(item, quantity);
      mydb.updateInventoryQuantityHelper(item.getId(), quantity);
    }
  }

  /**
   * Updates the price of an item.
   * 
   * @param itemId the item whose price will be updated.
   * @param price the new price.
   */
  public void updatePrice(int itemId, BigDecimal price) {
    if (itemId <= 0) {
      throw new IllegalArgumentException("itemId cannot be less than 0");
    } else if (price.compareTo(BigDecimal.ZERO) == -1) {
      throw new IllegalArgumentException("price cannot be less than 0");
    }
    mydb.updateItemPriceHelper(price, itemId);
  }

  /**
   * Get a shopping cart using the customer's ID. This method alters the current Admin's ID in order
   * to create a cart for the customer's sake.
   * 
   * @param customer the customer
   * @param id customer's ID
   * @param name customer's name
   * @param address customer's address
   * @param age customer's age
   * @return the new cart
   */
  public ShoppingCart getCustomerCart(User customer, int id, String name, String address, int age) {
    // Parameter checks
    if (customer == null) {
      throw new IllegalArgumentException("customer cannot be null");
    } else if (id < 0) {
      throw new IllegalArgumentException("id cannot be less than 0");
    } else if (name.trim() == "") {
      throw new IllegalArgumentException("name cannot be blank");
    } else if (address.trim() == "") {
      throw new IllegalArgumentException("address cannot be blank");
    } else if (age < 0) {
      throw new IllegalArgumentException("age cannot be less than 0");
    }
    // Create the customer's cart
    ShoppingCart cart = createCustomerCart(customer, id, name, address, age);
    return cart;
  }

  /**
   * Create a ShoppingCart for the customer, checked out by the Admin.
   * 
   * @param customer the customer
   * @param id customer's id
   * @param name customer's name
   * @param address customer's address
   * @param age customer's age
   * @return a cart with customer's ID and Admin's control
   */
  private ShoppingCart createCustomerCart(User customer, int id, String name, String address,
      int age) {
    // Parameter checks
    if (customer == null) {
      throw new IllegalArgumentException("customer cannot be null");
    } else if (id < 0) {
      throw new IllegalArgumentException("id cannot be less than 0");
    } else if (name.trim() == "") {
      throw new IllegalArgumentException("name cannot be blank");
    } else if (address.trim() == "") {
      throw new IllegalArgumentException("address cannot be blank");
    } else if (age < 0) {
      throw new IllegalArgumentException("age cannot be less than 0");
    }
    if (currentAdmin.isAuthenticated()) {
      // Get the customers' info
      int realId = customer.getId();
      // Check that the id is matching
      if (id != realId) {
        throw new IllegalArgumentException("The ID is incorrect");
      }
      // Get customer's other info
      String realName = customer.getName();
      String realAddress = customer.getAddress();
      int realAge = customer.getAge();
      // Check if the other info matches
      boolean nameMatch = name == realName;
      boolean addressMatch = address == realAddress;
      boolean ageMatch = age == realAge;
      // Check if at least 2 of 3 of the info is correct
      if ((nameMatch && addressMatch) || (nameMatch && ageMatch) || (addressMatch && ageMatch)) {
        // Set Admin's current ID to the user's temporarily to make purchases
        currentAdmin.setId(realId);
        ShoppingCart customerCart = new ShoppingCart(currentAdmin);
        return customerCart;
      } else {
        throw new IllegalArgumentException("Not enough validation submitted.");
      }

    } else {
      throw new AccessControlException("Not currently authenticated.");
    }
  }

  /**
   * Create a new account for a customer.
   * 
   * @param name customer's name
   * @param age customer's age
   * @param address customer's address
   * @param password customer's password
   * @return new account id
   * @throws DatabaseInsertException if something goes wrong
   * @throws SQLException if something goes wrong
   * @throws CreateUserFailedException if something goes wrong
   */
  public int createUserAccount(String name, int age, String address, String password)
      throws DatabaseInsertException, SQLException, CreateUserFailedException {
    // Check if the parameters are valid
    if (name.trim() == "") {
      throw new IllegalArgumentException("Name cannot be empty.");
    }
    if (age < 0) {
      throw new IllegalArgumentException("Invalid age input.");
    }
    if (address.trim() == "") {
      throw new IllegalArgumentException("No address has been given.");
    }
    if (password == "") {
      throw new IllegalArgumentException("No password has been given.");
    }
    // Create the new user account
    int userId;
    userId = this.createCustomer(name, age, address, password);
    // Insert a new account into the database under the user and get the account id
    int accountId;
    accountId = mydb.insertAccountHelper(userId, false);
    // Return the account id
    return accountId;
  }

  /**
   * create a customer for this administrator interface.
   * 
   * @param name the name of the customer
   * @param age the age of the customer
   * @param address the address of the customer
   * @param password the password of the customer
   * @return the id of the customer
   * @throws CreateUserFailedException if fail to create new customer
   * @throws SQLException if something goes wrong
   * @throws DatabaseInsertException if unable to insert new user to database
   */
  public int createCustomer(String name, int age, String address, String password)
      throws CreateUserFailedException, SQLException, DatabaseInsertException {
    // Create the new user account and get the new id
    int userId;
    userId = mydb.insertNewUserHelper(name, age, address, password);
    // Get the role id of the 'customer' role
    int roleId;
    roleId = mydb.getRoleIdHelper("customer");
    // Insert the role of the newly made user to 'customer'
    mydb.insertUserRoleHelper(userId, roleId);
    // Check if the role was updated
    boolean roleUpdated;
    roleUpdated = mydb.updateUserRoleHelper(roleId, userId);
    // Return the customers' new ID if successful
    if (roleUpdated) {
      return userId;
    } else {
      // if customer cannot be created, raise an exception
      throw new CreateUserFailedException();
    }
  }
  
  /**
   * Create a new Admin.
   * @param name admin's name
   * @param age admin's age
   * @param address admin's address
   * @param password admin's password
   * @return admin's UserRoleId
   * @throws DatabaseInsertException if something goes wrong
   * @throws SQLException if something goes wrong
   */
  public int createAdmin(String name, int age, String address, String password)
      throws DatabaseInsertException, SQLException {
    // Check if the parameters are valid
    if (name.trim() == "") {
      throw new IllegalArgumentException("Name cannot be empty.");
    }
    if (age < 0) {
      throw new IllegalArgumentException("Invalid age input.");
    }
    if (address.trim() == "") {
      throw new IllegalArgumentException("No address has been given.");
    }
    if (password  == "") {
      throw new IllegalArgumentException("No password has been given.");
    }
    // Create a new Admin instance
    int userId;
    userId = mydb.insertNewUserHelper(name, age, address, password);
    // Get the role id of the 'Admin' role
    int roleId;
    roleId = mydb.getRoleIdHelper("admin");
    mydb.insertUserRoleHelper(userId, roleId);
    // Insert the role into the database
    mydb.insertUserRoleHelper(userId, roleId);
    return userId;
  }

  /**
   * allow the administrator to view historic sales records, listing the details of each individual
   * sale, and then the total sales amount for all sales as well as the total quantity of each type
   * of item sold.
   * 
   * @throws DatabaseInsertException if unable to insert into database
   * 
   */
  public void viewBooks() throws DatabaseInsertException {
    try {
      // initialize variables
      SalesLogImpl salesLog = mydb.getSalesHelper().getInstance();
      List<SaleImpl> salesList = salesLog.getSales();
      SaleImpl currentSale = new SaleImpl();
      BigDecimal totalSalesPrice = new BigDecimal(0);
      String itemTypeAsString = "";
      String currentItemName = "";
      HashMap<ItemImpl, Integer> currentItemMap = new HashMap<ItemImpl, Integer>();
      // initialize item map that will contain total quantities sold of all items
      HashMap<String, Integer> itemQuantityMap = new HashMap<String, Integer>();
      for (ItemTypes itemType : ItemTypes.values()) {
        itemTypeAsString = itemType.toString();
        itemQuantityMap.put(itemTypeAsString, 0);
      }
      // For every sale in the log of all sales,
      for (int i = 0; i < salesList.size(); i++) {
        currentSale = salesList.get(i);
        // print the customer's name, the sale ID and total price of the sale
        System.out.println("Customer: " + currentSale.getUser().getName());
        System.out.println("Purchase Number: " + currentSale.getId());
        System.out.println("Total Purchase Price: " + currentSale.getTotalPrice());
        // add the price to the total for all sales
        totalSalesPrice = totalSalesPrice.add(currentSale.getTotalPrice());
        // print the itemized breakdown
        System.out.println("Itemized Breakdown: ");
        for (Map.Entry<ItemImpl, Integer> entry : currentItemMap.entrySet()) {
          ItemImpl currentItem = entry.getKey();
          int currentQuantity = entry.getValue();
          // For each item in the current sale,
          // print the quantity of that item sold within the individual sale and update the total
          // quantity sold for that item.
          currentItemName = currentItem.getName();
          System.out.print(currentItemName + ": " + currentQuantity);
          itemQuantityMap.put(currentItemName, itemQuantityMap.get(currentItem) + currentQuantity);
          System.out.println("                    ");
        }
        System.out.println("----------------------------------------");
      }
      // print the total quantities sold of all items, and the total price of all of these.
      for (Map.Entry<String, Integer> entry : itemQuantityMap.entrySet()) {
        String itemName = entry.getKey();
        int quantity = entry.getValue();
        System.out.println("Number " + itemName + " sold: " + quantity);
      }
      System.out.println("Total Sales: " + totalSalesPrice);
    } catch (SQLException e) {
      System.out.println("There was a problem retrieving the sales log from the database.");
      e.printStackTrace();
    }
  }
}
