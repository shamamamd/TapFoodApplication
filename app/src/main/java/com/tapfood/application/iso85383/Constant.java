package com.tapfood.application.iso85383;

import android.os.Looper;
import android.util.Log;

import com.tapfood.application.constant.HexUtil;

import org.jpos.iso.ISOUtil;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.chrono.IsoChronology;
import java.util.Arrays;
import java.util.Formatter;

public class Constant {

    public static String convertDecimalToHexDecimal(String decimal){

        return new BigDecimal(decimal).toBigInteger().toString(16) ;
    }
    public static String convertHexStringTo4Length(String s){
        s=convertDecimalToHexDecimal(s);
        String temp="00000000";
        temp=temp.substring(0,temp.length()-s.length())+s;
        return temp;
    }
    public static String convertHexStringTo2Length(String s){
        s=convertDecimalToHexDecimal(s);
        String temp="0000";
        temp=temp.substring(0,temp.length()-s.length())+s;
        return temp;
    }
    public static String convertHexStringTo1Length(String s){
        s=convertDecimalToHexDecimal(s);
        String temp="00";
        temp=temp.substring(0,temp.length()-s.length())+s;
        return temp;
    }

    public static String MacKey;
    public static String PINKey;


    public static void fnp_enc_GenDefaultKeysEx(String pos_serial_number) throws NoSuchAlgorithmException {

        byte [] pos_serial = pos_serial_number.getBytes();

        byte[] masterKeyByte = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8}; // 16
        byte[] second_pos_serial_number = new byte[20]; // initial value 0
        byte[] motherKey; //= new byte[20]; // initial value 0

        if(pos_serial.length >= 15){
//            second_pos_serial_number = Arrays.copyOfRange(pos_serial, pos_serial.length - 15, pos_serial.length);
            // append 0 to end
//            byte[] tmp = Arrays.copyOfRange(pos_serial, pos_serial.length -1 - 15, pos_serial.length  );
            System.arraycopy(pos_serial, pos_serial.length - 15, second_pos_serial_number,
                    0, 15);

            Log.i("shaLengthPos",String.valueOf(second_pos_serial_number.length));
            Log.i("shaNewPOSByteArray2HEX",byteArray2Hex(second_pos_serial_number));
            Log.i("shaNewPOSbcdtostr",HexUtil.bcd2str(second_pos_serial_number));
            Log.i("shaNewPOS_new_str",new String(second_pos_serial_number));

        }
        else{
//            second_pos_serial_number = Arrays.copyOfRange(pos_serial, 15 - pos_serial.length, pos_serial.length);
            System.arraycopy(pos_serial, 0, second_pos_serial_number,
            15 - pos_serial.length, pos_serial.length);
            Log.i("shaPOSID",byteArray2Hex(second_pos_serial_number));

        }

        masterKeyByte = ISOUtil.xor(masterKeyByte, second_pos_serial_number);

        Log.i("shaXOR_convert_C2java",HexUtil.bcd2str(masterKeyByte));

        SHAA1(masterKeyByte);
        motherKey = SHA1ByteArray(masterKeyByte);

        String motherKeyString = HexUtil.bcd2str(motherKey);

        StringBuilder mackKey = new StringBuilder ();
        StringBuilder pinKey = new StringBuilder ();

       /* for ( int i = 0; i < motherKeyString.length(); i+=4)
        {
            mackKey.append(motherKeyString.charAt(i));
            mackKey.append(motherKeyString.charAt(i + 1));

            pinKey.append(motherKeyString.charAt(i + 2));
            pinKey.append(motherKeyString.charAt(i + 3));
        }*/

        for (int i = 0; i < 8; ++i) {
            mackKey.append(motherKeyString.charAt(i * 2));
            pinKey.append(motherKeyString.charAt((i*2) + 1));
        }

        MacKey = mackKey.toString();
        PINKey = pinKey.toString();

    }



    private static String SHAA1(byte[] convert) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        Log.i("sha1",byteArray2Hex(md.digest(convert)));
        return HexUtil.bcd2str(md.digest(convert));
    }


    // calculate key MAC for logon mac
    public static String defCalculatorKeyMAC() {
        String mac = null;

        String masterKey = "01020304050607080102030405060708";
        String serial    = "00000000000000040000000000000100";

        try {

            Log.i("sha1XOR_HEX_STRING",SHA1(HexUtil.xor(HexUtil.hexStringToByte(masterKey),HexUtil.hexStringToByte(serial))));
            Log.i("shaXOR_hexString", ISOUtil.hexor(masterKey, serial));
            mac = getEvenIndex(SHA1(HexUtil.hexStringToByte(ISOUtil.hexor(masterKey, serial))));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return mac;
    }

    private static byte[] SHA1ByteArray(byte[] convert) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        Log.i("shaSHA1",byteArray2Hex(md.digest(convert)));
        return md.digest(convert);
    }

    private static String SHA1(byte[] convert) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        Log.i("shaSHA15",byteArray2Hex(md.digest(convert)));
        return HexUtil.bcd2str(md.digest(convert));
    }

    private static String byteArray2Hex(final byte[] b) {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result +=
                    Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }

    private static String getEvenIndex(String s) {
        String str = "";
        for (int i = 0; i < 15; i = i + 2) {
            char x = (s.charAt(i));
            str = str + String.valueOf(x);

        }
        return str;
    }






}
