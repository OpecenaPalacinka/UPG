import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class BasicDrawing {

    public static void main(String[] args) {
        JFrame win = new JFrame();
        win.setTitle("A19B0157P");

        ChartPanel chartPanel = new ChartPanel(makeBarChart());
        win.add(chartPanel);
        //makeGui(win);
        win.pack();

        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setLocationRelativeTo(null);
        win.setVisible(true);
    }

    private static JFreeChart makeBarChart(){
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

        ArrayList<String[]> loadedData = loadData("Data2.txt");

        for (Iterator iterator = loadedData.iterator(); iterator.hasNext();) {
            String[] strings = (String[]) iterator.next();

            for (int i = 1; i < strings.length; i++) {
                dataset.add(loadedData,strings[0],i + ".4");
            }
        }

        JFreeChart chart = ChartFactory.
                createBoxAndWhiskerChart("Vývoj infekce v čase","Den",
                        "Počet infikovaných na 100 tis. obyvatel",dataset,true);

      /*
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.GRAY);

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(0);
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDefaultItemLabelGenerator(
                new StandardCategoryItemLabelGenerator(
                        "{2}", nf));
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelFont(new Font("Calibri",Font.PLAIN,13));
        renderer.setItemMargin(0.06);
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setSeriesPaint(0, Color.getHSBColor(0.6f, 0.5f, 0.9f));
        renderer.setSeriesPaint(1, Color.getHSBColor(0f, 0.5f, 0.9f));
        renderer.setSeriesPaint(2, Color.getHSBColor(0.4f, 0.5f, 0.9f));
       */


        return chart;
    }

    private static ArrayList<String[]> loadData(String fileName) {
        ArrayList<String[]> list = new ArrayList<>();

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(new File(fileName)));

            String line;
            while((line = br.readLine()) != null) {
                list.add(line.split("\\s"));
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

/*
    private static void makeGui(JFrame win) {
        DrawingPanel panel = new DrawingPanel();
        win.setLayout(new BorderLayout());
        win.add(panel, BorderLayout.CENTER);

        JButton bttnExit = new JButton("Exit");

        JPanel buttons = new JPanel();
        buttons.add(bttnExit);

        win.add(buttons, BorderLayout.SOUTH);

        bttnExit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                win.dispose();
            }
        });
    }

 */
}

