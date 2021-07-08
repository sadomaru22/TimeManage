package model;

import java.time.LocalDateTime;
import java.util.Calendar;

public class LastDayofMonth {
    public static int lastDay() {
    	
    	LocalDateTime now = LocalDateTime.now();
    	//対象年
    	int year = now.getYear();
    	//対象月
    	int month = now.getMonthValue();

        //取得処理
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        //int result = 
        int result = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //標準出力
        //System.out.format("取得結果=%1$d", result);
        return result;
    }
}
