package qwe;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Main extends JFrame {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JFileChooser fileChooser = null;
    private JMenuItem resetGraphicsMenuItem;
    private GraphicsDisplay display = new GraphicsDisplay();
    private boolean fileLoaded = false;

    public Main() {
        super("Обработка событий от мыши");
        this.setSize(700, 500);
        Toolkit kit = Toolkit.getDefaultToolkit();
        this.setLocation((kit.getScreenSize().width - 700) / 2, (kit.getScreenSize().height - 500) / 2);
        this.setExtendedState(6);
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        Action openGraphicsAction = new AbstractAction("Открыть файл с графиком") {
            public void actionPerformed(ActionEvent event) {
                if (Main.this.fileChooser == null) {
                    Main.this.fileChooser = new JFileChooser();
                    Main.this.fileChooser.setCurrentDirectory(new File("."));
                }

                Main.this.fileChooser.showOpenDialog(Main.this);
                Main.this.openGraphics(Main.this.fileChooser.getSelectedFile());
            }
        };
        fileMenu.add(openGraphicsAction);
        Action resetGraphicsAction = new AbstractAction("Отменить все изменения") {
            public void actionPerformed(ActionEvent event) {
                Main.this.display.reset();
            }
        };
        this.resetGraphicsMenuItem = fileMenu.add(resetGraphicsAction);
        this.resetGraphicsMenuItem.setEnabled(false);
        this.getContentPane().add(this.display, "Center");
    }

    protected void openGraphics(File selectedFile) {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(selectedFile));
            ArrayList<Double[]> graphicsData = new ArrayList(50);

            while(in.available() > 0) {
                Double x = in.readDouble();
                Double y = in.readDouble();
                graphicsData.add(new Double[]{x, y});
            }

            if (graphicsData.size() > 0) {
                this.fileLoaded = true;
                this.resetGraphicsMenuItem.setEnabled(true);
                this.display.displayGraphics(graphicsData);
            }

        } catch (FileNotFoundException var6) {
            JOptionPane.showMessageDialog(this, "Указанный файл не найден", "Ошибка загрузки данных", 2);
        } catch (IOException var7) {
            JOptionPane.showMessageDialog(this, "Ошибка чтения координат точек из файла", "Ошибка загрузки данных", 2);
        }
    }

    public static void main(String[] args) {
        Main frame = new Main();
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
}
