'''
Created on 2018年9月15日

@author: jrq
'''
import random
from time import sleep
import threading
from labs.common import SensorData
from labs.module02 import SmtpClientConnector 

DEFAULT_RATE_IN_SEC = 10
Sensor = SensorData.SensorData()
SmtpConnector = SmtpClientConnector.SmtpClientConnector()

class TempSensorEmulator (threading.Thread):
    enableEmulator = False
    isPrevTempSet  = False
    rateInSec      = DEFAULT_RATE_IN_SEC
    Sensor.setName('Temperature')
    lowVal = 0
    highVal = 30
    alertDiff = 5
   
    def __init__(self, rateInSec = DEFAULT_RATE_IN_SEC):
        super(TempSensorEmulator, self).__init__()
        
        if rateInSec > 0:
            self.rateInSec = rateInSec
        
    def run(self):
        while True:
            if self.enableEmulator:
                self.curTemp = random.uniform(float(self.lowVal), float(self.highVal))             
                Sensor.addValue(self.curTemp)
                print('\n--------------------')
                print('New sensor readings:')
                print(' ' + str(self.curTemp))
                
                if self.isPrevTempSet == False:
                    self.prevTemp = self.curTemp
                    self.isPrevTempSet = True
                else:
                    print (Sensor.__str__())
                    if (abs(self.curTemp - Sensor.getAvgValue()) >= self.alertDiff):
                        print('\nCurrent temp exceeds average by > ' + str(self.alertDiff) + '. Triggeringalert...')
                        self.sensorData =   Sensor.__str__()
                        SmtpConnector.publishMessage('Exceptional sensor data [test]', self.sensorData)
                sleep(self.rateInSec)
                
                
                
                
            