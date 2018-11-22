package org.songli.iot.module6;

import java.util.logging.Logger;
import org.songli.iot.common.ConfigConst;


public class MqttPubClientTestApp {
	
	 	// static
	
	 private static final Logger _Logger =
			 Logger.getLogger(MqttPubClientTestApp.class.getName());
	 
	 private static MqttPubClientTestApp _App;
	 
	 /**
	 * @param args
	 */
	 public static void main(String[] args)
	 {
		 _App = new MqttPubClientTestApp();
		 
		 try {
		 _App.start();
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
	 }
	 
	 // params
	 private MqttClientConnector _mqttClient;
	 
	
	// constructors
	/**
	 * Default.
	 */
	 public MqttPubClientTestApp()
	 {
		 super();
	 }
	 
	 // public methods
	 /**
	 * Connect to the MQTT client, then:
	 * 
	 *  If this is the publish app, publish a test message to the given topic
	 */
	 public void start()
	 {
		 _mqttClient = new MqttClientConnector();
		 _mqttClient.connect();
		 String topicName = "test";
		 String payload = "This is a test...";
		 // only for publishing...
		 _mqttClient.publishMessage(topicName, 2, payload.getBytes());
		 //_mqttClient.disconnect();
	 }
}
