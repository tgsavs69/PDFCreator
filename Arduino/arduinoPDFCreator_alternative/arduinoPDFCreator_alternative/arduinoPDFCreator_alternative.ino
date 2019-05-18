int temporaryReads[16];
int currentStage = 0;
int currentTry = 1;
int liftingTime = 4000;
bool liftStarted = false;
long startLiftingTime;
int refreshRate = 250;
int lastReadTime = 0;
bool testStarted = false;
bool liftReleased = false;

//store the names of the stages
const String stages[] = {"HIGH NEAR", "HIGH FAR", "WAIST LIFT", "KNEE LIFT", "FLOOR LIFT"};
//store the pins used by the switches
const int switchPins[] = {/*HIGH NEAR*/12, /*HIGH FAR*/ 11,/*WAIST LIFT*/ 10,/*KNEE LIFT*/ 9, /*FLOOR LIFT*/8};

double maximum[5][3];


#include "displayFile.h"
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
}

void loop() {
  if (testStarted == false) return;

  if (digitalRead(switchPins[currentStage]) == 1 && liftStarted == false) {
    displayMessage("SWITCH TO", stages[currentStage]);
    return;
  }

  if (liftReleased == false && liftStarted == true && scale.get_units() > 1) {
    displayMessage("RELEASE", "   THE LIFT");
    return;
  }

  if (liftStarted == false && scale.get_units() < 1) {
    displayMessage("STAGE " + stages[currentStage], "ATTEMPT " + String(currentTry));
    lastReadTime = 0;
    return;
  }


  if (liftStarted == false && scale.get_units() > 1) {
    liftStarted = true;
    startLiftingTime = millis();
    displayMessage("LIFT", "    STARTED");
    liftReleased = true;
  }


  if (lastReadTime < liftingTime / refreshRate) {
    //if (millis() - startLiftingTime < liftingTime) {



    if ((millis() - startLiftingTime) / refreshRate != lastReadTime) {
      lastReadTime = (millis() - startLiftingTime) / refreshRate;
      double tempReading = scale.get_units();
      if (maximum[currentStage][currentTry - 1] < tempReading) {
        maximum[currentStage][currentTry - 1] = tempReading;
      }
      /*    Serial.print(lastReadTime);
            Serial.print(" -> ");
            Serial.print(tempReading);
            Serial.print("  maximum ->");
            Serial.println(maximum[currentStage][currentTry - 1]);*/
    }

    //Serial.println("READING");

  }
  else {
    if (liftReleased == true) {

      /* Serial.print("The ");
        Serial.print(currentTry);
        Serial.println(" has finished. Prepare for the next one"); */
      displayMessage("ATTEMPT" + String(currentTry), "FINISHED", 3000);
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

        displayMessage("STAGE FINISHED", "max: 50 cv: 69%", 3000);

      }



    }
    if (scale.get_units() < 1) liftStarted = false;

  }


}
