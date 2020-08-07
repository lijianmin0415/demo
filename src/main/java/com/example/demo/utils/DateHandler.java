package com.example.demo.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateHandler
{
  public static String getCurrentDate()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(new Date());
  }

  public static String getCurrentSysDate()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    return sdf.format(new Date());
  }

  public static String getCurrentSysYear()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    return sdf.format(new Date());
  }

  public static String getCurrentSysTime()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    return sdf.format(new Date());
  }

  public static String getCurrentSysDate(String format)
  {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(new Date());
  }

  public static String getCurrentSysDate(String format, int day)
  {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Calendar calendar = Calendar.getInstance();
    calendar.add(5, day);
    return sdf.format(calendar.getTime());
  }

  public static String dateToStr(Date date, String format)
  {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }

  public static String getCurTime()
  {
    Calendar c = Calendar.getInstance();
    SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    c = Calendar.getInstance(Locale.CHINESE);
    return simpleDateTimeFormat.format(c.getTime());
  }

  public static String dateToString(Date date)
  {
    if (date == null) {
      return "";
    }
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return df.format(date);
  }

  public static String dateToString(java.sql.Date date)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    return sdf.format(date);
  }

  public static String dateToString2(Date date)
  {
    DateFormat df = new SimpleDateFormat("yyyyMMddHHMMssssss");
    return df.format(date);
  }

  public static String dateToString()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    return sdf.format(new Date());
  }

  public static String dateToString3(Date date)
  {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    return df.format(date);
  }

  public static boolean checkTimes(String targetTime)
  {
    if ((targetTime == null) || ("".equals(targetTime.trim()))) {
      return false;
    }
    Pattern p = Pattern.compile("^(([0-1][0-9])|([2][0-4])):[0-5][0-9]:[0-5][0-9]$");
    Matcher m = p.matcher(targetTime);
    return m.matches();
  }

  public static boolean checkDateTimes(String sourceDate)
  {
    boolean result = true;
    if (StringHandler.isEmptyOrNull(sourceDate))
    {
      result = false;
    }
    else
    {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      try
      {
        sdf.parse(sourceDate.trim());
      }
      catch (ParseException e)
      {
        result = false;
      }
    }
    return result;
  }

  public static boolean checkDate(String sourceDate)
  {
    boolean result = true;
    if (StringHandler.isEmptyOrNull(sourceDate))
    {
      result = false;
    }
    else
    {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      try
      {
        sdf.parse(sourceDate.trim());
      }
      catch (ParseException e)
      {
        result = false;
      }
    }
    return result;
  }

  public static java.sql.Date stringToDate(String source)
  {
    java.sql.Date date = null;
    try
    {
      date = java.sql.Date.valueOf(source.trim());
    }
    catch (IllegalArgumentException e)
    {
      date = null;
    }
    return date;
  }

  public static Date stringToDates(String source)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
    if (StringHandler.isEmptyOrNull(source)) {
      return null;
    }
    try
    {
      return sdf.parse(source);
    }
    catch (ParseException e) {}
    return null;
  }

  public static Date stringToDates(String source, String format)
  {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    if (StringHandler.isEmptyOrNull(source)) {
      return null;
    }
    try
    {
      return sdf.parse(source);
    }
    catch (ParseException e) {}
    return null;
  }

  public static Date stringToDate2(String source)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    if (StringHandler.isEmptyOrNull(source)) {
      return null;
    }
    try
    {
      return sdf.parse(source);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  public static boolean checkTwoTime(String time1, String time2)
  {
    Date d1 = stringToDates(time1);
    Date d2 = stringToDates(time2);
    return d1.before(d2);
  }

  public static String stringToDate(String source, String informat, String outformat)
  {
    Date date = stringToDates(source, informat);
    if (date == null) {
      return "";
    }
    return dateToStr(date, outformat);
  }

  public static int decideTwoTime(String time1, String time2)
  {
    Date t1 = stringToDates(time1, "HH:mm");
    Date t2 = stringToDates(time2, "HH:mm");
    return t1.compareTo(t2);
  }

  public static String formartNumber(double inpara)
  {
    DecimalFormat df = new DecimalFormat("###,##0.00");
    return df.format(inpara);
  }

  public static String getdateForDayToString(Date date, int day)
  {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    long myTime = date.getTime() / 1000L + day * 60 * 60 * 24;
    date.setTime(myTime * 1000L);
    String strDay = df.format(date);

    return strDay;
  }

  public static String getdateForDayToString(Date date, int day, String format)
  {
    DateFormat df = new SimpleDateFormat(format);


    long myTime = date.getTime() / 1000L + day * 60 * 60 * 24;
    date.setTime(myTime * 1000L);
    String strDay = df.format(date);

    return strDay;
  }

  public static Date string2Datetime(String source)
  {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try
    {
      return df.parse(source);
    }
    catch (ParseException e) {}
    return null;
  }

  public static String getdateForMonth(String sourceDate, String format, int months)
  {
    if (StringHandler.isEmptyOrNull(sourceDate)) {
      return "";
    }
    Date date = stringToDates(sourceDate, format);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(2, months);
    Date result = calendar.getTime();
    return dateToStr(result, format);
  }

  public static String getdateForDayToString(String sourceDate, String format, int days)
  {
    if (StringHandler.isEmptyOrNull(sourceDate)) {
      return "";
    }
    Date date = stringToDates(sourceDate, format);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(5, days);
    Date result = calendar.getTime();
    return dateToStr(result, format);
  }

  public static String getdateForDayToString(String sourceDate, String informat, String outformat, int days)
  {
    if (StringHandler.isEmptyOrNull(sourceDate)) {
      return "";
    }
    Date date = stringToDates(sourceDate, informat);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(5, days);
    Date result = calendar.getTime();
    return dateToStr(result, outformat);
  }

  public static String getdateForHourToString(String sourceDate, String format, int hours)
  {
    if (StringHandler.isEmptyOrNull(sourceDate)) {
      return "";
    }
    Date date = stringToDates(sourceDate, format);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(11, hours);
    Date result = calendar.getTime();
    return dateToStr(result, format);
  }
  
  public static String getWeek(int day)
  {
    String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
    Calendar calendar = Calendar.getInstance();
    calendar.add(5, day);
    int index = calendar.get(7) - 1;
    if (index < 0) {
      index = 0;
    }
    return weekDays[index];
  }
  
  public static List<String> calculationYear(int calYear)
  {
    List<String> years = new ArrayList();
    for (int i = 0; i < 5; i++)
    {
      int yr = calYear + i;
      years.add(String.valueOf(yr));
    }
    Calendar clen = Calendar.getInstance();
    clen.add(1, -1);
    String preYear = String.valueOf(clen.get(1));
    if (!years.contains(preYear)) {
      years.add(preYear);
    }
    clen.add(1, 1);
    String currYear = String.valueOf(clen.get(1));
    if (!years.contains(currYear)) {
      years.add(currYear);
    }
    clen.add(1, 1);
    String nextYear = String.valueOf(clen.get(1));
    if (!years.contains(nextYear)) {
      years.add(nextYear);
    }
    clen.add(1, 1);
    String lastYear = String.valueOf(clen.get(1));
    if (!years.contains(lastYear)) {
      years.add(lastYear);
    }
    return years;
  }

}
