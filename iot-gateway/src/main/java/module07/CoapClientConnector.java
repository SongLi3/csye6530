/**
 * 
 */
package module07;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

public class CoapClientConnector {
	// static
	private static final Logger _Logger = Logger.getLogger(CoAPClientConnectionApp.class.getName());

	private String _protocol = "coap";
	private String _host = "localhost";
	private int _port = 5683;
	private String _currUrl = null;
	private CoapClient _coapClient;
	private String _serverAddr;
	private boolean _isInitialized;

	// this method is to choose the protocol and set the the address
	public CoapClientConnector(String protocol,String host, int port) {
		super();
		 _protocol=protocol;
		 _host=host;
		 _port=port;
		 _serverAddr = _protocol + "://" + _host + ":" + _port;
	     _Logger.info("Using URL for server connection: " + _serverAddr);
	     _isInitialized=false; 
		 
		_currUrl = new String(_protocol + "://" + _host + ":" + _port + "/");
	}

	// public methods
	// run all test function to make sure wo could use all API
	public void runTests() {
		initClient();
		pingServer();
		discoverResources();
		sendGetRequest();
	}

	// to check whether the action is successful
	public void pingServer() {
		 _Logger.info("Sending ping...");
		initClient();
		if (_coapClient.ping()) {
			_Logger.info("Sending ping...successfully!");
		}
	}

	// to find out all the resources 
	public void discoverResources() {
		initClient();
		_Logger.info(" discover......");
		Set<WebLink> webLinkSet = _coapClient.discover();
		if (webLinkSet != null) {
			for (WebLink wl : webLinkSet) {
				_Logger.info("--> WebLink:" + wl.getURI());
			}
		}
	}
	/*
	 * resource should only be the file path
	 */

	public void sendGetRequest() {
		sendGetRequest(null);
	}
// the API which is to get the resource 
	public void sendGetRequest(String resource) {
		initClient(resource);
		handleGetRequest(true);
	}

	public void sendDeleteRequest() {
		initClient();
		handleDeleteRequest();
	}

// the API which is to delete the resource 
	public void sendDeleteRequest(String resourceName) {
		_isInitialized = false;
		initClient(resourceName);
		handleDeleteRequest();
	}

// the API which is to post the resource 
	public void sendPostRequest(String payload, boolean useCON) {
		initClient();
		handlePostRequest(payload, useCON);
	}

	// the API which is to post the resource
	public void sendPostRequest(String resourceName, String payload, boolean useCON) {
		initClient(resourceName);
		handlePostRequest(payload, useCON);
	}

	public void sendPutRequest(String payload, boolean useCON) {
		initClient();
		handlePutRequest(payload, useCON);
	}

	// the API which is to update the resource
	public void sendPutRequest(String resourceName, String payload, boolean useCON) {
		_isInitialized = false;
		initClient(resourceName);
		handlePutRequest(payload, useCON);
	}

	private void initClient() {
		initClient(null);
	}

	// this method is to connect to resource 
	private void initClient(String resource) {

		if (_coapClient != null) {
			_coapClient.shutdown();
			_coapClient = null;
		}
		try {
			if (resource != null && resource.trim().length() > 0) {
				_coapClient = new CoapClient(_currUrl + resource);
				 _Logger.info("Created client connection to server / resource: " + _serverAddr);
			} else {
				_coapClient = new CoapClient(_currUrl);
				 _Logger.info("Created client connection to server / resource: " + _serverAddr);
			}
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to connect to server: ", e);
		}
	}

	// this method is to complete the get function when it receives the get request 
	private void handleGetRequest(boolean useCON) {

		CoapResponse response = null;
		if (useCON) {
			_coapClient.useCONs();
		}
		response = _coapClient.get();
		if (response != null) {
			byte[] Payload = response.getPayload();
			_Logger.info("ResponseCode: " + response.getCode());
			_Logger.info("ResponsePayload: "  + new String(Payload));
		} else {
			_Logger.warning("No response received from server/resource ");
		}
	}

	// this method is to complete the delete function when it receives the delete request 
	private void handleDeleteRequest() {
		_Logger.info(" Deleting ...");
		CoapResponse response = null;
		response = _coapClient.delete();
		_Logger.info("Response: " + response.isSuccess() );
		_Logger.info("ResponseCode: " + response.getCode());

	}

	// this method is to complete the update function when it receives the put request
	private void handlePutRequest(String payload, boolean useCON) {
		_Logger.info("Update...");
		CoapResponse response = null;
		if (useCON) {
			_coapClient.useCONs().useEarlyNegotiation(32).get();
		}
		response = _coapClient.put(payload, MediaTypeRegistry.TEXT_PLAIN);
		if (response != null) {
			byte[] Payload = response.getPayload();
			_Logger.info("Response: " + response.isSuccess());
			_Logger.info("ResponseCode: " + response.getCode());
			_Logger.info("ResponsePayload: "  + new String(Payload));
		} else {
			_Logger.warning("No response received from the server.");
		}
	}

	// this method is to complete the post function when it receives the post request
	private void handlePostRequest(String payload, boolean useCON) {
		_Logger.info("Posting...");
		CoapResponse response = null;
		if (useCON) {
			_coapClient.useCONs().useEarlyNegotiation(32).get();
		}
		response = _coapClient.post(payload, MediaTypeRegistry.TEXT_PLAIN);

		if (response != null) {
			byte[] Payload = response.getPayload();
			_Logger.info("Response: " + response.isSuccess());
			_Logger.info("ResponseCode: " + response.getCode());
			_Logger.info("ResponsePayload: "  + new String(Payload));
		} else {
			_Logger.warning("No response received from the server.");
		}
	}

	// this method is to get the current URL 
	public String getCurrentUri() {
		return (_coapClient != null ? _coapClient.getURI() : _serverAddr);
	}
}
