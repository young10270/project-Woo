#include "HX711.h"

#define calibration_factor -7050.0 //This value is obtained using the SparkFun_HX711_Calibration sketch

#define DOUT  9
#define CLK  8

HX711 scale(DOUT, CLK);

void setup() {
  Serial.begin(9600);
  Serial.println("Weight Test");

  scale.set_scale(calibration_factor); //This value is obtained by using the SparkFun_HX711_Calibration sketch
  scale.tare(); //Assuming there is no weight on the scale at start up, reset the scale to 0

  Serial.println("Readings:");
}

void loop() {
  Serial.print(scale.get_scale(),1);
  Serial.println("lb");
  Serial.print(scale.get_Gram(),1);
  Serial.println(" g");
  Serial.print(scale.get_Gram()*0.15,1);
  Serial.println(" kg");
  Serial.println();
  
  float kg = 0;
  kg = scale.get_Gram()*0.15 + 0.15;
  Serial.println(kg);
  
  delay(300); // 0.3초 대기 후 다시 측정
}

