package com.b07.sales;

import java.math.BigDecimal;

public interface SaleTotalPrice {

  /**
   * get the Sale's TotalPrice.
   * 
   * @return the TotalPrice of the Sale.
   */
  public BigDecimal getTotalPrice();

  /**
   * set the Sale's TotalPrice.
   * 
   * @param price the Sale's TotalPrice.
   */
  public void setTotalPrice(BigDecimal price);
}
