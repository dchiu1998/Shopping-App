package com.b07.inventory;

import com.b07.database.DatabaseAndroidHelper;
import android.support.v7.app.AppCompatActivity;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryImpl extends AppCompatActivity implements InventoryItemMap, InventoryUpdateMap, InventoryTotalItems {

  // initialize variables for InventoryImpl
  private static HashMap<ItemImpl, Integer> itemMap;
  private int totalItems;
  private static InventoryImpl inventoryInstance;
  DatabaseAndroidHelper mydb = new DatabaseAndroidHelper(this);

  @Override
  public HashMap<ItemImpl, Integer> getItemMap() {
    return itemMap;
  }

  @Override
  public void setItemMap(HashMap<ItemImpl, Integer> newItemMap) {
    // check item map is not null
    if (newItemMap == null) {
      throw new IllegalArgumentException("newItemMap cannot be null");
    } else {
      itemMap = newItemMap;
    }
  }

  @Override
  public void updateMap(ItemImpl item, Integer quantity) {
    // check item is not null and value is a valid value
    if (item == null) {
      throw new IllegalArgumentException("item cannot be null");
    } else  if (quantity < 0) {
      throw new IllegalArgumentException("quantity cannot be less than 0");
    } else {
      for (Map.Entry<ItemImpl, Integer> entry : itemMap.entrySet()) {
        if (item.getId() == entry.getKey().getId()) {
          int currentQuantity = entry.getValue();
          itemMap.replace(entry.getKey(), currentQuantity + quantity);
        }
      }
    }
  }

  @Override
  public int getTotalItems() {
    return this.totalItems;
  }

  @Override
  public void setTotalItems(int totalItems) {
    // check total is a valid total
    if (totalItems < 0) {
      throw new IllegalArgumentException("total cannot be less than 0");
    } else {
      this.totalItems = totalItems;
    }
  }

  /**
   * Get an instance of the InventoryImpl.
   * 
   * @return the instance of the InventoryImpl.
   * @throws SQLException if something goes wrong with the query.
   */
  public InventoryImpl getInstance() throws SQLException {
    if (inventoryInstance == null) {
      inventoryInstance = new InventoryImpl();
    }
    if (itemMap == null) {
      HashMap<ItemImpl, Integer> newItemMap = new HashMap<ItemImpl,Integer>();
      List<ItemImpl> items = mydb.getAllItemsHelper();
      for (ItemImpl item : items) {
        newItemMap.put(item, mydb.getInventoryQuantityHelper(item.getId()));
      }
      itemMap = newItemMap;
    }
    return inventoryInstance;
  }

}
