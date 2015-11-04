package in.ac.iitb.poem.services;

import org.springframework.stereotype.Component;

@Component("mainService")
public class MainService {
	public String processRequest(String data)
	{
		System.out.println("Data Received in Service :"+ data);
		return data;
	}
}
