package pdfcreator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import javax.swing.JOptionPane;
import static pdfcreator.PDFCreator.guiWindow;

public class SerialTest implements SerialPortEventListener {

    SerialPort serialPort;
    /**
     * The port we're normally going to use.
     */
    public static String PORT_NAMES = "none";

    /**
     * A BufferedReader which will be fed by a InputStreamReader converting the
     * bytes into characters making the displayed results codepage independent
     */
    public void setPortName(String tempPort) {
        PORT_NAMES = tempPort;
    }
    private BufferedReader input;
    /**
     * The output stream to the port
     */
    private OutputStream output;
    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 2000;
    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 115200;

    public String initialize() {
        // the next line is for Raspberry Pi and 
        // gets us into the while loop and was suggested here was suggested https://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
        // System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
        try {
            CommPortIdentifier portId = null;
            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

            //First, Find an instance of serial port as set in PORT_NAMES.
            while (portEnum.hasMoreElements()) {
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();

                if (currPortId.getName().equals(PORT_NAMES)) {
                    portId = currPortId;
                    break;
                }

            }
            if (portId == null) {
                System.out.println("Could not find COM port.");
                //  guiWindow.
                return "error";
            }

            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (UnsatisfiedLinkError e) {
            System.out.println("e praf");
            if (e.toString().contains("no rxtxSerial") == true) {
                guiWindow.displayError("Java is not configured correctly.\n rxtxSerial.dll not found");
            }
        } catch (Exception e) {
            System.out.println("Error ->" + e.toString());
            if (e.toString().contains("PortInUseException")) {
                guiWindow.displayError("COM port in use.\nClose all programs that use that COM port");
            }

            return "error";
        }

        return "succes";
    }

    /**
     * This should be called when you stop using the port. This will prevent
     * port locking on platforms like Linux.
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    public void sendConfig(int paramA) throws IOException {

        String firstParam = Integer.toString(paramA);
        String messageToArduino = "config:" + firstParam + "#";
        output.write(messageToArduino.getBytes());
        System.out.println(messageToArduino);

    }

    public void resetArduino() throws IOException {
        String messageToArduino = "reset Arduino#";
        output.write(messageToArduino.getBytes());
        System.out.println("Arduino reseted");
    }

    public void loadConfiguration() throws IOException {
        System.out.println("sending sendConfig# to arduino");
        String messageToArduino = "sendConfig#";
        output.write(messageToArduino.getBytes());
    }

    public void startTest() throws IOException {
        String messageToArduino = "startTest#";
        output.write(messageToArduino.getBytes());
    }

    public void tare() throws IOException {
        String messageToArduino = "tareTare#";
        output.write(messageToArduino.getBytes());
    }

    public int[] records = {0, 0, 0, 0, 0};
    public String[] states = {"High Near", "High Far", "Waist Lift", "Knee Lift", "Floor Lift"};
    int counter = -1;
    boolean labelReceived = false;

    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = input.readLine();
                System.out.println(inputLine);

                if (inputLine.indexOf("MAXIMUM") >= 0) {
                    guiWindow.modifyValues(inputLine);

                }

                if (inputLine.contains("currentReading")) {
                    // System.out.println(inputLine);
                    guiWindow.modifyCurrentReading(inputLine);

                }

                if (inputLine.indexOf("config") >= 0) {
                    System.out.println("message received ->" + inputLine);

                    String paramA = inputLine.substring(inputLine.indexOf(":") + 1, inputLine.indexOf("#"));
                    guiWindow.updateConfig(paramA);

                }
                /*
                for (int i = 0; i < states.length; i++) {
                    if (inputLine.contains(states[i]) == true) {
                        String liftValue = inputLine.substring(inputLine.indexOf('!') + 1, inputLine.indexOf('$'));
                        String cvValue = inputLine.substring(inputLine.indexOf('%') + 1, inputLine.indexOf('#'));

                        guiWindow.modifyLabel(states[i], liftValue, cvValue);

                        break;

                    }
                }*/
            } catch (Exception e) {
                this.close();
                guiWindow.arduinoDisconnected();
                System.out.println("Arduino disconnected");
                System.err.println(e.toString());
            }
        }

    }

    public static void main(String[] args) throws Exception {

        SerialTest main = new SerialTest();
        main.initialize();

        System.out.println("Started");

    }
}
