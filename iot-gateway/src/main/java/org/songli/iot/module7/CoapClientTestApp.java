package org.songli.iot.module7;

import java.util.logging.Logger;

import module07.CoapClientConnector;

public class CoapClientTestApp
{
	// static
	
	private static final Logger _Logger =
			Logger.getLogger(CoapClientTestApp.class.getName());
	
	private static CoapClientTestApp _App;
	
	 /**
	 * @param args
	 */
	 public static void main(String[] args)
	 {
		 
		_App = new CoapClientTestApp();
		
		 try {
			 _App.start();
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	 }
	 
	 // private var's
	 
	 private CoapClientConnector _coapClient;
	 
	 // constructors
	 
	 /**
	 *
	 */
	 public CoapClientTestApp()
	 {
		 super();
	 }
	 
	 // public methods
	 
	 /**
	 * Connect to the CoAP server
	 *
	 */
	 public void start()
	 {
		 _Logger.info("starting the client ");
		 String payload = "this is a test";
		 //create the instance of coap client connector to use the methods. 
		 CoapClientConnector client = new CoapClientConnector("coap", "localhost",5683);
		 client.runTests();
		 client.pingServer();
		 client.sendGetRequest("Temp");
		 client.sendPostRequest("Temp", payload, true);
		 client.sendPutRequest("Temp", payload, true);
		 client.sendDeleteRequest("Temp");
			
	 }
 
}
