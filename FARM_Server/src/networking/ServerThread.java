package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import controller.SQLCommands;
import model.Crop;
import model.Customer;
import model.Farmer;

public class ServerThread extends Thread{

	private Socket socket;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	boolean success;
	static String email;
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
					else if(action.equals("Request Login"))
					{
						os.writeObject(true);
						
						Customer cus = (Customer)is.readObject();
						sql.retrieveLogin(cus);
					    Customer cus2 = new Customer();
							cus2 = sql.retrieveCustomerData(cus);
							os.writeObject(cus2);
					}
					else if(action.equals("request crops"))
					{
						os.writeObject(true);	
						email = (String)is.readObject();
						
						@SuppressWarnings("unchecked")
						ArrayList<Crop> crr = (ArrayList<Crop>)is.readObject();
						
						ArrayList<Crop> cr = new ArrayList<Crop>();
						cr = sql.retrieveCropData(crr,email);
						os.writeObject(cr);
						
					}
					else if(action.equals("farmer"))
					{
						
						os.writeObject(true);
						
					      Farmer fam = (Farmer)is.readObject();
						 sql.retrieveFarmerLogin(fam);
					  
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
					else if(action.equals("Update Crop"))
					{
						os.writeObject(true);
						 cropName = (String)is.readObject();
						Crop crop = (Crop)is.readObject();
						sql.updateCrops(crop, cropName);
						
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
	
}
