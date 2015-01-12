// Android Stepper Motor
#include <Stepper.h> 

void setup() {                
  Serial.begin(1200); // set the baud rate
  Serial.println("Ready"); // print "Ready" once  
  //String readStuff;
}

void loop () {
  int Pin1;
  int Pin2;
  int Pin3;
  int Pin4;
  int Speed;
  int Direction; // can either be +1 (fwd) or -1 (rev)
  int stopIndicator = 1;
  
  while (!Serial.available()) ;
  Pin1 = Serial.read() - '0';
  pinMode(Pin1, OUTPUT);
  while (!Serial.available()) ;
  Pin2 = Serial.read() - '0';
  pinMode(Pin2, OUTPUT);
  while (!Serial.available()) ;
  Pin3 = Serial.read() - '0';
  pinMode(Pin3, OUTPUT);
  while (!Serial.available()) ;
  Pin4 = Serial.read() - '0';
  pinMode(Pin4, OUTPUT);
  
  Stepper motor(200, Pin1, Pin2, Pin3, Pin4); 
  
  while (!Serial.available()) ;
  Speed = Serial.read() - '0';
  while (!Serial.available()) ;
  Direction = Serial.read() - '0';
  
  motor.setSpeed(Speed);
  
  motor.step(Speed*Direction);
  
}
