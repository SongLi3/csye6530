'''
Created on 2018年9月15日

@author: okok_no
'''

import threading
import psutil

from time import sleep

DEFAULT_RATE_IN_SEC=5


class SystemPerformanceAdaptor(threading.Thread):
    enableAdaptor =False
    rateInsec = DEFAULT_RATE_IN_SEC
    def __init__(self,rateInsec= DEFAULT_RATE_IN_SEC):
        super(SystemPerformanceAdaptor,self.__init__())
        if rateInsec >0:
            self.rateInsec=rateInsec
    def run(self):
        while True:
            if self.enableAdaptor:
                print('\n----------')
                print('New system performance reading:')
                print(''+str(psutil.cpu_stats()))
                print(''+str(psutil.virtual_memory()))
            sleep(self.rateInsec)
        
    

        