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

void resetData() {
  currentMode = 0;
  currentValue = 0;
  lastReading = 0;
  /*initialise the history matrix with value -1*/
  for (int i = 0; i < numberOfStages; i++) {
    for (int j = 0; j < numberOfSamples; j++) {
      history[i][j] = -1;
    }
  }

  /*reset maximum array to value -1*/
  for (int i = 0; i < numberOfStages; i++) {
    maximum[i] = -1;
  }

  /*reset current sum array to value -1*/
  for (int i = 0; i < numberOfStages; i++) {
    currentSum[i] = -1 * numberOfSamples;
  }

  /*reset maximum array to value -1*/
  for (int i = 0; i < numberOfStages; i++) {
    coefficientOfVariation[i] = -1;
  }
}

void setup() {

  startLCD();
  startHX711();

  loadConfiguration();

  /*configure the switchPins as INPUT_PULLUP*/
  for (int i = 0; i < numberOfStages; i++) {
    pinMode(switchPins[i], INPUT_PULLUP);
  }
  /*begin the serial communication at 115200! It is important to be 115220( the same as the client's baud rate)*/
  Serial.begin(115200);


  resetData();



}

void changeMode() {
  /*wait until the user changed to the required mode*/
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Change to");
  lcd.setCursor(0, 1);
  lcd.print("   ");
  lcd.print(stages[currentMode]);
  while (digitalRead(switchPins[currentMode]) == 1);
}


void waitLift() {
  /*wait until the user starts to lift*/
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Mode: ");
  lcd.print(stages[currentMode]);
  lcd.setCursor(0, 1);
  lcd.print("Waiting to lift...");
  while (scale.get_units() < 1);
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

void loop() {

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
    Serial.println("arduino restarted");
    if (message == "reset Arduino") {
      delay(2000);
      Serial.println("arduino restarted");
      resetData();
    }


  }

}
