package com.b07.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.NoSuchElementException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.ItemImpl;
import com.b07.sales.SaleImpl;
import com.b07.sales.SalesLog;
import com.b07.sales.SalesLogImpl;
import com.b07.userinterface.Account;
import com.b07.users.User;
import com.b07.users.UserBuilderImpl;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseAndroidHelper extends DatabaseDriverAndroid {

    // constructor
    public DatabaseAndroidHelper (Context context)
    {
        super(context);
    }



    // insert methods

    public int insertRoleHelper(String role) {
        int id = (int)(super.insertRole(role));
        super.close();
        return id;
    }

    public int insertNewUserHelper(String name, int age, String address, String password) {
        int id = (int)(super.insertNewUser(name, age, address, password));
        super.close();
        return id;
    }

    public int insertUserRoleHelper(long userIdInput, int RoleId) {
        int userId = (int)userIdInput;
        int id = (int)(super.insertUserRole(userId, RoleId));
        super.close();
        return id;
    }

    public int insertItemHelper(String name, BigDecimal price) {
        int itemId = (int)(super.insertItem(name,price));
        super.close();
        return itemId;
    }

    public int insertInventoryHelper(int itemId, int quantity) {
        int inventoryId = (int)(super.insertInventory(itemId, quantity));
        super.close();
        return inventoryId;
    }

    public int insertSaleHelper(int userId, BigDecimal totalPrice) {
        int saleId = (int)(super.insertSale(userId, totalPrice));
        super.close();
        return saleId;
    }

    public int insertItemizedSaleHelper(int saleId, int itemId, int quantity) {
        int itemizedSaleId = (int)(super.insertItemizedSale(saleId,itemId,quantity));
        super.close();
        return itemizedSaleId;
    }

    public int insertAccountHelper(int userId, boolean active) {
        int accountId = (int)(super.insertAccount(userId,active));
        super.close();;
        return accountId;
    }

    public int insertAccountLineHelper(int accountId, int itemId, int quantity) {
        int accountLineId = (int)(super.insertAccountLine(accountId,itemId,quantity));
        super.close();
        return accountLineId;
    }

    // select methods
    public List<Integer> getRolesHelper() {
        Cursor cursor = super.getRoles();
        // List initialization
        List<Integer> roleIds = new ArrayList<>();
        // loop through cursor
        while (cursor.moveToNext()) {
            // add role id to list of role ids
            roleIds.add(cursor.getInt(cursor.getColumnIndex("ID")));
        }
        cursor.close();
        super.close();
        return roleIds;
    }

    public String getRoleHelper(int roleId) {
        String roleName = super.getRole(roleId);
        super.close();;
        return roleName;
    }

    public int getUserRoleHelper(int userId) {
        int roleId = super.getUserRole(userId);
        super.close();
        return roleId;
    }

    public int getRoleIdHelper(String roleName) throws SQLException {
        // get ResultSet
        Cursor results = super.getRoles();
        int roleId = -1;
        while (results.moveToNext()) {
            if (getRoleNameHelper(results.getInt(results.getColumnIndex("ID"))).equalsIgnoreCase(roleName)) {
                roleId = results.getInt(results.getColumnIndex("ID"));
            }
        }
        if (roleId == -1) {
            throw new NoSuchElementException("role not found in database");
        }
        results.close();
        super.close();
        return roleId;
    }

    public String getRoleNameHelper(int roleId) throws SQLException {
        // ROLEID validation
        if (roleId < 0) {
            throw new IllegalArgumentException("roleId cannot be less than 0");
        }
        // open connection
        // get role
        String role = super.getRole(roleId);
        super.close();
        return role;
    }

    public List<Integer> getUsersByRoleHelper(int roleId) {
        Cursor cursor = super.getUsersByRole(roleId);
        List<Integer> userIds = new ArrayList<>();
        // loop through cursor
        while (cursor.moveToNext()) {
            // add user id to list of user ids
            userIds.add(cursor.getInt(cursor.getColumnIndex("USERID")));
        }
        cursor.close();;
        super.close();
        return userIds;
    }

    public List<User> getUsersDetailsHelper() {
        Cursor cursor = super.getUsersDetails();
        List<User> users = new ArrayList<>();
        while (cursor.moveToNext()) {
            // get the id of current user
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            // get the name of current user
            String name = cursor.getString(cursor.getColumnIndex("NAME"));
            // get the age of current user
            int age = cursor.getInt(cursor.getColumnIndex("AGE"));
            // get the address of current user
            String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
            int roleId = getUserRole(id);
            String roleName = getRole(roleId);
            try {
                // build user
                User user = new UserBuilderImpl().name(name).age(age).address(address).build(roleName);
                // add user to list of users
                users.add(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        cursor.close();;
        super.close();
        return users;
    }

    public User getUserDetailsHelper(int userId) {
        Cursor cursor = super.getUserDetails(userId);
        String name = cursor.getString(cursor.getColumnIndex("NAME"));
        int age = cursor.getInt(cursor.getColumnIndex("AGE"));
        String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
        int roleId = getUserRole(userId);
        String roleName = getRole(roleId);
        // create a user object
        User user = null;
        try {
            // build user
            user = new UserBuilderImpl().name(name).age(age).address(address).build(roleName);
            user.setId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cursor.close();
        super.close();
        return user;
    }

    public String getPasswordHelper(int userId) {
        String password = super.getPassword(userId);
        return password;
    }

    public List<ItemImpl> getAllItemsHelper() {
        Cursor cursor = super.getAllItems();
        // List initialization
        List<ItemImpl> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            // creating a new item object for each row.
            ItemImpl item = new ItemImpl();
            // set properties of the item.
            item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
            item.setPrice(new BigDecimal(cursor.getString(cursor.getColumnIndex("PRICE"))));
            // add item to the list of items.
            items.add(item);
        }
        cursor.close();
        super.close();
        return items;
    }

    public ItemImpl getItemHelper(int itemId) {
        Cursor cursor = super.getItem(itemId);
        // item initialization
        ItemImpl item = new ItemImpl();
        // set item properties
        item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
        item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
        item.setPrice(new BigDecimal(cursor.getString(cursor.getColumnIndex("PRICE"))));
        cursor.close();
        super.close();
        return item;
    }

    public InventoryImpl getInventoryHelper() {
        Cursor cursor = super.getInventory();
        InventoryImpl inventory = null;
        try {
            // inventory initialization
            InventoryImpl instance = new InventoryImpl();
            inventory = instance.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
            while (cursor.moveToNext()) {
                // create a new item for every row and setting properties
                ItemImpl item = getItemHelper(cursor.getInt(cursor.getColumnIndex("ITEMID")));
                int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndex("QUANTITY")));
                // add created item to inventory
                inventory.updateMap(item, quantity);
            }
            cursor.close();
        super.close();
        return inventory;
    }

    public int getInventoryQuantityHelper(int itemId) {
        int quantity = super.getInventoryQuantity(itemId);
        super.close();
        return quantity;
    }

    public SalesLogImpl getSalesHelper() throws SQLException {
        Cursor cursor = super.getSales();
        // saleLog initialization
        SalesLogImpl salesLog = SalesLogImpl.getInstance();
        while (cursor.moveToNext()) {
            // create a new sale for every row and setting properties
            SaleImpl sale = new SaleImpl();
            sale.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            User user = createUser(cursor.getInt(cursor.getColumnIndex("USERID")));
            sale.setUser(user);
            sale.setTotalPrice(new BigDecimal(cursor.getString(cursor.getColumnIndex("TOTALPRICE"))));
            // if sale is not already in the salesLog
            List<SaleImpl> sales = salesLog.getSales();
            if (!sales.contains(sale)) {
                salesLog.addSale(sale);
            }
        }
        return salesLog;
    }

    public SaleImpl getSaleByIdHelper(int saleId) throws SQLException {
        Cursor cursor = super.getSaleById(saleId);
        // sale initialization
        SaleImpl sale = new SaleImpl();
        // set sale's properties
        sale.setId(cursor.getInt(cursor.getColumnIndex("ID")));
        User user = createUser(cursor.getInt(cursor.getColumnIndex("ID")));
        sale.setUser(user);
        sale.setTotalPrice(new BigDecimal(cursor.getString(cursor.getColumnIndex("TOTALPRICE"))));
        // close connection
        cursor.close();
        super.close();
        return sale;
    }

    public List<SaleImpl> getSalesToUserHelper(int userId) throws SQLException {
        Cursor cursor = super.getSalesToUser(userId);
        // List initialization
        List<SaleImpl> sales = new ArrayList<>();
        while (cursor.moveToNext()) {
            // sale initialization
            SaleImpl sale = new SaleImpl();
            // set sale's properties
            sale.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            User user = createUser(cursor.getInt(cursor.getColumnIndex("ID")));
            sale.setUser(user);
            sale.setTotalPrice(new BigDecimal(cursor.getString(cursor.getColumnIndex("TOTALPRICE"))));
            // add sale to list of sales
            sales.add(sale);
        }
        cursor.close();
        super.close();
        return sales;
    }

    public HashMap<ItemImpl, Integer> getItemizedSalesHelper() {
        Cursor cursor = super.getItemizedSales();
        // HashMap initialization
        HashMap<ItemImpl, Integer> itemizedSales = new HashMap<ItemImpl, Integer>();
        // iterate through each row
        while (cursor.moveToNext()) {
            // get item id
            int itemId = cursor.getInt(cursor.getColumnIndex("ITEMID"));
            // create an item to represent the HashMap key
            ItemImpl item = getItemHelper(itemId);
            // put the item and its quantity into the HashMap
            itemizedSales.put(item, cursor.getInt(cursor.getColumnIndex("QUANTITY")));
        }
        cursor.close();
        super.close();
        return itemizedSales;
    }

    public HashMap<ItemImpl, Integer> getItemizedSaleByIdHelper(int saleId) {
        Cursor cursor = super.getItemizedSaleById(saleId);
        // HashMap initialization
        HashMap<ItemImpl, Integer> itemizedSales = new HashMap<ItemImpl, Integer>();
        // iterate through each row
        while (cursor.moveToNext()) {
            // create an item to represent the HashMap key
            ItemImpl item = getItemHelper(cursor.getInt(cursor.getColumnIndex("ITEMID")));
            // put the item and its quantity into the HashMap
            itemizedSales.put(item, cursor.getInt(cursor.getColumnIndex("QUANTITY")));
        }
        cursor.close();
        super.close();
        return itemizedSales;
    }

    public List<Account> getUserAccountsHelper(int userId) throws SQLException, DatabaseInsertException {
        Cursor cursor = super.getUserAccounts(userId);
        // List<Account> initialization
        List<Account> accounts = new ArrayList<>();
        // iterate through each row
        while (cursor.moveToNext()) {
            Account account = new Account(cursor.getInt(cursor.getColumnIndex("ID")));
            accounts.add(account);
        }
        cursor.close();
        super.close();
        return accounts;
    }

    public Account getAccountDetailsHelper(int accountId) throws SQLException, DatabaseInsertException {
        Cursor cursor = super.getAccountDetails(accountId);
        // creating the Account
        Account account = new Account(cursor.getInt(cursor.getColumnIndex("ID")));
        cursor.close();
        super.close();
        return account;
    }

    public List<Integer> getUserActiveAccountsHelper(int userId) {
        Cursor cursor = super.getUserActiveAccounts(userId);
        // Create a list to hold account ID's of user's accounts
        List<Integer> activeAccountId = new ArrayList<Integer>();
        // Go through every row of the result set
        while (cursor.moveToNext()) {
            activeAccountId.add(cursor.getInt(cursor.getColumnIndex("ID")));
        }
        cursor.close();
        super.close();
        return activeAccountId;
    }

    public List<Integer> getUserInactiveAccountsHelper(int userId) {
        Cursor cursor = super.getUserInactiveAccounts(userId);
        // Create a list to hold account ID's of user's accounts
        List<Integer> InactiveAccountId = new ArrayList<Integer>();
        // Go through every row of the result set
        while (cursor.moveToNext()) {
            InactiveAccountId.add(cursor.getInt(cursor.getColumnIndex("ID")));
        }
        cursor.close();
        super.close();
        return InactiveAccountId;
    }

    // update methods

    public boolean updateRoleNameHelper(String name, int id) {
        boolean updated = super.updateRoleName(name, id);
        super.close();
        return updated;
    }

    public boolean updateUserNameHelper(String name, int id) {
        boolean updated = super.updateUserName(name, id);
        super.close();
        return updated;
    }

    public boolean updateUserAgeHelper(int age, int id) {
        boolean updated = super.updateUserAge(age, id);
        super.close();
        return updated;
    }

    public boolean updateUserAddressHelper(String address, int id) {
        boolean updated = super.updateUserAddress(address, id);
        super.close();
        return updated;
    }

    public boolean updateUserRoleHelper(int roleId, int id) {
        boolean updated = super.updateUserRole(roleId, id);
        super.close();
        return updated;
    }

    public boolean updateItemNameHelper(String name, int id) {
        boolean updated = super.updateItemName(name, id);
        super.close();
        return updated;
    }

    public boolean updateItemPriceHelper(BigDecimal price, int id) {
        boolean updated = super.updateItemPrice(price, id);
        super.close();
        return updated;
    }

    public boolean updateInventoryQuantityHelper(int quantity, int id) {
        boolean updated = super.updateInventoryQuantity(quantity, id);
        super.close();
        return updated;
    }

    public boolean updateAccountStatusHelper(int accountId, boolean active) {
        boolean updated = super.updateAccountStatus(accountId, active);
        super.close();
        return updated;
    }

    public boolean updateUserPasswordHelper(String password, int id) {
        boolean updated = super.updateUserPassword(password, id);
        super.close();
        return updated;
    }
    
        // private methods
    private User createUser(int userId) throws SQLException {
        // get ResultSet
        Cursor cursor = super.getUserDetails(userId);
        // variable declaration
        String name = cursor.getString(cursor.getColumnIndex("NAME"));
        int age = cursor.getInt(cursor.getColumnIndex("NAME"));
        String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
        int roleId = super.getUserRole(userId);
        String roleName = super.getRole(roleId);
        // build user
        User user = new UserBuilderImpl().name(name).age(age).address(address).build(roleName);
        cursor.close();
        super.close();
        return user;
    }
}

