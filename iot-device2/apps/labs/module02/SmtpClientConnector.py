'''
Created on 2018年9月28日

@author: okok_no
'''
import threading
import smtplib

from labs.common import ConfigUtil
from labs.common import ConfigConst
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText

class SmtpClientConnector (threading.Thread):
    def __init__(self):
#       self.config = ConfigUtil.ConfigUtil('iot-device/data/ConnectedDevicesConfig.props')
        self.config = ConfigUtil.ConfigUtil('/Users/okok_no/Desktop/iot-device/data/ConnectedDevicesConfig.props')
        self.config.loadConfig()
        print('Configuration data...\n' + str(self.config))

    def publishMessage(self, topic, data):
        #print('hahahah' + ConfigConst.HOST_KEY)
        #print('hahahah' + ConfigConst.FROM_ADDRESS_KEY)
        host = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.HOST_KEY)
        port = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.PORT_KEY)
        fromAddr = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.FROM_ADDRESS_KEY)
        toAddr = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.TO_ADDRESS_KEY)
        authToken = self.config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY)
        msg = MIMEMultipart()
        msg['From'] = fromAddr
        msg['To'] = toAddr
        msg['Subject'] = topic
        msgBody = str(data)   
        msg.attach(MIMEText(msgBody))
        msgText = msg.as_string()
# send e-mail notification
        
        server=smtplib.SMTP_SSL(host,port)  
        #print(1)
        server.ehlo()  
        #print(2)
        #print(fromAddr + authToken)
        server.login(fromAddr,authToken)   
        #print(5)
        #print(toAddr)
        server.sendmail(fromAddr,toAddr,msgText)   
        #print(6)
        server.quit()  
        server.close()
        print('\nthe email has been sent successfully')        
            
                
    
            