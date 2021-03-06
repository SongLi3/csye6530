'''
Created on 2018年10月3日

@author: okok_no
'''
import threading
# This next import is why we need the RPi.GPIO proxy class
import RPi.GPIO as GPIO

from time import sleep

class SimpleLedActivator(threading.Thread):
    enableLed = False
    rateInSec = 5
    
    def __init__(self, rateInSec = 1):
        super(SimpleLedActivator, self).__init__()
        if rateInSec > 0:
            self.rateInSec = rateInSec
        GPIO.setmode(GPIO.BCM)
        GPIO.setup(17, GPIO.OUT)
        
    def run(self):
        while True:
            if self.enableLed:
                GPIO.output(17, GPIO.HIGH)
                sleep(self.rateInSec)
                GPIO.output(17, GPIO.LOW)
            sleep(self.rateInSec)
            
    def getRateInSeconds(self):
        return self.rateInSec
    
    def setEnableLedFlag(self, enable):
        self.enableLed = enable