package in.ac.iitb.poem.controllers;

import in.ac.iitb.poem.beans.Poem;
import in.ac.iitb.poem.beans.User;
import in.ac.iitb.poem.services.MainService;

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
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Component
public class MainController {

	@Autowired
	MainService mainService;

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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;

	}

	/**
	 * @param user
	 * @return user objet in json
	 * 
	 * Desc: Checks if user is present in stored users.json file from location iserfilepath
	 */
	@RequestMapping(value="login", method=RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody String login( @RequestBody User user ) {
		System.out.println("Username :" + user.getUsername() + ", Password: " + user.getPassword());
		ObjectMapper mapper = new ObjectMapper();
		String returnString="{\"result\": \"Fail\"}";
		boolean flag=false;
		try {
			Properties prop=getPropertiesFile("config.properties");
			List<User> list   = mapper.readValue(new File(prop.getProperty("userfilepath")),new TypeReference<List<User>>(){});
			//System.out.println("Username :" + user.getUsername() + ", Password: " + user.getPassword());
			for (User obj : list)
			{
				if((user.getUsername().equalsIgnoreCase(obj.getUsername())) && user.getPassword().equals(obj.getPassword())){
					flag=true;
					user.setEmail(obj.getEmail());
					user.setMobile(obj.getMobile());
					returnString=mapper.writeValueAsString(user);
					System.out.println(obj.getUsername());
					System.out.println(obj.getPassword());
				}
			}						
		}
		catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
	@RequestMapping(value="signup", method=RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody String signup( @RequestBody User user ) {
		ObjectMapper mapper = new ObjectMapper();
		JSONParser parser = new JSONParser();
		Properties prop=getPropertiesFile("config.properties");
		try {
			FileReader fileReader = new FileReader(prop.getProperty("userfilepath"));
			JSONArray json = (JSONArray) parser.parse(fileReader);
			json.add(user);
			mapper.writeValue(new File(prop.getProperty("userfilepath")), json);
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
			return "{\"result\": \"Success\"}";
	}
	
	/**
	 * @return complete poems in json string
	 */
	@RequestMapping(value="loadpoems", method=RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody String loadpoems() {
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
	
	@RequestMapping(value="upload", method=RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody String upload( @RequestBody Poem poem ) {
		ObjectMapper mapper = new ObjectMapper();
		JSONParser parser = new JSONParser();
		Properties prop=getPropertiesFile("config.properties");
		try {
			FileReader fileReader = new FileReader(prop.getProperty("poemfilepath"));
			JSONArray json = (JSONArray) parser.parse(fileReader);
			json.add(poem);
			mapper.writeValue(new File(prop.getProperty("poemfilepath")), json);
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
			return "{\"result\": \"Success\"}";
	}

	@RequestMapping(value="ajayurl", method=RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody String submitAdmissionForm(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String jsonString = null;
		jsonString = req.getReader().readLine();
		System.out.println("In controller");
		return mainService.processRequest(jsonString);
	}

}
