package com.b07.sales;

import java.util.List;

public interface SalesLog {

  /**
   * adds a sale to the SalesLog.
   * 
   * @param sale the sale to be added.
   */
  public void addSale(SaleImpl sale);

  /**
   * gets a list of all sales.
   * 
   * @return a list of all sales.
   */
  public List<SaleImpl> getSales();
}
