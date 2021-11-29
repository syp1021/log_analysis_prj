package project01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * logfile data�� �����ϰ� ���� ��(���� ����)�� �����ϴ� class<br>
 * 
 * @author syp
 */
public class LogFileData {
	// method�� ���� �׸��� �ν��Ͻ��� ����
	private String[] errCodeArr; // [errCode]
	private String[] urlArr; // [url (key����)]
	private String[] browserArr; // [������]
	private String[] timeArr;// [�̿�ð�]

	private File file;

	// �������� ����Ƚ�� method���� ���� ��� ����
	private static final int OPERA = 0;
	private static final int FIREFOX = 1;
	private static final int SAFARI = 2;
	private static final int CHROME = 3;
	private static final int IE = 4;

	/**
	 * �� Ŭ������ ��üȭ �� �� ���� �� ������<br>
	 * �⺻���� data�� ���� �� �迭�� �Ҵ���<br>
	 * 
	 * @throws IOException
	 */
	public LogFileData(LogFileView lfv) throws IOException {
		file = new File(lfv.selectedPath); // ���õ� ��η� file ��ü ����
		BufferedReader bs = new BufferedReader(new FileReader(file));

		List<String> logLineList = new ArrayList<String>(); // logfile�� 1��

		// logfile ���� �迭�� �ֱ�

		while (bs.readLine() != null) {
			logLineList.add(bs.readLine());
		} // end while

		// �������� ���� ���� �迭
		errCodeArr = new String[logLineList.size()];
		urlArr = new String[logLineList.size()];
		browserArr = new String[logLineList.size()];
		timeArr = new String[logLineList.size()];

		for (int i = 0; i < logLineList.size(); i++) { // ���پ� �о�鿩�� ������ ��ū ��� �迭
			if (logLineList.get(i) != null) {
				StringTokenizer st = new StringTokenizer(logLineList.get(i), "[]");

				while (st.hasMoreTokens()) {
					errCodeArr[i] = st.nextToken(); // �����ڵ�
					urlArr[i] = st.nextToken(); // URL (KEY�� ����)
					browserArr[i] = st.nextToken(); // OS
					timeArr[i] = st.nextToken(); // �ð�

				} // end while
			} // end if
		} // end for
		if (bs != null) { // Stream �ݱ�
			bs.close();
		} // end if

	}// logFileData

	/**
	 * 1. �ִٻ�� Ű�� �̸��� Ƚ�� | java xxȸ
	 * 
	 * @throws IOException
	 */
	public String getMaxKey() throws IOException {
		// ���� �б�
		FileReader filereader = new FileReader(file);
		BufferedReader br = new BufferedReader(filereader);
		Map<String, Integer> keyMap = new HashMap<String, Integer>(); // key :�̸� valaue : Ƚ��
		List<String> list = new ArrayList<String>();

		String line = "";
		while ((line = br.readLine()) != null) {
			String[] lineName = line.split("=");

			if (lineName[1].toString().contains("&")) {
				String[] realKey = lineName[1].split("&");
				list.add(realKey[0]);
			} // end if
		} // end while

		// Map�� ����
		for (String str : list) {
			Integer count = keyMap.get(str);
			if (count == null) {
				keyMap.put(str, 1);
			} else {
				keyMap.put(str, count + 1);
			} // end else
		} // end if

		// Map max�� ���
		String maxKey = "";
		String maxVal = "";

		for (String key : keyMap.keySet()) {
			if (maxKey == "") {
				maxKey = key;
				maxVal = keyMap.get(key).toString();
			} else if (Integer.parseInt(maxVal) < keyMap.get(key)) {
				maxKey = key;
				maxVal = keyMap.get(key).toString();
			}
		} // end for

		// System.out.println("�ִ� ���Ű�� �̸��� " + maxKey +"�̰� Ƚ���� " + maxVal + "ȸ �Դϴ�") ;

		br.close();
		return maxKey + " : " + maxVal;

	}// getMaxKey

