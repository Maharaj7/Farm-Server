package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;


import model.Basket;
import model.Crop;
import model.Customer;
import model.Farmer;

public class SQLCommands {
	 Connection connection = null;
	 
	 public SQLCommands(){
		 connection = SqlConnection.dbConnector();
	 }
	 //private static org.apache.log4j.Logger logger = org.apache.log4j.LogManager.getLogger(SQLCommands.class);
	 
	 //adds
	 public boolean addCustomer(Customer customer)
	 {
		String query  = "insert into customer (fName,lName,email,password,image,fund) values (?,?,?,?,?,?)";
		String query2 = "insert into login (email,password) values (?,?)";
	    try{
	    	//logger.debug(query);
	    	//logger.debug(query2);
		PreparedStatement pst = connection.prepareStatement(query);
		PreparedStatement pst2 = connection.prepareStatement(query2);
		pst.setString(1, customer.getfName());
		pst.setString(2, customer.getlName());
		pst.setString(3, customer.getEmail());
		pst.setString(4, customer.getPassword());
		pst.setString(5, customer.getphotoPath());
		pst.setFloat(6, customer.getFunds());
		
		pst.execute();
		pst2.setString(1, customer.getEmail());
		pst2.setString(2, customer.getPassword());
		pst2.execute();
		//logger.debug("Data inserted into customer table");
		return true;
		
	    }
	    catch(SQLException e){
	    	//logger.error("Failed to insert data into customer table");
	    }
		 
		 return false;
	 }
	 
