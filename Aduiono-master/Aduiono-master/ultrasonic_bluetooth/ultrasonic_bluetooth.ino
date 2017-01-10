#include <SoftwareSerial.h>
#include "HX711.h"

#define calibration_factor -7050.0 //This value is obtained using the SparkFun_HX711_Calibration sketch

// Digital Pin 2
#define rxPin 2 
// Digital Pin 3
#define txPin 3 

#define DOUT  3
#define CLK  2

SoftwareSerial swSerial(txPin, rxPin);

HX711 scale(DOUT, CLK);

float height, weight;
/////////////// for bluetooth

void setup()
{
  swSerial.begin(9600);
  Serial.begin(9600);
  pinMode(2,OUTPUT); // 센서 Trig 핀
  pinMode(3,INPUT); // 센서 Echo 핀
  
  scale.set_scale(calibration_factor); //This value is obtained by using the SparkFun_HX711_Calibration sketch
  scale.tare(); //Assuming there is no weight on the scale at start up, reset the scale to 0
}

void loop(){
  if(swSerial.available()){
    height = avgHeight();
    //weight = avgWeight();
    //9600bps 기준으로 delay 를 1ms 을 줘야 한다.   ??
    delay(1);
    // 블루투스를 통하여 데이터를 보낸다.
    swSerial.write(height);
    //swSerial.write(weight);
  }
  delay(5000);
  //안드로이드에서 값을 받으면 연결을 끊을 시간을 줌
}

float microsecondsToCentimeters(long microseconds)
{
  // The speed of sound is 340 m/s or 29 microseconds per centimeter.
  // The ping travels out and back, so to find the distance of the
  // object we take half of the distance travelled.
  return (float)microseconds / 29 / 2;
}

float avgHeight(){
  int i=10;
  long duration=0;
  float sum=0;
  float cm=0;
  float avg=0;
  while(i!=0){  // 10번 행
    digitalWrite(2,HIGH); // 센서에 Trig 신호 입력
    delayMicroseconds(300); // 10us 정도 유지
    digitalWrite(2,LOW); // Trig 신호 off

    duration = pulseIn(3,HIGH); // Echo pin: HIGH->Low 간격을 측정
    cm = microsecondsToCentimeters(duration); // 거리(cm)로 변환

    Serial.print(cm, 2);// 소수점 이하 2자리까지 인쇄한다.
    Serial.print("cm");
    Serial.println();

    sum += cm;
    delay(300); // 0.3초 대기 후 다시 측정
    i--;
  }
  avg = sum/10;
  
  return avg;
}

