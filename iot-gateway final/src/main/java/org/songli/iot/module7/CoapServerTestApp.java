package org.songli.iot.module7;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author okok_no
 * this is gateway app
 *
 */
public class CoapServerTestApp
{
	// static
	
	private static final Logger _Logger =
			Logger.getLogger(CoapServerTestApp.class.getName());
	
	private static CoapServerTestApp _App;
	
	 /**
	 * @param args
	 */
	 public static void main(String[] args)
	 {
		 _App = new CoapServerTestApp();
		 
		 try {
				 	_App.start();
		 } catch (Exception e) {
			 	e.printStackTrace();
		 }
	 }
	
	 private CoapServerConnector _coapServer;
	 	
	 /**
	 *constructors 
	 */
	 public CoapServerTestApp()
	 {
		 super();
	 }
	
	 /**
	 * public methods
	 */
	 public void start()
	 {    // start the coap server on local gateway
		 _coapServer = new CoapServerConnector();
		 _coapServer.start();		 
	 }
 
}