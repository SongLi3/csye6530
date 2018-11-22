
package org.songli.iot.module7;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.songli.iot.common.ConfigConst;

import module07.CoAPClientConnectionApp;

public class CoapClientConnector {
	// static
	private static final Logger _Logger = Logger.getLogger(CoapClientConnector.class.getName());

	private String _protocol = "coap";
	private String _host = "localhost";
	private int _port = 5683;
	private String _currUrl = null;
	private String _serverAddr;
	private CoapClient _coapClient;
	
	private boolean _isInitialized;
	
	public CoapClientConnector()
	 {
		this(ConfigConst.DEFAULT_COAP_SERVER, false);
	 }
	
	public CoapClientConnector(String dEFAULT_COAP_SERVER, boolean b) 
	{
		// TODO Auto-generated constructor stub
	}
	
	// Constructor
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
	public void runTests() {
		initClient();
		pingServer();
		discoverResources();
		sendGetRequest();
		
	}

	// to implement ping
	public void pingServer() {
		 _Logger.info("ping is beginning");
		initClient();
		if (_coapClient.ping()) {
			_Logger.info("ping has been successfully!");
		}
	}

	// to find all the resources 
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
	 * get request
	 */
	public void sendGetRequest() {
		sendGetRequest(null);
	}
	// get the resource 
	public void sendGetRequest(String resource) {
		initClient(resource);
		handleGetRequest(true);
	}

	public void sendDeleteRequest() {
		initClient();
		handleDeleteRequest();
	}

	// delete the resource 
	public void sendDeleteRequest(String resourceName) {
		_isInitialized = false;
		initClient(resourceName);
		handleDeleteRequest();
	}

	// post the resource 
	public void sendPostRequest(String payload, boolean useCON) {
		initClient();
		handlePostRequest(payload, useCON);
	}

	// post the resource
	public void sendPostRequest(String resourceName, String payload, boolean useCON) {
		initClient(resourceName);
		handlePostRequest(payload, useCON);
	}

	public void sendPutRequest(String payload, boolean useCON) {
		initClient();
		handlePutRequest(payload, useCON);
	}

	//update the resource
	public void sendPutRequest(String resourceName, String payload, boolean useCON) {
		_isInitialized = false;
		initClient(resourceName);
		handlePutRequest(payload, useCON);
	}

	private void initClient() {
		initClient(null);
	}

	// connect to resource 
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

	// this method is to configure the get function 
	private void handleGetRequest(boolean useCON) {
		_Logger.info(" getting ...");
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

	// this method is to configure the delete function 
	private void handleDeleteRequest() {
		_Logger.info(" Deleting ...");
		CoapResponse response = null;
		response = _coapClient.delete();
		_Logger.info("Response: " + response.isSuccess() );
		_Logger.info("ResponseCode: " + response.getCode());

	}

	// this method is to configure the update function 
	private void handlePutRequest(String payload, boolean useCON) {
		_Logger.info("put...");
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

	// this method is to configure the post function 
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
