package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CsvOutput2 {

	public static void csvWrite(HttpServletRequest request, HttpServletResponse response, List<WorkTimeDTO> workTimeThisMonthList, int sum) {

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH時mm分");
		try {
		FileWriter fw = new FileWriter("/Users/watanabekazuma/sum_worktime.csv", false);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
		
		//カンマ区切り
		final String COMMA = ",";
		//改行
		final String NEW_FILE = "\r\n";

		pw.print("出勤日");
		pw.print(COMMA);
		pw.print("出勤時間");
		pw.print(COMMA);
		pw.print("退勤時間");
		pw.print(COMMA);
		pw.print("実働時間");
		pw.print(NEW_FILE);
		
			//Personの情報を1行ずつインスタンスへ登録
			for (WorkTimeDTO p: workTimeThisMonthList) {
				
				pw.print((p.getWorkDate()).toString());
				pw.print(COMMA);
				pw.print((p.getStartTime().format(timeFormat)).toString());
				pw.print(COMMA);
				pw.print((p.getFinishTime().format(timeFormat)).toString());
				pw.print(COMMA);
				pw.print(String.valueOf(p.getWorkingHours()) + "時間" + String.valueOf(p.getWorkingMins()) + "分");
				pw.print(NEW_FILE);
			}
			
			pw.print("実働時間合計");
			pw.print(COMMA);
			pw.print(String.valueOf(sum) + "時間");
			pw.print(NEW_FILE);
			
			pw.close();
			
			System.out.println("CSVファイルを出力しました");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}