	 public boolean updateCustomer(Customer cust, String email)
	 {
		 try{
			 String query = "Update customer set fund = '"+cust.getFunds()+"' where email = '"+email+"'" ;
			 PreparedStatement pst = connection.prepareStatement(query);
			 pst.execute();
				return true;
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1);
				}
		 return false;
	 }
	 
	 
	 
	 public boolean addFarmer(Farmer farmer)
	 {
		String query  = "insert into farmer (fName,lName,email,address,password,image,earning) values (?,?,?,?,?,?,?)";
		String query2 = "insert into farmerlogin (email,password) values (?,?)";
	    try{
		PreparedStatement pst = connection.prepareStatement(query);
		PreparedStatement pst2 = connection.prepareStatement(query2);
		pst.setString(1, farmer.getfName());
		pst.setString(2, farmer.getlName());
		pst.setString(3, farmer.getEmail());
		pst.setString(4, farmer.getAddress());
		pst.setString(5, farmer.getPassword());
		pst.setString(6, farmer.getphotoPath());
		pst.setFloat(7, farmer.getEarnings());
		pst.execute();
		pst2.setString(1, farmer.getEmail());
		pst2.setString(2, farmer.getPassword());
		pst2.execute();
		
		return true;
	    }
	    catch(SQLException e){
	    	e.printStackTrace();
	    }
		 
		 return false;
	 }
	 
	 public void updateCrops(String name,String email)
	 {
		 
		 removeCrop(name, email);
	 }
	 
	 public boolean addCrop(Crop crop)
	 {
		String query  = "insert into crop (email,image,name,weight,cost,available,quantity) values (?,?,?,?,?,?,?)";
	    try{
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, crop.getEmail());
		pst.setString(2, crop.getimagePath());//write bytes to a file, generate random filename save as jpeg, save in the folder in server project 
		pst.setString(3, crop.getName());// database set from blob to string to read path
		pst.setFloat(4, crop.getWeight());
		pst.setFloat(5, crop.getCostPerUnit());
		pst.setString(6, crop.getAvailable());
		pst.setInt(7, crop.getQuantity());
		pst.execute();
		
		return true;
	    }
	    catch(SQLException e){
	    	e.printStackTrace();
	    }
		 finally{
			 try{
				 connection.close();
			 }catch(Exception a)
			 {
				 a.printStackTrace();
			 }
		 }
		 return false;
	 }
	 
	 public boolean addCustomerPurchase(Basket basket)
	 {
		String query  = "insert into farmercustomers (farmerEmail,customerEmail,fund,customerName,location) values (?,?,?,?,?)";
	    try{
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, basket.getFarmerEmail());
		pst.setString(2, basket.getCustomerEmail());
		pst.setFloat(3, basket.getCost());
		pst.setString(4, basket.getcName());
		pst.setString(5, basket.getLocation());
		pst.execute();
		return true;
	    }
	    catch(SQLException e){
	    	e.printStackTrace();
	    }
		 finally{
			 try{
				 connection.close();
			 }catch(Exception a)
			 {
				 a.printStackTrace();
			 }
		 }
		 return false;
	 }
	 
	 
	 public boolean addToBasket(Basket crop)
	 {
		String query  = "insert into basket (farmerEmail,customerEmail,itemName,quantity,cost,weight) values (?,?,?,?,?,?)";
	    try{
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, crop.getFarmerEmail());
		pst.setString(2, crop.getCustomerEmail());
		pst.setString(3, crop.getItemName());
		pst.setInt(4, crop.getQuantity());
		pst.setFloat(5, crop.getCost());
		pst.setFloat(6, crop.getWeight());
		pst.execute();
		
		return true;
	    }
	    catch(SQLException e){
	    	e.printStackTrace();
	    }
		 finally{
			 try{
				 connection.close();
			 }catch(Exception a)
			 {
				 a.printStackTrace();
			 }
		 }
		 return false;
	 }
	 
	 public boolean retrieveLogin(Customer customer)
	 {
		 try
			{
				String query= "Select * from login where email=? and password=?";
				PreparedStatement pst = connection.prepareStatement(query);

			    pst.setString(1, customer.getEmail());
				pst.setString(2, customer.getPassword());
				ResultSet rs = pst.executeQuery();
			   if(rs.next())
			   {
					return true;
			   }
			   else
			   {
				   return false;
			   }
			  
			}
        
		 catch(SQLException e)
		 {
			 JOptionPane.showMessageDialog(null,e);
		 }
		 return false;
	 }
	 
	
	 public boolean retrieveFarmerLogin(Farmer customer)
	 {
		 try
			{
				String query= "Select * from farmerlogin where email=? and password=?";
				PreparedStatement pst = connection.prepareStatement(query);
				pst.setString(1, customer.getEmail());
				pst.setString(2, customer.getPassword());
				
				ResultSet rs = pst.executeQuery();
				if(rs.next())
				{
					return true;
				}
				else{ 
					 return false;
				}
			}
     
		 catch(SQLException e)
		 {
			 e.printStackTrace();
		 }
		 return false;
	 }
	 
	 public Customer retrieveCustomerData(Customer fam)
	 {
		 try{
		 java.sql.Statement  pst = connection.createStatement();
	        ResultSet rs = pst.executeQuery("select * from customer where email = '"+fam.getEmail()+"'");	
		 if(rs.next())
		 {
			 String img = rs.getString("image");
			 fam.setfName(rs.getString("fName"));
			 fam.setlName(rs.getString("lName"));
			 fam.setEmail(rs.getString("email"));
			 fam.setphotoPath(img);
			 fam.setFunds(rs.getFloat("fund"));
		 }
		  return fam;
		 
		 }
		 catch(SQLException e)
		 {
			 e.printStackTrace();
		 }
		 return null;
	 }
	 
	 public Farmer retrieveFarmerData(Farmer fam)
	 {
		 try{
		 java.sql.Statement  pst = connection.createStatement();
	        ResultSet rs = pst.executeQuery("select * from farmer where email = '"+fam.getEmail()+"'");	
		 if(rs.next())
		 {
			 String img = rs.getString("image");
			 fam.setfName(rs.getString("fname"));
			 fam.setlName(rs.getString("lname"));
			 fam.setEmail(rs.getString("email"));
			 fam.setAddress(rs.getString("address"));
			 fam.setphotoPath(img); 
			 fam.setEarnings(rs.getFloat("earning"));
		 }
		  return fam;
		 
		 }
		 catch(SQLException e)
		 {
			 e.printStackTrace();
		 }
		 return null;
	 }
	 
	 
	 public ArrayList<Crop> retrieveCropData(ArrayList<Crop> crops,String email)
	 {
		 
		 try{
			 java.sql.Statement  pst = connection.createStatement();
	          ResultSet rs = pst.executeQuery("select * from crop where email = '"+email+"'");
		     Crop crop;
		    
		     while(rs.next())
		     {
		    	 crop = new Crop(rs.getString("email"),rs.getString("image"),rs.getString("name"),rs.getFloat("weight"),rs.getFloat("cost"),rs.getString("available"),rs.getInt("quantity"));
		         crops.add(crop);
		     }
			 
		 }catch(SQLException e)
		 {
                  e.printStackTrace();
		 }
		 return crops;
	 
	 }
	 public ArrayList<Crop> retrieveAllCropData(ArrayList<Crop> crops)
	 {
		 
		 try{
			 java.sql.Statement  pst = connection.createStatement();
	          ResultSet rs = pst.executeQuery("select * from crop ");
		     Crop crop;
		    
		     while(rs.next())
		     {
		    	 crop = new Crop(rs.getString("email"),rs.getString("image"),rs.getString("name"),rs.getFloat("weight"),rs.getFloat("cost"),rs.getString("available"),rs.getInt("quantity"));
		         crops.add(crop);
		     }
			 
		 }catch(SQLException e)
		 {
                  e.printStackTrace();
		 }
		 return crops;
	 
	 }
	 
	 public ArrayList<Basket> retrieveCustomerBasketData(ArrayList<Basket> basket,String email)
	 {
		 try{
			 java.sql.Statement  pst = connection.createStatement();
	          ResultSet rs = pst.executeQuery("select * from basket where customerEmail = '"+email+"'");
		     Basket bas;
		    
		     while(rs.next())
		     {
		    	 bas = new Basket(rs.getString("farmerEmail"),rs.getString("customerEmail"),rs.getString("itemName"),rs.getInt("quantity"),rs.getFloat("cost"),rs.getFloat("weight"));
		         basket.add(bas);
		     }
			 
		 }catch(SQLException e)
		 {
                  e.printStackTrace();
		 }
		 return basket;
	 
	 }
	 
	 public ArrayList<Basket> retrieveBasketData(ArrayList<Basket> basket)
	 {
		 try{
			 java.sql.Statement  pst = connection.createStatement();
	          ResultSet rs = pst.executeQuery("select * from basket ");
		     Basket bas;
		    
		     while(rs.next())
		     {
		    	 bas = new Basket(rs.getString("farmerEmail"),rs.getString("customerEmail"),rs.getString("itemName"),rs.getInt("quantity"),rs.getFloat("cost"),rs.getFloat("weight"));
		         basket.add(bas);
		     }
			 
		 }catch(SQLException e)
		 {
                  e.printStackTrace();
		 }
		 return basket;
	 
	 }
	 
	 public ArrayList<Farmer> retrieveFarmers(ArrayList<Farmer> farmer)
	 {
		 try{
			 java.sql.Statement  pst = connection.createStatement();
	          ResultSet rs = pst.executeQuery("select * from farmer");
		     Farmer bas;
		    
		     while(rs.next())
		     {
		    	 bas = new Farmer(rs.getString("email"),rs.getString("password"));
		         farmer.add(bas);
		     }
			 
		 }catch(SQLException e)
		 {
                  e.printStackTrace();
		 }
		 return farmer;
	 
	 }
     
	 public boolean updateBasket(Basket bas,String cropName)
	 {
		 try{
		String query = "Update basket set quantity = '"+bas.getQuantity()+"' where itemName = '"+cropName+"'";
		
		 PreparedStatement pst = connection.prepareStatement(query);
			pst.execute();
			return true;
			}
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, e1);
			}
		 return false;
	 }
	 
	 
	 public void removeBasketItem(String name)
	 {
		 try{
			 String query = "Delete from basket where itemName = '"+name+"' ";
			 PreparedStatement pst = connection.prepareStatement(query);
				pst.execute();
				pst.close();
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1);
				}
	 }
	 
	 public void removeBasketData(String email)
	 {
		 try{
			 String query = "Delete from basket where customerEmail = '"+email+"' ";
			 PreparedStatement pst = connection.prepareStatement(query);
				pst.execute();
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1);
				}
	 }
	 
	 public ArrayList<Basket> retrievePurchaseList(ArrayList<Basket> baskets,String email)
	 {
		 
		 try{
			 java.sql.Statement  pst = connection.createStatement();
	          ResultSet rs = pst.executeQuery("select * from farmercustomers where farmerEmail = '"+email+"'");
		     Basket basket;
		    
		     while(rs.next())
		     {
		    	 basket = new Basket(rs.getString("farmerEmail"),rs.getString("customerEmail"),rs.getString("customerName"),rs.getString("location"),rs.getFloat("fund"));
		         baskets.add(basket);
		     }
			 
		 }catch(SQLException e)
		 {
                  e.printStackTrace();
		 }
		 return baskets;
	 
	 }
	 
	 public void removeCrop(String name,String email)
	 {
		 try{
			 String query = "Delete from crop where email = '"+email+"' and name = '"+name+"' ";
			 PreparedStatement pst = connection.prepareStatement(query);
				pst.execute();
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1);
				}
	 }
	 
}
