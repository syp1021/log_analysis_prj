package project01;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * LogIn Ŭ�������� �α����� ������ �� �ߴ� �ڽ�â <br>
 * view�� report ��ư
 * 
 * @author user
 *
 */
@SuppressWarnings("serial")
public class LogFileView extends JDialog implements ActionListener {

   private JButton jbtnView;
   private JButton jbtnReport;

   private JLabel jlblSF; // Select File
   private JTextField jtfSF;

   private ImageIcon img;
   private JLabel jlImg;
   private JScrollPane jsp;
   protected String selectedPath; // ���õ� ������ ��� ��� ����

   private String username;

   public LogFileView(LogIn login) {
      super(login, "����â", true);

      this.username = login.userName;

      jlblSF = new JLabel("���õ� ����");
      jtfSF = new JTextField(50);

      jbtnView = new JButton("View");
      jbtnReport = new JButton("Report");

      img = new ImageIcon("e:/dev/project_01/hello.PNG");
      jlImg = new JLabel(img);

      jsp = new JScrollPane();
      jsp.getViewport().setBackground(Color.WHITE);

      setLayout(null);

      jlblSF.setBounds(50, 210, 100, 30);
      jtfSF.setBounds(150, 210, 250, 30);

      jbtnView.setBounds(100, 120, 120, 60);
      jbtnView.setBackground(Color.ORANGE);
      jbtnReport.setBounds(250, 120, 120, 60);
      jbtnReport.setBackground(Color.ORANGE);
      jsp.setBounds(0, 0, 500, 300);

      jlImg.setBounds(0, 10, 500, 100);
      add(jlblSF);
      add(jtfSF);
      add(jbtnView);
      add(jbtnReport);
      add(jlImg);
      add(jsp);
      jbtnView.addActionListener(this);
      jbtnReport.addActionListener(this);

      //
      setBounds(login.getX() + 100, login.getY() + 100, 500, 300);
      setVisible(true);

      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      System.exit(0);

   }// LogInFile

   public void openLogFileView() {
      FileDialog fdOpen = new FileDialog(this, "���Ͽ���", FileDialog.LOAD);
      fdOpen.setVisible(true);

      String path = fdOpen.getDirectory();
      String fileName = fdOpen.getFile();

      if (path != null) {
         jtfSF.setText(path + fileName);
         this.selectedPath = path + fileName;
         new ViewDialog(this);
      } // end if

   }// LogFileView

   public void openLogFileReport() throws IOException {

      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      Date d = new Date();
      String s = sdf.format(d);
      StringBuilder sb = new StringBuilder();
      sb.append(s).append(".dat");

      File file = new File("e:/dev/Report/report_");
      file.mkdirs();
      File deleteForder = new File("e:/dev/Report/report_/");
      deleteForder.delete();

      BufferedWriter bw = new BufferedWriter(new FileWriter(file + sb.toString()));

      LogFileData lfd = new LogFileData(this);

      String stMaxKey = lfd.getMaxKey();
      String[] stBrowserNum = lfd.getBrowserConnectionNum();
      String stServiceResult = lfd.getServiceResult();
      String[] stReqData = lfd.getRequestMany();
      String st403Result = lfd.get403Info();
      String st500Result = lfd.get500Info();
      String stRangeKey = lfd.getRangeMaxKey();

      bw.write("1. �ִ� ��� Ű�� �̸��� Ƚ��\n" + stMaxKey + "\n");
      bw.write("\n2. �������� ���� Ƚ��, ����\n");
      for (String value : stBrowserNum) {
         bw.write(value + "\n");
      }
      bw.write("\n3. ���񽺸� ���������� ������ Ƚ���� ������ Ƚ��\n" + stServiceResult + "\n" + "\n4. ��û�� ���� ���� �ð�\n" + stReqData[0]
            + "��\n" + "\n5. ���������� ��û(403)�� �߻��� Ƚ��, ����\n" + st403Result + "\n" + "\n6. ��û�� ���� ����(500)�� �߻��� Ƚ��, ����\n"
            + st500Result + "\n" + "\n7. �Էµ� ���� 1000 ~ 1500��° ���ο� �ش��ϴ� ���� ��\n�ִ� ��� Ű�� �̸��� Ƚ��\n" + stRangeKey
            + "\n");

      bw.flush();
      bw.close();
   }// LogFileReport

   @Override
   public void actionPerformed(ActionEvent ae) {

      if (ae.getSource() == jbtnView) {
         openLogFileView();
      } // end if

      if (ae.getSource() == jbtnReport) {
         try {

            if (this.username.equals("root")) {
               JOptionPane.showMessageDialog(null, "���� ���� �Ұ� ! ");
            } else {
               openLogFileReport();
               JOptionPane.showMessageDialog(null, "������ �����Ǿ����ϴ� ! ");
            }

         } catch (IOException e) {
            e.printStackTrace();
         } // end catch
      } // end if

   }// actionPerformed

}// class