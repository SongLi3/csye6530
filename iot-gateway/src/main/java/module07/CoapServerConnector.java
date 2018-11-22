/**
 * 
 */
package module07;

import java.util.logging.Logger;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
//import org.eclipse.californium.core.coap.CoAP.ResponseCode;
//import org.eclipse.californium.core.server.resources.CoapExchange;

public class CoapServerConnector {

	// static
	private static final Logger _Logger = Logger.getLogger(CoapServerConnector.class.getName());
	// private var's
	private CoapServer _coapServer;

	
	public CoapServerConnector() {
		super();
	}
// to set the resource to the coap server.
	public CoapServerConnector(CoapResource resource)
	{
		_coapServer= new CoapServer();
		_coapServer.add(resource);
	}


	// public methods
	public void addDefaultResource(String Name) {

	}
// this method is to add another resource to the coap server 
	public void addResource(CoapResource resource) {
		if (resource != null) {
			_coapServer.add(resource);
		}
	}

	// this method is to start server
	public void start() {
		_coapServer.start();
		_Logger.info("Creating CoAP server...");
	}

	// this method is to stop server
	public void stop() {
		_coapServer.stop();
		_Logger.info("Stopping CoAP server...");
	}

}
