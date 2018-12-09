# '''
# Created on 2018年10月3日
# 
# @author: okok_no
# '''
# import os,sys
# sys.path.append('/home/pi/Desktop/workspace/iot-device/apps')
# 
# from time import sleep
# from labs.module03 import TempSensorAdaptor
# 
# Tem = TempSensorAdaptor.TempSensorAdaptor()
# print("Starting system performance app daemon thread...")
# 
# Tem.enableEmulator = True
# Tem.daemon = True
# Tem.start()
# while (True): 
#     sleep(2)
#     pass