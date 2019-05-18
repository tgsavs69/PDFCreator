#include "displayFile.h"
#include "EEPROMAnything.h"


int currentStage = 0;
int currentTry = 1;
int liftingTime = 4000;
bool liftStarted = false;
long startLiftingTime;
int refreshRate = 250;
int lastReadTime = 0;
bool testStarted = false;
bool liftReleased = false;
double currentReading = 0.0;

long updateCurrentReading = 0;

const int numberOfStages = 5;
const int numberOfTries = 3;
//store the names of the stages
const String stages[] = {"HIGH NEAR", "HIGH FAR", "WAIST LIFT", "KNEE LIFT", "FLOOR LIFT"};
//store the pins used by the switches
const int switchPins[] = {/*HIGH NEAR*/12, /*HIGH FAR*/ 11,/*WAIST LIFT*/ 10,/*KNEE LIFT*/ 9, /*FLOOR LIFT*/8};

double maximum[numberOfStages][numberOfTries];


void resetData() {
  currentStage = 0;
  currentTry = 1;
  liftStarted = false;
  liftReleased = false;
  testStarted = false;
  currentReading = 0.0;
  updateCurrentReading = 0;
  for (int i = 0; i < numberOfStages; i++) {
    for (int j = 0; j < numberOfTries; j++) {
      maximum[i][j] = 0;
    }
  }


  displayMessage("WELCOME", "TO THE TEST");
}


/*file responsabile for receiving and transmitting data to client*/
#include "serialEvent.h"
#include "loadCell.h"



void setup() {
  Serial.begin(115200);
  initLCD();
  startHX711();
  /*configure the switchPins as INPUT_PULLUP*/
  for (int i = 0; i < 5; i++) {
    pinMode(switchPins[i], INPUT_PULLUP);
  }

  EEPROM_readAnything(0, liftingTime);

  if (liftingTime < 500) {
    liftingTime = 4000;
  }

}

void loop() {
  currentReading = scale.get_units();
  if (millis() - updateCurrentReading > 500) {
    String tempCurrentReading = "currentReading:";
    tempCurrentReading += String(currentReading);
    tempCurrentReading += "#";
    Serial.println(tempCurrentReading);
    updateCurrentReading = millis();
  }

  if (currentStage == numberOfStages) {
    displayMessage("TEST FINISHED", "RESET DATA");
    return;
  }
  if (testStarted == false) return;

  if (digitalRead(switchPins[currentStage]) == 1 && liftStarted == false) {
    displayMessage("SWITCH TO", stages[currentStage]);
    return;
  }

  if (liftReleased == false && liftStarted == true && currentReading > 1) {
    displayMessage("RELEASE", "   THE LIFT");
    return;
  }

  if (liftStarted == false && currentReading < 1) {
    displayMessage("STAGE " + stages[currentStage], "ATTEMPT " + String(currentTry));
    lastReadTime = 0;
    return;
  }


  if (liftStarted == false && currentReading > 1) {
    liftStarted = true;
    startLiftingTime = millis();
    displayMessage("LIFT STARTED", "    STARTED");
    liftReleased = true;
  }


  if (lastReadTime < liftingTime / refreshRate) {

    if ((millis() - startLiftingTime) / refreshRate != lastReadTime) {
      lastReadTime = (millis() - startLiftingTime) / refreshRate;
      double tempReading = currentReading;

      if (maximum[currentStage][currentTry - 1] < tempReading) {
        maximum[currentStage][currentTry - 1] = tempReading;
      }
      displayMessage("LIFT STARTED", "Current " + String(tempReading));

    }
  }
  else {
    if (liftReleased == true && lastReadTime == liftingTime / refreshRate) {

      displayMessage("ATTEMPT " + String(currentTry), "MAXIMUM " + String(maximum[currentStage][currentTry - 1]), 3000);
      currentTry = currentTry + 1;

      liftReleased = false;

      if (currentTry == 4) {
        Serial.print("MAXIMUM ");
        Serial.print(stages[currentStage]);

        for (int i = 0; i < 3; i++) {
          Serial.print(":");
          Serial.print(maximum[currentStage][i]);
        }
        Serial.println("#");
        currentTry = 1;
        currentStage = currentStage + 1;

        displayMessage("STAGE FINISHED", "", 3000);

      }



    }
    if (currentReading < 1) liftStarted = false;

  }


}
