'''
Created on 2018年9月22日

@author: okok_no
'''
SECTION_SEPARATOR = '.'
#DEFAULT_CONFIG_FILE_NAME = 'iot-device/data/ConnectedDevicesConfig.props'
DEFAULT_CONFIG_FILE_NAME = '/Users/okok_no/Desktop/iot-device/data/ConnectedDevicesConfig.props'
CLOUD = 'cloud'
MQTT = 'mqtt'
COAP = 'coap'
SMTP = 'smtp'
GATEWAY_DEVICE = 'gateway'
CONSTRAINED_DEVICE = 'device'
UBIDOTS = 'ubidots'

UBIDOTS_CLOUD_SECTION = UBIDOTS + SECTION_SEPARATOR + CLOUD
SMTP_CLOUD_SECTION = SMTP + SECTION_SEPARATOR + CLOUD
MQTT_CLOUD_SECTION = MQTT + SECTION_SEPARATOR + CLOUD
COAP_CLOUD_SECTION = COAP + SECTION_SEPARATOR + CLOUD
MQTT_GATEWAY_SECTION = MQTT + SECTION_SEPARATOR + GATEWAY_DEVICE
COAP_GATEWAY_SECTION = COAP + SECTION_SEPARATOR + GATEWAY_DEVICE
MQTT_DEVICE_SECTION = MQTT + SECTION_SEPARATOR + CONSTRAINED_DEVICE
COAP_DEVICE_SECTION = COAP + SECTION_SEPARATOR + CONSTRAINED_DEVICE

CLOUD_API_KEY = 'apiKey'
CLOUD_MQTT_BROKER = 'mqttBroker'
CLOUD_MQTT_PORT = 'mqttPort'
CLOUD_COAP_HOST = 'coapHost'
CLOUD_COAP_PORT = 'coapPort'

FROM_ADDRESS_KEY = 'fromAddr'
#FROM_ADDRESS_KEY = 'zbls0071@163.com'
#TO_ADDRESS_KEY = 'zbls007@yahoo.com'
TO_ADDRESS_KEY = 'toAddr'
TO_MEDIA_ADDRESS_KEY = 'toMediaAddr'
TO_TXT_ADDRESS_KEY = 'toTxtAddr'

#HOST_KEY = 'smtp.163.com'
#HOST_KEY = 'smtp.163.com'
HOST_KEY = 'host'
PORT_KEY = 'port'
#PORT_KEY = '465'
SECURE_PORT_KEY = 'securePort'

USER_NAME_TOKEN_KEY = 'userNameToken'
USER_AUTH_TOKEN_KEY = 'authToken'

ENABLE_AUTH_KEY = 'enableAuth'
ENABLE_CRYPT_KEY = 'enableCrypt'
ENABLE_EMULATOR_KEY = 'enableEmulator'
ENABLE_LOGGING_KEY = 'enableLogging'
POLL_CYCLES_KEY = 'pollCycleSecs'   