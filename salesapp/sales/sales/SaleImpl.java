package com.b07.sales;

import android.provider.ContactsContract;

import com.b07.database.DatabaseAndroidHelper;
import com.b07.inventory.ItemImpl;
import com.b07.users.User;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import android.support.v7.app.AppCompatActivity;

public class SaleImpl extends AppCompatActivity implements SaleId, SaleItemMap, SaleTotalPrice, SaleUser {

  DatabaseAndroidHelper helper = new DatabaseAndroidHelper(this);
  private int saleId;
  private User user;
  private BigDecimal totalPrice;
  private HashMap<ItemImpl, Integer> itemMap;

  @Override
  public int getId() {
    return this.saleId;
  }

  @Override
  public void setId(int saleId) {
    // check that the input is non negative
    if (saleId < 0) {
      throw new IllegalArgumentException("sale id cannot be negative");
    }
    this.saleId = saleId;
  }

  @Override
  public User getUser() {
    return this.user;
  }

  @Override
  public void setUser(User user) {
    // check that the user is not null and that it is in the database
    if (user == null) {
      throw new IllegalArgumentException("user cannot be null");
    }
      if (helper.getUserDetailsHelper(user.getId()) == null) {
        throw new IllegalArgumentException("user is not in the database");
      }

    this.user = user;
  }

  @Override
  public BigDecimal getTotalPrice() {
    return this.totalPrice;
  }

  @Override
  public void setTotalPrice(BigDecimal price) {
    // check that the price is non negative
    if (price.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("price must be greater than zero");
    }
    this.totalPrice = price;
  }

  @Override
  public HashMap<ItemImpl, Integer> getItemMap() {
    return this.itemMap;
  }

  @Override
  public void setItemMap(HashMap<ItemImpl, Integer> itemMap) {
    // check that itemMap input is not null
    if (itemMap == null) {
      throw new IllegalArgumentException("item map cannot be null");
    }
    this.itemMap = itemMap;
  }
}
