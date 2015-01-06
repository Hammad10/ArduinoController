import serial
import pymysql
import time

conn = pymysql.connect(host='xxxx', port=xxxx, user='xxxx', passwd='xxxx!', db='xxxx')

cur = conn.cursor()

# Intializes Arduino
def initializeArduino():
    global arduino
    arduino = serial.Serial("COM3", 1200)#, timeout=1)
    time.sleep(3)
    print("Arduino Initilialize Complete")

# Get DB values and store into strings
def getDBValues():
    global ArduinoPin, LEDIntensity, Length
    cur.execute("SELECT ArduinoPin FROM LED WHERE id='1'")
    ArduinoPin = ''.join( c for c in cur.fetchone() if  c not in '()' )
    cur.execute("SELECT LEDIntensity FROM LED WHERE id='1'")
    LEDIntensity = ''.join( c for c in cur.fetchone() if  c not in '()' )
    cur.execute("SELECT Length FROM LED WHERE id='1'")
    Length = ''.join( c for c in cur.fetchone() if  c not in '()' )

# Convert string to bytes and return it
def convertToByte(string):
    return str.encode(string)

# get readIndicator from DB
def readIndicatorValue():
    cur.execute("SELECT readIndicator FROM LED WHERE id='1'")
    readIndicator = ''.join( c for c in cur.fetchone() if  c not in '()' )
    return readIndicator

# convert all values to bytes
def getAllBytes():
    global bArduinoPin, bLEDIntensity, bLength

    #bArduinoPin = str.encode(ArduinoPin)
    bArduinoPin = convertToByte(ArduinoPin)
    
    #bLEDIntensity = str.encode(LEDIntensity)
    bLEDIntensity = convertToByte(LEDIntensity)
    
    #bLength = str.encode(Length)
    bLength = convertToByte(Length)

# Print all values
def printValues():
    print(ArduinoPin)
    #print(type(ArduinoPin))
    print(bArduinoPin)
    print(type(bArduinoPin))
    print(LEDIntensity)
    #print(type(LEDIntensity))
    print(bLEDIntensity)
    print(type(bLEDIntensity))
    print(Length)
    #print(type(Length))
    print(bLength)
    print(type(bLength))

def chooseIntensity():
    global intensityIndicator
    if LEDIntensity == 'Low':
        intensityIndicator = b'1'
    elif LEDIntensity == 'Medium':
        intensityIndicator = b'2'
    elif LEDIntensity == 'High':
        intensityIndicator = b'3'

def timeDelay(x):
    for i in range(1,x+1):
        time.sleep(i)
        print(str(i) + " seconds...")

# Send PIN to Arduino -- (basically main function)
initializeArduino()
time.sleep(1)
arduino.readline().decode('ascii').strip() #Reads the "Ready"
print("readIndicator = " + readIndicatorValue())

while 1:
    
    print("waiting for input in app...")
    while readIndicatorValue() == '0':
        #do nothing
        time.sleep(1)
        print("waiting for input in app...")
        
    print("input detected")
    
    getDBValues()
    getAllBytes()
    #printValues()
    chooseIntensity()
    
    while readIndicatorValue() == '1':
        #print(bArduinoPin)
        print("writing LED pin...")
        arduino.write(bArduinoPin)
        print("reading output...")
        print(arduino.readline().decode('ascii').strip())
        cur.execute("UPDATE LED SET readIndicator = '0' WHERE id = '1' ")

        print("writing intensity...")
        arduino.write(intensityIndicator)
        print("reading output...")
        print(arduino.readline().decode('ascii').strip())

        print("led on for " + str(Length) + " seconds")
        time.sleep(float(Length))
        #timeDelay(int(Length))

        print("turning led off...")
        arduino.write(b'0')
        print("reading output")
        print(arduino.readline().decode('ascii').strip())
        
        readIndicator = '0'
    
arduino.close()

# ------------------ 

print("done")

cur.close()

conn.close()
