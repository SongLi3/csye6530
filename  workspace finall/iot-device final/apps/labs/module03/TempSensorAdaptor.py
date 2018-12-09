'''
Created on 2018年9月15日
 
@author: jrq
'''
import os,sys
import threading
sys.path.append('/home/pi/Desktop/workspace/iot-device/apps')

 
from labs.common import ConfigConst
from labs.common import ConfigUtil 
from labs.common import ActuatorData
from labs.common import SensorData
from labs.module03 import SmtpClientConnector
from labs.module03 import TempActuatorEmulator
from labs.module03 import SimpleLedActivator
from labs.module03 import  CoapSimpleClientConnector
 
from sense_hat import SenseHat
from time import sleep
 
DEFAULT_RATE_IN_SEC = 10
 
sen = SenseHat()
Tem = TempActuatorEmulator.TempActuatoeEmulator()
Sens = SensorData.SensorData()
SmtpConnector = SmtpClientConnector.SmtpClientConnector()
Act = ActuatorData.ActuatorData()
Sim=SimpleLedActivator.SimpleLedActivator()
coapConnector = CoapSimpleClientConnector.CoapSimpleClientConnector()
 
class TempSensorAdaptor (threading.Thread):
    enableEmulator = False
    isPrevTempSet  = False
    rateInSec      = DEFAULT_RATE_IN_SEC
    Sens.setName('Temperature')
    alertDiff = 2
    alertDiffb = 0
   
    def __init__(self, rateInSec = DEFAULT_RATE_IN_SEC):
        super(TempSensorAdaptor, self).__init__()
         
        if rateInSec > 0:
            self.rateInSec = rateInSec
         
    def run(self):
        self.a = True
        self.enableEmulator = True
        while self.a:
            if self.enableEmulator:
                self.curTemp = sen.get_temperature()
                coapConnector.initClient()
                coapConnector.handlePostTest("Temp", str(self.curTemp))
                SmtpClientConnector.publish("Temp", str(self.curTemp))
                
            sleep(self.rateInSec)
        
    # trager the actuator      
    def implementActuator(self, data):
        Tem.processMessage(data)     
#                     
                                    
      
                 
                 
                 
                 
             



