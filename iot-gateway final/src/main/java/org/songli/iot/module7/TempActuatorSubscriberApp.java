package org.songli.iot.module7;


import java.util.logging.Logger;
import org.songli.iot.common.ConfigConst;



public class TempActuatorSubscriberApp
	{
		// static
	 private static final Logger _Logger =
			 Logger.getLogger(TempActuatorSubscriberApp.class.getName());
	 
	 private static TempActuatorSubscriberApp _App;
	 
	 /**
	 * @param args
	 */
	 public static void main(String[] args)
	 {
		 _App = new TempActuatorSubscriberApp();
		 
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
	 public TempActuatorSubscriberApp()
	 {
		 super();
	 }
	 
	 // public methods
	 /**
	  * Connect to the MQTT client, then:
	  * 1) If this is the subscribe app, subscribe to the given topic
	  * 2) If this is the publish app, publish a test message to the given topic
	  */
	 public void start()
	 {
			 String pathway ="/Users/okok_no/eclipse-workspace/iot-gateway/data/ubidots_cert.pem";
			 _mqttClient = new MqttClientConnector("things.ubidots.com","A1E-b1OQWqGHz6Ed9xtXisztjg74Dc1aMU",pathway);
			 _mqttClient.connect();
			 String topicname = "/v1.6/devices/homeiotgateway/tempactuator/lv";
			 // only for subscribing...
			 _mqttClient.subscribeToTopic(topicname,1);
			
			
			
	 }
	
}
