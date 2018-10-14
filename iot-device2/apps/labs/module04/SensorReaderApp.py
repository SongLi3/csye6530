'''
Created on 2018年10月13日

@author: okok_no
'''
from time import sleep
import os,sys
sys.path.append('/home/pi/workspace/iot-device/apps')
from labs.module04 import I2CSenseHatAdaptor
import threading



i2c = threading.Thread(target = I2CSenseHatAdaptor.I2CSenseHatAdaptor().run())

print("Starting system performance app daemon thread...")
i2c.start()

