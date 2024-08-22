import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
 
public class SimpleNotepad extends JFrame {
   private JTextArea textArea;
 
   public SimpleNotepad() {
       setTitle("Simple Notepad");
       setSize(600, 400);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
       textArea = new JTextArea();
       JScrollPane scrollPane = new JScrollPane(textArea);
       add(scrollPane, BorderLayout.CENTER);
       JMenuBar menuBar = new JMenuBar();
       JMenu fileMenu = new JMenu("File");
       JMenuItem newMenuItem = new JMenuItem("New");
       JMenuItem openMenuItem = new JMenuItem("Open");
       JMenuItem saveMenuItem = new JMenuItem("Save");
       JMenuItem saveAsMenuItem = new JMenuItem("Save As");
       JMenuItem exitMenuItem = new JMenuItem("Exit");
       fileMenu.add(newMenuItem);
       fileMenu.add(openMenuItem);
       fileMenu.add(saveMenuItem);
       fileMenu.add(saveAsMenuItem);
       fileMenu.add(exitMenuItem);
       menuBar.add(fileMenu);
       setJMenuBar(menuBar);
       newMenuItem.addActionListener(e -> textArea.setText(""));
       openMenuItem.addActionListener(e -> openFile());
       saveMenuItem.addActionListener(e -> saveFile(false));
       saveAsMenuItem.addActionListener(e -> saveFile(true));
       exitMenuItem.addActionListener(e -> System.exit(0));
       setVisible(true);
   }
   private void openFile() {
       JFileChooser fileChooser = new JFileChooser();
       int returnValue = fileChooser.showOpenDialog(this);
       if (returnValue == JFileChooser.APPROVE_OPTION) {
           File file = fileChooser.getSelectedFile();
           try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
               String line;
               StringBuilder content = new StringBuilder();
               while ((line = reader.readLine()) != null) {
                   content.append(line).append("\n");
               }
               textArea.setText(content.toString());
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
 
   private void saveFile(boolean saveAs) {
       JFileChooser fileChooser = new JFileChooser();
       int returnValue;
       if (saveAs) {
           returnValue = fileChooser.showSaveDialog(this);
       } else {
           returnValue = fileChooser.showOpenDialog(this);
       }
       if (returnValue == JFileChooser.APPROVE_OPTION) {
           File file = fileChooser.getSelectedFile();
           try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
               writer.write(textArea.getText());
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
   public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> new SimpleNotepad());
   }
}