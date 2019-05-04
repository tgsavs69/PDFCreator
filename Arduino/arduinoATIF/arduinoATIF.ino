#define numberOfStages 5
#define numberOfSamples 4

//store the names of the stages
const String stages[] = {"High Near", "High Far", "Waist Lift", "Knee Lift", "Floor Lift"};

//store the pins used by the switches
const int switchPins[] = {12, 11, 10, 9, 8};

//the history matrix is going to help us to calculate the maximum value of the last seconds
double history[numberOfStages][numberOfSamples];
double maximum[numberOfStages];

int refreshRate = 1000;
int currentMode = 0;
double currentValue = 0;
long lastReading = 0;
void setup() {
  for (int i = 0; i < numberOfStages; i++) {
    pinMode(switchPins[i], INPUT_PULLUP);
  }
  Serial.begin(9600);
  Serial.println("Arduino started");
  Serial.print("Default mode: ");
  Serial.println(stages[currentMode]);


  /*reset history matrix*/
  for (int i = 0; i < numberOfStages; i++) {
    for (int j = 0; j < numberOfSamples; j++) {
      history[i][j] = -1;
    }
  }

  /*reset maximum array */
  for (int i = 0; i < numberOfStages; i++) {
    maximum[i] = -1;
  }


  lastReading = millis();
}


void loop() {

  if (millis() - lastReading > refreshRate) {

    currentValue = random(1000, 5000) * 1.0 / 100;
    int valueAdded = false;
    for (int i = 0; i < numberOfSamples; i++) {
      if (history[currentMode][i] == -1) {
        valueAdded = true;
        history[currentMode][i] = currentValue;
        break;
      }
    }

    if (valueAdded == false) {
      double tempSum = 0;
      for (int i = 0; i < numberOfSamples - 1; i++) {
        history[currentMode][i] = history[currentMode][i + 1];
        tempSum += history[currentMode][i];
      }

      history[currentMode][numberOfSamples - 1] = currentValue;
      tempSum += history[currentMode][numberOfSamples - 1];
      tempSum = (tempSum * 1.0 ) / 4.0;
      if (tempSum > maximum[currentMode]) {
        maximum[currentMode] = tempSum;
      }
    }



    Serial.print(stages[currentMode]);
    for (int j = 0; j < numberOfSamples; j++) {
      Serial.print(" ");
      Serial.print(history[currentMode][j]);
    }
    Serial.print(" maximum -> ");
    Serial.println(maximum[currentMode]);


    lastReading = millis();
  }



  for (int i = 0; i < numberOfStages; i++) {
    if (digitalRead(switchPins[i]) == 0 && currentMode != i) {
      currentMode = i;
      Serial.print("Mode changed to ");
      Serial.println(stages[i]);
      break;
    }
  }



}
