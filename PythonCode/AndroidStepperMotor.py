import serial
import pymysql
import time

conn = pymysql.connect(host='xxxx', port=xxxx, user='xxxx', passwd='xxxx', db='xxxx')

cur = conn.cursor()

# Intializes Arduino
def initializeArduino():
    global arduino
    arduino = serial.Serial("COM3", 1200)#, timeout=1)
    time.sleep(3)
    print("Arduino Initilialize Complete")

# Get DB values and store into strings
def getDBValues():
    global Pin1, Pin2, Pin3, Pin4, Speed, Direction
    cur.execute("SELECT Pin1 FROM StepperMotor WHERE id='1'")
    Pin1 = ''.join( c for c in cur.fetchone() if  c not in '()' )
    cur.execute("SELECT Pin2 FROM StepperMotor WHERE id='1'")
    Pin2 = ''.join( c for c in cur.fetchone() if  c not in '()' )
    cur.execute("SELECT Pin3 FROM StepperMotor WHERE id='1'")
    Pin3 = ''.join( c for c in cur.fetchone() if  c not in '()' )
    cur.execute("SELECT Pin4 FROM StepperMotor WHERE id='1'")
    Pin4 = ''.join( c for c in cur.fetchone() if c not in '()' )
    cur.execute("SELECT Speed FROM StepperMotor WHERE id='1'")
    Speed = ''.join( c for c in cur.fetchone() if  c not in '()' )
    cur.execute("SELECT Direction FROM StepperMotor WHERE id='1'")
    Direction = ''.join( c for c in cur.fetchone() if  c not in '()' )

# Convert string to bytes and return it
def convertToByte(string):
    return str.encode(string)

# get readIndicator from DB
def readIndicatorValue():
    cur.execute("SELECT readIndicator FROM StepperMotor WHERE id='1'")
    readIndicator = ''.join( c for c in cur.fetchone() if  c not in '()' )
    return readIndicator

# convert all values to bytes
def getAllBytes():
    global bPin1, bPin2, bPin3, bPin4, bSpeed, bDirection

    bPin1 = convertToByte(Pin1)
  
    bPin2 = convertToByte(Pin2)
	
    bPin3 = convertToByte(Pin3)
                
    bPin4 = convertToByte(Pin4)
                
    bSpeed = convertToByte(Speed)
                
    bDirection = convertToByte(Direction)

# Print all values
def printValues():
    print(Pin1)
    #print(type(ArduinoPin))
    print(bPin1)
    print(type(bPin1))
	
    print(Pin2)
    #print(type(LEDIntensity))
    print(bPin2)
    print(type(bPin2))
	
    print(Pin3)
    #print(type(Length))
    print(bPin3)
    print(type(bPin3))
	
    print(Pin4)
    #print(type(Length))
    print(bPin4)
    print(type(bPin4))

    print(Speed)
    #print(type(Length))
    print(bSpeed)
    print(type(bSpeed))
	
    print(Direction)
    #print(type(Length))
    print(bDirection)
    print(type(bDirection))

def chooseSpeed():
    global speedIndicator
    if Speed == '10':
        speedIndicator = b'1'
    elif Speed == '20':
        speedIndicator = b'2'
    elif Speed == '30':
        speedIndicator = b'3'
    elif Speed == '40':
        speedIndicator = b'4'
    elif Speed == '50':
        speedIndicator = b'5'
    elif Speed == '60':
        speedIndicator = b'6'
    elif Speed == '70':
        speedIndicator = b'7'
    elif Speed == '80':
        speedIndicator = b'8'
    elif Speed == '90':
        speedIndicator = b'9'

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
    chooseSpeed()
    
    while readIndicatorValue() == '1':
	
        print("writing Pin1...")
        arduino.write(bPin1)
        print("reading output...")
        print(arduino.readline().decode('ascii').strip())
        cur.execute("UPDATE StepperMotor SET readIndicator = '0' WHERE id = '1' ")

        print("writing Pin2...")
        arduino.write(bPin2)
        print("reading output...")
        print(arduino.readline().decode('ascii').strip())
		
        print("writing Pin3...")
        arduino.write(bPin3)
        print("reading output...")
        print(arduino.readline().decode('ascii').strip())
		
        print("writing Pin4...")
        arduino.write(bPin4)
        print("reading output...")
        print(arduino.readline().decode('ascii').strip())
		
        print("writing Speed...")
        arduino.write(speedIndicator)
        print("reading output...")
        print(arduino.readline().decode('ascii').strip())

        print("writing Direction...")
        arduino.write(bDirection)
        print("reading output...")
        print(arduino.readline().decode('ascii').strip())

        #print("turning led off...")
        #arduino.write(b'0')
        #print("reading output")
        #print(arduino.readline().decode('ascii').strip())
        
        readIndicator = '0'
    
arduino.close()

# ------------------ 

print("done")

cur.close()

conn.close()
