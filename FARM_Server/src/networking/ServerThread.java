package networking;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import controller.SQLCommands;
import model.Basket;
import model.Crop;
import model.Customer;
import model.Farmer;

public class ServerThread extends Thread{

	private Socket socket;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	boolean success;
	static String cropName;
	public ServerThread(Socket socket)
	{
		this.socket = socket;
	}
	
	public void run()
	{
		waitForRequest();
	}
	
public void waitForRequest() {
		
		Object action = "";
		SQLCommands sql = new SQLCommands(); 
		
		try{
		while(true)
		{
			this.getStreams();
			do{
				try{
					action = is.readObject();
					if(action.equals("Add Customer")){
					os.writeObject(true);
					Customer cus = (Customer)is.readObject(); // Casts the customer object back
					  
					sql.addCustomer(cus); // adds the customer object to the database
					
					}
					else if(action.equals("Add Farmer")){
						os.writeObject(true);
						Farmer fam = (Farmer)is.readObject(); 
						  
						sql.addFarmer(fam);
					}
					else if(action.equals("Add to Basket"))
					{
						os.writeObject(true);
						Basket bas = (Basket)is.readObject();
						sql.addToBasket(bas);
					}
					
					else if(action.equals("Request Login"))
					{
						  Customer cus = (Customer)is.readObject();
					      os.writeObject(sql.retrieveLogin(cus));
					      Customer cus2 = new Customer();
							cus2 = sql.retrieveCustomerData(cus);
							os.writeObject(cus2);
			                
					}
					else if(action.equals("request crops"))
					{
						os.writeObject(true);	
						String email = (String)is.readObject();
						
						@SuppressWarnings("unchecked")
						ArrayList<Crop> crr = (ArrayList<Crop>)is.readObject();
						
						ArrayList<Crop> cr = new ArrayList<Crop>();
						cr = sql.retrieveCropData(crr,email);
						os.writeObject(cr);
						
					}
					else if(action.equals("request customer basket"))
					{
						
						@SuppressWarnings("unchecked")
						ArrayList<Basket> crr = (ArrayList<Basket>)is.readObject();
						
						ArrayList<Basket> cr = new ArrayList<Basket>();
						cr = sql.retrieveBasketData(crr);
						os.writeObject(cr);
						
					}
					else if(action.equals("request customerPurchaseList"))
					{
						os.writeObject(true);	
						String email = (String)is.readObject();
						
						@SuppressWarnings("unchecked")
						ArrayList<Basket> crr = (ArrayList<Basket>)is.readObject();
						
						ArrayList<Basket> cr = new ArrayList<Basket>();
						cr = sql.retrievePurchaseList(crr, email);
						os.writeObject(cr);
						
					}
					
					else if(action.equals("request all crops"))
					{
						os.writeObject(true);	
						
						@SuppressWarnings("unchecked")
						ArrayList<Crop> crr = (ArrayList<Crop>)is.readObject();
						
						ArrayList<Crop> cr = new ArrayList<Crop>();
						cr = sql.retrieveAllCropData(crr);
						os.writeObject(cr);
						
					}
					else if(action.equals("farmer"))
					{	
					      Farmer fam = (Farmer)is.readObject();
					      os.writeObject(sql.retrieveFarmerLogin(fam));
					  
					     Farmer fam2 = new Farmer();
						fam2 = sql.retrieveFarmerData(fam);
					  
							os.writeObject(fam2);
						
					}
					else if(action.equals("Add Crop"))
					{
						os.writeObject(true);
						Crop crop = (Crop)is.readObject();
						sql.addCrop(crop);
						
					}
					else if(action.equals("Add CustomerPurchase"))
					{
						os.writeObject(true);
						Basket basket = (Basket)is.readObject();
						sql.addCustomerPurchase(basket);
					}
					else if(action.equals("Update Crop"))
					{
						 String email;
						 os.writeObject(true);
						 String cropName = (String)is.readObject();
						 email = (String)is.readObject();
						Crop crop = (Crop)is.readObject();
						sql.updateCrops(crop, cropName,email);
					}
					else if(action.equals("Update Quantity"))
					{
						os.writeObject(true);
						String cropName = (String)is.readObject();
						Basket bas = (Basket)is.readObject();
						sql.updateBasket(bas, cropName);
					}
					else if(action.equals("Update Customer"))
					{
						os.writeObject(true);
						String email = (String)is.readObject();
						Customer cus = (Customer)is.readObject();
						sql.updateCustomer(cus, email);
					}
					else if(action.equals("Remove Basket Data"))
					{
						os.writeObject(true);
						String email = (String)is.readObject();
						sql.removeBasketData(email);
					}
					else if(action.equals("Remove basket item"))
					{
						os.writeObject(true);
						String name = (String)is.readObject();
						sql.removeBasketItem(name);
					}
					else if(action.equals("request farmer list"))
					{
						os.writeObject(true);	
						
						@SuppressWarnings("unchecked")
						ArrayList<Farmer> crr = (ArrayList<Farmer>)is.readObject();
						
						ArrayList<Farmer> cr = new ArrayList<Farmer>();
						cr = sql.retrieveFarmers(crr);
						os.writeObject(cr);
						
					}
				}
					
				catch(ClassNotFoundException | ClassCastException e)
				{
					e.printStackTrace();
				}
			}while(!action.equals("Exit"));
			this.closeConnection();
		}
		}catch(IOException e)
		{
			System.out.println("Client has terminated connections with the server");
		}
		
	}
	
	public void closeConnection()
	{
		try {
			os.close();
			is.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	

	public void getStreams()
	{
         try{
			
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String convert_ImageByteArray_ToPathLocation(byte[] image)
	{
		try {
            final BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));
            ImageIO.write(bufferedImage, "jpg", new File("path/to/image.jpg"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return null;
	}
	
	public void storeToImages(String pathName)
	{
		
	}
	
}
