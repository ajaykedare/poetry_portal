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
import org.json.simple.JSONObject;
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

	/**
	 * @param user
	 * @return user objet in json
	 * 
	 * Desc: Checks if user is present in stored users.json file from location userfilepath
	 */
	@RequestMapping(value="login", method=RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody String login( @RequestBody User user ) {
		return mainService.login(user);		
	}

	/**
	 * @param user
	 * @return "Success" message
	 * 
	 * Desc: Method for signing up user
	 */
	@RequestMapping(value="signup", method=RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody String signup( @RequestBody User user ) {
		return mainService.signup(user);	
	}
	
	/**
	 * @return complete poems in json string
	 */
	@RequestMapping(value="loadpoems", method=RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody String loadpoems() {
		return mainService.loadpoems();
	}
	
	/**
	 * @param poem
	 * @return success
	 * 
	 * Desc: Uploads the poem to poemdata.json file
	 */
	@RequestMapping(value="upload", method=RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody String upload( @RequestBody Poem poem ) {
		return mainService.upload(poem);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody String savepoems( HttpServletRequest req, HttpServletResponse res ) {
		String jsonString = null;
		try {
			jsonString = req.getReader().readLine();			
		}catch (IOException e) {
			e.printStackTrace();
		}
		return mainService.savepoems(jsonString);
	}	
}
