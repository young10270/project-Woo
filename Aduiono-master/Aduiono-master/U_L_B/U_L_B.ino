//#include <HX711.h>

#include <SoftwareSerial.h>
#include "HX711.h"

#define calibration_factor -7050.0 //This value is obtained using the SparkFun_HX711_Calibration sketch

#define DOUT  9
#define CLK  8

HX711 scale(DOUT, CLK);

SoftwareSerial swSerial(10, 11);

float height, weight;
/////////////// for bluetooth

void setup()
{
  Serial.begin(9600);
  pinMode(4,OUTPUT); // 센서 Trig 핀
  pinMode(5,INPUT); // 센서 Echo 핀
  swSerial.begin(9600);
  swSerial.println("Hello, world?");
  
  scale.set_scale(calibration_factor); //This value is obtained by using the SparkFun_HX711_Calibration sketch
  scale.tare(); //Assuming there is no weight on the scale at start up, reset the scale to 0
}

void loop(){
  if(swSerial.available()){
    height = avgHeight();
    weight = avgWeight();
    //9600bps 기준으로 delay 를 1ms 을 줘야 한다.   ??
    delay(1);
    // 블루투스를 통하여 데이터를 보낸다.
   Serial.write(swSerial.read(height));
   Serial.write(swSerial.read(weight));
  
    
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
    digitalWrite(4,HIGH); // 센서에 Trig 신호 입력
    delayMicroseconds(10); // 10us 정도 유지
    digitalWrite(4,LOW); // Trig 신호 off

    duration = pulseIn(5,HIGH); // Echo pin: HIGH->Low 간격을 측정
    cm = microsecondsToCentimeters(duration); // 거리(cm)로 변환

    Serial.print(cm, 2);// 소수점 이하 2자리까지 인쇄한다.
    Serial.print("cm");
    Serial.println();

    sum += cm;
    delay(300); // 0.3초 대기 후 다시 측정
    i--;
  }
  avg = sum/10;
  
  Serial.print("avg = ");
  Serial.print(avg);
  Serial.println(" cm");
  
  return avg;
}

float avgWeight(){
  int i=10;
  float sum = 0;
  float kg = 0;
  float avg = 0;
  
  while(i != 0){
    kg = scale.get_Gram()*0.15 + 0.15;
    sum += kg;
    Serial.print(kg);
    Serial.println(" kg");
    delay(300);
    i--;
  }  
  avg = sum/10;
  
  Serial.print("avg = ");
    Serial.print(avg);
  Serial.println(" kg");
  
  return avg;
}
