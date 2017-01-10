// import library
#include "HX24.h"
#include <Wire.h>

// Global variable
unsigned long Weight = 0;

void setup()
{
	// library initialization
	Init_HX24(); // Loadsell Library              
	delay(1000);
	Serial.begin(9600);
        transfer(0x01);
	Get_M();		//Get M
}

void loop()
{
	unsigned char i;
	Weight = Get_Weight();	//Calculated on the weight of the weight sensor
	Serial.print(Weight);	
	Serial.println(" g\r\n");
	delay(500);				//Delay 0.5s
}
