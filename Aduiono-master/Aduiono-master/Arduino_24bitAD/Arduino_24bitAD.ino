// Set pin (2~11)
#define DATA 9 // DO/RX
#define CLK 8 // CK/TX 

//to use simple function
#define reader  digitalRead(DATA)
#define High digitalWrite(CLK,HIGH) // CLK HIGH
#define Low digitalWrite(CLK,LOW) // CLK LOW

unsigned long raw; // 24bit Load sell
int i;
unsigned long val;
signed int rval;
char buf[20];

void setup(){ 
 Serial.begin(9600);
 pinMode(DATA, INPUT);
 pinMode(CLK, OUTPUT);
 digitalWrite(DATA,HIGH); 
}

void loop(){
  digitalWrite(DATA,HIGH);
  High; // CLK HIGH
  raw = 0; 
 //while(reader==HIGH);
  raw=reader;
  for(i=0;i<24;i++){ // Read 24Bit data
    High; // CLK High 
    raw <<= 1;
    Low; // CLK LOW
    if(reader) raw++;
  }
  High; // CLK High 
//  delay(1);
  Low; // CLK LOW
  
  val = raw; // put raw data to val 
  rval=(val>>8) & 0xffff; // change from the 24bit to 16bit
  sprintf(buf,"%d",rval);
  Serial.print("Weight : ");
  Serial.println(buf);
  delay(1000);
}
