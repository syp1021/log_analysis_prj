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

	public ViewDialog(LogFileView lfv) { // ���̾�α� â�� ����� �θ� �־���Ѵ�. has a ����
		super(lfv, "�α׺м� - ���â", true);

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

			jbtnClose = new JButton("Ȯ��");

			maxKey = new JLabel("1. �ִ� ��� Ű�� �̸��� Ƚ��");
			browserConnectionNum = new JLabel("2. �������� ���� Ƚ��, ����");
			serviceResult = new JLabel("3. ���񽺸� ���������� ������ Ƚ���� ������ Ƚ��");
			requestMany = new JLabel("4. ��û�� ���� ���� �ð�");
			info403 = new JLabel("5. ���������� ��û(403)�� �߻��� Ƚ��, ����");
			info500 = new JLabel("6. ��û�� ���� ����(500)�� �߻��� Ƚ��, ����");
			rangeMaxKey = new JLabel("<html>7. �Էµ� ���� 1000 ~ 1500��° ���ο� �ش��ϴ� ���� �� <br> �ִ� ��� Ű�� �̸��� Ƚ��</html>");

			taGetMaxKey = new JTextArea();
			taGetBrowserConnectionNum = new JTextArea();
			taGetServiceResult = new JTextArea();
			taGetRequestMany = new JTextArea();
			taGet403Info = new JTextArea();
			taGet500Info = new JTextArea();
			taGetRangeMaxKey = new JTextArea();

			// ���ֱ�
			taGetMaxKey.append(stMaxKey); // 1.
			// 2.
			for (int i = 0; i < stBrowserNum.length; i++) {
				taGetBrowserConnectionNum.append(stBrowserNum[i] + "\n");
			}
			taGetServiceResult.append(stServiceResult);// 3.
			taGetRequestMany.append(stReqData[0]+"��"); // 4.
			taGet403Info.append(st403Result);// 5.
			taGet500Info.append(st500Result); // 6.
			taGetRangeMaxKey.append(stRangeKey);// 7.

			setLayout(null);
			jbtnClose.addActionListener(this);

			// ��ġ
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

		// �θ�â�� ��ǥ��� x ��ǥ getX(), y ��ǥ getY()
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