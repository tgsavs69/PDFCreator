

void parseMessage(String message) {
  int occurrence = message.indexOf(":");
  if (occurrence != -1) {
    String option =  message.substring(0, occurrence );
    if (option == "config") {

      displayMessage("Configuration", "   Received", 3000);
      return;
    }

  }

  if (message == "reset Arduino") {
    displayMessage("Arduino", "   Restarted", 3000);
    return;
  }
  if (message == "sendConfig") {
    displayMessage("Configuration", "   Transmitted", 3000);
    return;
  }
  if (message == "startTest") {
    displayMessage("The test", "     has started", 3000);
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
