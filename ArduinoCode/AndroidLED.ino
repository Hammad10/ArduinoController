int led;

void setup() {                
  Serial.begin(1200); // set the baud rate
  Serial.println("Ready"); // print "Ready" once  
  //String readStuff;
}
    //h = 56
    //H = 24
    //M = 29
    //L = 28
    //S = 35
void loop() {
  int LEDPin;
  int LEDIntensity;
  int stopIndicator = 1;
  //if (Serial.available() > 0) {
    while(!Serial.available()) ;
    LEDPin = Serial.read() - '0';
    //led = LEDPin;
    pinMode(LEDPin, OUTPUT);
    Serial.println(LEDPin);
    //analogWrite(LEDPin, 120);
    //delay(1000);
    //analogWrite(LEDPin, 0);
  //}
  //if (Serial.available() > 0) {
    while(!Serial.available()) ;
    LEDIntensity = Serial.read() - '0';
    if (LEDIntensity == 1)
      analogWrite(LEDPin, 30);
    else if (LEDIntensity == 2)
      analogWrite(LEDPin, 120);
    else
      analogWrite(LEDPin, 255);
    Serial.println(LEDIntensity);
  //}
  //if (Serial.available() > 0) {
    while(!Serial.available()) ;
    stopIndicator = Serial.read() - '0';
    if (stopIndicator == 0)
      analogWrite(LEDPin, 0);
    Serial.println(stopIndicator);
  //}
    
  /*
  if (Serial.available() > 0) {
    ser = Serial.read();
    // if ser == 'H', high brightness
    if (ser == 72) {
      Serial.println(ser);
      pinMode(led, OUTPUT);
      analogWrite(led, 255);
    }
  }
  
  if (Serial.available() > 0) {
    ser = Serial.read();
    // if ser == 'M', medium brightness
    if (ser == 77) {
      Serial.println(ser);
      pinMode(led, OUTPUT);
      analogWrite(led, 120);
    }
  }
  
  if (Serial.available() > 0) {
    ser = Serial.read();
    // if ser == 'L', low brightness
    if (ser == 76) {
      Serial.println(ser);
      pinMode(led, OUTPUT);
      analogWrite(led, 0);
    }
  }*/
}
    
     
 // String readStuff = "";
    
   /* 
   if (Serial.available() > 0) {
     char c = Serial.read();
     readStuff += c;
   }  
   
   if (readStuff.length() > 1) {
     int led = 10;
      pinMode(led, OUTPUT);
      digitalWrite(led, HIGH);
      delay(1000);
   } */
