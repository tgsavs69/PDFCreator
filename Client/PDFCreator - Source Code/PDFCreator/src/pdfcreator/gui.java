/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfcreator;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import gnu.io.CommPortIdentifier;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.AbstractCategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author banci
 */
public class gui extends javax.swing.JFrame {

    void modifyLabel(String name, String value, String cv) {
        switch (name) {
            case "High Near":
                highNearValue1.setText(value);
                highNearCVValue.setText(cv);
                break;
            case "High Far":
                highFarValue1.setText(value);
                highFarCVValue.setText(cv);
                break;
            case "Waist Lift":
                waistLiftValue1.setText(value);
                waistLiftCVValue.setText(cv);
                break;
            case "Knee Lift":
                kneeLiftValue1.setText(value);
                kneeLiftCVValue.setText(cv);
                break;
            case "Floor Lift":
                floorLiftValue1.setText(value);
                floorLiftCVValue.setText(cv);
                break;
        }
    }

    public static void writeChartToPDF(JFreeChart chart, int width, int height, String fileName) throws IOException {
        PdfWriter writer = null;

        Document document = new Document();

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(
                    fileName));
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(width, height);

            Graphics2D graphics2d = template.createGraphics(width, height,
                    new DefaultFontMapper());
            Rectangle2D rectangle2d = new Rectangle2D.Double(20, 0, width,
                    height);

            chart.draw(graphics2d, rectangle2d);

