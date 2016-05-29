/*
 * Dev By Thanh Tran 2016
 */

#include <SoftwareSerial.h>

SoftwareSerial wifiSerial(9, 10);
char ledStatus = 0;

String tmpWiFiData = "";
void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  wifiSerial.begin(9600);

  Serial.println("Starting...");

  for (byte i = 2; i < 9; i++)
  {
    pinMode(i, OUTPUT);
  }

  digitalWrite(7, LOW); //Reset WiFi
  digitalWrite(7, HIGH);
  showWiFiSerialData();
  delay(5000);
  digitalWrite(8, HIGH); //broadcast WiFi
  delay(5000);
  showWiFiSerialData();
  doWiFiConnect();
  openNetworkPort("100");
  //sendATAndGetResponse("AT+CIPSERVER=1,100", 1000);
}

void loop() {
  // put your main code here, to run repeatedly:
  //wifiSerial.listen();
  tmpWiFiData = "";
  char temp[31];
  while (wifiSerial.available() > 0)
  {
    char c = wifiSerial.read();
    tmpWiFiData += c;
  }

  if (tmpWiFiData.length() > 0)
  {
    tmpWiFiData.trim();
    Serial.println(tmpWiFiData);
    tmpWiFiData.toCharArray(temp, 32);
    ledStatus = temp[22];
    Serial.println(ledStatus);    
    showLED(ledStatus);
  }

  delay(200);
}

void showLED(char led)
{
  switch (led)
  {
    case '0':
      Serial.println("All LEDs will off...");
      digitalWrite(2, LOW);
      digitalWrite(3, LOW);
      digitalWrite(4, LOW);
      digitalWrite(5, LOW);
      digitalWrite(6, LOW);
      break;
    case '1':
      Serial.println("LED 1 will on...");
      digitalWrite(2, HIGH);
      digitalWrite(3, LOW);
      digitalWrite(4, LOW);
      digitalWrite(5, LOW);
      digitalWrite(6, LOW);
      break;
    case '2':
      Serial.println("LED 2 will on...");
      digitalWrite(2, LOW);
      digitalWrite(3, HIGH);
      digitalWrite(4, LOW);
      digitalWrite(5, LOW);
      digitalWrite(6, LOW);
      break;
    case '3':
      Serial.println("LED 3 will on...");
      digitalWrite(2, LOW);
      digitalWrite(3, LOW);
      digitalWrite(4, HIGH);
      digitalWrite(5, LOW);
      digitalWrite(6, LOW);
      break;
    case '4':
      Serial.println("LED 4 will on...");
      digitalWrite(2, LOW);
      digitalWrite(3, LOW);
      digitalWrite(4, LOW);
      digitalWrite(5, HIGH);
      digitalWrite(6, LOW);
      break;
    case '5':
      Serial.println("LED 5 will on...");
      digitalWrite(2, LOW);
      digitalWrite(3, LOW);
      digitalWrite(4, LOW);
      digitalWrite(5, LOW);
      digitalWrite(6, HIGH);
      break;
  }
}

void doWiFiConnect()
{
  sendATAndGetResponse("AT+CWMODE=1", 3000);
  sendATAndGetResponse("AT+CWJAP=\"YOUR_WIFI_SSID\",\"YOUR_WIFI_PASSWORD\"", 5000);
  sendATAndGetResponse("AT+CIFSR", 1000);
  sendATAndGetResponse("AT+CIPMUX=1", 1000);
}

void openNetworkPort(String portStr)
{
  sendATAndGetResponse("AT+CIPSERVER=1," + portStr, 1000);
}

void sendATAndGetResponse(String ATCommand, int waitMS)
{
  tmpWiFiData = "";
  wifiSerial.println(ATCommand);
  delay(waitMS);
  showWiFiSerialData();
}

void showWiFiSerialData()
{
  while (wifiSerial.available() > 0)
  {
    char c = wifiSerial.read();
    tmpWiFiData += c;
  }
  Serial.println(tmpWiFiData);
}

