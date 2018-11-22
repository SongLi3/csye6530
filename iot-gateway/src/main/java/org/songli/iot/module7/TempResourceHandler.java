package org.songli.iot.module7;

import java.util.logging.Logger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class TempResourceHandler extends CoapResource {
	private static final Logger _Logger = 
			Logger.getLogger(TempResourceHandler.class.getName());
	
	// set the resource name of TempSource
	public TempResourceHandler(String name) {
		super(name);
	}
	
	//override the methods of CoapResource class
	// finishing the get function when getting the request from the client
	@Override
	public void handleGET(CoapExchange exchange) {
		exchange.respond(ResponseCode.VALID, "Getting resource ");
		_Logger.info("Get request：" + super.getName());
	}

	//override the methods of CoapResource class
	// finishing the post function when getting the request from the client
	@Override
	public void handlePOST(CoapExchange exchange) {
		exchange.respond(ResponseCode.CREATED, "Posting resource ");
		_Logger.info("Post request：" + exchange.getRequestText());
	}
	
	//override the methods of CoapResource class
	// finishing the put function when getting the request from the client
	@Override
	public void handlePUT(CoapExchange exchange) {
		exchange.respond(ResponseCode.CHANGED, "Putting resource");
		_Logger.info("Put request：" + exchange.getRequestText());
	}
	//override the methods of CoapResource class
	// finishing the delete function when getting the request from the client
	@Override
	public void handleDELETE(CoapExchange exchange) {
		exchange.respond(ResponseCode.DELETED, "Deleting resource");
		_Logger.info("Delete request：" + super.getName());
	}

}
