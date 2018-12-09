'''
Created on 2018年12月6日

@author: okok_no
'''
import os,sys
sys.path.append('/home/pi/Desktop/workspace/iot-device/apps')
import paho.mqtt.client as mqttClient
from time import sleep
from labs.module03 import TempSensorAdaptor

class MQTTser:

    def on_connect(clientConn, data, flags, resultCode):
        print("Client connected to server. Result: " + str(resultCode))
        # subscribe the specific topic
        clientConn.subscribe("Temp") 
    
    def on_message(clientConn, data, msg):
        print("Received PUBLISH on topic {0}. Payload: {1}".format(str(msg.topic), str(msg.payload)))
        # implementing actuator function
        Tem.implementActuator(msg.payload)
        
    def run(self):
        while True:
            self.mc = mqttClient.Client()
            self.mc.on_connect = on_connect
            self.mc.on_message = on_message
            self.mc.connect("test.mosquitto.org", 1883, 60)
            self.mc.loop_forever()
        sleep(5)



