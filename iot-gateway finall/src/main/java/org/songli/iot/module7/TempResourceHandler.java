package org.songli.iot.module7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class TempResourceHandler extends CoapResource {
	String pathway ="/Users/okok_no/eclipse-workspace/iot-gateway/data/ubidots_cert.pem";
	MqttClientConnector _mqttClient = new MqttClientConnector("things.ubidots.com","A1E-b1OQWqGHz6Ed9xtXisztjg74Dc1aMU",pathway);
	MqttClientConnector _mqttClient1 = new MqttClientConnector();
	
	private static final Logger _Logger = 
			Logger.getLogger(TempResourceHandler.class.getName());
	
	public TempResourceHandler(String name) {
		super(name);
	}
	
	/**(non-Javadoc)
	 * @see org.eclipse.californium.core.CoapResource#handleGET(org.eclipse.californium.core.server.resources.CoapExchange)
	 * override the methods of CoapResource class
	 * finishing the get function when getting the request from the client
	 */
	@Override
	public void handleGET(CoapExchange exchange) {
		exchange.accept();
		exchange.respond(ResponseCode.VALID, "Getting resource  ");
		
		_Logger.info("Handling Get:" + ResponseCode.VALID.toString());
		_Logger.info( exchange.getRequestCode().toString() + ":" + exchange.getRequestText());
		
		String msg = new String(exchange.getRequestPayload());
		
		if(_mqttClient != null) {
			try {
				_mqttClient.publishMessage("/v1.6/devices/homeiotgateway/tempsensor/lv", 1,msg.getBytes() );
			}catch (Exception e) {
				_Logger.warning("No MQTT broker available.");
			}
		}
	}

	/** (non-Javadoc)
	 * @see org.eclipse.californium.core.CoapResource#handlePOST(org.eclipse.californium.core.server.resources.CoapExchange)
	 *override the methods of CoapResource class
	 *finishing the post function when getting the request from the client
	 */
	@Override
	public void handlePOST(CoapExchange exchange) {
		
		exchange.accept();
		exchange.respond(ResponseCode.CREATED, "Created item via post ");
		
		_Logger.info("Handling Post:" + ResponseCode.CREATED.toString());
		_Logger.info( exchange.getRequestCode().toString() + ":" + exchange.getRequestText());
		
		String msg = new String(exchange.getRequestPayload());
		File f = new File("/Users/okok_no/a.txt");
		FileOutputStream fop;
		try {
			fop = new FileOutputStream(f);
			fop.write(msg.getBytes());
			fop.flush();
			fop.close();
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(_mqttClient != null) {
			try {
				_mqttClient.connect();
				_mqttClient.publishMessage("/v1.6/devices/homeiotgateway/tempsensor", 1,msg.getBytes() );
			}catch (Exception e) {
				_Logger.warning("No MQTT broker available.");
			}
		}
	}
	
	/** (non-Javadoc)
	 *override the methods of CoapResource class
	 *finishing the put function when getting the request from the client
	 * @see org.eclipse.californium.core.CoapResource#handlePUT(org.eclipse.californium.core.server.resources.CoapExchange)
	 */
	@Override
	public void handlePUT(CoapExchange exchange) {
		exchange.accept();
		exchange.respond(ResponseCode.CHANGED, "Putting resource ");
		
		_Logger.info("Handling Put:" + ResponseCode.CHANGED.toString());
		_Logger.info( exchange.getRequestCode().toString() + ":" + exchange.getRequestText());
		
		String msg = new String(exchange.getRequestPayload());
		
		if(_mqttClient != null) {
			try {
				_mqttClient.publishMessage("/v1.6/devices/homeiotgateway/tempsensor/lv", 1,msg.getBytes() );
			}catch (Exception e) {
				_Logger.warning("No MQTT broker available.");
			}
		}
	}
	
	/**(non-Javadoc)
	 * @see org.eclipse.californium.core.CoapResource#handleDELETE(org.eclipse.californium.core.server.resources.CoapExchange)
	 * override the methods of CoapResource class
	 * finishing the delete function when getting the request from the client
	 */
	@Override
	public void handleDELETE(CoapExchange exchange) {
		exchange.accept();
		exchange.respond(ResponseCode.DELETED, "Deleting resource ");
		
		_Logger.info("Handling Delete:" + ResponseCode.DELETED.toString());
		_Logger.info( exchange.getRequestCode().toString() + ":" + exchange.getRequestText());
		
		String msg = new String(exchange.getRequestPayload());
		
		if(_mqttClient != null) {
			try {
				_mqttClient.publishMessage("/v1.6/devices/homeiotgateway/tempsensor/lv", 1,msg.getBytes() );
			}catch (Exception e) {
				_Logger.warning("No MQTT broker available.");
			}
		}
		
	}

}
