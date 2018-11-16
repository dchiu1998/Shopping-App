package com.b07.sales;

import java.util.ArrayList;
import java.util.List;

public class SalesLogImpl implements SalesLog {

  // to store list of sales
  List<SaleImpl> sales = new ArrayList<SaleImpl>();
  private static SalesLogImpl salesLogInstance;

  private SalesLogImpl() {}

  @Override
  public void addSale(SaleImpl sale) {
    // check input sale is not null
    if (sale == null) {
      throw new IllegalArgumentException("sale cannot be null");
    }
    this.sales.add(sale);

  }

  @Override
  public List<SaleImpl> getSales() {
    return this.sales;
  }

  /**
   * get an instance of the SalesLog.
   * 
   * @return the instance of SalesLog.
   */
  public static SalesLogImpl getInstance() {
    if (salesLogInstance == null) {
      salesLogInstance = new SalesLogImpl();
    }
    return salesLogInstance;
  }
}
