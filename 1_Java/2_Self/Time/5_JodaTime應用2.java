import org.joda.time.LocalDate;

import java.sql.Date;
import java.util.Calendar;

public class DateTimeTool {

    // 取得前一個交易日
    public static Date getPreviousTradingDay() {
        int dayOfTheWeek = getDayOfTheWeek();
        int day = dayOfTheWeek == 1 ? -3 : -1;
        return DateTimeTool.getDeltaDay(0 ,0, day);
    }

    // 加減sql.Date
    public static Date getDeltaDay(int year, int month, int day) {
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        date.setTime(calendar.getTime().getTime());
        return date;
    }

    // 取得星期幾 Mon=1, Tue=2
    public static int getDayOfTheWeek() {
        return new LocalDate().getDayOfWeek();
    }

    /**
     * 取得月份區間
     * @param plusMinus -1
     * @return 2021/10/01~2021/10/31
     */
    public static String getMonthRange(int plusMinus) {
        LocalDate today = new LocalDate();
        LocalDate start = today.plusMonths(plusMinus).withDayOfMonth(1);          // 目標月一號
        LocalDate nextFirst = today.plusMonths(plusMinus + 1).withDayOfMonth(1);  // 目標月下個月一號
        LocalDate end = nextFirst.plusDays(-1);                                   // 目標月最後一號
        return getDateRange(start.toString(), end.toString());
    }

    /**
     * 取得月份區間
     * @param month 2022-11
     * @return 2022/11/01~2022/11/30
     */
    public static String getMonthRange(String month) {
        LocalDate start = new LocalDate(month);
        LocalDate nextFirst = start.plusMonths(1).withDayOfMonth(1);
        LocalDate end = nextFirst.plusDays(-1);
        return getDateRange(start.toString(), end.toString());
    }

    /**
     * 取得日期區間
     * @param start 2022-10-05
     * @param end 2022-12-25
     * @return 2022/10/05~2022/12/25
     */
    public static String getDateRange(String start, String end) {
        LocalDate startDate = new LocalDate(start);
        LocalDate endDate = new LocalDate(end);
        String range = startDate + "~" + endDate;
        return range.replace("-", "/");
    }

}
