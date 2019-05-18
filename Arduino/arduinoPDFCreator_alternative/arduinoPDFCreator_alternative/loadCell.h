#pragma once
#include <HX711.h>

#define calibration_factor 3000
#define DOUT  3
#define CLK  2
HX711 scale;

void startHX711() {

  scale.begin(DOUT, CLK);
  scale.set_scale(calibration_factor); //This value is obtained by using the SparkFun_HX711_Calibration sketch
  scale.tare();
}
