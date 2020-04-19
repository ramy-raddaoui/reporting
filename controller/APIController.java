package com.sofct.sofct.controller;
import org.apache.http.HttpResponse;
import com.sofct.sofct.dao.ConfigurationDAO;
import com.sofct.sofct.model.Configuration;

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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.apache.http.entity.StringEntity; 

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
    @Autowired
    private ConfigurationDAO configDao;
    
    @GetMapping("/get/configuration/{alias}")
    public List<Configuration> getConfiguration(@PathVariable String alias)
	{
    
		 return configDao.findByAliasTable(alias);
	} 

	public boolean delete_chart(int id)
	{
 
		String resultat = "";
		try {
		URL url = new URL("http://localhost:3000/api/card/"+id);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true); 
		conn.setRequestMethod("DELETE");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("X-Metabase-Session", "68a09086-0d96-4789-b85f-0932a466fb42");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream()))); 

		String output;
		
		while ((output = br.readLine()) != null) {
			resultat=resultat+output;
		}
		System.out.println(resultat);

		conn.disconnect();
	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }
		return true;

	}
	
	 
	@PostMapping("/pieandhistchart")
	public String PiechartSofctwithCustomized_Request(@RequestBody String data) {
		Hashtable<String,String> h = new Hashtable<String,String>();
		h.put("nom intervenant","interv_full_name");
		h.put("ventes par produit","vg_achievement_value_nb");
		h.put("objectif par produit","vg_target_value");
		h.put("Rémunération par produit","vg_payment_value");
		h.put("Nom du produit","commer_obj_line_name");
		h.put("Rémunération finale","final_payment_value");
		h.put("boutique","interv_dse_name");
		h.put("debut_période","start_period");
		h.put("fin_période","end_period");

		//JSONArray my_array = data.getJSONArray("param2");
		//System.out.println("This is first step "+data);

		JSONObject json_object = new JSONObject(data); 
		JSONObject json_object1= new JSONObject(json_object.get("data").toString());
		String display=json_object1.getString("display");
		String param1= json_object1.getString("param1");
		JSONArray test_param2 = json_object1.getJSONArray("param2");
		JSONArray GroupByElements = json_object1.getJSONArray("GroupBy");
		JSONArray periodElements = json_object1.getJSONArray("period");

	//	String seuil= json_object1.getString("seuil");

		String query="SELECT "+h.get(param1)+" AS "+param1.replaceAll(" ","_");
		
		//SELECT Injecting Items Boucle
		 JSONObject JSONObjItem = null;
		 String Group_By_Elements="";
		 String Order_By_Elements="";
		 if (display.equals(new String("area"))==false && display.equals(new String("line"))==false)
		 {
			  Order_By_Elements=h.get(param1)+" ";
		 }
		 String last_GB = "";
		 String GB_WHERE= "";
		 for (int i=0;i<GroupByElements.length();i++)
		 {
		     JSONObjItem = new JSONObject(GroupByElements.getJSONObject(i).toString());
		   
		     query+=" ,"+h.get(JSONObjItem.getString("nom"))+" AS "+JSONObjItem.getString("nom").replaceAll(" ","_")+" " ; 
	    	 Group_By_Elements+=" ,"+h.get(JSONObjItem.getString("nom"))+" ";
	    	 last_GB=h.get(JSONObjItem.getString("nom"));
	    	 GB_WHERE+=" AND "+h.get(JSONObjItem.getString("nom"))+" IS NOT NULL ";
		 }
//	     System.out.println("query version "+query);
    	// System.out.println("Group_By_Elements"+Group_By_Elements);

		// System.out.println("group by variaable "+Group_By_Elements);
		  for (int i = 0; i < test_param2.length(); i++) 
  		{
		     JSONObjItem = new JSONObject(test_param2.getJSONObject(i).toString());
	//	     System.out.println("JSONObjItem"+JSONObjItem);
		    	 query+= " ,"+JSONObjItem.getString("metrique")+"("+h.get(JSONObjItem.getString("nom"))+") AS "+JSONObjItem.getString("nom").replaceAll(" ","_")+" ";
  		}
		  if (display.equals(new String("area"))==true || display.equals(new String("line"))==true)
		    Order_By_Elements=last_GB;
		    query+=" FROM commissions_fact_indiv WHERE "+h.get(param1)+" IS NOT NULL "+GB_WHERE;
		    if (periodElements.length()!=0)
		    {
		    JSONObject JSONObjItem_period_debut = new JSONObject(periodElements.getJSONObject(0).toString());
		    JSONObject JSONObjItem_period_fin = new JSONObject(periodElements.getJSONObject(1).toString());
		    System.out.println("JSONObjItem_period_debut"+JSONObjItem_period_debut);
		    query+="AND TO_CHAR("+h.get(JSONObjItem_period_debut.getString("name"))+",'DD/MM/YYYY')>= '"+JSONObjItem_period_debut.getString("value")+"' ";
		    query+=" AND TO_CHAR("+h.get(JSONObjItem_period_fin.getString("name"))+",'DD/MM/YYYY')<= '"+JSONObjItem_period_fin.getString("value")+"' ";
		    }
		    query+=" GROUP BY "+h.get(param1)+Group_By_Elements+" "+"ORDER BY "+Order_By_Elements;
		   // System.out.println("queryyy"+query);
		   // query="SELECT interv_dse_name AS nom_boutique ,commer_obj_line_name AS nom_produit,SUM(final_payment_value) AS rémunération_vendeurs_all FROM commissions_fact_indiv GROUP BY interv_dse_name,commer_obj_line_name ORDER BY interv_dse_name , commer_obj_line_name";
		//   System.out.println("query"+query);
		    /*Group By Injecting Items Boucle
			for (int i = 0; i < test_param2.length(); i++) 
		  	{
				  JSONObject JSONObjItem = new JSONObject(test_param2.getJSONObject(i).toString());	
				  query+=(i==0) ? " "+h.get(JSONObjItem.getString("nom")) : " ,"+h.get(JSONObjItem.getString("nom"));
		  	}
		  	*/
			
			//System.out.println(query_updated);
		//String param2= json_object1.getString("param2");
		//System.out.println(display);
 	String resultat=""; 
	//param1=param1.replaceAll(" ","_");
	//param2=param2.replaceAll(" ","_");
 
	//query_updated+=  
	//query="SELECT "+h.get(param1)+" AS "+param1.replaceAll(" ","_")+","+h.get(metrique)+"("+h.get(param2)+") AS "+param2.replaceAll(" ","_")+" from commissions_fact_indiv GROUP BY "+h.get(param1)+" HAVING "+h.get(metrique)+"("+h.get(param2)+")>"+seuil ;
	//query="SELECT interv_dse_name AS boutique , count(*) as compter ,sum(final_payment_value) AS Rémunération_finale  FROM commissions_fact_indiv WHERE interv_dse_name IS NOT NULL AND TO_CHAR(start_period,'DD/MM/YYYY')>= '01/10/2019'  AND TO_CHAR(end_period,'DD/MM/YYYY')<= '31/12/2019'  GROUP BY interv_dse_name ORDER BY interv_dse_name ";
 	//System.out.println(query);
	try {
			  	
				URL url = new URL("http://localhost:3000/api/card");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("X-Metabase-Session", "33f570da-ac9e-476a-874e-5a26c251a1de");
				//String input_marche_old = "{\"dataset_query\": {\"database\": 4,\"name\":\"Nom\",\"native\": {\"query\": \"SELECT c.interv_full_name as nom_intervenant , SUM(c.vg_achievement_value_nb) as Nombre_de_produits_vendus from commissions_fact_indiv as c GROUP BY c.interv_full_name having SUM(c.vg_achievement_value_nb)>0 \" },\"type\": \"native\" },\"display\": \"pie\",\"name\": \"test:1\",\"visualization_settings\": {\"graph.dimensions\": [\"nom intervenant\"],\"graph.metrics\": [\"Nombre_de_produits_vendus\"],\"graph.show_goal\": false,\"line.interpolate\": \"linear\",\"line.marker_enabled\": true,\"line.missing\": \"interpolate\",\"stackable.stack_type\": \"stacked\",\"table.column_widths\": [] }}";
			//	String input = "{\"dataset_query\": {\"database\": 4,\"name\":\"Nom\",\"native\": {\"query\":\""+query+"\"  },\"type\": \"native\" },\"display\": \""+display+"\",\"name\": \"test:1\",\"visualization_settings\": {\"graph.dimensions\": [\""+param1+"\"],\"graph.metrics\": [\"param2\"],\"graph.show_goal\": false,\"line.interpolate\": \"linear\",\"line.marker_enabled\": true,\"line.missing\": \"interpolate\",\"stackable.stack_type\": \"stacked\",\"table.column_widths\": [] }}";
				String input = "{\"dataset_query\": {\"database\": 4,\"name\":\"Nom\",\"native\": {\"query\":\""+query+"\"  },\"type\": \"native\" },\"display\": \"combo\",\"name\": \"test:1\",\"visualization_settings\": {\"graph.dimensions\": [\""+param1+"\"],\"graph.metrics\": [\"param2\"],\"graph.show_goal\": false,\"line.interpolate\": \"linear\",\"line.marker_enabled\": true,\"line.missing\": \"interpolate\",\"stackable.stack_type\": \"stacked\",\"table.column_widths\": [] }}";

				System.out.println("input"+input);
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
			//System.out.println("This is resultat"+resultat);
		   JSONObject jsonobject = new JSONObject(resultat);
		   int ID_CARD=jsonobject.getInt("id");
		 //  System.out.println("ID_CARD"+ID_CARD);
		//   System.out.println("TEST PARAM2"+test_param2.toString());
	      String resultat_final=this.PiechartSofctwithParamsnew(ID_CARD,display,test_param2).toString();
	      //this.delete_chart(ID_CARD);
	      //System.out.println(ID_CARD);
	
	       
		/*  for (int i = 0; i < jsonArray.length(); i++) {
		      JSONObject explrObject = jsonArray.getJSONObject(i);
		      System.out.println(explrObject);
		  }
		  */
		
	      	System.out.println("resultat final"+resultat_final);
		  		return resultat_final;
			}
    
	
//	@GetMapping("/test/sofct/params/{seuil}")
	public JSONArray PiechartSofctwithParamsnew(int id,String display,JSONArray param2)
	{
	        
	        HttpClient httpClient = HttpClientBuilder.create().build();
	      
	        HttpPost request = new HttpPost("http://localhost:3000/api/card/"+id+"/query");
	        String inputJson = "{\"parameters\": [{\"type\": \"category\",\"target\": [\"variable\",[\"template-tag\",\"seuil\"]],\"value\": \"0\"}]}";
	        		 StringEntity stringEntity = null;
					try {
						stringEntity = new StringEntity(inputJson);
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
	        request.addHeader("X-Metabase-Session","33f570da-ac9e-476a-874e-5a26c251a1de");
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
	     //   JSONArray jsonArray = new JSONArray(body); 
			   JSONObject jsonobject = new JSONObject(body).getJSONObject("data");
		        JSONArray jsonArray = new JSONArray(jsonobject.getJSONArray("rows").toString());
		        System.out.println("jsonArray"+jsonArray);
		        JSONArray jsonArrayResult = new JSONArray();
		        JSONArray jsonArraySortedFinal = new JSONArray();
		        
 
		        String display_group_by_names="";
		        System.out.println("displaaaay"+display);
		        if (display.equals(new String("area")) || display.equals(new String("line")) )
		        {
		        	for (int i=0;i<jsonArray.length();i++)
		        	{
			        	display_group_by_names="";
		        		JSONArray  MyArrayItem=new JSONArray(jsonArray.getJSONArray(i).toString());
		        		JSONArray jsonArraySortedItem = new JSONArray();
		        		//System.out.println("MyArrayItem"+MyArrayItem);
		        		 for (int j=1;j<MyArrayItem.length()-2;j++)
		        		 {
		        		//	 System.out.println("focus on this item"+MyArrayItem.get(j).toString());
		        			// System.out.println("length"+jsonArray.length());
		        			 //System.out.println();
		        			 //System.out.println(j);
		        			// System.out.println(MyArrayItem.get(j));
		        			 display_group_by_names+=MyArrayItem.get(j).toString()+" / ";

		        		 }
		        		 if (JSONObject.NULL.equals(MyArrayItem.get(MyArrayItem.length()-2)))
		        		 {
		        			 jsonArraySortedItem.put("Others");
		        		 }
		        		 else
		        		 {
		        			 if (display_group_by_names.equals(new String("")))
			        			 jsonArraySortedItem.put(MyArrayItem.get(MyArrayItem.length()-2).toString());
		        				 else
		        			 jsonArraySortedItem.put(display_group_by_names+MyArrayItem.get(MyArrayItem.length()-2).toString());
		        		 }
		        	
		        		 if (JSONObject.NULL.equals(MyArrayItem.get(0)))
		        		 {
		        			 jsonArraySortedItem.put("Others");
		        		 }
		        		 else
		        		 {
			        		 jsonArraySortedItem.put(MyArrayItem.get(0).toString());

		        		 }
		        		 jsonArraySortedItem.put(MyArrayItem.get(MyArrayItem.length()-1));
		    
		        		 jsonArraySortedFinal.put(jsonArraySortedItem);
		        	}
	        		// System.out.println("check this "+jsonArraySortedFinal);
		        	jsonArray=jsonArraySortedFinal;
		        	//System.out.println("jsonArraySortedFinal"+jsonArraySortedFinal);
		        }
		        if (display.equals(new String("line")) || display.equals(new String("area")) || display.equals(new String("stackv")))
		        {
		        	int i=0;
		        	String X_FINALE = null;
		        	while (i<(jsonArray.length()-1))
		        	{
		        		JSONObject jsonObj = new JSONObject();
		        		JSONArray  MyArrayItem=new JSONArray(jsonArray.getJSONArray(i).toString());
		        		JSONArray  MyArrayItemNext=new JSONArray(jsonArray.getJSONArray(i+1).toString());
		        	  	//System.out.println("MyArrayItemNext"+MyArrayItemNext.toString());
		        		String X;
		        		 if (JSONObject.NULL.equals(MyArrayItem.get(0)))
		        			 X="Others";
		        		 else
		        		 X=(String) MyArrayItem.get(0);
		        		 
			    	    jsonObj.put("name",X);
				  		JSONArray series = new JSONArray();
				  		String xNext;
		        		 if (JSONObject.NULL.equals(MyArrayItemNext.get(0)))
			        		 xNext="Others";	 
			        		 else 
			        		 xNext=(String) MyArrayItemNext.get(0);
		        			JSONObject jsonObjInSeries_debut = new JSONObject();
		        			//System.out.println(" MyArrayItem.getString(1)"+ MyArrayItem.getString(1));
		        			String name_debut=(JSONObject.NULL.equals( MyArrayItem.get(1)))?"Others":(String)  MyArrayItem.get(1);
		        			jsonObjInSeries_debut.put("name", name_debut);
		        			//System.out.println(" MyArrayItem.getNumber(2)"+ MyArrayItem.getNumber(2));
		        			jsonObjInSeries_debut.put("value", MyArrayItem.getNumber(MyArrayItem.length()-1));
				  			series.put(jsonObjInSeries_debut);
				  		//	System.out.println(jsonObjInSeries_debut);
				  		while (X.equals(xNext))
				  		{
				  		//	System.out.println("while (X.equals(xNext))");
				  			JSONObject jsonObjInSeries = new JSONObject();
				  			String name=(JSONObject.NULL.equals(MyArrayItemNext.get(1)))?"Others":(String) MyArrayItemNext.get(1);

				  			jsonObjInSeries.put("name", name);
				  			jsonObjInSeries.put("value", MyArrayItemNext.getNumber(MyArrayItemNext.length()-1));
				  		//	System.out.println(jsonObjInSeries.toString());
				  			series.put(jsonObjInSeries);
				  			 i++;
				  			 if (i==(jsonArray.length()-1))
				  			 {
				  				 MyArrayItem=new JSONArray(jsonArray.getJSONArray(i).toString());
				  				 if (JSONObject.NULL.equals(MyArrayItem.get(0)))
				  					X_FINALE="Others";
				        		 else
				        		 X_FINALE=(String) MyArrayItem.get(0);
				  				 if (X.equals(X_FINALE))
				  				 {
				  					 String namefinal=(JSONObject.NULL.equals(MyArrayItem.get(1)))?"Others":(String) MyArrayItem.get(1);
				  					jsonObjInSeries.put("name", namefinal);
						  			jsonObjInSeries.put("value", MyArrayItem.getNumber(MyArrayItem.length()-1));
						  			series.put(jsonObjInSeries);
						  		//	System.out.println("jsonObjInSeries.toString() DANS IF"+jsonObjInSeries.toString());

				  				 }
				  				 else
				  				 {
				  					String X1;
					        		 if (JSONObject.NULL.equals(MyArrayItem.get(0)))
					        			 X1="Others";
					        		 else
					        			 X1=(String) MyArrayItem.get(0);
					        		 
						    	    jsonObj.put("name",X1);
							  		JSONArray series1 = new JSONArray();
							  		JSONObject jsonObjInSeries1 = new JSONObject();
							  		jsonObjInSeries1.put("name", MyArrayItemNext.getString(1));
							  		jsonObjInSeries1.put("value", MyArrayItemNext.getNumber(2));
						  			series.put(jsonObjInSeries1);
						  		//	System.out.println("jsonObjInSeries1.toString() DANS ELSE"+jsonObjInSeries1.toString());
				  				 }
				  					 
				  				break;	 
				  			 }
				  			 else
				  			 {
				  				 MyArrayItem=new JSONArray(jsonArray.getJSONArray(i).toString());
				        		 MyArrayItemNext=new JSONArray(jsonArray.getJSONArray(i+1).toString());
				        		 if (JSONObject.NULL.equals(MyArrayItem.get(0)))
				        			 X="Others";
				        		 else
				        		 X=(String) MyArrayItem.get(0);
				        		 if (JSONObject.NULL.equals(MyArrayItemNext.get(0)))
					        		 xNext="Others";	 
					        	else 
					        	xNext=(String) MyArrayItemNext.get(0); 
				  			 }
				  			
				  		}
				  		jsonObj.put("series",series);
				  	//	System.out.println("jsonObj"+jsonObj);
				  		jsonArrayResult.put(jsonObj);
				  		i++;
		        	}
		        }
		        //*******************
		        else if (display.equals(new String("combo")))
		        {
		        	JSONObject JSONObjItem = new JSONObject(param2.getJSONObject(1).toString());

		        	
		        	JSONArray Barchart= new JSONArray();
		        	JSONArray Linechart= new JSONArray();
		        	JSONArray series= new JSONArray();
		        	 JSONObject LineChartObj = new JSONObject();
		        	 LineChartObj.put("name",JSONObjItem.getString("nom"));
		        	 
				       System.out.println("result of fectchin data"+jsonArray);
				      	for (int i = 0; i < jsonArray.length()/2; i++) 
			        	{
						    JSONArray jsonArrayItem = jsonArray.getJSONArray(i);
						    JSONObject jsonObj = new JSONObject();
						    JSONObject jsonObj2 = new JSONObject();

						
							    jsonObj.put("name",jsonArrayItem.get(0));	   
							    jsonObj.put("value",jsonArrayItem.get(1));
							    jsonObj2.put("name",jsonArrayItem.get(0));	   
							    jsonObj2.put("value",jsonArrayItem.get(2));
							    Barchart.put(jsonObj);
							    series.put(jsonObj2);
					
						    
						
						}
				      	LineChartObj.put("series", series);
				      	Linechart.put(LineChartObj);
				        jsonArrayResult.put(Barchart);
					    jsonArrayResult.put(Linechart);
		        }
		        
		        //********************
		        else
		        {
		        	for (int i = 0; i < jsonArray.length()/3; i++) 
		        	{
					    JSONArray jsonArrayItem = jsonArray.getJSONArray(i);
					    JSONObject jsonObj = new JSONObject();
					    if (jsonArrayItem.get(0).equals(null))
					    	jsonObj.put("name","Others");
					    else 
					    jsonObj.put("name",jsonArrayItem.get(0));	   
					    jsonObj.put("value",jsonArrayItem.get(1));
					    jsonArrayResult.put(jsonObj);
					}
		        }
		        	//System.out.println("jsonArrayResult"+jsonArrayResult);
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
