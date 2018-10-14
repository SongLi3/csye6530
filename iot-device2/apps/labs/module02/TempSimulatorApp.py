'''
Created on 2018年9月22日

@author: okok_no
'''

from time import sleep
from labs.module02 import TempSensorEmulator

Tem = TempSensorEmulator.TempSensorEmulator()
print('start\n')

Tem.enableEmulator = True
Tem.start()
while (True):
    sleep(10)
    pass
