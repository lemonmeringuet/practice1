/*
 * Created by JFormDesigner on Wed Feb 27 14:24:53 MSK 2019
 */

package lab_09_1;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.event.*;
import net.miginfocom.swing.*;
import java.awt.Component;
import java.awt.Frame;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;

/**
 * @author Denis
 */
public class MyForm extends JFrame {

    String url = "jdbc:mysql://localhost:3306/";
    String db = "vet_clinic";
    String driver = "com.mysql.cj.jdbc.Driver";
    String user = "root";
    String pass = "qwerty28";

    static Handler fileHandler = null;
    static Handler fileHandler1 = null;
    private static Logger log = Logger.getLogger(MyForm.class.getClass().getName());
    private static Logger log1 = Logger.getLogger(MyForm.class.getClass().getName());




    public static void setup() {

        try {
            fileHandler = new FileHandler("./logfile4events.log");//file
            fileHandler.setFilter(new MyEventsFilter());
            SimpleFormatter simple = new SimpleFormatter();
            fileHandler.setFormatter(simple);
            log.addHandler(fileHandler);//adding Handler for file

            fileHandler1 = new FileHandler("./logfile4exceptions.log");//file
            fileHandler1.setFilter(new MyErrorsFilter());
            SimpleFormatter simple1 = new SimpleFormatter();
            fileHandler1.setFormatter(simple1);
            log1.addHandler(fileHandler1);//adding Handler for file


        } catch (IOException e) {
            log1.warning("Could not setup logger configuration ERROR");
        }
    }

    static class MyEventsFilter implements Filter {

        @Override
        public boolean isLoggable(LogRecord record) {
            return record.getLevel() == Level.INFO;
        }
    }
    static class MyErrorsFilter implements Filter {

        @Override
        public boolean isLoggable(LogRecord record) {
            return record.getLevel() == Level.WARNING;
        }
    }

