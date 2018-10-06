'''
Created on 2018年9月22日

@author: okok_no
'''

from time import sleep
from labs.module02 import TempSensorEmulator




Tem = TempSensorEmulator.TempSensorEmulator()
Tem.daemon = True
print("Starting system performance app daemon thread...")
Tem.enableEmulator = True

Tem.start()
while (True):
    sleep(10)
    pass
