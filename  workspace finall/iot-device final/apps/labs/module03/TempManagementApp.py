'''
Created on 2018年10月3日
 
@author: okok_no
'''
import os,sys
sys.path.append('/home/pi/Desktop/workspace/iot-device/apps')
 
from time import sleep
from labs.module03 import TempSensorAdaptor
from labs.module03 import MQTTser

Tem = TempSensorAdaptor.TempSensorAdaptor()
print("Starting system performance app daemon thread...")
Tem.start()

Mqtt = MQTTser.MQTTser()
Mqtt.run()
while (True): 
    sleep(2)
    pass