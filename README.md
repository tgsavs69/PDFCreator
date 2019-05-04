# How to use PDFCreator:

1. Download all the files from GitHub and save them on your computer.

2. To launch the client, you have to double click on "PDFCreator.jar"


## There are 3 main sections in the interface:
### I. OVERVIEW
You can preview data collected from Arduino, such as maximum lift for every stage and its coefficient of variation.


### II. REPORT DETAILS	
Here you can complete the report with the required data such as subject name, date of birth etc.
#### NOTE! Fields can not be blank!
#### IMPORTANT! The "Date of Birth" field MUST have this format "dd.mm.yyyy".Example: 04.05.2019

#### FEATURE! If you do not specify the absolute location (e.g. "C:\Users\Reports\"), the program will save the PDF in its directory.
#### NOTE! This software do not require administrator rights so you are not allowed to save anywhere on your computer.
#### FEATURE! In the report name, you do not have to include the extension. For example "output" and "output.pdf" mean the same thing.



After you completed all report details, you have to click on "Generate report". If everything is fine, the default program that you use to open PDFs should open with the generated report. 


### III. Arduino Settings

Here you can connect to the Arduino to receive data.
Steps:
####1. Plug Arduino board to the computer.
####2. Wait some time (5 seconds) then click on "Refresh" button.
####3. Select the right COM port.
####4. Click "Connect".

Now, the status should change from "Disconnected" to "Connected".


##BUGS
No bugs known yet.
