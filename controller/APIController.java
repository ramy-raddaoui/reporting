package com.sofct.sofct.controller;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Hashtable;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.apache.http.entity.StringEntity; 

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class APIController { 
	
	
	@PostMapping("/piechart")
	public String PiechartSofctwithCustomized_Request(@RequestBody String data) {
		System.out.println("data "+data);
		JSONObject json_object = new JSONObject(data);
		JSONObject json_object1= new JSONObject(json_object.get("data").toString());
		String metrique= json_object1.getString("metrique");
		String param1= json_object1.getString("param1");
		String param2= json_object1.getString("param2");
		String seuil= json_object1.getString("seuil");

		System.out.println("json metrique"+metrique);
 	String resultat="";
	//param1=param1.replaceAll(" ","_");
	//param2=param2.replaceAll(" ","_");
	System.out.println("param1"+param1);
	System.out.println("param2"+param2);
	Hashtable<String,String> h = new Hashtable<String,String>();
	h.put("nom intervenant","interv_full_name");
	h.put("ventes par produit","vg_achievement_value_nb");
	h.put("somme", "SUM"); 
	String query="SELECT "+h.get(param1)+" AS "+param1.replaceAll(" ","_")+","+h.get(metrique)+"("+h.get(param2)+") AS "+param2.replaceAll(" ","_")+" from commissions_fact_indiv GROUP BY "+h.get(param1)+" HAVING "+h.get(metrique)+"("+h.get(param2)+")>"+seuil ;

	try {
			  	
				URL url = new URL("http://localhost:3000/api/card");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("X-Metabase-Session", "68a09086-0d96-4789-b85f-0932a466fb42");
				//String input_marche_old = "{\"dataset_query\": {\"database\": 4,\"name\":\"Nom\",\"native\": {\"query\": \"SELECT c.interv_full_name as nom_intervenant , SUM(c.vg_achievement_value_nb) as Nombre_de_produits_vendus from commissions_fact_indiv as c GROUP BY c.interv_full_name having SUM(c.vg_achievement_value_nb)>0 \" },\"type\": \"native\" },\"display\": \"pie\",\"name\": \"test:1\",\"visualization_settings\": {\"graph.dimensions\": [\"nom intervenant\"],\"graph.metrics\": [\"Nombre_de_produits_vendus\"],\"graph.show_goal\": false,\"line.interpolate\": \"linear\",\"line.marker_enabled\": true,\"line.missing\": \"interpolate\",\"stackable.stack_type\": \"stacked\",\"table.column_widths\": [] }}";
				String input = "{\"dataset_query\": {\"database\": 4,\"name\":\"Nom\",\"native\": {\"query\":\""+query+"\"  },\"type\": \"native\" },\"display\": \"pie\",\"name\": \"test:1\",\"visualization_settings\": {\"graph.dimensions\": [\""+param1+"\"],\"graph.metrics\": [\""+param2+"\"],\"graph.show_goal\": false,\"line.interpolate\": \"linear\",\"line.marker_enabled\": true,\"line.missing\": \"interpolate\",\"stackable.stack_type\": \"stacked\",\"table.column_widths\": [] }}";
				OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				String output;
				
				while ((output = br.readLine()) != null) {
					resultat=resultat+output;
				}
	
				conn.disconnect();
			  } catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			 }
		   JSONObject jsonobject = new JSONObject(resultat);
		   int ID_CARD=jsonobject.getInt("id");
	
	      String resultat_final=this.PiechartSofctwithParamsnew(ID_CARD).toString();
	      System.out.println(resultat_final);
	      //System.out.println(ID_CARD);
	
	       
		/*  for (int i = 0; i < jsonArray.length(); i++) {
		      JSONObject explrObject = jsonArray.getJSONObject(i);
		      System.out.println(explrObject);
		  }
		  */
		

		  		return resultat_final;
			}
    
	
