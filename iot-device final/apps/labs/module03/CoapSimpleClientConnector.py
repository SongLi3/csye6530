'''
Created on Dec 4, 2018

@author: xingli
'''

from coapthon.client.helperclient import HelperClient

from labs.common import ConfigUtil 
from labs.common import ConfigConst


class CoapSimpleClientConnector(object):
    config = None
    serverAddr = None
    host = "10.0.1.136" 
    port = 5683
    
    def __init__(self):
            
            print('\tHost: ' + self.host) 
            print('\tPort: ' + str(self.port))
            
            self.serverAddr = (self.host, self.port)
            self.url = "coap://" + self.host + ":" + str(self.port) + "/temp"
    
    def initClient(self): 
        try:
            self.client = HelperClient(server=(self.host, self.port))
            
            print("Created CoAP client ref: " + str(self.client))
            print(" coap://" + self.host + ":" + str(self.port)) 
            
        except Exception:
            
            print("Failed to create CoAP helper client reference using host: " + self.host)
             
            pass
    
    def handleGetTest(self, resource):
        print("Testing GET for resource: " + resource)
        self.initClient()
        
        print("initclient successful")
        
        response = self.client.get(self.url)
        
        print("get response--")
        
        if response: 
            print(response.pretty_print())
        else:
            print("No response received for GET using resource: " + resource)
        self.client.stop()
        
    def handlePostTest(self, resource, payload):
        print("Testing POST for resource: " + resource + ", payload: " + payload)
        
        self.initClient()
        response = self.client.post(resource, payload)
        if response: 
            print(response.pretty_print())
            
        else:
            print("No response received for POST using resource: " + resource)
            
        self.client.stop()
