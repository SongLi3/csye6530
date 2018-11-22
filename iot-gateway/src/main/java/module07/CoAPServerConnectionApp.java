/**
 * 
 */
package module07;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CoAPServerConnectionApp {

	private static final Logger _Logger = Logger.getLogger(CoAPServerConnectionApp.class.getName());

	public CoAPServerConnectionApp() {
		super();
	}

	// to create instances of the CoapServerConnector and TempResource class
public static void main(String[] args) throws InterruptedException{
		//set the resource name Test and start the method.
		ResourceTemp tResource= new ResourceTemp("Test");
		CoapServerConnector coapServer= new CoapServerConnector(tResource);
		coapServer.start(); 
		_Logger.info("Starting the server");
	}

}
