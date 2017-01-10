void setup()
{
  Serial.begin(9600);
  pinMode(2,OUTPUT); // 센서 Trig 핀
  pinMode(3,INPUT); // 센서 Echo 핀
}

void loop()
{
  long duration;
  float cm;

  digitalWrite(2,HIGH); // 센서에 Trig 신호 입력
  delayMicroseconds(10); // 10us 정도 유지
  digitalWrite(2,LOW); // Trig 신호 off

  duration = pulseIn(3,HIGH); // Echo pin: HIGH->Low 간격을 측정
  cm = microsecondsToCentimeters(duration); // 거리(cm)로 변환

  //Serial.print(cm);
  Serial.print(cm, 2);// 소수점 이하 2자리까지 인쇄한다.
  Serial.print("cm");
  Serial.println();

  delay(300); // 0.3초 대기 후 다시 측정
}

float microsecondsToCentimeters(long microseconds)
{
  // The speed of sound is 340 m/s or 29 microseconds per centimeter.
  // The ping travels out and back, so to find the distance of the
  // object we take half of the distance travelled.
  return (float)microseconds / 29 / 2;
}
