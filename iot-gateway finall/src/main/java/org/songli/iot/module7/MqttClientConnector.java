 package org.songli.iot.module7;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.songli.iot.common.ConfigConst;
	
 class MqttClientConnector implements MqttCallback
{	
	// static
	private static final Logger _Logger = 
			Logger.getLogger(MqttClientConnector.class.getName());
	
	// parameter
	private String _protocol = ConfigConst.DEFAULT_MQTT_PROTOCOL;
	private String _host = ConfigConst.DEFAULT_MQTT_SERVER;
	private int _port = ConfigConst.DEFAULT_MQTT_PORT;
	
	private String _clientID;
	private String _brokerAddr;
	
	private MqttClient _mqttClient;
	private String _userName;
	private String _pemFileName;
	private boolean _isSecureConn;
	
	static String lk;
	
	/**
	 * Default.
	 * constructors
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
	 
	 /**
	 * @param host
	 * @param userName
	 * @param pemFileName
	 * connecting ubidots
	 */
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
	 	 
	 /**
	 * connect to mosquitto
	 */
	public void connect()
	 {
		 if (_mqttClient == null) {
			 MemoryPersistence persistence = new MemoryPersistence();
			 try {
				 _mqttClient = new MqttClient(_brokerAddr, _clientID, persistence);
				 MqttConnectOptions connOpts = new MqttConnectOptions();
				 // TODO: do we always want a clean session?
				 connOpts.setCleanSession(true);
				 if (_userName != null) {
					 connOpts.setUserName(_userName);
				 }
				 
				 // if we are using a secure connection, there's a bunch of stuff we need to do...
				 if (_isSecureConn) {
					 initSecureConnection(connOpts);
				 }
				 _mqttClient.setCallback(this);
				 _mqttClient.connect(connOpts);
				 _Logger.info("Connected to broker: " + _brokerAddr);
			 } catch (MqttException e) {
				 _Logger.log(Level.SEVERE, "Failed to connect to broker: " + _brokerAddr, e);
			 }
		 }
	 }
	 
	 /**
	 * @param connOpts
	 * this is certification of Ubidots 
	 */
	private void initSecureConnection(MqttConnectOptions connOpts)
	 {
		 try {
			 _Logger.info("Configuring TLS...");
			 SSLContext sslContext = SSLContext.getInstance("SSL");
			 KeyStore keyStore = readCertificate();
			 TrustManagerFactory trustManagerFactory =
			 TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			 trustManagerFactory.init(keyStore);
			 sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
			 connOpts.setSocketFactory(sslContext.getSocketFactory());
		 } catch (Exception e) 
		 {
			 _Logger.log(Level.SEVERE, "Failed to initialize secure MQTT connection.", e);
		 }
		 
	 }
	 
	 private KeyStore readCertificate()
	 		throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
	 	{
	 		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
	 		FileInputStream fis = new FileInputStream(_pemFileName);
	 		BufferedInputStream bis = new BufferedInputStream(fis);
	 		CertificateFactory cf = CertificateFactory.getInstance("X.509");
	 		ks.load(null);
	 		
	 		while (bis.available() > 0) 
	 		{
	 			Certificate cert = cf.generateCertificate(bis);
	 			ks.setCertificateEntry("adk_store" + bis.available(), cert);
	 		}
	 		return ks;
	 	}
	 
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
		     System.out.println("public begin:--" + msg);

		     _mqttClient.publish(topic,msg);
		     System.out.println("public success");
		     
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
	 
	 /**
	 * @param topic
	 * @param qosLevel
	 * @return
	 * this is function to subscribe specific topic 
	 */
	public boolean subscribeToTopic(String topic,int qosLevel)
	 {
		 // TODO: subscribe related topic 
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
		 // TODO: it will implement another MQTTconnector, sending the message to mosquitto
		 String lk= new String(msg.getPayload());
		 System.out.println(lk);
		 System.out.println(lk);
		 System.out.println(lk);

		 MqttClientConnectorr _mqttClientt = new MqttClientConnectorr();
		 _mqttClientt.connect();
		 _mqttClientt.publishMessage("Temp", 1, lk.getBytes());
		 _Logger.info("Message arrived: " + data + ", " + new String(msg.getPayload()));
		 
	 }

}