	/**
	 * 2. �������� ����Ƚ���� ���� ���ϴ� method<br>
	 * 
	 * @return
	 */
	public String[] getBrowserConnectionNum() {
		String[] browser = "opera,firefox,Safari,Chrome,ie".split(",");
		String[] result = new String[browser.length]; // ��ȯ�� �迭
		// ����Ƚ���� ���� ���� Arr
		int[] connectionNum = new int[browser.length];
		double[] connectionRatio = new double[browser.length];

		int totalNum = browserArr.length;
		for (int i = 0; i < totalNum; i++) { // ����Ƚ����ŭ for ����
			if (browserArr[i] != null) { // null�� �ƴ� ��� ����
				if (browserArr[i].equals("opera")) { // �� �������� Ȯ���� �� Ƚ���� ���� ���ϴ� if
					connectionRatio[OPERA] = ++connectionNum[OPERA] / (double) totalNum;
				}
				if (browserArr[i].equals("firefox")) {
					connectionRatio[FIREFOX] = ++connectionNum[FIREFOX] / (double) totalNum;
				}
				if (browserArr[i].equals("Safari")) {
					connectionRatio[SAFARI] = ++connectionNum[SAFARI] / (double) totalNum;
				}
				if (browserArr[i].equals("Chrome")) {
					connectionRatio[CHROME] = ++connectionNum[CHROME] / (double) totalNum;
				}
				if (browserArr[i].equals("ie")) {
					connectionRatio[IE] = ++connectionNum[IE] / (double) totalNum;
				} // end if
			} // end if
		} // end for

		for (int j = 0; j < browser.length; j++) { // ������ ���� ������ result �迭�� �Ҵ�
			connectionRatio[j] = Double.parseDouble(String.format("%.2f", (connectionRatio[j]) * 100)); // ���� ����
			result[j] = "��������:" + browser[j] + " / ����Ƚ��:" + connectionNum[j] + "ȸ / ����:" + connectionRatio[j] + "%";
		} // end for

		return result;
	}// getBrowserConnectionNum

	/**
	 * 3. ���񽺸� ���������� ������ Ƚ��. ������ Ƚ�� ���ϴ� method<br>
	 * ���� - 200, ���� - 404<br>
	 * 
	 * @return
	 */
	public String getServiceResult() {
		// index - 0:200 , 1:404 �� ���� Ƚ�� ���� �迭
		int[] num = new int[2];
		// errcode�� ����ŭ for �� ����
		for (int i = 0; i < errCodeArr.length; i++) {
			if (errCodeArr[i] != null) { // �ε����� null�� �ƴ� ��� �ϱ� ����
				if (errCodeArr[i].equals("200")) { // ������ ���
					num[0] += 1;
				}
				if (errCodeArr[i].equals("404")) { // ������ ���
					num[1] += 1;
				} // end if
			} // end if
		} // end for

		return "����Ƚ��(200):" + num[0] + "ȸ \n����Ƚ��(404):" + num[1] + "ȸ";
	}// getServiceResult

	/**
	 * 4. ��û�� ���� ���� �ð�
	 * 
	 * @return
	 */
	public String[] getRequestMany() {
		String[] returnData = new String[2]; // ������ returnData �迭 ����
		String[] retime = timeArr;

		ArrayList<String> date = new ArrayList<>(); // 2021-00 -00 ����
		ArrayList<String> time = new ArrayList<>(); // 00:00:00 ����
		Map<String, Integer> hour = new HashMap<String, Integer>();
		for (int i = 0; i < retime.length; i++) {
			if (retime[i] != null) {
				date.add(retime[i].substring(0, 10));
				time.add(retime[i].substring(11, retime[i].length()));
				if (!hour.containsKey(retime[i].substring(11, 13))) { // �ð��� ������ ���� ������
					hour.put(retime[i].substring(11, 13), 1); // Ű�� ���� �ִ´�
				} else { // �ð��� ������������ Ű�� ���� �޾� ī��Ʈ
					hour.put(retime[i].substring(11, 13), hour.get(retime[i].substring(11, 13)) + 1);
				}
			}
		} // end for

		String maxKey = ""; // ��û�� ���帹�� �ð�
		String maxVal = ""; // ��û�� ���� �����ð��� Ƚ��

		// Map ���
		for (String key : hour.keySet()) {
			if (maxKey == "") {
				maxKey = key;
				maxVal = hour.get(key).toString();
			} else if (Integer.parseInt(maxVal) < hour.get(key)) {
				maxKey = key;
				maxVal = hour.get(key).toString();
			}
		} // end for

		returnData[0] = maxKey; // ��û�� ���帹�� �ð�
		returnData[1] = maxVal; // ��û�� ���� �����ð��� Ƚ��

		return returnData;
	}// getRequestMany

