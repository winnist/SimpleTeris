
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

public class test {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new test().new MyFrame());
    }

    public class MyFrame extends JFrame {

        private static final long serialVersionUID = 1L;

        private ExampleDrawingModel model;
        
        JTextComponent tc;
        
        MyPanel p1;

        public MyFrame() {
            super("My Frame");
            this.model = new ExampleDrawingModel();
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setJMenuBar(createMenuBar());
            
            p1 = new MyPanel(model);
            this.add(p1, BorderLayout.CENTER);
            this.add(createTextPanel(), BorderLayout.SOUTH);
            
            this.pack();
            this.setLocationByPlatform(true);
//          this.setResizable(false);
            this.setVisible(true);
        }
        
        private JMenuBar createMenuBar() {
            JMenuBar mb = new JMenuBar();
            JMenu fm = new JMenu("File");
            JMenuItem loadItem = new JMenuItem("Load file");
            loadItem.addActionListener(e -> {
                tc.setText("loading" + "\n");
                model.readFile();
                p1.repaint();
            });
            fm.add(loadItem);
            mb.add(fm);
            
            return mb;
        }
        
        private JPanel createTextPanel() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            
            tc = new JTextPane();
            tc.setPreferredSize(new Dimension(450, 50));
            JScrollPane sp = new JScrollPane(tc);
            panel.add(sp, BorderLayout.CENTER);
            
            return panel;
        }
        
        public void repaint() {
            p1.repaint();
        }

    }

    public class MyPanel extends JPanel {

        private static final long serialVersionUID = 1L;
        
        private ExampleDrawingModel model;

        public MyPanel(ExampleDrawingModel model) {
            this.model = model;
            this.setPreferredSize(new Dimension(450, 200));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2D = (Graphics2D) g;
            paintBorder(g2D);
            
            for (LineSegment line : model.getLines()) {
                Point startPoint = line.getStartPoint();
                Point endPoint = line.getEndPoint();
                g2D.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
            }
        }

        private void paintBorder(Graphics2D g2D) {
            int margin = 5;
            int x1 = margin;
            int x2 = getWidth() - margin;
            int y1 = margin;
            int y2 = getHeight() - margin;
            
            g2D.setStroke(new BasicStroke(3f));
            g2D.setPaint(Color.blue);
            g2D.drawLine(x1, y1, x1, y2);
            g2D.drawLine(x1, y1, x2, y1);
            g2D.drawLine(x2, y1, x2, y2);
            g2D.drawLine(x1, y2, x2, y2);
        }

    }
    
    public class ExampleDrawingModel {
        
        private List<LineSegment> lines;
        
        public ExampleDrawingModel() {
            this.lines = new ArrayList<>();
        }
        
        public void readFile() {
            this.lines.clear();
            // Here's where you'd read a file and create a list of lines.
            lines.add(new LineSegment(new Point(100, 100), new Point(100, 150)));
        }

        public List<LineSegment> getLines() {
            return lines;
        }
        
    }
    
    public class LineSegment {
        
        private final Point startPoint, endPoint;

        public LineSegment(Point startPoint, Point endPoint) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
        }

        public Point getStartPoint() {
            return startPoint;
        }

        public Point getEndPoint() {
            return endPoint;
        }
        
    }

}