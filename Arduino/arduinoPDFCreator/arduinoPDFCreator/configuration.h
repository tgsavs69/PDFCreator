

#define refreshRate 250  //how often to read from the load cell amp HX711
#define numberOfSamples 16  //number of samples 

#define numberOfStages 5

//store the names of the stages
const String stages[] = {"High Near", "High Far", "Waist Lift", "Knee Lift", "Floor Lift"};

//store the pins used by the switches
const int switchPins[] = {12, 11, 10, 9, 8};


/*LCD with I2C */
LiquidCrystal_I2C lcd(/*address*/0x27, /*number of lines*/16,/*number of rows*/ 2);

/*HX 711 SACLE*/
#define calibration_factor 3000 
#define DOUT  3
#define CLK  2

#define liftingTime 4000

HX711 scale;
