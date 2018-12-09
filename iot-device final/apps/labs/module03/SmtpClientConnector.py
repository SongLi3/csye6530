'''
Created on 2018年9月28日

@author: okok_no
'''
import os,sys
sys.path.append('/home/pi/Desktop/workspace/iot-device/apps')
import threading
import smtplib

from labs.common import ConfigUtil
from labs.common import ConfigConst
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText

class SmtpClientConnector (threading.Thread):
    def __init__(self):
        #reading the config from file 
        
        self.config = ConfigUtil.ConfigUtil('/home/pi/workspace/iot-device/data/ConnectedDevicesConfig.props')
        self.config.loadConfig()
        
        print('Configuration data...\n' + str(self.config))
        
    def publishMessage(self, topic, data):
        print('hahahah' + ConfigConst.HOST_KEY)
        print('hahahah' + ConfigConst.FROM_ADDRESS_KEY)
        
        #get the specific details from config
        
        host = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.HOST_KEY)
        port = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.PORT_KEY)
        fromAddr = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.FROM_ADDRESS_KEY)
        toAddr = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.TO_ADDRESS_KEY)
        authToken = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY)
        
        #creating the sending-message format
        
        msg = MIMEMultipart()
        msg['From'] = fromAddr
        msg['To'] = toAddr
        msg['Subject'] = topic
        msgBody = str(data)   
        msg.attach(MIMEText(msgBody))
        msgText = msg.as_string()
        
        print ('')
        print ('The content of msgText:')
        print ('-----------------------------')
        print (str(msgText))
        print ('---------------------')
        
        #using SMTP to send e-mail
        
        server=smtplib.SMTP_SSL(host,port)  
        server.ehlo()                
        server.login(fromAddr,authToken)
        server.sendmail(fromAddr,toAddr,msgText)   
        
        print('____________________\n')
        print('sending success') 
        
        server.quit()
        server.close()
    

   
        