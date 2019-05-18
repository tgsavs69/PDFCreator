/*libraries required for I2C LCD*/
#include <LiquidCrystal_I2C.h>
#include <Wire.h>
/*LCD with I2C */
LiquidCrystal_I2C lcd(/*address*/0x27, /*number of lines*/16,/*number of rows*/ 2);


String lastLineA , lastLineB;

void displayMessage(String lineA, String lineB) {
  if(lastLineA == lineA && lastLineB == lineB) return;
  
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print(lineA);
  lcd.setCursor(0, 1);
  lcd.print(lineB);
  lastLineA = lineA;
  lastLineB = lineB;
}


void displayMessage(String lineA, String lineB, int displayTime) {
  long timestamp = millis();
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print(lineA);
  lcd.setCursor(0, 1);
  lcd.print(lineB);
  while (millis() - timestamp < displayTime);
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print(lastLineA);
  lcd.setCursor(0, 1);
  lcd.print(lastLineB);
}


void initLCD() {
  lcd.init();
  lcd.backlight();
  lcd.clear();

  displayMessage("WELCOME", "TO THE TEST");
}
