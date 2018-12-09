package org.songli.iot.module7;

import java.util.logging.Logger;


import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class CoapServerConnector
{
	 private static final Logger _Logger =
			 Logger.getLogger(CoapServerConnector.class.getName());
	 
	 private CoapServer _coapServer;
	 
	 /**
	 * Default.
	 *
	 */
	 public CoapServerConnector()
	 {
		 super();
	 }
	 
	 public void addResource(CoapResource resource)
	 {
		 if (resource != null) {
			 _coapServer.add(resource);
		 }
	 }
	 
	 public void start()
	 {
		 if (_coapServer == null) {
			 _Logger.info("Creating CoAP server instance and 'temp' handler...");
			 _coapServer = new CoapServer();
			 
	   // 	 using the override TempResourceHandler
			 TempResourceHandler tempHandler = new TempResourceHandler("Temp");
			 _coapServer.add(tempHandler);
		 }
			_Logger.info("Starting CoAP server...");
			_coapServer.start();
	 }
	 
	 public void stop()
	 {
		 	_Logger.info("Stopping CoAP server...");
		 	_coapServer.stop();
	 }
}
