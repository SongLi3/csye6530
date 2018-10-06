'''
Created on 2018年9月15日

@author: okok_no
'''
from time import sleep
from labs.module01 import SystemPerformanceAdaptor
sysPerfAdaptor = SystemPerformanceAdaptor.SystemPerformanceAdaptor()

print("Starting system performance app daemon thread...")
sysPerfAdaptor.enableAdaptor(True)
sysPerfAdaptor.start()
while (True):
    sleep(5)
    pass
  
