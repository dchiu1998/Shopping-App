package com.b07.inventory;

public interface InventoryUpdateMap {

  /**
   * update the map of this inventory.
   * 
   * @param item the item we want to update
   * @param quantity the quantity item we want to update
   */
  public void updateMap(ItemImpl item, Integer quantity);

}