//	@GetMapping("/test/sofct/params/{seuil}")
	public JSONArray PiechartSofctwithParamsnew(int id)
	{
	        

	        HttpClient httpClient = HttpClientBuilder.create().build();
	      
	        HttpPost request = new HttpPost("http://localhost:3000/api/card/"+id+"/query");
	        String inputJson = "{\"parameters\": [{\"type\": \"category\",\"target\": [\"variable\",[\"template-tag\",\"seuil\"]],\"value\": \"0\"}]}";
	        		 System.out.println(inputJson);
	        		 StringEntity stringEntity = null;
					try {
						stringEntity = new StringEntity(inputJson);
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
	        request.addHeader("X-Metabase-Session","68a09086-0d96-4789-b85f-0932a466fb42");
	        ResponseHandler<String> handler = new BasicResponseHandler();
	        String body = null;
	        HttpResponse response = null;
			try {
			
				response = httpClient.execute(request);
				 body = handler.handleResponse(response);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   //     System.out.println(response.getStatusLine().getStatusCode());
	        System.out.println("body"+body);
	     //   JSONArray jsonArray = new JSONArray(body); 
			   JSONObject jsonobject = new JSONObject(body).getJSONObject("data");
		        JSONArray jsonArray = new JSONArray(jsonobject.getJSONArray("rows").toString()); 
		      //  System.out.println("rows"+jsonArray.toString());
		        JSONArray jsonArrayResult = new JSONArray();

			for (int i = 0; i < jsonArray.length(); i++) {
			    JSONArray jsonArrayItem = jsonArray.getJSONArray(i);
			    JSONObject jsonObj = new JSONObject();
			  //^^^^^^^^^^^^^^^^^^^^^^^^^^^ add this line
			    jsonObj.put("name",jsonArrayItem.get(0));
			    jsonObj.put("value",jsonArrayItem.get(1));
			    jsonArrayResult.put(jsonObj);
			 //   System.out.println("Test dans boucle "+i+ " "+ jsonArrayItem.get(0));
			}
	
		     return jsonArrayResult;
	    }
	   
	
	@GetMapping("/test/{seuil}")
	public String PiechartSofctwithParams_test(@PathVariable("seuil") String seuil) {
	String resultat="";
		  try {
			  	
				URL url = new URL("http://localhost:3000/api/card/3/query");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("X-Metabase-Session", "7c92ff42-aec4-4850-a84d-02552d774ef5");
				String input = "{\"parameters\": [{\"type\": \"category\",\"target\": [\"variable\",[\"template-tag\",\"seuil\"]],\"value\": \""+seuil+"\"}]}";

				OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				String output;
				
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					resultat=resultat+output;
				}
	
				conn.disconnect();
			  } catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			 }

		  		return resultat;
			}
    
	

	   
	@GetMapping("/test/sofct/{seuil}")
	public String PiechartSofctwithParams(@PathVariable("seuil") String seuil)
	{
		  final String uri = "http://localhost:3000/api/card/3/query";  
		    RestTemplate restTemplate = new RestTemplate();
		    HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON)); 
		    headers.set("X-Metabase-Session", "7c92ff42-aec4-4850-a84d-02552d774ef5");
		    headers.set("value", "0");
		//    headers.setAccessControlRequestHeaders("X-Metabase-Session:7c92ff42-aec4-4850-a84d-02552d774ef5");
		    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		     
		    ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
		   // System.out.println(result);
		   String reponse=result.getBody().replaceAll("<202,","");
		/*    String result_body=result.getBody();
		    JSONObject obj = new JSONObject(result.getBody());
		    JSONArray data= obj.getJSONObject("data").getJSONArray("rows");
		    String dataString=data.toString();
		   dataString=dataString.replaceAll("\\[","\\{");
		  dataString=dataString.replaceAll("\\]","\\}");
	
		   System.out.println(dataString);
		   dataString[0]='[';
		    return data.toString();
		   
		   */
		   
		   return reponse;
		/*    JSONArray jsonArray = new JSONArray();
		    JSONArray array = jsonArray.put(obj);
		    System.out.println(((JSONArray) array.get(0))); 
		    */
		   /* try (JsonReader reader = Json.createReader(new StringReader(document))) {
                JsonArray array = reader.readArray();
 
                String nom = obj.getJsonString("nom").getString();
            }
            */
		    // this is what i added Now
	  /*  JSONObject obj = new JSONObject(result.getBody());
	    JSONObject obj2= new JSONObject(obj.get("data"));
	    JSONObject obj3= new JSONObject(obj2.get("rows"));
	    */

	   //JSONArray array = obj.getJSONArray("data");
	    //System.out.println(array); 

	 /*   System.out.println(array.getJSONObject(0));
	    

		           JsonObject obj = array.getJsonObject(1);
		    System.out.println(obj+"\n");
		   String association_name = obj.getJSONObject("data").getString("cols");
	       String association_name = obj.getJSONObject("body").getString("data");

	       System.out.println(association_name);
*/
	      /*  JSONArray arr = obj.getJSONArray("posts");
	        for (int i = 0; i < arr.length(); i++) {
	            String post_id = arr.getJSONObject(i).getString("post_id");
	            System.out.println(post_id);
	        }
	        */

	
	}
	@GetMapping("/test/sofct")
	public String PiechartSofct()
	{
		  final String uri = "http://localhost:3000/api/embed/card/eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyZXNvdXJjZSI6eyJxdWVzdGlvbiI6Mn0sInBhcmFtcyI6e30sImV4cCI6NmUrNDUsImlhdCI6MTU4MzIzMzM4MH0.JAYJprCgXqiugexMv0bZkGMT6unHvbmDZUprmKfSB3A/query";  
		    RestTemplate restTemplate = new RestTemplate();
		    HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON)); 
		    headers.set("X-Metabase-Session", "7c92ff42-aec4-4850-a84d-02552d774ef5");
		//    headers.setAccessControlRequestHeaders("X-Metabase-Session:7c92ff42-aec4-4850-a84d-02552d774ef5");
		    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		     
		    ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		   // System.out.println(result);
		   String reponse=result.getBody().replaceAll("<202,","");
		/*    String result_body=result.getBody();
		    JSONObject obj = new JSONObject(result.getBody());
		    JSONArray data= obj.getJSONObject("data").getJSONArray("rows");
		    String dataString=data.toString();
		   dataString=dataString.replaceAll("\\[","\\{");
		  dataString=dataString.replaceAll("\\]","\\}");
	
		   System.out.println(dataString);
		   dataString[0]='[';
		    return data.toString();
		   
		   */
		   
		   return reponse;
		/*    JSONArray jsonArray = new JSONArray();
		    JSONArray array = jsonArray.put(obj);
		    System.out.println(((JSONArray) array.get(0))); 
		    */
		   /* try (JsonReader reader = Json.createReader(new StringReader(document))) {
                JsonArray array = reader.readArray();
 
                String nom = obj.getJsonString("nom").getString();
            }
            */
		    // this is what i added Now
	  /*  JSONObject obj = new JSONObject(result.getBody());
	    JSONObject obj2= new JSONObject(obj.get("data"));
	    JSONObject obj3= new JSONObject(obj2.get("rows"));
	    */

	   //JSONArray array = obj.getJSONArray("data");
	    //System.out.println(array); 

	 /*   System.out.println(array.getJSONObject(0));
	    

		           JsonObject obj = array.getJsonObject(1);
		    System.out.println(obj+"\n");
		   String association_name = obj.getJSONObject("data").getString("cols");
	       String association_name = obj.getJSONObject("body").getString("data");

	       System.out.println(association_name);
*/
	      /*  JSONArray arr = obj.getJSONArray("posts");
	        for (int i = 0; i < arr.length(); i++) {
	            String post_id = arr.getJSONObject(i).getString("post_id");
	            System.out.println(post_id);
	        }
	        */

	
	}
	
	// do not touch
	
	@GetMapping("/test")
	public String Piechart()
	{
		  final String uri = "http://localhost:3000/api/embed/card/eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyZXNvdXJjZSI6eyJxdWVzdGlvbiI6MX0sInBhcmFtcyI6e30sImV4cCI6NzU4MjU1NDc1NywiaWF0IjoxNTgyNTU0ODE2fQ.gUIfkQHQpI7n49V5wtezybY_hhHDA7NWn0IftnZJYgA/query";  
		    RestTemplate restTemplate = new RestTemplate();
		    HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON)); 
		    headers.set("X-Metabase-Session", "7c92ff42-aec4-4850-a84d-02552d774ef5");
		//    headers.setAccessControlRequestHeaders("X-Metabase-Session:7c92ff42-aec4-4850-a84d-02552d774ef5");
		    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		     
		    ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		   // System.out.println(result);
		   String reponse=result.getBody().replaceAll("<202,","");
		/*    String result_body=result.getBody();
		    JSONObject obj = new JSONObject(result.getBody());
		    JSONArray data= obj.getJSONObject("data").getJSONArray("rows");
		    String dataString=data.toString();
		   dataString=dataString.replaceAll("\\[","\\{");
		  dataString=dataString.replaceAll("\\]","\\}");
	
		   System.out.println(dataString);
		   dataString[0]='[';
		    return data.toString();
		   
		   */
		   
		   return reponse;
		/*    JSONArray jsonArray = new JSONArray();
		    JSONArray array = jsonArray.put(obj);
		    System.out.println(((JSONArray) array.get(0))); 
		    */
		   /* try (JsonReader reader = Json.createReader(new StringReader(document))) {
                JsonArray array = reader.readArray();
 
                String nom = obj.getJsonString("nom").getString();
            }
            */
		    // this is what i added Now
	  /*  JSONObject obj = new JSONObject(result.getBody());
	    JSONObject obj2= new JSONObject(obj.get("data"));
	    JSONObject obj3= new JSONObject(obj2.get("rows"));
	    */

	   //JSONArray array = obj.getJSONArray("data");
	    //System.out.println(array); 

	 /*   System.out.println(array.getJSONObject(0));
	    

		           JsonObject obj = array.getJsonObject(1);
		    System.out.println(obj+"\n");
		   String association_name = obj.getJSONObject("data").getString("cols");
	       String association_name = obj.getJSONObject("body").getString("data");

	       System.out.println(association_name);
*/
	      /*  JSONArray arr = obj.getJSONArray("posts");
	        for (int i = 0; i < arr.length(); i++) {
	            String post_id = arr.getJSONObject(i).getString("post_id");
	            System.out.println(post_id);
	        }
	        */

	
	}

}
