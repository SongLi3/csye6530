//package org.songli.iot.common;
//
//import java.io.Serializable;
//import java.text.SimpleDateFormat;
//import java.util.Date;
///**
// *
// */
//public class SensorData implements Serializable 
//{
//	// static
//	 /**
//	 *
//	 */
//	private static final long serialVersionUID = 1L;
// // private var's
//	private String _timeStamp = null;
//	private String _name = "Not Set";
//	private float _curValue = 0.0f;
//	private float _avgValue = 0.0f;
//	private float _minValue = 0.0f;
//	private float _maxValue = 0.0f;
//	private float _totValue = 0.0f;
//	private int _sampleCount = 0;
//	
//		// constructors
//	
//	 /**
//	 * Default.
//	 *
//	 */
//	 public SensorData()
//	 {
//		 super();
//		 
//		 updateTimeStamp();
//	 }
//	 
//	 // public methods
//	 
//	 /**
//	 *
//	 * @param val
//	 */
//	 public void addValue(float val)
//	 {
//		 updateTimeStamp();
//		 
//		 ++_sampleCount;
//		 
//		 _curValue = val;
//		 _totValue += val;
//		 
//		 if (_curValue < _minValue) {
//		 _minValue = _curValue;
//		 }
//		 
//		 if (_curValue > _maxValue) {
//		 _maxValue = _curValue;
//		 }
//		 
//		 if (_totValue != 0 && _sampleCount > 0) {
//		 _avgValue = _totValue / _sampleCount;
//		 }
//	 
//	 }
//	 
//	 public void fromXml(String xmlData)
//	 {
//		 // TODO: implement this
//		 String host = "mail.pop3.host"
//		 String from = ...;
//		 String to = ...;
//		  
//		 // Get system properties
//		 Properties props = System.getProperties();
//		  
//		 // Setup mail server
//		 props.put("mail.smtp.host", host);
//		  
//		 // Get session
//		 Session session = Session.getDefaultInstance(props, null);
//		  
//		 // Define message
//		 MimeMessage message = new MimeMessage(session);
//		 message.setFrom(new InternetAddress(from));
//		 message.addRecipient(Message.RecipientType.TO,
//		 new InternetAddress(to));
//		 message.setSubject("Hello JavaMail");
//		 message.setText("Welcome to JavaMail");
//		  
//		 // Send message
//		 Transport.send(message);
//	 }
//	 
//	 public float getAvgValue()
//	 {
//		 return _avgValue;
//	 }
//	 
//	 public float getMaxValue()
//	 {
//	
//		 return _maxValue;
//	 }
//	 
//	 public float getMinValue()
//	 {
//		 return _minValue;
//	 }
//	 
//	 public String getName()
//	 {
//		 return _name;
//	 }
//	 
//	 public float getValue()
//	 {
//		 return _curValue;
//	 }
//	 
//	 public void setName(String name)
//	 {
//		 if (name != null && name.trim().length() > 0) {
//			 _name = name;
//	 }
//	 }
//	 
//	 // private methods
//	 
//	 /**
//	 *
//	 */
//	 private void updateTimeStamp()
//	 {
//		 _timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm.ss").format(new Date());
//	 }
//	}
