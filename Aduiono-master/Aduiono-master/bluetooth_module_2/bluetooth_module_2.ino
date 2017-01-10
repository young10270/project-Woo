#include <SoftwareSerial.h>

// Digital Pin 0
#define rxPin 0 
// Digital Pin 1
#define txPin 1 

SoftwareSerial swSerial(txPin, rxPin);

int height, weight;

void setup() {
  swSerial.begin(9600);
}

void loop() {
  while(swSerial.available()) {
    height = ;
    weight = ;
    // 9600bps 기준으로 delay 를 1ms 을 줘야 한다.
    delay(1);
  }   
  // 블루투스를 통하여 데이터를 보낸다.
  swSerial.write(height);
  swSerial.write(weight);
  }
