package org.songli.iot.module6;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.songli.iot.common.ConfigConst;
//only include this if you have a ‘ConfigConst’ class...


	
public class MqttClientConnector implements MqttCallback
{	
	// static
	private static final Logger _Logger = 
			Logger.getLogger(MqttClientConnector.class.getName());
	
	// using ConfigConst parameter
	private String _protocol = ConfigConst.DEFAULT_MQTT_PROTOCOL;
	private String _host = ConfigConst.DEFAULT_MQTT_SERVER;
	private int _port = ConfigConst.DEFAULT_MQTT_PORT;
	
	private String _clientID;
	private String _brokerAddr;
	
	private MqttClient _mqttClient;
	private String _userName;
	private String _pemFileName;
	private boolean _isSecureConn;
	
	// constructors
	/**
	 * Default.
	 *
	 */
	 public MqttClientConnector()
	 {
		 // use defaults
		 this(null, false);
	 }
	
	 /**
	 * Constructor.
	 *
	 * @param host The name of the broker to connect.
	 * @param isSecure Currently unused.
	 */
	 public MqttClientConnector(String host, boolean isSecure)
	 {
		 super();
		 
		 // NOTE: 'isSecure' ignored for now
		 if (host != null && host.trim().length() > 0) {
		 _host = host;
		 }
		 
		 // NOTE: URL does not have a protocol handler for "tcp",
		 // so we need to construct the URL manually
		 _clientID = MqttClient.generateClientId(); 
		 _Logger.info("Using client ID for broker conn: " + _clientID);
		 _brokerAddr = _protocol + "://" + _host + ":" + _port; 
		 _Logger.info("Using URL for broker conn: " + _brokerAddr);
	 }
	 
	 //when connecting Ubidots
	 public MqttClientConnector(String host, String userName, String pemFileName)
	 {
		 super();
		 if (host != null && host.trim().length() > 0) 
		 {
			 _host = host;
		 }
		 
		 if (userName != null && userName.trim().length() > 0) 
		 {
			 _userName = userName;
		 }
		 
		 if (pemFileName != null) {
			 File file = new File(pemFileName);
			 if (file.exists()) {
				 _protocol = "ssl";
				 _port = 8883;
				 _pemFileName = pemFileName;
				 _isSecureConn = true;
				 _Logger.info("PEM file valid. Using secure connection: " + _pemFileName);
			 } else {
				 _Logger.warning("PEM file invalid. Using insecure connection: " + pemFileName);
			 }
		 }
		 _clientID = MqttClient.generateClientId();
		 _brokerAddr = _protocol + "://" + _host + ":" + _port;
		 _Logger.info("Using URL for broker conn: " + _brokerAddr);
	 }
	 
	 // public methods
	 public void connect()
	 {
		 if (_mqttClient == null) {
			 // TODO: making memory remainnable 
			 MemoryPersistence persistence = new MemoryPersistence();
			 
		try {
			 _mqttClient = new MqttClient(_brokerAddr, _clientID, persistence); 		
			 MqttConnectOptions connOpts = new MqttConnectOptions();
			 connOpts.setCleanSession(true);		
			_Logger.info("Connecting to broker: " + _brokerAddr);
			_mqttClient.setCallback(this);
			_mqttClient.connect(connOpts);
			_Logger.info("Connected to broker: " + _brokerAddr);
			 } catch (MqttException e) 
		  	{
			_Logger.log(Level.SEVERE, "Failed to connect to broker: " + _brokerAddr, e);
			 }
		 }
	 }
	 
	 //disconnect Mqtt connection
	 public void disconnect()
	 {
		 try {
			 _mqttClient.disconnect();
			 
			 _Logger.info("Disconnected from broker: " + _brokerAddr);
		 } catch (Exception e) {
			 _Logger.log(Level.SEVERE, "Failed to disconnect from broker: " + _brokerAddr, e);
		 }
	 }
	 	
	 /**
	 * Publishes the given payload to broker directly to topic 'topic'.
	 *
	 * @param topic
	 * @param qosLevel
	 * @param payload
	 */
	 public boolean publishMessage(String topic, int qosLevel, byte[] payload)
	 {
		 boolean success = false;
		 
		 try {
			 _Logger.info("Publishing message to topic: " + topic);
			 
			 // TODO: create a new MqttMessage, pass 'payload' to the constructor
			 MqttMessage msg = new MqttMessage(payload);
			 // TODO: set the QoS on the message to qosLevel
			 msg.setQos(qosLevel);
			 // TODO: call 'publish' on the MQTT client, passing the 'topic' and MqttMessage
			 // instance
		     msg.setRetained(true);
		     _mqttClient.publish(topic,msg);
			 // TODO: log the result - include the ID from the message
		     _Logger.info("msg ID is: " + msg.getId()); 
			 success = true;
	 } catch (Exception e) {
		 	_Logger.log(Level.SEVERE, "Failed to publish MQTT message: " + e.getMessage());
	
	 	}
		 return success;
	 }
	 
	 public boolean subscribeToAll()
	 {
		 try {
			 	_mqttClient.subscribe("$SYS/#");
			 	
			 	return true;
		 } catch (MqttException e) {
			 	_Logger.log(Level.WARNING, "Failed to subscribe to all topics.", e);
		 }
		 return false;
	 }
	 
	// subscribe to specific topic
	 public boolean subscribeToTopic(String topic,int qosLevel)
	 {
		 
		 try {
			 	_mqttClient.subscribe(topic,qosLevel);
			 	_Logger.info("Subscribed to Topic:  " + topic);
			 	return true;
		 } catch (MqttException e) {
			 	_Logger.log(Level.WARNING, "Failed to subscribe to all topics.", e);
		 }
		 return false;
	 }
	 
	 /*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.
	 * Throwable)
	 */
	 public void connectionLost(Throwable t)
	 {
		 // TODO: now what?
		 _Logger.log(Level.WARNING, "Connection to broker lost. Will retry soon.", t);
	 }
	 
	 /*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho
	 * .client.mqttv3.IMqttDeliveryToken)
	 */
	 public void deliveryComplete(IMqttDeliveryToken token)
	 {
		 // TODO: what else should you do here?
		 try {
			 _Logger.info("Delivery complete: " + token.getMessageId() + " - " + token.getResponse() + " - "
					 	  + token.getMessage());
		 } catch (Exception e) {
			 _Logger.log(Level.SEVERE, "Failed to retrieve message from token.", e);
		 }
	 }
	 
	 /*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String,
	 * org.eclipse.paho.client.mqttv3.MqttMessage)
	 */
	 public void messageArrived(String data, MqttMessage msg) throws Exception
	 {
		 // TODO: should you analyze the message or just log it?
		 _Logger.info("Message arrived: " + data + ", " + msg.getId());
	 }
}