    public MyForm() {
        super ("Ветеринарная клиника");
        setup();//calling to the file content
        log.info("------------------EVENTS--------------------");
        log1.warning("------------------ERRORS--------------------");
      /*  this.setSize(500, 500);*/
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3,3,3,3));

       // JButton b = new JButton(button1.getName());


        initComponents();

    }

    private void menuItem7ActionPerformed(ActionEvent e) {
        log.info("Help EVENT");
        JOptionPane.showMessageDialog(null,"Помощь уже в пути");
    }

    private void menuItem1ActionPerformed(ActionEvent e) {
        log.info("File open EVENT");
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();

        }
    }

    private void menuItem3ActionPerformed(ActionEvent e) {
            System.exit(0);
        log.info("Exit EVENT");
    }

    private void menuItem4ActionPerformed(ActionEvent e) {
        log.info("Sort #1 EVENT");
        textArea1.setEditable(false);
        textArea1.setText(null);
        Connection con = null;
        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url+db, user, pass);

            System.out.println("is connected successfully to DB " + con);
            String query = "SELECT * FROM pet_owners WHERE o_pet_count > 2 ORDER BY o_name";
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            String name;
            String o_id;
            String pet_count;
            while (rs.next()) {
                o_id = rs.getString(1);
                name = rs.getString(2);
                pet_count = rs.getString(3);

              //  TextArea ta = new TextArea();
                label1.setText("Владельцы более 2 питомцев: \n");
                textArea1.append(o_id + "\t" + name + "\n");

            } // end while

            con.close();
        } // end try
        catch (ClassNotFoundException b) {
            log1.warning("Could not find the database driver ERROR");
            b.printStackTrace();
            // Could not find the database driver
        } catch (SQLException b) {
            log1.warning("Could not connect to the database ERROR");
            b.printStackTrace();
            // Could not connect to the database
        }
    }

    void main() {
        log1.warning("UnsupportedOperationException ERROR");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void menuItem5ActionPerformed(ActionEvent e) {
        log.info("Sort #2 EVENT");
        textArea1.setEditable(false);
        textArea1.setText(null);
        Connection con = null;
        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url+db, user, pass);

            System.out.println("is connected successfully to DB " + con);
            //тут нужно вызвать владельцев у которых животные не привиты p_isVactinated = 'n'
            String query = "SELECT * FROM pet_owners, pets WHERE o_id = p_id && p_isVactinated = 'n' ORDER BY o_name";
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            String o_id;
            String name;
            String isVactinated;
            while (rs.next()) {
                o_id = rs.getString(1);
                name = rs.getString(2);
               isVactinated = rs.getString(3);

                //  TextArea ta = new TextArea();
                label1.setText("Владельцы питомцев без прививки: \n");
                textArea1.append(o_id + "\t" + name + "\n");

            } // end while

            con.close();
        } // end try
        catch (ClassNotFoundException b) {
            log1.warning("Could not find the database driver ERROR");
            b.printStackTrace();
            // Could not find the database driver
        } catch (SQLException b) {
            log1.warning("Could not connect to the database ERROR");
            b.printStackTrace();
            // Could not connect to the database
        }
    }

    private void menuItem6ActionPerformed(ActionEvent e) {
        log.info("Sort #3 EVENT");
        textArea1.setEditable(false);
        textArea1.setText(null);
        Connection con = null;
        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url+db, user, pass);

            System.out.println("is connected successfully to DB " + con);
            String query = "SELECT * FROM pet_owners WHERE o_visit_date = curdate() ORDER BY o_name";
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            String o_id;
            String name;
            String visit_date;
            while (rs.next()) {
                o_id = rs.getString(1);
                name = rs.getString(2);
                visit_date = rs.getString(4);

                //  TextArea ta = new TextArea();
                label1.setText("Владельцы, записанные на сегодня: \n");
                textArea1.append(o_id + "\t" + name + "\n");

            } // end while

            con.close();
        } // end try
        catch (ClassNotFoundException b) {
            log1.warning("Could not find the database driver ERROR");
            b.printStackTrace();
            // Could not find the database driver
        } catch (SQLException b) {
            log1.warning("Could not connect to the database ERROR");
            b.printStackTrace();
            // Could not connect to the database
        }
    }

    //англ
    private void menuItem9ActionPerformed(ActionEvent e) {
        super.setTitle ("Vet Clinic");
        menu1.setText("File");
        menuItem1.setText("Open");
        menuItem2.setText("Save");
        menuItem3.setText("Exit");
        menu2.setText("Sort");
        menuItem4.setText("Owners of >2 pets");
        menuItem5.setText("Owners of non-vactinated pets");
        menuItem6.setText("Owners to visit the current date");
        menu3.setText("Help");
        menuItem7.setText("Help...");
        menu4.setText("Language");
        menuItem8.setText("Russian");
        menuItem9.setText("English");
        menuItem10.setText("Ukrainian");
    }

    //укр
    private void menuItem10ActionPerformed(ActionEvent e) {
        super.setTitle ("Ветерінарна клініка");
            menu1.setText("Файл");
            menuItem1.setText("Відкрити");
            menuItem2.setText("Зберегти");
            menuItem3.setText("Вийти");
            menu2.setText("Вибірка");
            menuItem4.setText("Володарі >2 тварин");
            menuItem5.setText("Володарі тварин без счеплення");
            menuItem6.setText("Володарі записані на поточну дату");
            menu3.setText("Справка");
            menuItem7.setText("Допомога");
            menu4.setText("Мова");
            menuItem8.setText("Російська");
            menuItem9.setText("Англійська");
            menuItem10.setText("Українська");
    }

    //рус
    private void menuItem8ActionPerformed(ActionEvent e) {
        super.setTitle ("Ветеринарная клиника");
        menu1.setText("Файл");
        menuItem1.setText("Открыть");
        menuItem2.setText("Сохранить");
        menuItem3.setText("Выйти");
        menu2.setText("Выборка");
        menuItem4.setText("Владельцы более 2 питомцев");
        menuItem5.setText("Владельцы питомцев без прививки");
        menuItem6.setText("Владельцы, записанные на сегодня");
        menu3.setText("Справка");
        menuItem7.setText("Помощь");
        menu4.setText("Язык");
        menuItem8.setText("Русский");
        menuItem9.setText("Английский");
        menuItem10.setText("Украинский");
    }






    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Denis
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menu2 = new JMenu();
        menuItem4 = new JMenuItem();
        menuItem5 = new JMenuItem();
        menuItem6 = new JMenuItem();
        menu3 = new JMenu();
        menuItem7 = new JMenuItem();
        separator1 = new JPopupMenu.Separator();
        menu4 = new JMenu();
        menuItem8 = new JMenuItem();
        menuItem9 = new JMenuItem();
        menuItem10 = new JMenuItem();
        label1 = new JLabel();
        scrollPane2 = new JScrollPane();
        textArea1 = new JTextArea();

        //======== this ========
        setMinimumSize(new Dimension(1, 22));
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill,hidemode 3,align center center",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("\u0424\u0430\u0439\u043b");

                //---- menuItem1 ----
                menuItem1.setText("\u041e\u0442\u043a\u0440\u044b\u0442\u044c");
                menuItem1.addActionListener(e -> menuItem1ActionPerformed(e));
                menu1.add(menuItem1);

                //---- menuItem2 ----
                menuItem2.setText("\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c");
                menu1.add(menuItem2);

                //---- menuItem3 ----
                menuItem3.setText("\u0412\u044b\u0439\u0442\u0438");
                menuItem3.addActionListener(e -> menuItem3ActionPerformed(e));
                menu1.add(menuItem3);
            }
            menuBar1.add(menu1);

            //======== menu2 ========
            {
                menu2.setText("\u0412\u044b\u0431\u043e\u0440\u043a\u0430");

                //---- menuItem4 ----
                menuItem4.setText("\u0412\u043b\u0430\u0434\u0435\u043b\u044c\u0446\u044b >2 \u043f\u0438\u0442\u043e\u043c\u0446\u0435\u0432");
                menuItem4.addActionListener(e -> menuItem4ActionPerformed(e));
                menu2.add(menuItem4);

                //---- menuItem5 ----
                menuItem5.setText("\u0412\u043b\u0430\u0434\u0435\u043b\u044c\u0446\u044b \u043f\u0438\u0442\u043e\u043c\u0446\u0435\u0432 \u0431\u0435\u0437 \u043f\u0440\u0438\u0432\u0438\u0432\u043a\u0438");
                menuItem5.addActionListener(e -> menuItem5ActionPerformed(e));
                menu2.add(menuItem5);

                //---- menuItem6 ----
                menuItem6.setText("\u0412\u043b\u0430\u0434\u0435\u043b\u044c\u0446\u044b \u0437\u0430\u043f\u0438\u0441\u0430\u043d\u043d\u044b\u0435 \u043d\u0430 \u0442\u0435\u043a\u0443\u0449\u0443\u044e \u0434\u0430\u0442\u0443");
                menuItem6.setMinimumSize(new Dimension(1, 1));
                menuItem6.addActionListener(e -> menuItem6ActionPerformed(e));
                menu2.add(menuItem6);
            }
            menuBar1.add(menu2);

            //======== menu3 ========
            {
                menu3.setText("\u0421\u043f\u0440\u0430\u0432\u043a\u0430");

                //---- menuItem7 ----
                menuItem7.setText("\u041f\u043e\u043c\u043e\u0449\u044c");
                menuItem7.addActionListener(e -> menuItem7ActionPerformed(e));
                menu3.add(menuItem7);
            }
            menuBar1.add(menu3);
            menuBar1.add(separator1);

            //======== menu4 ========
            {
                menu4.setText("\u042f\u0437\u044b\u043a");

                //---- menuItem8 ----
                menuItem8.setText("\u0420\u0443\u0441\u0441\u043a\u0438\u0439");
                menuItem8.addActionListener(e -> menuItem8ActionPerformed(e));
                menu4.add(menuItem8);

                //---- menuItem9 ----
                menuItem9.setText("\u0410\u043d\u0433\u043b\u0438\u0439\u0441\u043a\u0438\u0439");
                menuItem9.addActionListener(e -> menuItem9ActionPerformed(e));
                menu4.add(menuItem9);

                //---- menuItem10 ----
                menuItem10.setText("\u0423\u043a\u0440\u0430\u0438\u043d\u0441\u043a\u0438\u0439");
                menuItem10.addActionListener(e -> menuItem10ActionPerformed(e));
                menu4.add(menuItem10);
            }
            menuBar1.add(menu4);
        }
        setJMenuBar(menuBar1);
        contentPane.add(label1, "cell 0 1 3 1,growx");

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(textArea1);
        }
        contentPane.add(scrollPane2, "cell 0 2 3 5,grow");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Denis
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenu menu2;
    private JMenuItem menuItem4;
    private JMenuItem menuItem5;
    private JMenuItem menuItem6;
    private JMenu menu3;
    private JMenuItem menuItem7;
    private JPopupMenu.Separator separator1;
    private JMenu menu4;
    private JMenuItem menuItem8;
    private JMenuItem menuItem9;
    private JMenuItem menuItem10;
    private JLabel label1;
    private JScrollPane scrollPane2;
    private JTextArea textArea1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

