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
 * LogIn 클래스에서 로그인이 성공한 뒤 뜨는 자식창 <br>
 * view와 report 버튼
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
   protected String selectedPath; // 선택된 파일의 경로 담는 변수

   private String username;

   public LogFileView(LogIn login) {
      super(login, "선택창", true);

      this.username = login.userName;

      jlblSF = new JLabel("선택된 파일");
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
      FileDialog fdOpen = new FileDialog(this, "파일열기", FileDialog.LOAD);
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

      bw.write("1. 최다 사용 키의 이름과 횟수\n" + stMaxKey + "\n");
      bw.write("\n2. 브라우저별 접속 횟수, 비율\n");
      for (String value : stBrowserNum) {
         bw.write(value + "\n");
      }
      bw.write("\n3. 서비스를 성공적으로 수행한 횟수와 실패한 횟수\n" + stServiceResult + "\n" + "\n4. 요청이 가장 많은 시간\n" + stReqData[0]
            + "시\n" + "\n5. 비정상적인 요청(403)이 발생한 횟수, 비율\n" + st403Result + "\n" + "\n6. 요청에 대한 에러(500)가 발생한 횟수, 비율\n"
            + st500Result + "\n" + "\n7. 입력된 라인 1000 ~ 1500번째 라인에 해당하는 정보 중\n최다 사용 키의 이름과 횟수\n" + stRangeKey
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
               JOptionPane.showMessageDialog(null, "파일 생성 불가 ! ");
            } else {
               openLogFileReport();
               JOptionPane.showMessageDialog(null, "파일이 생성되었습니다 ! ");
            }

         } catch (IOException e) {
            e.printStackTrace();
         } // end catch
      } // end if

   }// actionPerformed

}// class