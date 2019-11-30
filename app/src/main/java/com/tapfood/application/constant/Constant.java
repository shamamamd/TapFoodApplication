package com.tapfood.application.constant;

import android.util.Log;

import org.jpos.iso.ISOUtil;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class Constant {

    public static String getStar(int len){
        String str = "";
        while (len > 0) {
            str += "*";
            len--;
        }
        return str;
    }
    public static String convertEnToFa(String str){
        char[] farsiChar = {'٠','١','٢','٣','٤','٥','٦','٧','٨','٩'};
        StringBuilder builder = new StringBuilder();
        for(int i =0;i<str.length();i++)
        {
            if(Character.isDigit(str.charAt(i)))
            {
                builder.append(farsiChar[(int)(str.charAt(i))-48]);
            }
            else
            {
                builder.append(str.charAt(i));
            }

        }
        return builder.toString();
    }
    public static String formatPrice(int number){
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        String numberAsString = numberFormat.format(number);
        return numberAsString;
    }
    public static String getCurTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String time = format.format(date);
        return time;
    }
    public static String getCurDate(){
        JalaliCalendar jalaliCalendar=new JalaliCalendar();
        String s=jalaliCalendar.getJalaliDate(new Date());
        return s;
    }

}
