package org.songli.iot.module8;


import java.util.Random;
import java.util.logging.Logger;
import org.songli.iot.common.ConfigConst;


public class TempSensorPublisherApp {
	
	 	// static
	
	 private static final Logger _Logger =
			 Logger.getLogger(TempSensorPublisherApp.class.getName());
	 
	 private static TempSensorPublisherApp _App;
	 
	 /**
	 * @param args
	 */
	 public static void main(String[] args)
	 {
		 _App = new TempSensorPublisherApp();
		 
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
	 public TempSensorPublisherApp()
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
		 _mqttClient = new MqttClientConnector("things.ubidots.com","A1E-Os1nrIWs29oGohzAwmrpihXt08pnJg",pathway);
		 _mqttClient.connect();
		 String topicname = "/v1.6/devices/homeiotgateway/tempsensor/lv";
		 
		 Random rand = new Random();
		 int max =27;
		 int min =18;
		 // nextInt as provided by Random is exclusive of the top value so you need to add 1
		 int temp = rand.nextInt((max - min) + 1) + min;
		 String payload = String.valueOf(temp);
		
		 _mqttClient.publishMessage(topicname,2,payload.getBytes()); 
		 try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	 }
}
