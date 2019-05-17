int currentMode = 0;
double currentValue = 0;
long lastReading = 0;
double firstTerm = 0.0;
double secondTerm = 0.0;
double standardDeviation = 0.0;


double calculateStandardVariation() {
  double tempResult = 0.0;
  double mean = (currentSum[currentMode] * 1.0) / numberOfSamples;
  for (int i = 0; i < numberOfSamples; i++) {
    tempResult += ((history[currentMode][i] - mean) * (history[currentMode][i] - mean));
  }
  tempResult = (tempResult * 1.0) / (numberOfSamples-1);
  tempResult = sqrt(tempResult);


  return tempResult;
}


void refreshFunction() {
  /*read from the sensor*/
  double  currentValue = scale.get_units();

  /*substract the first element of the history and add the latest value*/
  currentSum[currentMode] = currentSum[currentMode] - history[currentMode][0];
  currentSum[currentMode] = currentSum[currentMode] + currentValue;


  /*update the history matrix with the latest value
    Shift all elements on the line "currentMode" to the left and add the
    latest element as last.  */

  for (int i = 0; i < numberOfSamples - 1; i++) {
    history[currentMode][i] = history[currentMode][i + 1];
  }
  history[currentMode][numberOfSamples - 1] = currentValue;

  /*update the maximum sum recorded*/
  if (currentValue > maximum[currentMode]) {
    maximum[currentMode] = currentValue;
    
  }




/*
  Serial.print(stages[currentMode]);
  for (int j = 0; j < numberOfSamples; j++) {
    Serial.print(" ");
    Serial.print(history[currentMode][j]);
  }

  Serial.print(" currentSum -> ");
  Serial.print(currentSum[currentMode]);

  Serial.print(" maximum -> ");
  Serial.print(maximum[currentMode]);
  Serial.print(" CV -> ");
  Serial.println(coefficientOfVariation[currentMode]);

*/

  lastReading = millis();
}
