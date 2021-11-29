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
 * logfile data를 생성하고 여러 일(정보 추출)을 수행하는 class<br>
 * 
 * @author syp
 */
public class LogFileData {
	// method에 사용될 항목은 인스턴스로 선언
	private String[] errCodeArr; // [errCode]
	private String[] urlArr; // [url (key포함)]
	private String[] browserArr; // [브라우저]
	private String[] timeArr;// [이용시간]

	private File file;

	// 브라우저별 접속횟수 method에서 사용될 상수 선언
	private static final int OPERA = 0;
	private static final int FIREFOX = 1;
	private static final int SAFARI = 2;
	private static final int CHROME = 3;
	private static final int IE = 4;

	/**
	 * 본 클래스가 객체화 될 때 수행 될 생성자<br>
	 * 기본적인 data를 만들어서 각 배열에 할당함<br>
	 * 
	 * @throws IOException
	 */
	public LogFileData(LogFileView lfv) throws IOException {
		file = new File(lfv.selectedPath); // 선택된 경로로 file 객체 생성
		BufferedReader bs = new BufferedReader(new FileReader(file));

		List<String> logLineList = new ArrayList<String>(); // logfile의 1줄

		// logfile 내용 배열에 넣기

		while (bs.readLine() != null) {
			logLineList.add(bs.readLine());
		} // end while

		// 주제별로 정보 담을 배열
		errCodeArr = new String[logLineList.size()];
		urlArr = new String[logLineList.size()];
		browserArr = new String[logLineList.size()];
		timeArr = new String[logLineList.size()];

		for (int i = 0; i < logLineList.size(); i++) { // 한줄씩 읽어들여서 주제별 토큰 담는 배열
			if (logLineList.get(i) != null) {
				StringTokenizer st = new StringTokenizer(logLineList.get(i), "[]");

				while (st.hasMoreTokens()) {
					errCodeArr[i] = st.nextToken(); // 에러코드
					urlArr[i] = st.nextToken(); // URL (KEY값 포함)
					browserArr[i] = st.nextToken(); // OS
					timeArr[i] = st.nextToken(); // 시간

				} // end while
			} // end if
		} // end for
		if (bs != null) { // Stream 닫기
			bs.close();
		} // end if

	}// logFileData

	/**
	 * 1. 최다사용 키의 이름과 횟수 | java xx회
	 * 
	 * @throws IOException
	 */
	public String getMaxKey() throws IOException {
		// 파일 읽기
		FileReader filereader = new FileReader(file);
		BufferedReader br = new BufferedReader(filereader);
		Map<String, Integer> keyMap = new HashMap<String, Integer>(); // key :이름 valaue : 횟수
		List<String> list = new ArrayList<String>();

		String line = "";
		while ((line = br.readLine()) != null) {
			String[] lineName = line.split("=");

			if (lineName[1].toString().contains("&")) {
				String[] realKey = lineName[1].split("&");
				list.add(realKey[0]);
			} // end if
		} // end while

		// Map에 저장
		for (String str : list) {
			Integer count = keyMap.get(str);
			if (count == null) {
				keyMap.put(str, 1);
			} else {
				keyMap.put(str, count + 1);
			} // end else
		} // end if

		// Map max값 출력
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

		// System.out.println("최대 사용키의 이름은 " + maxKey +"이고 횟수는 " + maxVal + "회 입니다") ;

		br.close();
		return maxKey + " : " + maxVal;

	}// getMaxKey

