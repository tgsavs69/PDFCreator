//the history matrix is going to help us to calculate the maximum value of the last seconds

const int numberOfStages = 5;
const int numberOfSamples = 4;
//double history[numberOfStages][numberOfSamples];
double maximum[numberOfStages];
double currentSum[numberOfStages];
double coefficientOfVariation[numberOfStages];

double **history;



struct config_t {
  int numberOfSamples;
  int liftingTime;
} configuration;


int refreshRate; //how often to read from the load cell amp HX711
//int numberOfSamples = 16; //number of samples
//int numberOfStages = 5;

//store the names of the stages
const String stages[] = {"High Near", "High Far", "Waist Lift", "Knee Lift", "Floor Lift"};

//store the pins used by the switches
const int switchPins[] = {/*HIGH NEAR*/12, /*HIGH FAR*/ 11,/*WAIST LIFT*/ 10,/*KNEE LIFT*/ 9, /*FLOOR LIFT*/8};


/*LCD with I2C */
LiquidCrystal_I2C lcd(/*address*/0x27, /*number of lines*/16,/*number of rows*/ 2);

/*HX 711 SACLE*/
#define calibration_factor 3000
#define DOUT  3
#define CLK  2

//#define liftingTime 4000

HX711 scale;


void loadConfiguration() {
  configuration.numberOfSamples = 4;



  if (history != NULL) {
    delete[] history;
  }

  EEPROM_readAnything(0, configuration);

  
  int errorReading = false;
  if (configuration.numberOfSamples == 0) {
    Serial.println("Invalid number of samples");
    errorReading = true;
  }
  else {
    if (configuration.liftingTime == 0) {
      Serial.println("Invalid liftingTime");
      errorReading = true;
    }
  }
  if (errorReading == true) {
    /*load default settings*/
    configuration.numberOfSamples = 4;
    configuration.liftingTime = 4000;
    EEPROM_writeAnything(0, configuration);
    Serial.println("Default configuration loaded");
  }



  Serial.print("numberOfSamples = ");
  Serial.println(configuration.numberOfSamples);
  Serial.print("liftingTime = ");
  Serial.println(configuration.liftingTime);

  refreshRate = 1000.0 / numberOfSamples;
  history = (double **) calloc(numberOfStages, sizeof(double*));

  for (int i = 0; i < numberOfStages; i++) {
    history[i] = (double*) calloc(configuration.numberOfSamples, sizeof(double));
  }



  /*
     configuration.resetValues();
     EEPROM_writeAnything(0, configuration);
  */

}
