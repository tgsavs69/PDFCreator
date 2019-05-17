package pdfcreator;

public class PDFCreator {

    public static gui guiWindow;

    public static void main(String[] args) {
        try {
            guiWindow = new gui();
            guiWindow.setVisible(true);
        } catch (Exception ex) {
            System.out.println("Unable to start the user interface");
        }

    }
}
