package org.songli.iot.module6;

import java.util.logging.Logger;
import org.songli.iot.common.ConfigConst;



public class MqttSubClientTestApp
	{
		// static
	 private static final Logger _Logger =
			 Logger.getLogger(MqttSubClientTestApp.class.getName());
	 
	 private static MqttSubClientTestApp _App;
	 
	 /**
	 * @param args
	 */
	 public static void main(String[] args)
	 {
		 _App = new MqttSubClientTestApp();
		 
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
	 public MqttSubClientTestApp()
	 {
		 super();
	 }
	 
	 // public methods
	 /**
	  * Connect to the MQTT client, then:
	  * 1) If this is the subscribe app, subscribe to the given topic
	  * 
	  */
	 public void start()
	 {
		 _mqttClient = new MqttClientConnector();
		 _mqttClient.connect();
		 String topicName = "test";
		 // only for subscribing...
		 _mqttClient.subscribeToTopic(topicName,2); 
		 //  _mqttClient.subscribeToAll(); 
		 //_mqttClient.disconnect();
	 }
}
