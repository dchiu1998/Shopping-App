package com.b07.inventory;

import java.math.BigDecimal;

public interface ItemPrice {

  /**
   * get the price of this item.
   * 
   * @return price the price of this item
   */
  public BigDecimal getPrice();

  /**
   * set the price of this item.
   * 
   * @param price the price to be set to this item
   */
  public void setPrice(BigDecimal price);
}
