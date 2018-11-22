package module07;

import java.util.logging.Logger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class ResourceTemp extends CoapResource {
	private static final Logger _Logger = Logger.getLogger(ResourceTemp.class.getName());
// set the name of the resource and the parameter of TempSource
	public ResourceTemp(String name) {
		super(name);
	}
	public ResourceTemp(String name, boolean visible) {
		super(name, visible);
	}

	//override the methods of CoapResource class
	// to complete the get function when get the request from the client
	@Override
	public void handleGET(CoapExchange exchange) {
		exchange.respond(ResponseCode.VALID, "Get resource ");
		_Logger.info("Get request：" + super.getName());
	}

	
	// to complete the post function when get the request from the client
	@Override
	public void handlePOST(CoapExchange exchange) {
		exchange.respond(ResponseCode.CREATED, "Post resource ");
		_Logger.info("Post request：" + exchange.getRequestText());
	}

	// to complete the put function when get the request from the client
	@Override
	public void handlePUT(CoapExchange exchange) {
		exchange.respond(ResponseCode.CHANGED, "Put resource");
		_Logger.info("Put request：" + exchange.getRequestText());
	}

	// to complete the delete function when get the request from the client
	@Override
	public void handleDELETE(CoapExchange exchange) {
		exchange.respond(ResponseCode.DELETED, "Delete resource");
		_Logger.info("Delete request：" + super.getName());
	}

}
