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
const String stages[] = {"High Near", "High Far", "Waist Lift", "Knee Lift", "Floor Lift"};


#include "displayFile.h"
/*file responsabile for receiving and transmitting data to client*/
#include "serialEvent.h"
#include "loadCell.h"



void setup() {
  Serial.begin(115200);
  initLCD();
  startHX711();
}

void loop() {
  if (testStarted == false) return;

  if (liftReleased == false && liftStarted == true && scale.get_units() > 1) {
    displayMessage("RELEASE", "   THE LIFT");
    return;
  }

  if (liftStarted == false && scale.get_units() < 1) {
    displayMessage("STAGE " + stages[currentStage], "ATTEMP " + String(currentTry));
    return;
  }


  if (liftStarted == false && scale.get_units() > 1) {
    liftStarted = true;
    startLiftingTime = millis();
    displayMessage("Lift", "    Started");
    liftReleased = true;
  }

  if (millis() - startLiftingTime < liftingTime) {
    /*
      if ((millis() - startLiftingTime) / refreshRate != lastReadTime) {
      lastReadTime = (millis() - startLiftingTime) / refreshRate;
      Serial.print(lastReadTime);
      }
    */
    Serial.println("READING");

  }
  else {
    if (liftReleased == true) {

      Serial.print("The ");
      Serial.print(currentTry);
      Serial.println(" has finished. Prepare for the next one");
      currentTry = currentTry + 1;
      displayMessage("ATTEMPT" + String(currentTry), "FINISHED",3000);
      liftReleased = false;

      if (currentTry == 4){
        currentTry = 1;
        currentStage = currentStage + 1;
        displayMessage("STAGE FINISHED","max: 50 cv: 69%",3000);
        
      }



    }
  if (scale.get_units() < 1) liftStarted = false;

  }


}
