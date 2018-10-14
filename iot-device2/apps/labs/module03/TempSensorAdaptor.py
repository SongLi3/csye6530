'''
Created on 2018年9月15日

@author: jrq
'''

from time import sleep
import os,sys
sys.path.append('/home/pi/workspace/iot-device/apps')
from labs.common import SensorData
from labs.module03 import SmtpClientConnector
from labs.common import ConfigUtil 
from labs.common import ActuatorData
from labs.module03 import TempActuatorEmulator
from labs.module03 import SimpleLedActivator
from labs.common import ConfigConst
from sense_hat import SenseHat
import threading


sen = SenseHat()
DEFAULT_RATE_IN_SEC = 10
Tem = TempActuatorEmulator.TempActuatoeEmulator()
Sens = SensorData.SensorData()
SmtpConnector = SmtpClientConnector.SmtpClientConnector()
Act = ActuatorData.ActuatorData()
Sim=SimpleLedActivator.SimpleLedActivator()

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
        while True:
            if self.enableEmulator:
                self.curTemp = sen.get_temperature() 
                Sens.addValue(self.curTemp)
                #self.SensorData.addValue(self.curTemp)
                print('\n--------------------')
                print('New sensor readings:')
                print(' ' + str(self.curTemp))
                
                if self.isPrevTempSet == False:
                    self.prevTemp = self.curTemp
                    self.isPrevTempSet = True
                else:
                    print (Sens.__str__())
                    print ('\nCurTemp - AvgValue = ' + str(abs(self.curTemp - Sens.getAvgValue())))
                    print ('Threshold          = ' + str(self.alertDiff))
                    if (abs(self.curTemp - Sens.getAvgValue()) >= self.alertDiff):
                        print('\nCurrent temp exceeds average by > ' + str(self.alertDiff) + '. Triggeringalert...')
                        self.sensorData =   Sens.__str__()
                        SmtpConnector.publishMessage('Exceptional sensor data [test]', self.sensorData)
                    self.config = ConfigUtil.ConfigUtil('/home/pi/workspace/iot-device/data/ConnectedDevicesConfig.props')
                    self.config.loadConfig()
                    aler = self.config.getProperty(ConfigConst.CONSTRAINED_DEVICE, ConfigConst.NB)
                    if(self.curTemp-float(aler) >self.alertDiffb):
                        print('_______________should decrease tem___________')
                        Act.setCommand(0)
                            #Act.setName('Not set')
                            #Act.setStateData(None)
                            #Act.setStatusCode(0)
                        self.decrease = self.curTemp-float(aler)
                        Act.setValue(self.decrease)   
                            #print(self.AdaptorData)
                        Tem.processMessage('topic',Act)     
                            
                    if(self.curTemp-float(aler)<self.alertDiffb):
                        print('_______________should increase tem___________')
                        Act.setCommand(1)
                        self.increase =float(aler)-self.curTemp #Act.setName('Not set')
                        Act.setValue(self.increase)  #Acts=Act.updateData(self, self.Act)
                        #Act.updateData(self.AdaptorData)
                        #self.AdaptorData =Act.__str__()
                        #print(self.AdaptorData)
                        Tem.processMessage('topic', Act)
                                   
                sleep(self.rateInSec)
                
                
                
                
                
            