	/**
	 * 5. ���������� ��û(403)�� �߻��� Ƚ��, ���� ���ϱ�
	 * 
	 * @return
	 */
	public String get403Info() {
		int[] num = new int[1]; // 403�� �߻��� Ƚ���� ���� �迭

		// errcode�� ����ŭ for �� ����
		for (int i = 0; i < errCodeArr.length; i++) {
			if (errCodeArr[i] != null) { // �ε����� null�� �ƴ� ��� �ϱ� ����

				if (errCodeArr[i].equals("403")) { // 403�� ���
					num[0] += 1;
				} // end if
			} // end if
		} // end for

		int length = errCodeArr.length;
		return num[0] + "ȸ, " + (String.format("%.2f", (Integer.valueOf(num[0]) / (double) length) * 100)) + "%";

	}// get403Info

	/**
	 * 6. ��û�� ���� ����(500)�� �߻��� Ƚ��, ���� ���ϱ�
	 * 
	 * @return
	 */
	public String get500Info() {

		int[] num = new int[1]; // 500�� �߻��� Ƚ���� ���� �迭

		// errcode�� ����ŭ for �� ����
		for (int i = 0; i < errCodeArr.length; i++) {
			if (errCodeArr[i] != null) { // �ε����� null�� �ƴ� ��� �ϱ� ����

				if (errCodeArr[i].equals("500")) { // 403�� ���
					num[0] += 1;
				} // end if
			} // end if
		} // end for

		int length = errCodeArr.length;

		return num[0] + "ȸ, " + (String.format("%.2f", (Integer.valueOf(num[0]) / (double) length) * 100) + "%");

	}// get500Info

	/**
	 * 7. �ԷµǴ� ���ο� �ش��ϴ� ������ ����ϴ� method<br>
	 * (�� : 1000~1500���� �� �ԷµǸ�) <br>
	 * 1000~1500��° ���ο� �ش��ϴ� ���� ��<br>
	 * �ִٻ�� Ű�� �̸��� Ƚ�� | java / xxȸ)
	 * 
	 * @author user
	 *
	 */
	public String getRangeMaxKey() throws IOException {
		// ���� �б�
		BufferedReader br = new BufferedReader(new FileReader(file));
		Map<String, Integer> keyMap2 = new HashMap<String, Integer>(); // key :�̸� valaue : Ƚ��
		List<String> list = new ArrayList<String>();

		String line = "";
		while ((line = br.readLine()) != null) {
			String[] lineName = line.split("=");

			if (lineName[1].toString().contains("&")) {
				String[] realKey = lineName[1].split("&");
				list.add(realKey[0]); // Key
			} // end if
		} // end while

		// ���� ��
		int lineCount = 0;

		// map ����
		// keymap2 -> list�� 1000-1500 ���θ� ������ ��
		for (String str : list) {
			if (lineCount >= 1000 && lineCount <= 1500) { // ����

				Integer count = keyMap2.get(str);
				if (count == null) {
					keyMap2.put(str, 1); // ���� ������ 1
				} else {
					keyMap2.put(str, count + 1); // ���� ������ 1 �߰�
				} // end else
			} // end if

			lineCount++; // 1000�̻� 1500���� if ���ǿ� �ɸ��� �ϱ� ����
		} // end for

		// ������ ���� map2 �� value�� �ִ񰪰� �� key �� ���ϱ�
		Integer maxValue2 = 0; // ����1000-1500 ������ �ִ� value ��
		String maxKey2 = ""; // ����1000-1500 ������ �ִ� value ���� key��
		for (String i : keyMap2.keySet()) {
			if (keyMap2.get(i) > maxValue2) {
				maxKey2 = i;
				maxValue2 = keyMap2.get(i);
			} // end if
		} // end for

		br.close();
		return maxKey2 + " / " + maxValue2 + "ȸ";
	}// RangeMaxKey

}// class