package com.wipro.sales.dao;

import com.wipro.sales.util.*;
import java.sql.*;

import com.wipro.sales.bean.Sales;

public class SalesDao {
	int insertSales(Sales sales) {
		
		//get values to be inserted
		String SalesID = sales.getSalesID();
		java.util.Date SalesDate = sales.getSalesDate();
		String ProductId = sales.getProductID();
		int QuantitySold = sales.getQuantitySold();
		double salesPricePerUnit = sales.getSalesPricePerUnit();
		
		
		//get connection from DBUtill.java
		Connection conn = DBUtill.getDBConnection();
		try {
			Statement st = conn.createStatement();
			String sql = "Insert into TBL_SALES values('"+SalesID+"','"+SalesDate+"','"+ProductId+"','"+QuantitySold+"','"+salesPricePerUnit+"')";
			st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Database Update failed at SalesDao.java");
			e.printStackTrace();
		}
		
		
		return 0;
		
	}
}
