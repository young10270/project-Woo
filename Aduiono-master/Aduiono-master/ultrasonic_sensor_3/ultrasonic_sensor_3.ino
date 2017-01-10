void setup()
{
  Serial.begin(9600);
  pinMode(4,OUTPUT); // 센서 Trig 핀
  pinMode(5,INPUT); // 센서 Echo 핀
}

void loop()
{
  Serial.print("Average : ");
  Serial.print(avgHeight(), 2); //3sec동안 10번 잰 평균
  Serial.print("cm");
  Serial.println();
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
  long duration=0, sum=0;
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
  
  return avg;
}
