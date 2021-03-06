#include "loadCell.h"

void parseMessage(String message) {
  int occurrence = message.indexOf(":");
  if (occurrence != -1) {
    String option =  message.substring(0, occurrence);
    if (option == "config") {
      String paramA = message.substring(message.indexOf(":") + 1, message.indexOf("#"));
      liftingTime = (paramA.toInt()) * 1000;
      Serial.println(liftingTime);
      EEPROM_writeAnything(0, liftingTime);
      displayMessage("CONFIGURATION", "   RECEIVED", 3000);
      return;
    }

  }

  if (message == "tareTare") {
    scale.tare();
    displayMessage("SCALE", "   RESETED", 3000);
    return;
  }
  if (message == "reset Arduino") {
    displayMessage("ARDUINO", "   RESTARTED", 3000);
    resetData();
    return;
  }
  if (message == "sendConfig") {

    String outputMessage = "config:";
    outputMessage += liftingTime/1000;
    outputMessage += "#";

    Serial.println(outputMessage);
    displayMessage("CONFIGURATION", "   TRANSMITTED", 3000);

    return;
  }
  if (message == "startTest") {
    displayMessage("THE TEST", "     HAS STARTED", 3000);
    testStarted = true;
    Serial.println(liftStarted);
    return;
  }



}


void serialEvent() {
  /*wait 100ms, so the whole message can arrive*/
  delay(100);
  /*prepare a new string to store the incoming message*/
  String message = "";

  /*read the first character from the message*/
  char inputCharacter;

  long timeout = millis();


  while (Serial.available()  && millis() - timeout < 3000) {
    inputCharacter = Serial.read();
    if (inputCharacter == '#') break;
    message += inputCharacter;
    delay(1);
  }
  parseMessage(message);

}
