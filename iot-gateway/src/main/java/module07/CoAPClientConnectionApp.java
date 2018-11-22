/**
 * 
 */
package module07;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CoAPClientConnectionApp {

	private static final Logger _Logger = Logger.getLogger(CoAPClientConnectionApp.class.getName());

	private static CoAPClientConnectionApp _App = null;

	public CoAPClientConnectionApp() {
		super();
	}

	
	public static void main(String[] args) {
// create the instance of the client app
		try {
			_App = new CoAPClientConnectionApp();
			_App.start();
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Bad staff ", e);
			System.exit(1);
		}

	}

// Execute the following requests from the client to the server:  ping, GET, POST, PUT, and DELETE
	public void start() {
		_Logger.info("starting the client ");
		String mes1 = "test";
		String mes2 = "another test";
//create the instance of coap client connector to use the methods. 
		CoapClientConnector client = new CoapClientConnector("coap", "localhost",5683);
		client.runTests();
		client.sendGetRequest("Test");
		client.sendPostRequest("Test", mes1, true);
		client.sendPutRequest("Test", mes2, true);
		client.sendDeleteRequest("Test");

	}

	

}
