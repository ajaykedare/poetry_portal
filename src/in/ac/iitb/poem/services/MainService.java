package in.ac.iitb.poem.services;

import in.ac.iitb.poem.beans.Poem;
import in.ac.iitb.poem.beans.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Component("mainService")
public class MainService {
	public String processRequest(String data)
	{
		System.out.println("Data Received in Service :"+ data);
		return data;
	}
	
	/**
	 * @param propFileName
	 * @return Properties instance
	*/
	public Properties getPropertiesFile(String propFileName)
	{
		Properties prop = new Properties();
		InputStream inputStream;
		inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		try 
		{
			if (inputStream != null) 
			{
				prop.load(inputStream);
			} 
			else 
			{
				throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	/**
	 * @param user
	 * @return user objet in json
	 * 
	 * Desc: Checks if user is present in stored users.json file from location userfilepath
	 */
	public String login( User user ) {
		ObjectMapper mapper = new ObjectMapper();
		String returnString="{\"result\": \"Fail\"}";
		try {
			Properties prop=getPropertiesFile("config.properties");
			List<User> list   = mapper.readValue(new File(prop.getProperty("userfilepath")),new TypeReference<List<User>>(){});
			for (User obj : list)
			{
				if((user.getUsername().equalsIgnoreCase(obj.getUsername())) && user.getPassword().equals(obj.getPassword())){
					user.setEmail(obj.getEmail());
					user.setMobile(obj.getMobile());
					returnString=mapper.writeValueAsString(user);					
				}
			}						
		}catch (JsonGenerationException e) {
			e.printStackTrace();
		}catch (JsonMappingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return returnString;				
	}
	
	/**
	 * @param user
	 * @return "Success" message
	 * 
	 * Desc: Method for signing up user
	 */
	public String signup(User user ) {
		ObjectMapper mapper = new ObjectMapper();
		JSONParser parser = new JSONParser();
		Properties prop=getPropertiesFile("config.properties");
		String returnString="{\"result\": \"Success\"}";
		boolean isUserExist=false;
		try {
			FileReader fileReader = new FileReader(prop.getProperty("userfilepath"));
			JSONArray userJsonArray = (JSONArray) parser.parse(fileReader);
			for(int i = 0; i < userJsonArray.size(); i++)
			{
			     JSONObject userObj = (JSONObject) userJsonArray.get(i);
			     if(userObj.get("username").equals(user.getUsername()))
			     {
			    	 returnString="{\"result\": \"UserExists\"}";
			    	 isUserExist=true;
			    	 break;
			     }			     
			}
			if(!isUserExist)
			{
				userJsonArray.add(user);
				mapper.writeValue(new File(prop.getProperty("userfilepath")), userJsonArray);
			}
		}catch (JsonGenerationException e) {
			e.printStackTrace();
		}catch (JsonMappingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return returnString;
	}
	
	/**
	 * @return complete poems in json string
	 */
	public String loadpoems() {
		ObjectMapper mapper = new ObjectMapper();
		JSONParser parser = new JSONParser();
		JSONArray json = new JSONArray();
		Properties prop=getPropertiesFile("config.properties");
		try {
			FileReader fileReader = new FileReader(prop.getProperty("poemfilepath"));
			json = (JSONArray) parser.parse(fileReader);
		}
		catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
			return json.toJSONString();
	}
	
	/**
	 * @param poem
	 * @return success
	 * 
	 * Desc: Uploads the poem to poemdata.json file
	 */
	public String upload(Poem poem ) {
		ObjectMapper mapper = new ObjectMapper();
		JSONParser parser = new JSONParser();
		Properties prop=getPropertiesFile("config.properties");
		try {
			FileReader fileReader = new FileReader(prop.getProperty("poemfilepath"));
			JSONArray poemJsonArray = (JSONArray) parser.parse(fileReader);
			poemJsonArray.add(poem);
			mapper.writeValue(new File(prop.getProperty("poemfilepath")), poemJsonArray);
		}catch (JsonGenerationException e) {
			e.printStackTrace();
		}catch (JsonMappingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "{\"result\": \"Success\"}";
	}
	
	/**
	 * @param jsonString
	 * @return
	 * 
	 * Desc: Stores the poem json object recieved from frontend to poemdata.json file
	 */
	public String savepoems( String jsonString ) {
		ObjectMapper mapper = new ObjectMapper();		
		JSONParser parser = new JSONParser();
		Properties prop=getPropertiesFile("config.properties");
		try {
			JSONArray json = (JSONArray) parser.parse(jsonString);
			mapper.writeValue(new File(prop.getProperty("poemfilepath")), json);
		}catch (JsonGenerationException e) {
			e.printStackTrace();
		}catch (JsonMappingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "{\"result\": \"Success\"}";
	}

}
