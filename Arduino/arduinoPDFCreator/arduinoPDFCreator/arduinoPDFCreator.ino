#include <HX711.h>
#include <EEPROM.h>
#include "EEPROMAnything.h"
#include <LiquidCrystal_I2C.h>
#include <Wire.h>
#include "configuration.h"
#include "additional.h"


void startLCD() {
  lcd.init();
  lcd.backlight();
  lcd.clear();
}

void startHX711() {

  scale.begin(DOUT, CLK);
  scale.set_scale(calibration_factor); //This value is obtained by using the SparkFun_HX711_Calibration sketch
  scale.tare();
}

void resetArduino() {

  currentMode = 0;
  lastMode = -1;
  /*
  currentValue = 0;
  lastReading = 0;

 
 
  //initialise the history matrix with value -1
  for (int i = 0; i < numberOfStages; i++) {
    for (int j = 0; j < numberOfSamples; j++) {
      history[i][j] = -1;
    }
  }

  //reset maximum array to value -1
  for (int i = 0; i < numberOfStages; i++) {
    maximum[i] = -1;
  }

  //reset current sum array to value -1
  for (int i = 0; i < numberOfStages; i++) {
    currentSum[i] = -1 * numberOfSamples;
  }

  //reset maximum array to value -1
  for (int i = 0; i < numberOfStages; i++) {
    coefficientOfVariation[i] = -1;
  }
  */
}

void setup() {

  startLCD();
  startHX711();

  // loadConfiguration();

  /*configure the switchPins as INPUT_PULLUP*/
  for (int i = 0; i < numberOfStages; i++) {
    pinMode(switchPins[i], INPUT_PULLUP);
  }
  /*begin the serial communication at 115200! It is important to be 115220( the same as the client's baud rate)*/
  Serial.begin(115200);


  resetArduino();
  EEPROM_readAnything(0, configuration);




}
int currentStage = 0;
void changeStage() {
  /*wait until the user changed to the required mode*/
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Change to");
  lcd.setCursor(0, 1);
  lcd.print("   ");
  lcd.print(stages[currentStage]);
}


void waitLift() {
  /*wait until the user starts to lift*/
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Mode: ");
  lcd.print(stages[currentStage]);
  lcd.setCursor(0, 1);
  lcd.print("Waiting to lift...");
 
}

void printResult() {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Result ");
  lcd.print(stages[currentMode]);
  lcd.setCursor(0, 1);
  lcd.print("max:");
  lcd.print(maximum[currentMode], 1);
  lcd.print(" cv");
  lcd.print(coefficientOfVariation[currentMode]);
}


void sendResultToClient() {
  long startTime = millis();
  while (millis() - startTime < 5000) {
    Serial.print(stages[currentMode]);
    Serial.print("!");
    Serial.print(maximum[currentMode]);
    Serial.print("$CV%");
    Serial.print(coefficientOfVariation[currentMode]);
    Serial.println("#");
    delay(100);
  }
}


void waitingToStart() {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("     Welcome ");
  lcd.setCursor(0, 1);
  lcd.print("Waiting to start");

}




long displayConfig = millis();
long debounce = 0;
void loop() {

  if (currentMode != lastMode) {
    switch (currentMode) {
      case 0: waitingToStart(); break;
      case 1: changeStage(); break;

      case 2: waitLift();break;
    }
    lastMode = currentMode;
  }

  if(currentMode == 1 && digitalRead(switchPins[currentStage]) == 0 && debounce == 0){
  debounce = millis();   
  }
  if(digitalRead(switchPins[currentStage]) == 1){
    debounce = 0;
  }
  if(debounce != 0 && millis() - debounce > 1000){
    currentMode++;
  }
 





  /*
    while (currentMode < numberOfStages) {

    changeMode();
    waitLift();

    long startTime = millis();
    int displayTime = configuration.liftingTime / 1000 + 1;
    while (millis() - startTime < configuration.liftingTime + 100) {
      if (millis() - lastReading > refreshRate) {
        refreshFunction();


        lcd.setCursor(0, 1);
        lcd.print("Time left ");
        lcd.print((configuration.liftingTime - (millis() - startTime)) / 1000);
        lcd.print(" sec  ");


      }
      double mean = (currentSum[currentMode] * 1.0) / numberOfSamples;
      double standardVariation = calculateStandardVariation();
      coefficientOfVariation[currentMode]  = (standardVariation / mean ) * 100.0;

    }
    printResult();
    sendResultToClient();
    currentMode++;

    }
  */

  if (Serial.available()) {
    delay(100);
    String message = "";
    char c = Serial.read();
    long timeout = millis();
    while (c != '#' && millis() - timeout < 3000) {
      message += c;
      c = Serial.read();
      delay(10);
    }

    if(message.indexOf("startTest") >=0 && currentMode == 0){
      currentMode = 1;
    }

    if (message.indexOf("sendConfig") >= 0) {
      delay(500);
      String message = "config:";
      message += configuration.numberOfSamples;
      message += ";";
      message += configuration.liftingTime;

      Serial.println(message);
      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print("Sending config");
      lcd.setCursor(0, 1);
      lcd.print(message);
      displayConfig = millis();

    }

    if (message.indexOf("config") >= 0) {
      String paramA = message.substring(message.indexOf(":") + 1, message.indexOf(";"));
      String paramB = message.substring(message.indexOf(";") + 1);
      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print("paramA: |");
      lcd.print(paramA);
      lcd.print("|");
      lcd.setCursor(0, 1);
      lcd.print("paramB: |");
      lcd.print(paramB);
      lcd.print("|");
      displayConfig = millis();
      configuration.numberOfSamples = paramA.toInt();
      configuration.liftingTime = paramB.toInt();
      EEPROM_writeAnything(0, configuration);
    }

    if (message == "reset Arduino") {
      lcd.clear();
      lcd.setCursor(0,0);
      lcd.print("Device");
      lcd.setCursor(0,1);
      lcd.print("   Restarted");
      delay(2000);
      
      resetArduino();
    }


  }






}
