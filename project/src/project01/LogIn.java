package project01;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LogIn extends JFrame implements ActionListener {

   public String userName;
   private JLabel jlblUN; // User Name
   private JTextField jtfUN;

   private JLabel jlSub;

   private JLabel jlblPW; // Password
   private JTextField jtfPW;

   private JButton jbtnLogIn;

   private JScrollPane jsp;
   private ImageIcon img;
   private JLabel jlImg;

   public LogIn() {
      super("LogIn");

      // 일반 컴포넌트 생성
      jlblUN = new JLabel("User Name");
      jtfUN = new JTextField(20);

      jlblPW = new JLabel("PassWord");
      jtfPW = new JTextField(20);

      jbtnLogIn = new JButton("Login");
      jbtnLogIn.setBackground(Color.ORANGE);

      jsp = new JScrollPane();
      jsp.getViewport().setBackground(Color.WHITE);

      img = new ImageIcon("e:/dev/project_01/ssang.PNG");
      jlImg = new JLabel(img);

      jlSub = new JLabel("Project_ 3조");
      setLayout(null);
      // 배치
      jlblUN.setBounds(60, 200, 100, 30);
      jtfUN.setBounds(160, 200, 150, 30);

      jlblPW.setBounds(60, 250, 100, 30);
      jtfPW.setBounds(160, 250, 150, 30);

      jbtnLogIn.setBounds(330, 200, 100, 80);
      jlImg.setBounds(40, 10, 400, 180);

      jlSub.setBounds(380, 325, 90, 20);

      jsp.setBounds(0, 0, 500, 400);

      add(jlblUN);
      add(jtfUN);
      add(jlblPW);
      add(jtfPW);
      add(jbtnLogIn);
      add(jlImg);
      add(jlSub);
      add(jsp);
      jbtnLogIn.addActionListener(this);

      // 윈도우 컴포넌트
      setBounds(200, 200, 500, 400);
      // 가시화
      setVisible(true);
      // 윈도우 종료 이벤트 처리
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

   }// LogIn

   /**
    * ‘admin,1234’, <br>
    * ‘root, 1111’, <br>
    * ’administrator,12345 <br>
    * ’로 로그인 할 수 있습니다. <br>
    * 이때 root계정으로 로그인하면 report문서를 생성할 수 없습니다.<br>
    * (report생성을 클릭하면 “문서를 생성할 수 있는 권한이 없음”을 보여줍니다.
    */
   public void isLogIn() {

      String password;
      userName = jtfUN.getText();
      password = jtfPW.getText();

      if (userName.equals("admin") && password.equals("1234")) {
         dispose();
         logInFile();
      } else if (userName.equals("administrator") && password.equals("12345")) {
         dispose();
         logInFile();
      } else if (userName.equals("root") && password.equals("1111")) {
         dispose();
         logInFile();
      } else {
         JOptionPane.showMessageDialog(this, "Invalid User name or Password ");
      } // end else

   }// IsLogIn

   public void logInFile() {
      new LogFileView(this);
   } // LogInFile

   @Override
   public void actionPerformed(ActionEvent ae) {
      if (ae.getSource() == jbtnLogIn) {
         isLogIn();
      } // end if
   }// actionPerformed

   public static void main(String[] args) {
      new LogIn();
   }// main

}// class