package com.b07.inventory;

import java.util.HashMap;

public interface InventoryItemMap {

  /**
   * get the item map of this inventory.
   * 
   * @return itemMap the item map of this inventory
   */
  public HashMap<ItemImpl, Integer> getItemMap();

  /**
   * set the item map of this inventory.
   * 
   * @param itemMap the item map of this inventory
   */
  public void setItemMap(HashMap<ItemImpl, Integer> itemMap);

}