            graphics2d.dispose();
            contentByte.addTemplate(template, 25, 250);

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        document.close();
    }

    public static JFreeChart generateBarChart(String highNear, String highFar, String waistLift, String kneeLift, String floorLift) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        dataSet.setValue(Integer.parseInt(highNear), "lifting from\nthe shoulders", "High Near");
        dataSet.setValue(Integer.parseInt(highFar), "lifting above\nthe shoulders", "High Far");
        dataSet.setValue(Integer.parseInt(waistLift), "lifting from\nthe waist", "Waist Lift");
        dataSet.setValue(Integer.parseInt(kneeLift), "lifting from\nthe knees", "Knee Lift");
        dataSet.setValue(Integer.parseInt(floorLift), "lifting from\nthe floor", "Floor Lift");

        //JFreeChart chart2 = ChartFactory.createBarChart(title, categoryAxisLabel, valueAxisLabel, dataSet, PlotOrientation.HORIZONTAL, true, true, true)
        JFreeChart chart = ChartFactory.createBarChart(
                "        Physical abilities recommendations in lbs", "Lift Height", "Force (lbs)",
                dataSet, PlotOrientation.HORIZONTAL, true, true, false);
        AbstractCategoryItemRenderer renderer = (AbstractCategoryItemRenderer) chart.getCategoryPlot().getRenderer();

        renderer.setSeriesPaint(0, Color.black);
        renderer.setSeriesPaint(1, Color.red);
        renderer.setSeriesPaint(2, Color.yellow);
        renderer.setSeriesPaint(3, Color.blue);
        renderer.setSeriesPaint(4, Color.green);

        //   final  BasicStroke dashed =new BasicStroke(1.0f);
        // renderer.setOutlineStroke(dashed);
        //  renderer.setBaseStroke(dashed);
        chart.getPlot().setBackgroundPaint(Color.gray);

        return chart;
    }

    public String prepareFileName(String fileName) throws Exception {
        try {

            System.out.println("File location before parse -> " + fileName);
            while (!fileName.equals(fileName.replace("%20", " "))) {
                fileName = fileName.replace("%20", " ");

            }

            File f = new File(fileName);
            if (f.exists()) {
                throw new IOException("The file is a folder");
            }

            if (!fileName.endsWith(".pdf")) {
                fileName += ".pdf";
            }
            System.out.println("File location after parse -> " + fileName);
            return fileName;
        } catch (IOException error) {
            String errorMessage = "Unable to create the file \n" + error.getMessage();
            // JOptionPane.showMessageDialog(rootPane, errorMessage, "Unable to create file", ERROR_MESSAGE);
            throw new Exception(errorMessage);

        }

    }

    public boolean createPDF(String fileName) throws Exception {

        try {
            PDDocument createDocument = new PDDocument();
            createDocument.save(fileName);
            createDocument.close();
            return true;
        } catch (IOException error) {
            /*
            
            JOptionPane.showMessageDialog(rootPane, errorMessage, "Unable to create file", ERROR_MESSAGE);
             */
            String errorMessage = "Unable to create the file \n" + error.getMessage();
            //throw new Exception (errorMessage);

        }
        return false;
    }

    /**
     * Creates new form gui
     */
    public gui() {
        initComponents();
        this.setTitle("PDF Creator");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        overviewLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        highNearValue1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        highFarValue1 = new javax.swing.JLabel();
        kneeLiftValue1 = new javax.swing.JLabel();
        waistLiftValue1 = new javax.swing.JLabel();
        floorLiftValue1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        overviewLabel2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        highNearValue2 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        highFarValue2 = new javax.swing.JLabel();
        kneeLiftValue2 = new javax.swing.JLabel();
        waistLiftValue2 = new javax.swing.JLabel();
        floorLiftValue2 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        overviewLabel4 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        highNearValue3 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        highFarValue3 = new javax.swing.JLabel();
        kneeLiftValue3 = new javax.swing.JLabel();
        waistLiftValue3 = new javax.swing.JLabel();
        floorLiftValue3 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        overviewLabel7 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        highNearValueSummary = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        highFarValueSummary = new javax.swing.JLabel();
        kneeLiftValueSummary = new javax.swing.JLabel();
        waistLiftValueSummary = new javax.swing.JLabel();
        floorLiftValueSummary = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        overviewLabel8 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        highNearCVValue = new javax.swing.JLabel();
        highNearValue9 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        highFarCVValue = new javax.swing.JLabel();
        highNearValue11 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        waistLiftCVValue = new javax.swing.JLabel();
        highNearValue13 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        kneeLiftCVValue = new javax.swing.JLabel();
        highNearValue15 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        floorLiftCVValue = new javax.swing.JLabel();
        highNearValue17 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        officeName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        address = new javax.swing.JTextField();
        phoneNumber = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tehnicianName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        outputLocation = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        subjectName = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        dateBirth = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        comPorts = new javax.swing.JComboBox<>();
        numberOfSamplesValue = new javax.swing.JTextField();
        liftingTimeValue = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("REPORT SETTINGS");

        jLabel8.setText("jLabel8");

        jLabel21.setText("%");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTabbedPane1.setFocusable(false);
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        overviewLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        overviewLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        overviewLabel1.setText("FIRST TRY");
        overviewLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("HIGH NEAR");

        highNearValue1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        highNearValue1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highNearValue1.setText("4");
        highNearValue1.setMaximumSize(new java.awt.Dimension(8, 20));
        highNearValue1.setMinimumSize(new java.awt.Dimension(8, 20));
        highNearValue1.setPreferredSize(new java.awt.Dimension(8, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("HIGH FAR");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("WAIST LIFT");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("KNEE LIFT");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("FLOOR LIFT");

        highFarValue1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        highFarValue1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highFarValue1.setText("10");
        highFarValue1.setMaximumSize(new java.awt.Dimension(8, 20));
        highFarValue1.setMinimumSize(new java.awt.Dimension(8, 20));
        highFarValue1.setPreferredSize(new java.awt.Dimension(8, 20));

        kneeLiftValue1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        kneeLiftValue1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kneeLiftValue1.setText("150");
        kneeLiftValue1.setMaximumSize(new java.awt.Dimension(8, 20));
        kneeLiftValue1.setMinimumSize(new java.awt.Dimension(8, 20));
        kneeLiftValue1.setPreferredSize(new java.awt.Dimension(8, 20));

        waistLiftValue1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        waistLiftValue1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        waistLiftValue1.setText("42");
        waistLiftValue1.setMaximumSize(new java.awt.Dimension(8, 20));
        waistLiftValue1.setMinimumSize(new java.awt.Dimension(8, 20));
        waistLiftValue1.setPreferredSize(new java.awt.Dimension(8, 20));

        floorLiftValue1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        floorLiftValue1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        floorLiftValue1.setText("69");
        floorLiftValue1.setMaximumSize(new java.awt.Dimension(8, 20));
        floorLiftValue1.setMinimumSize(new java.awt.Dimension(8, 20));
        floorLiftValue1.setPreferredSize(new java.awt.Dimension(8, 20));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(floorLiftValue1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kneeLiftValue1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(waistLiftValue1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(highFarValue1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(highNearValue1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(overviewLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(overviewLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(highNearValue1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(highFarValue1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(waistLiftValue1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kneeLiftValue1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(floorLiftValue1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        overviewLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        overviewLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        overviewLabel2.setText("SECOND TRY");
        overviewLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("HIGH NEAR");

        highNearValue2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        highNearValue2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highNearValue2.setText("4");
        highNearValue2.setMaximumSize(new java.awt.Dimension(8, 20));
        highNearValue2.setMinimumSize(new java.awt.Dimension(8, 20));
        highNearValue2.setPreferredSize(new java.awt.Dimension(8, 20));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("HIGH FAR");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("WAIST LIFT");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("KNEE LIFT");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("FLOOR LIFT");

        highFarValue2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        highFarValue2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highFarValue2.setText("10");
        highFarValue2.setMaximumSize(new java.awt.Dimension(8, 20));
        highFarValue2.setMinimumSize(new java.awt.Dimension(8, 20));
        highFarValue2.setPreferredSize(new java.awt.Dimension(8, 20));

        kneeLiftValue2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        kneeLiftValue2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kneeLiftValue2.setText("150");
        kneeLiftValue2.setMaximumSize(new java.awt.Dimension(8, 20));
        kneeLiftValue2.setMinimumSize(new java.awt.Dimension(8, 20));
        kneeLiftValue2.setPreferredSize(new java.awt.Dimension(8, 20));

        waistLiftValue2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        waistLiftValue2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        waistLiftValue2.setText("42");
        waistLiftValue2.setMaximumSize(new java.awt.Dimension(8, 20));
        waistLiftValue2.setMinimumSize(new java.awt.Dimension(8, 20));
        waistLiftValue2.setPreferredSize(new java.awt.Dimension(8, 20));

        floorLiftValue2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        floorLiftValue2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        floorLiftValue2.setText("69");
        floorLiftValue2.setMaximumSize(new java.awt.Dimension(8, 20));
        floorLiftValue2.setMinimumSize(new java.awt.Dimension(8, 20));
        floorLiftValue2.setPreferredSize(new java.awt.Dimension(8, 20));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(floorLiftValue2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kneeLiftValue2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(waistLiftValue2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(highFarValue2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(highNearValue2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(overviewLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(overviewLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(highNearValue2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(highFarValue2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(waistLiftValue2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kneeLiftValue2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(floorLiftValue2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        overviewLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        overviewLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        overviewLabel4.setText("THIRD TRY");
        overviewLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("HIGH NEAR");

        highNearValue3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        highNearValue3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highNearValue3.setText("4");
        highNearValue3.setMaximumSize(new java.awt.Dimension(8, 20));
        highNearValue3.setMinimumSize(new java.awt.Dimension(8, 20));
        highNearValue3.setPreferredSize(new java.awt.Dimension(8, 20));

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("HIGH FAR");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("WAIST LIFT");

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("KNEE LIFT");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("FLOOR LIFT");

        highFarValue3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        highFarValue3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highFarValue3.setText("10");
        highFarValue3.setMaximumSize(new java.awt.Dimension(8, 20));
        highFarValue3.setMinimumSize(new java.awt.Dimension(8, 20));
        highFarValue3.setPreferredSize(new java.awt.Dimension(8, 20));

        kneeLiftValue3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        kneeLiftValue3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kneeLiftValue3.setText("150");
        kneeLiftValue3.setMaximumSize(new java.awt.Dimension(8, 20));
        kneeLiftValue3.setMinimumSize(new java.awt.Dimension(8, 20));
        kneeLiftValue3.setPreferredSize(new java.awt.Dimension(8, 20));

        waistLiftValue3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        waistLiftValue3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        waistLiftValue3.setText("42");
        waistLiftValue3.setMaximumSize(new java.awt.Dimension(8, 20));
        waistLiftValue3.setMinimumSize(new java.awt.Dimension(8, 20));
        waistLiftValue3.setPreferredSize(new java.awt.Dimension(8, 20));

        floorLiftValue3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        floorLiftValue3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        floorLiftValue3.setText("69");
        floorLiftValue3.setMaximumSize(new java.awt.Dimension(8, 20));
        floorLiftValue3.setMinimumSize(new java.awt.Dimension(8, 20));
        floorLiftValue3.setPreferredSize(new java.awt.Dimension(8, 20));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(floorLiftValue3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                    .addComponent(kneeLiftValue3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(waistLiftValue3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(highFarValue3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(highNearValue3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(overviewLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(overviewLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(highNearValue3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(highFarValue3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(waistLiftValue3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kneeLiftValue3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(floorLiftValue3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        overviewLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        overviewLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        overviewLabel7.setText("SUMMARY");
        overviewLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("HIGH NEAR");

        highNearValueSummary.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        highNearValueSummary.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highNearValueSummary.setText("4");
        highNearValueSummary.setMaximumSize(new java.awt.Dimension(8, 20));
        highNearValueSummary.setMinimumSize(new java.awt.Dimension(8, 20));
        highNearValueSummary.setPreferredSize(new java.awt.Dimension(8, 20));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("HIGH FAR");

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("WAIST LIFT");

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("KNEE LIFT");

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("FLOOR LIFT");

        highFarValueSummary.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        highFarValueSummary.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highFarValueSummary.setText("10");
        highFarValueSummary.setMaximumSize(new java.awt.Dimension(8, 20));
        highFarValueSummary.setMinimumSize(new java.awt.Dimension(8, 20));
        highFarValueSummary.setPreferredSize(new java.awt.Dimension(8, 20));

        kneeLiftValueSummary.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        kneeLiftValueSummary.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kneeLiftValueSummary.setText("150");
        kneeLiftValueSummary.setMaximumSize(new java.awt.Dimension(8, 20));
        kneeLiftValueSummary.setMinimumSize(new java.awt.Dimension(8, 20));
        kneeLiftValueSummary.setPreferredSize(new java.awt.Dimension(8, 20));

        waistLiftValueSummary.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        waistLiftValueSummary.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        waistLiftValueSummary.setText("42");
        waistLiftValueSummary.setMaximumSize(new java.awt.Dimension(8, 20));
        waistLiftValueSummary.setMinimumSize(new java.awt.Dimension(8, 20));
        waistLiftValueSummary.setPreferredSize(new java.awt.Dimension(8, 20));

        floorLiftValueSummary.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        floorLiftValueSummary.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        floorLiftValueSummary.setText("69");
        floorLiftValueSummary.setMaximumSize(new java.awt.Dimension(8, 20));
        floorLiftValueSummary.setMinimumSize(new java.awt.Dimension(8, 20));
        floorLiftValueSummary.setPreferredSize(new java.awt.Dimension(8, 20));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(floorLiftValueSummary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kneeLiftValueSummary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(waistLiftValueSummary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(highFarValueSummary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(highNearValueSummary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(overviewLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(overviewLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(highNearValueSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(highFarValueSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(waistLiftValueSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kneeLiftValueSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(floorLiftValueSummary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        overviewLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        overviewLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        overviewLabel8.setText("CV");
        overviewLabel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("HIGH NEAR");

        highNearCVValue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        highNearCVValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highNearCVValue.setText("100.00");
        highNearCVValue.setMaximumSize(new java.awt.Dimension(8, 20));
        highNearCVValue.setMinimumSize(new java.awt.Dimension(8, 20));
        highNearCVValue.setPreferredSize(new java.awt.Dimension(8, 20));

        highNearValue9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        highNearValue9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highNearValue9.setText("%");
        highNearValue9.setMaximumSize(new java.awt.Dimension(8, 20));
        highNearValue9.setMinimumSize(new java.awt.Dimension(8, 20));
        highNearValue9.setPreferredSize(new java.awt.Dimension(8, 20));

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("HIGH FAR");

        highFarCVValue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        highFarCVValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highFarCVValue.setText("100.00");
        highFarCVValue.setMaximumSize(new java.awt.Dimension(8, 20));
        highFarCVValue.setMinimumSize(new java.awt.Dimension(8, 20));
        highFarCVValue.setPreferredSize(new java.awt.Dimension(8, 20));

        highNearValue11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        highNearValue11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highNearValue11.setText("%");
        highNearValue11.setMaximumSize(new java.awt.Dimension(8, 20));
        highNearValue11.setMinimumSize(new java.awt.Dimension(8, 20));
        highNearValue11.setPreferredSize(new java.awt.Dimension(8, 20));

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("WAIST LIST");

        waistLiftCVValue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        waistLiftCVValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        waistLiftCVValue.setText("100.00");
        waistLiftCVValue.setMaximumSize(new java.awt.Dimension(8, 20));
        waistLiftCVValue.setMinimumSize(new java.awt.Dimension(8, 20));
        waistLiftCVValue.setPreferredSize(new java.awt.Dimension(8, 20));

        highNearValue13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        highNearValue13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highNearValue13.setText("%");
        highNearValue13.setMaximumSize(new java.awt.Dimension(8, 20));
        highNearValue13.setMinimumSize(new java.awt.Dimension(8, 20));
        highNearValue13.setPreferredSize(new java.awt.Dimension(8, 20));

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("KNEE LIFT");

        kneeLiftCVValue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        kneeLiftCVValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kneeLiftCVValue.setText("100.00");
        kneeLiftCVValue.setMaximumSize(new java.awt.Dimension(8, 20));
        kneeLiftCVValue.setMinimumSize(new java.awt.Dimension(8, 20));
        kneeLiftCVValue.setPreferredSize(new java.awt.Dimension(8, 20));

        highNearValue15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        highNearValue15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highNearValue15.setText("%");
        highNearValue15.setMaximumSize(new java.awt.Dimension(8, 20));
        highNearValue15.setMinimumSize(new java.awt.Dimension(8, 20));
        highNearValue15.setPreferredSize(new java.awt.Dimension(8, 20));

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("FLOOR LIFT");

        floorLiftCVValue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        floorLiftCVValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        floorLiftCVValue.setText("100.00");
        floorLiftCVValue.setMaximumSize(new java.awt.Dimension(8, 20));
        floorLiftCVValue.setMinimumSize(new java.awt.Dimension(8, 20));
        floorLiftCVValue.setPreferredSize(new java.awt.Dimension(8, 20));

        highNearValue17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        highNearValue17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highNearValue17.setText("%");
        highNearValue17.setMaximumSize(new java.awt.Dimension(8, 20));
        highNearValue17.setMinimumSize(new java.awt.Dimension(8, 20));
        highNearValue17.setPreferredSize(new java.awt.Dimension(8, 20));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                            .addComponent(floorLiftCVValue, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(highNearValue17, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                            .addComponent(kneeLiftCVValue, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(highNearValue15, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                            .addComponent(waistLiftCVValue, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(highNearValue13, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                            .addComponent(highFarCVValue, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(highNearValue11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                            .addComponent(highNearCVValue, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(highNearValue9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(45, Short.MAX_VALUE))
            .addComponent(overviewLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(overviewLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(highNearCVValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(highNearValue9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(highFarCVValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(highNearValue11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(waistLiftCVValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(highNearValue13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kneeLiftCVValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(highNearValue15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(floorLiftCVValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(highNearValue17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton5.setText("Reset data");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton8.setText("Start test");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Overview", jPanel5);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Office name");

        officeName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        officeName.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Address");

        address.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        address.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        phoneNumber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        phoneNumber.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        phoneNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneNumberActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Phone number");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Tehnician Name");

        tehnicianName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tehnicianName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tehnicianName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tehnicianNameActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Report name");

        outputLocation.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        outputLocation.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("REPORT DETAILS");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Subject Name");

        subjectName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        subjectName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        subjectName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectNameActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Date of birth (dd.mm.yyyy)");

        dateBirth.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dateBirth.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jButton1.setText("Generate report");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(officeName)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(address)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(phoneNumber)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tehnicianName)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(outputLocation)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subjectName)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateBirth))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(190, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subjectName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(officeName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tehnicianName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(44, 44, 44))
        );

        jLabel1.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 690, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Generate report", jPanel6);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Status");
        jLabel17.setMaximumSize(new java.awt.Dimension(70, 30));
        jLabel17.setMinimumSize(new java.awt.Dimension(70, 30));
        jLabel17.setName(""); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(70, 30));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Select COM port");
        jLabel19.setPreferredSize(new java.awt.Dimension(128, 30));

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("Lifting time per stage");
        jLabel46.setPreferredSize(new java.awt.Dimension(157, 30));

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("Samples per second");
        jLabel47.setPreferredSize(new java.awt.Dimension(157, 30));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(175, 175, 175)
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(213, 213, 213)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(436, Short.MAX_VALUE)))
        );

        statusLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusLabel.setText("Disconnected");
        statusLabel.setMaximumSize(new java.awt.Dimension(70, 30));
        statusLabel.setMinimumSize(new java.awt.Dimension(70, 30));
        statusLabel.setPreferredSize(new java.awt.Dimension(70, 30));

        comPorts.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comPorts.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));
        comPorts.setPreferredSize(new java.awt.Dimension(50, 30));

        numberOfSamplesValue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        numberOfSamplesValue.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numberOfSamplesValue.setText("-");
        numberOfSamplesValue.setPreferredSize(new java.awt.Dimension(59, 30));
        numberOfSamplesValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberOfSamplesValueActionPerformed(evt);
            }
        });

        liftingTimeValue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        liftingTimeValue.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        liftingTimeValue.setText("-");
        liftingTimeValue.setPreferredSize(new java.awt.Dimension(59, 30));
        liftingTimeValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liftingTimeValueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(comPorts, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(numberOfSamplesValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(liftingTimeValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comPorts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137)
                .addComponent(numberOfSamplesValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(liftingTimeValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(394, Short.MAX_VALUE))
        );

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setText("Connect");
        jButton3.setPreferredSize(new java.awt.Dimension(70, 30));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setText("Refresh");
        jButton2.setPreferredSize(new java.awt.Dimension(71, 30));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton6.setText("SAVE");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(136, 136, 136)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton4.setText("Disconnect");
        jButton4.setPreferredSize(new java.awt.Dimension(70, 30));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton7.setText("LOAD");
        jButton7.setPreferredSize(new java.awt.Dimension(185, 70));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(172, 172, 172)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Arduino Settings", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    boolean isValidDate(String tempDate) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        format.setLenient(false);

        try {
            format.parse(tempDate);
            return true;
        } catch (ParseException e) {
            System.out.println("Invalid birthday date");
            System.out.println(e.getMessage());
            return false;
        }

    }

    void displayError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error!", JOptionPane.ERROR_MESSAGE);

    }

    public void arduinoDisconnected() {
        try {
            scanComPorts();
            statusLabel.setText("Disconnected");
            JOptionPane.showMessageDialog(this, "Arduino disconnected!", "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (Exception er) {
            System.out.println(er.getMessage());
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {

            String subjectName = this.subjectName.getText();
            if (subjectName.length() == 0) {
                throw new Exception("Error!\nSubject name can't be blank.");
            }
            String dateOfBirth = this.dateBirth.getText();
            if (dateOfBirth.length() == 0) {
                throw new Exception("Error!\nBirthday date can't be blank.");
            }
            if (isValidDate(dateOfBirth) == false) {
                throw new Exception("Error!\nInvalid birthday date");
            }

            String officeName = this.officeName.getText();
            if (officeName.length() == 0) {
                throw new Exception("Error!\nOffice name can't be blank.");
            }
            String address = this.address.getText();
            if (address.length() == 0) {
                throw new Exception("Error!\nAddress can't be blank.");
            }

            String phoneNumber = this.phoneNumber.getText();
            if (phoneNumber.length() == 0) {
                throw new Exception("Error!\nPhone number can't be blank.");
            }

            String tehnicianName = this.tehnicianName.getText();
            if (tehnicianName.length() == 0) {
                throw new Exception("Error!\nTehnician name can't be blank.");
            }

            String fileName = outputLocation.getText();
            if (fileName.length() == 0) {
                throw new Exception("Error!\nReport name can't be blank.");
            }

            String highNear = highNearValue1.getText();
            String highFar = highFarValue1.getText();
            String waistLift = waistLiftValue1.getText();
            String kneeLift = kneeLiftValue1.getText();
            String floorLift = floorLiftValue1.getText();
            String highNearCV = highNearCVValue.getText();
            String highFarCV = highFarCVValue.getText();
            String waistLiftCV = waistLiftCVValue.getText();
            String kneeLiftCV = kneeLiftCVValue.getText();
            String floorLiftCV = floorLiftCVValue.getText();

            fileName = prepareFileName(fileName);

            createPDF(fileName);

            /*
            if (createPDF(fileName) == false) {
                throw new Exception("Unable to create PDF file");
            }
             */
            System.out.println("File created");

            try {
                writeChartToPDF(this.generateBarChart(highNear, highFar, waistLift, kneeLift, floorLift), 500, 350, fileName);
            } catch (IOException ex) {
                Logger.getLogger(gui.class.getName()).log(Level.SEVERE, null, ex);
            }
            File file = new File(fileName);
            PDDocument document = PDDocument.load(file);

            //Retrieving the pages of the document
            PDPage page = document.getPage(0);
            PDImageXObject pdImage = null;
            try {
                pdImage = PDImageXObject.createFromFile("logo.png", document);
            } catch (Exception err) {
                throw new Exception("Unable to find logo.png");
            }

            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
            //Drawing the image in the PDF document
            contentStream.drawImage(pdImage, 200, 750);
            //Setting the non stroking color
            contentStream.setStrokingColor(Color.black);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.setLineWidth(2);

            //Drawing a rectangle 
            contentStream.addRect(325, 620, 200, 110);

            //Drawing a rectangle
            contentStream.stroke();
            //  contentStream.fill();
            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

            //Setting the position for the line
            contentStream.newLineAtOffset(70, 750);
            contentStream.setLeading(20f);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
            //String logo = "                                        Fit to Lift";

            //contentStream.showText(logo);
            contentStream.newLine();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

            String text;
            text = "Name of subject: " + subjectName;
            contentStream.showText(text);
            contentStream.newLine();
            text = "Date of birth: " + dateOfBirth;
            contentStream.showText(text);
            contentStream.newLine();
            text = "Office name: " + officeName;
            contentStream.showText(text);
            contentStream.newLine();
            text = "Address: " + address;
            contentStream.showText(text);
            contentStream.newLine();
            text = "Phone number: " + phoneNumber;
            contentStream.showText(text);
            contentStream.newLine();
            text = "Tehnician Name: " + tehnicianName;
            contentStream.showText(text);
            contentStream.newLine();

            //Setting the position for the line
            contentStream.newLineAtOffset(275, 100);
            contentStream.setLeading(20f);

            text = "High Near: " + highNear + "lbs" + "   CV:" + highNearCV + " %";
            contentStream.showText(text);
            contentStream.newLine();
            text = "High Far: " + highFar + "lbs" + "   CV:" + highFarCV + " %";
            contentStream.showText(text);
            contentStream.newLine();
            text = "Waist Lift: " + waistLift + "lbs" + "   CV:" + waistLiftCV + " %";
            contentStream.showText(text);
            contentStream.newLine();
            text = "Knee Lift: " + kneeLift + "lbs" + "   CV:" + kneeLiftCV + " %";
            contentStream.showText(text);
            contentStream.newLine();
            text = "Floor Lift: " + floorLift + "lbs" + "   CV:" + floorLiftCV + " %";
            contentStream.showText(text);
            contentStream.newLine();

            contentStream.newLineAtOffset(-275, 0);
            contentStream.setLeading(14.5f);

            for (int i = 0; i < 25; i++) {
                contentStream.newLine();
            }

            String[] notes = {"Notes:_____________________________________________________________________________",
                "__________________________________________________________________________________",
                "__________________________________________________________________________________"};

            contentStream.setLeading(20f);
            for (int i = 0; i < notes.length; i++) {
                contentStream.newLine();
                contentStream.showText(notes[i]);
            }

            contentStream.setLeading(14.5f);
            contentStream.newLine();

            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = new Date();
            System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43 
            String outputDate = dateFormat.format(date);
            String[] disclaimer = {"Disclaimer:                                                                           ",
                "Post offer employment physical abilities tests are objective measuring tools designed ",
                "to determine whether an applicant possesses sufficient strength and ability to safely ",
                "perform essential physical job tasks. These results only represent the physical effort",
                "and ability provided by the subject and do not solely determine employability.        ",
                "                                                                                      ",
                "My signature represents that I have provided safe and reliable effort for my results  ",
                "on this abilities evaluation.                                                         ",
                "Signature                                                                                         Date   ",
                "_________________                                                                     " + outputDate + "    ",};

            for (int i = 0; i < disclaimer.length; i++) {
                contentStream.newLine();
                contentStream.showText(disclaimer[i]);
            }

            //Ending the content stream
            contentStream.endText();

            System.out.println("Content added");

            //Closing the content stream
            contentStream.close();

            //Saving the document
            document.save(new File(fileName));

            //Closing the document
            document.close();

            Desktop.getDesktop().open(new File(fileName));
        } catch (IOException ex) {

            System.out.println("Error!\nUnable to create PDF");
            System.out.println(ex.getMessage());

        } catch (Exception ex) {

            System.out.println("Error!\n" + ex.getMessage());
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        scanComPorts();
    }//GEN-LAST:event_jButton2ActionPerformed
    SerialTest communication;
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            if (statusLabel.getText().equals("Connected")) {
                throw new Exception("Arduino already connected. Disconnect first");
            }
            communication = new SerialTest();
            String selectedPort = comPorts.getSelectedItem().toString();
            communication.setPortName(selectedPort);
            String status = communication.initialize();

            if (status == "error") {
                if (selectedPort == "None") {
                    JOptionPane.showMessageDialog(this, "No COM port selected");
                }
                communication.close();
                statusLabel.setText("Disconnected");
            } else {
                statusLabel.setText("Connected");
            }
        } catch (Exception e) {
            System.out.println("Error connecting to Arduino");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {

            if (statusLabel.getText().equals("Connected")) {
                System.out.println("Serial closed");
                communication.close();
                statusLabel.setText("Disconnected");
            }
        } catch (Exception e) {
            System.out.println("Error disconnecting the Serial");
            System.out.println(e.getMessage());
        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            if (statusLabel.getText().equals("Connected") == true) {

                communication.resetArduino();
            } else {
                System.out.println("Arduino is not connected");
            }
        } catch (Exception er) {
            System.out.println(er.getMessage());
        }
        System.out.println("Data reseted");
        try {
            /*data*/
            subjectName.setText("");
            dateBirth.setText("");
            officeName.setText("");
            address.setText("");
            phoneNumber.setText("");
            tehnicianName.setText("");
            outputLocation.setText("");
            /*reset record for first try*/
            highNearValue1.setText("0");
            highFarValue1.setText("0");
            waistLiftValue1.setText("0");
            kneeLiftValue1.setText("0");
            floorLiftValue1.setText("0");

            /*reset record for second try*/
            highNearValue2.setText("0");
            highFarValue2.setText("0");
            waistLiftValue2.setText("0");
            kneeLiftValue2.setText("0");
            floorLiftValue2.setText("0");

            /*reset record for third try*/
            highNearValue3.setText("0");
            highFarValue3.setText("0");
            waistLiftValue3.setText("0");
            kneeLiftValue3.setText("0");
            floorLiftValue3.setText("0");

            /*reset record for first try*/
            highNearValueSummary.setText("0");
            highFarValueSummary.setText("0");
            waistLiftValueSummary.setText("0");
            kneeLiftValueSummary.setText("0");
            floorLiftValueSummary.setText("0");

            /*coefficient of variation*/
            highNearCVValue.setText("0");
            highFarCVValue.setText("0");
            waistLiftCVValue.setText("0");
            kneeLiftCVValue.setText("0");
            floorLiftCVValue.setText("0");
        } catch (Exception e) {
            System.out.println("Unable to reset the GUI");
            System.out.println(e.getMessage());
        }


    }//GEN-LAST:event_jButton5ActionPerformed

    private void phoneNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneNumberActionPerformed

    private void tehnicianNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tehnicianNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tehnicianNameActionPerformed

    private void subjectNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjectNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_subjectNameActionPerformed

    private void numberOfSamplesValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberOfSamplesValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numberOfSamplesValueActionPerformed

    private void liftingTimeValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liftingTimeValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_liftingTimeValueActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            if (communication == null) {
                throw new Exception("No Arduino connected");
            }

            int numberOfSamples = 0;
            int liftingTime = 0;
            try {
                numberOfSamples = Integer.parseInt(numberOfSamplesValue.getText());
            } catch (NumberFormatException ex) {
                displayError("Invalid input for \"Samples per second\"\n" + ex.getMessage());
            }
            try {
                liftingTime = Integer.parseInt(liftingTimeValue.getText());
            } catch (NumberFormatException ex) {
                displayError("Invalid input for \"Lifting time per stage\"\n" + ex.getMessage());
            }

            communication.sendConfig(numberOfSamples, liftingTime);
        } catch (Exception ex) {
            displayError(ex.getMessage());

        }
    }//GEN-LAST:event_jButton6ActionPerformed

    void updateConfig(String paramA, String paramB) {
        numberOfSamplesValue.setText(paramA);
        liftingTimeValue.setText(paramB);
    }
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            System.out.println("Asking for configuration");
            if (communication == null) {
                throw new Exception("No Arduino connected");
            }
            communication.loadConfiguration();
        } catch (Exception ex) {
            displayError(ex.getMessage());

        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
 try {
            System.out.println("Asking for configuration");
            if (communication == null) {
                throw new Exception("No Arduino connected");
            }
            communication.startTest();
        } catch (Exception ex) {
            displayError(ex.getMessage());

        }
        
             // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public void scanComPorts() {

        try {
            comPorts.removeAllItems();
            java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
            boolean found = false;
            while (portEnum.hasMoreElements()) {
                CommPortIdentifier portIdentifier = portEnum.nextElement();
                comPorts.addItem(portIdentifier.getName());
                found = true;
            }

            if (found == false) {
                comPorts.addItem("None");
            }
        } catch (UnsatisfiedLinkError e) {
            displayError("Java is not configured correctly.\n rxtxSerial.dll not found");
            jButton3.setEnabled(false);
            jButton4.setEnabled(false);
            jButton2.setEnabled(false);
        } catch (Exception e) {
            System.out.println("Unable to identify the COM ports");
            System.out.println(e.getMessage());
        }

    }

    public void main(String args[]) {

        scanComPorts();
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new gui().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address;
    private javax.swing.JComboBox<String> comPorts;
    private javax.swing.JTextField dateBirth;
    private javax.swing.JLabel floorLiftCVValue;
    private javax.swing.JLabel floorLiftValue1;
    private javax.swing.JLabel floorLiftValue2;
    private javax.swing.JLabel floorLiftValue3;
    private javax.swing.JLabel floorLiftValueSummary;
    private javax.swing.JLabel highFarCVValue;
    private javax.swing.JLabel highFarValue1;
    private javax.swing.JLabel highFarValue2;
    private javax.swing.JLabel highFarValue3;
    private javax.swing.JLabel highFarValueSummary;
    private javax.swing.JLabel highNearCVValue;
    private javax.swing.JLabel highNearValue1;
    private javax.swing.JLabel highNearValue11;
    private javax.swing.JLabel highNearValue13;
    private javax.swing.JLabel highNearValue15;
    private javax.swing.JLabel highNearValue17;
    private javax.swing.JLabel highNearValue2;
    private javax.swing.JLabel highNearValue3;
    private javax.swing.JLabel highNearValue9;
    private javax.swing.JLabel highNearValueSummary;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel kneeLiftCVValue;
    private javax.swing.JLabel kneeLiftValue1;
    private javax.swing.JLabel kneeLiftValue2;
    private javax.swing.JLabel kneeLiftValue3;
    private javax.swing.JLabel kneeLiftValueSummary;
    private javax.swing.JTextField liftingTimeValue;
    private javax.swing.JTextField numberOfSamplesValue;
    private javax.swing.JTextField officeName;
    private javax.swing.JTextField outputLocation;
    private javax.swing.JLabel overviewLabel1;
    private javax.swing.JLabel overviewLabel2;
    private javax.swing.JLabel overviewLabel4;
    private javax.swing.JLabel overviewLabel7;
    private javax.swing.JLabel overviewLabel8;
    private javax.swing.JTextField phoneNumber;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JTextField subjectName;
    private javax.swing.JTextField tehnicianName;
    private javax.swing.JLabel waistLiftCVValue;
    private javax.swing.JLabel waistLiftValue1;
    private javax.swing.JLabel waistLiftValue2;
    private javax.swing.JLabel waistLiftValue3;
    private javax.swing.JLabel waistLiftValueSummary;
    // End of variables declaration//GEN-END:variables
}
