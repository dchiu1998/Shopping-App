package com.b07.inventory;

import com.b07.database.DatabaseAndroidHelper;
import android.support.v7.app.AppCompatActivity;

import java.math.BigDecimal;

public class ItemImpl extends AppCompatActivity implements ItemId, ItemName, ItemPrice {

  DatabaseAndroidHelper mydb = new DatabaseAndroidHelper(this);
  // initialize variables for ItemImpl
  int id;
  String name;
  BigDecimal price;

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    // check id is a valid id
    if (id < 0) {
      throw new IllegalArgumentException();
    } else {
      this.id = id;
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    // check name is a valid name
    if (name == null || name == "") {
      throw new IllegalArgumentException();
    } else {
      this.name = name;
      // update item name in database
      mydb.updateItemNameHelper(name,this.id);
    }
  }

  @Override
  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public void setPrice(BigDecimal price) {
    // check price is a valid price
    if (price.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException();
    } else {
      this.price = price;
      // update item price in database
      mydb.updateItemPriceHelper(price, this.id);
    }
  }

}
