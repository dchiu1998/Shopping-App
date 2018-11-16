package com.b07.sales;

import com.b07.inventory.ItemImpl;
import java.util.HashMap;

public interface SaleItemMap {

  /**
   * get the Sale's ItemMap.
   * 
   * @return the ItemMap of the Sale.
   */
  public HashMap<ItemImpl, Integer> getItemMap();

  /**
   * set the Sale's ItemMap.
   * 
   * @param itemMap the Sale's ItemMap.
   */
  public void setItemMap(HashMap<ItemImpl, Integer> itemMap);
}
