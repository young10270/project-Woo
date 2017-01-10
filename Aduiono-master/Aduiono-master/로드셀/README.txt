안내사항 -- 
아두이노 IDE는 http://www.arduino.cc/ 에서 다운로드 받으실 수 있으며, 포함된 라이브러리와 예제 코드는 아두이노 1.0.6, 아두이노 1.6.3 에서 확인된 참고 예제소스 입니다. 
포함된 아두이노 라이브러리는 C:\Users\(사용자명)\Documents\Arduino\libraries 로 폴더를 통째로 이동시켜 주시면 됩니다.

예) 윈도우 7 일때
C:\Users\(사용자 이름)\Documents\Arduino\libraries\HX24\HX24.cpp
C:\Users\(사용자 이름)\Documents\Arduino\libraries\HX24\HX24.h

로드셀의 SCK/DATA 사용핀을 변경하려면 HX24.h 파일에서 수정할 수 있습니다.
로드셀의 최대값(3kg -> 5kg) 또는 로드셀 AD값 변환 비율은 HX24.c 파일의 unsigned int Get_Weight() 함수에서 확인 하실 수 있습니다.