	/**
	 * 2. 브라우저별 접속횟수와 비율 구하는 method<br>
	 * 
	 * @return
	 */
	public String[] getBrowserConnectionNum() {
		String[] browser = "opera,firefox,Safari,Chrome,ie".split(",");
		String[] result = new String[browser.length]; // 반환할 배열
		// 접속횟수와 비율 담을 Arr
		int[] connectionNum = new int[browser.length];
		double[] connectionRatio = new double[browser.length];

		int totalNum = browserArr.length;
		for (int i = 0; i < totalNum; i++) { // 접속횟수만큼 for 수행
			if (browserArr[i] != null) { // null이 아닐 경우 수행
				if (browserArr[i].equals("opera")) { // 각 브라우저를 확인한 후 횟수와 비율 구하는 if
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

		for (int j = 0; j < browser.length; j++) { // 위에서 구한 비율을 result 배열에 할당
			connectionRatio[j] = Double.parseDouble(String.format("%.2f", (connectionRatio[j]) * 100)); // 서식 설정
			result[j] = "브라우저명:" + browser[j] + " / 접속횟수:" + connectionNum[j] + "회 / 비율:" + connectionRatio[j] + "%";
		} // end for

		return result;
	}// getBrowserConnectionNum

	/**
	 * 3. 서비스를 성공적으로 수행한 횟수. 실패한 횟수 구하는 method<br>
	 * 성공 - 200, 실패 - 404<br>
	 * 
	 * @return
	 */
	public String getServiceResult() {
		// index - 0:200 , 1:404 에 따른 횟수 넣을 배열
		int[] num = new int[2];
		// errcode의 수만큼 for 문 수행
		for (int i = 0; i < errCodeArr.length; i++) {
			if (errCodeArr[i] != null) { // 인덱스가 null이 아닐 경우 하기 수행
				if (errCodeArr[i].equals("200")) { // 성공일 경우
					num[0] += 1;
				}
				if (errCodeArr[i].equals("404")) { // 실패일 경우
					num[1] += 1;
				} // end if
			} // end if
		} // end for

		return "성공횟수(200):" + num[0] + "회 \n실패횟수(404):" + num[1] + "회";
	}// getServiceResult

	/**
	 * 4. 요청이 가장 많은 시간
	 * 
	 * @return
	 */
	public String[] getRequestMany() {
		String[] returnData = new String[2]; // 리턴할 returnData 배열 생성
		String[] retime = timeArr;

		ArrayList<String> date = new ArrayList<>(); // 2021-00 -00 구간
		ArrayList<String> time = new ArrayList<>(); // 00:00:00 구간
		Map<String, Integer> hour = new HashMap<String, Integer>();
		for (int i = 0; i < retime.length; i++) {
			if (retime[i] != null) {
				date.add(retime[i].substring(0, 10));
				time.add(retime[i].substring(11, retime[i].length()));
				if (!hour.containsKey(retime[i].substring(11, 13))) { // 시간을 가지고 있지 않으면
					hour.put(retime[i].substring(11, 13), 1); // 키와 값을 넣는다
				} else { // 시간을 가지고있으면 키와 값을 받아 카운트
					hour.put(retime[i].substring(11, 13), hour.get(retime[i].substring(11, 13)) + 1);
				}
			}
		} // end for

		String maxKey = ""; // 요청이 가장많은 시간
		String maxVal = ""; // 요청이 가장 많은시간의 횟수

		// Map 출력
		for (String key : hour.keySet()) {
			if (maxKey == "") {
				maxKey = key;
				maxVal = hour.get(key).toString();
			} else if (Integer.parseInt(maxVal) < hour.get(key)) {
				maxKey = key;
				maxVal = hour.get(key).toString();
			}
		} // end for

		returnData[0] = maxKey; // 요청이 가장많은 시간
		returnData[1] = maxVal; // 요청이 가장 많은시간의 횟수

		return returnData;
	}// getRequestMany

	/**
	 * 5. 비정상적인 요청(403)이 발생한 횟수, 비율 구하기
	 * 
	 * @return
	 */
	public String get403Info() {
		int[] num = new int[1]; // 403이 발생한 횟수를 넣을 배열

		// errcode의 수만큼 for 문 수행
		for (int i = 0; i < errCodeArr.length; i++) {
			if (errCodeArr[i] != null) { // 인덱스가 null이 아닐 경우 하기 수행

				if (errCodeArr[i].equals("403")) { // 403일 경우
					num[0] += 1;
				} // end if
			} // end if
		} // end for

		int length = errCodeArr.length;
		return num[0] + "회, " + (String.format("%.2f", (Integer.valueOf(num[0]) / (double) length) * 100)) + "%";

	}// get403Info

	/**
	 * 6. 요청에 대한 에러(500)가 발생한 횟수, 비율 구하기
	 * 
	 * @return
	 */
	public String get500Info() {

		int[] num = new int[1]; // 500이 발생한 횟수를 넣을 배열

		// errcode의 수만큼 for 문 수행
		for (int i = 0; i < errCodeArr.length; i++) {
			if (errCodeArr[i] != null) { // 인덱스가 null이 아닐 경우 하기 수행

				if (errCodeArr[i].equals("500")) { // 403일 경우
					num[0] += 1;
				} // end if
			} // end if
		} // end for

		int length = errCodeArr.length;

		return num[0] + "회, " + (String.format("%.2f", (Integer.valueOf(num[0]) / (double) length) * 100) + "%");

	}// get500Info

	/**
	 * 7. 입력되는 라인에 해당하는 정보를 출력하는 method<br>
	 * (예 : 1000~1500라인 이 입력되면) <br>
	 * 1000~1500번째 라인에 해당하는 정보 중<br>
	 * 최다사용 키의 이름과 횟수 | java / xx회)
	 * 
	 * @author user
	 *
	 */
	public String getRangeMaxKey() throws IOException {
		// 파일 읽기
		BufferedReader br = new BufferedReader(new FileReader(file));
		Map<String, Integer> keyMap2 = new HashMap<String, Integer>(); // key :이름 valaue : 횟수
		List<String> list = new ArrayList<String>();

		String line = "";
		while ((line = br.readLine()) != null) {
			String[] lineName = line.split("=");

			if (lineName[1].toString().contains("&")) {
				String[] realKey = lineName[1].split("&");
				list.add(realKey[0]); // Key
			} // end if
		} // end while

		// 라인 수
		int lineCount = 0;

		// map 저장
		// keymap2 -> list의 1000-1500 라인만 가져온 맵
		for (String str : list) {
			if (lineCount >= 1000 && lineCount <= 1500) { // 조건

				Integer count = keyMap2.get(str);
				if (count == null) {
					keyMap2.put(str, 1); // 값이 없으면 1
				} else {
					keyMap2.put(str, count + 1); // 값이 있으면 1 추가
				} // end else
			} // end if

			lineCount++; // 1000이상 1500이하 if 조건에 걸리게 하기 위해
		} // end for

		// 생성한 위의 map2 의 value의 최댓값과 그 key 값 구하기
		Integer maxValue2 = 0; // 라인1000-1500 사이의 최대 value 값
		String maxKey2 = ""; // 라인1000-1500 사이의 최대 value 값의 key값
		for (String i : keyMap2.keySet()) {
			if (keyMap2.get(i) > maxValue2) {
				maxKey2 = i;
				maxValue2 = keyMap2.get(i);
			} // end if
		} // end for

		br.close();
		return maxKey2 + " / " + maxValue2 + "회";
	}// RangeMaxKey

}// class