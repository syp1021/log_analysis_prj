package project01;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ViewDialog extends JDialog implements ActionListener {

	private JButton jbtnClose;

	private JLabel maxKey;
	private JTextArea taGetMaxKey;

	private JLabel browserConnectionNum;
	private JTextArea taGetBrowserConnectionNum;

	private JLabel serviceResult;
	private JTextArea taGetServiceResult;

	private JLabel requestMany;
	private JTextArea taGetRequestMany;

	private JLabel info403;
	private JTextArea taGet403Info;

	private JLabel info500;
	private JTextArea taGet500Info;

	private JLabel rangeMaxKey;
	private JTextArea taGetRangeMaxKey;

	public ViewDialog(LogFileView lfv) { // 다이얼로그 창이 뜰려면 부모가 있어야한다. has a 관계
		super(lfv, "로그분석 - 결과창", true);

		try {
			LogFileData lfd = new LogFileData(lfv);

			// 1.
			String stMaxKey = lfd.getMaxKey();

			// 2.
			String[] stBrowserNum = lfd.getBrowserConnectionNum();

			// 3.
			String stServiceResult = lfd.getServiceResult();

			// 4.
			String[] stReqData = lfd.getRequestMany();

			// 5.
			String st403Result = lfd.get403Info();

			// 6.
			String st500Result = lfd.get500Info();

			// 7.
			String stRangeKey = lfd.getRangeMaxKey();

			jbtnClose = new JButton("확인");

			maxKey = new JLabel("1. 최다 사용 키의 이름과 횟수");
			browserConnectionNum = new JLabel("2. 브라우저별 접속 횟수, 비율");
			serviceResult = new JLabel("3. 서비스를 성공적으로 수행한 횟수와 실패한 횟수");
			requestMany = new JLabel("4. 요청이 가장 많은 시간");
			info403 = new JLabel("5. 비정상적인 요청(403)이 발생한 횟수, 비율");
			info500 = new JLabel("6. 요청에 대한 에러(500)가 발생한 횟수, 비율");
			rangeMaxKey = new JLabel("<html>7. 입력된 라인 1000 ~ 1500번째 라인에 해당하는 정보 중 <br> 최다 사용 키의 이름과 횟수</html>");

			taGetMaxKey = new JTextArea();
			taGetBrowserConnectionNum = new JTextArea();
			taGetServiceResult = new JTextArea();
			taGetRequestMany = new JTextArea();
			taGet403Info = new JTextArea();
			taGet500Info = new JTextArea();
			taGetRangeMaxKey = new JTextArea();

			// 값넣기
			taGetMaxKey.append(stMaxKey); // 1.
			// 2.
			for (int i = 0; i < stBrowserNum.length; i++) {
				taGetBrowserConnectionNum.append(stBrowserNum[i] + "\n");
			}
			taGetServiceResult.append(stServiceResult);// 3.
			taGetRequestMany.append(stReqData[0]+"시"); // 4.
			taGet403Info.append(st403Result);// 5.
			taGet500Info.append(st500Result); // 6.
			taGetRangeMaxKey.append(stRangeKey);// 7.

			setLayout(null);
			jbtnClose.addActionListener(this);

			// 배치
			maxKey.setBounds(50, 50, 300, 50);
			taGetMaxKey.setBounds(50, 100, 300, 50);

			browserConnectionNum.setBounds(50, 150, 300, 50);

			serviceResult.setBounds(50, 250, 300, 50);
			taGetServiceResult.setBounds(50, 300, 300, 50);

			requestMany.setBounds(50, 350, 300, 50);
			taGetRequestMany.setBounds(50, 400, 300, 50);

			info403.setBounds(50, 450, 300, 50);
			taGet403Info.setBounds(50, 500, 300, 50);

			info500.setBounds(50, 550, 300, 50);
			taGet500Info.setBounds(50, 600, 300, 50);

			rangeMaxKey.setBounds(50, 650, 350, 50);
			taGetRangeMaxKey.setBounds(50, 700, 300, 50);

			jbtnClose.setBounds(170, 780, 60, 40);

			add(maxKey);
			add(browserConnectionNum);
			add(serviceResult);
			add(requestMany);
			add(info403);
			add(info500);
			add(rangeMaxKey);

			add(taGetMaxKey);
			add(taGetBrowserConnectionNum);
			add(taGetServiceResult);
			add(taGetRequestMany);
			add(taGet403Info);
			add(taGet500Info);
			add(taGetRangeMaxKey);

			JScrollPane jsp = new JScrollPane(taGetBrowserConnectionNum, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			add(jsp);
			jsp.setBounds(50, 200, 300, 50);

			add(jbtnClose);

		} catch (IOException e) {
			e.printStackTrace();
		} // end catch

		// 부모창의 좌표얻기 x 좌표 getX(), y 좌표 getY()
		setBounds(100, 100, 420, 900);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}// UseJDialog

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == jbtnClose) {
			dispose();
		} // end if

	}// actionPerformed

}// class