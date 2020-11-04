package com.wipro.sales.dao;

import com.wipro.sales.bean.Product;
import com.wipro.sales.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockDao {
	
	static Connection conn = DBUtil.getDBConnection();
	static Product p = new Product();
	
	public static void insertStock()
	{
		try {
			PreparedStatement ps = conn.prepareStatement("insert into TBL_STOCK values(?,?,?,?,?)");
			
			ps.setString(1, p.getProductID());
			ps.setString(2, p.getProductName());
			ps.setInt(3, p.getQuantityOnHand());
			ps.setDouble(4, p.getProductUnitPrice());
			ps.setInt(5, p.getReorderLevel());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String generateProductID(String productName) throws SQLException {
		
		PreparedStatement ps;
		
		ps = conn.prepareStatement("SELECT * from SEQ_PRODUCT_ID.nextval from dual");
		ResultSet rs = ps.executeQuery();
		
		if(rs.next())
		{
			return productName.substring(0, 2)+rs.getInt(1);
		}
		else
			return "";
	}
	
	public static int updateStock(String productID, int soldQTY) throws SQLException
	{
		PreparedStatement ps = conn.prepareStatement("SELECT Quantity_On_Hand from TBL_STOCK where PRODUCT_ID = ?");
		ps.setString(1, productID);
		
		ResultSet rs = ps.executeQuery();
		
		int curQTY = 0;
		
		if(rs.next())
		{
			curQTY = rs.getInt(1);
		}
		
		ps = conn.prepareStatement("UPDATE TBL_STOCK set Quantity_On_Hand = ? where PRODUCT_ID = ?");
		ps.setInt(1, (curQTY-soldQTY));
		ps.setString(2, productID);
		
		if(ps.executeUpdate()==1)
			return 0;
		return -1;
	}
	
	public static Product getStock(String productID) throws SQLException {
		
		PreparedStatement ps = conn.prepareStatement("Select * from TBL_STOCK where PRODUCT_ID = ?");
		ps.setString(1, productID);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			Product p = new Product();
			p.setProductID(rs.getString(1));
			p.setProductName(rs.getString(2));
			p.setQuantityOnHand(rs.getInt(3));
			p.setProductUnitPrice(rs.getDouble(4));
			p.setReorderLevel(rs.getInt(5));
		}
		return p;
	}
	
	public static void deleteStock(String productID) throws SQLException {
		
		PreparedStatement ps = conn.prepareStatement("DELETE FROM TBL_STOCK WHERE PRODUCT_ID = ?");
		ps.setString(1, productID);
		ps.executeQuery();
	}
	
}
