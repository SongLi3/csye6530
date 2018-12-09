'''
Created on 2018年10月3日

@author: okok_no
'''
import os,sys
sys.path.append('/home/pi/Desktop/workspace/iot-device/apps')
import threading

from sense_hat import SenseHat
from labs.common import ActuatorData
from labs.module03 import SenseHatLedActivator
from labs.module03 import SimpleLedActivator

data = ActuatorData.ActuatorData()  
Sense = SenseHat()
LedActivator=SenseHatLedActivator.SenseHatLedActivator()
light = SimpleLedActivator.SimpleLedActivator()

class TempActuatoeEmulator(threading.Thread):
    
    def __init__(self):
        super(TempActuatoeEmulator, self).__init__()
        self.data= ActuatorData.ActuatorData()
        self.LedActivator =SenseHatLedActivator.SenseHatLedActivator()
        self.light =SimpleLedActivator.SimpleLedActivator()
        #when the temperature have unnatural quantity, then let Led show message

    def processMessage(self,actuadata):
        self.light.setEnableLedFlag(True)
        self.LedActivator.setEnableLedFlag(True)
        self.LedActivator.setDisplayMessage(actuadata)
        self.LedActivator.run()

     
         
        
        
        
      
        
    
    
        
        
    