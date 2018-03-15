package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Crop;
import model.Customer;
import model.Farmer;

public class SQLCommands {
	 Connection connection = null;
	 
	 public SQLCommands(){
		 connection = SqlConnection.dbConnector();
	 }
	 
	 
	 //adds
	 public boolean addCustomer(Customer customer)
	 {
		String query  = "insert into customer (fName,lName,email,password,image) values (?,?,?,?,?)";
		String query2 = "insert into login (email,password) values (?,?)";
	    try{
		PreparedStatement pst = connection.prepareStatement(query);
		PreparedStatement pst2 = connection.prepareStatement(query2);
		pst.setString(1, customer.getfName());
		pst.setString(2, customer.getlName());
		pst.setString(3, customer.getEmail());
		pst.setString(4, customer.getPassword());
		pst.setBytes(5, customer.getPhoto());
		pst.execute();
		pst2.setString(1, customer.getEmail());
		pst2.setString(2, customer.getPassword());
		pst2.execute();
		
		return true;
	    }
	    catch(SQLException e){
	    	e.printStackTrace();
	    }
		 
		 return false;
	 }
	 
	 public boolean addFarmer(Farmer farmer)
	 {
		String query  = "insert into farmer (fName,lName,email,address,password,image) values (?,?,?,?,?,?)";
		String query2 = "insert into farmerlogin (email,password) values (?,?)";
	    try{
		PreparedStatement pst = connection.prepareStatement(query);
		PreparedStatement pst2 = connection.prepareStatement(query2);
		pst.setString(1, farmer.getfName());
		pst.setString(2, farmer.getlName());
		pst.setString(3, farmer.getEmail());
		pst.setString(4, farmer.getAddress());
		pst.setString(5, farmer.getPassword());
		pst.setBytes(6, farmer.getPhoto());
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
	 
	 public boolean updateCrops(Crop crop,String email)
	 {
		 try{
		 String query = "Update crop set email = '"+crop.getEmail()+"', image = '"+crop.getImage()+"', name = '"+crop.getName()+"', weight = '"+crop.getWeight()+"', cost = '"+crop.getCostPerUnit()+"', available = '"+crop.getAvailable()+"', quantity = '"+crop.getQuantity()+"' where email = '"+email+"'";
		 PreparedStatement pst = connection.prepareStatement(query);
			pst.execute();
			
			JOptionPane.showMessageDialog(null, "Crop Updated");
			pst.close();
			return true;
			}
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, e1);
			}
		 return false;
	 }
	 
	 public boolean addCrop(Crop crop)
	 {
		String query  = "insert into crop (email,image,name,weight,cost,available,quantity) values (?,?,?,?,?,?,?)";
	    try{
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, crop.getEmail());
		pst.setBytes(2, crop.getImage());
		pst.setString(3, crop.getName());
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
	 
	 public boolean retrieveLogin(Customer customer)
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
			 byte[] img = rs.getBytes("image");
			 fam.setfName(rs.getString("fName"));
			 fam.setlName(rs.getString("lName"));
			 fam.setEmail(rs.getString("email"));
			 fam.setPhoto(img);
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
			 byte[] img = rs.getBytes("image");
			 fam.setfName(rs.getString("fname"));
			 fam.setlName(rs.getString("lname"));
			 fam.setEmail(rs.getString("email"));
			 fam.setAddress(rs.getString("address"));
			 fam.setPhoto(img); 
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
		    	 crop = new Crop(rs.getString("email"),rs.getBytes("image"),rs.getString("name"),rs.getFloat("weight"),rs.getFloat("cost"),rs.getString("available"),rs.getInt("quantity"));
		         crops.add(crop);
		     }
			 
		 }catch(SQLException e)
		 {
                  e.printStackTrace();
		 }
		 return crops;
	 
	 }
	 
}
