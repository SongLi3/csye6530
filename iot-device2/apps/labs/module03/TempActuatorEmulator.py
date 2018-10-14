'''
Created on 2018年10月3日

@author: okok_no
'''
import os,sys
sys.path.append('/home/pi/workspace/iot-device/apps')
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

    def processMessage(self,topic,actuadata):
        self.data.updateData(actuadata)
        if(self.data.getCommand()==0):
            self.light.setEnableLedFlag(True)
            message = 'Temperature need getting lowered:  '+str(self.data.getValue()) 
            #print(message)
            self.LedActivator.setEnableLedFlag(True)
            self.LedActivator.setDisplayMessage(message)
            print('wait\n')
            self.LedActivator.run()
            #self.light.run()
            print(self.data.__str__())
            
        if(self.data.getCommand()==1):
            self.light.setEnableLedFlag(True)
            message = 'Temperature need higher:            '  +str(self.data.getValue()) 
            #print(message)
            self.LedActivator.setEnableLedFlag(True)
            self.LedActivator.setDisplayMessage(message)
            print('wait\n')
            self.LedActivator.run()
            #self.light.run()
            print(self.data.__str__() )
         
        
        
        
      
        
    
    
        
        
    