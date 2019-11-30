package com.tapfood.application.iso85383;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.health.HealthStats;
import android.support.annotation.RequiresApi;
import android.util.Log;


import com.tapfood.application.constant.Data;
import com.tapfood.application.constant.HexUtil;
import com.tapfood.application.tapFood.receipt.successful.Successful;
import com.tapfood.application.tapFood.receipt.unsuccessful.Unsuccessful;


import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISOUtil;


import java.io.IOException;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClientISO extends Activity implements Pepiso {


    public ISOPackager packager;
    public ISOMsg isoMessage;
    public String hostname;
    public int portNumber;
    public Data data;

    public static ISOMsg iso;

    public void initialize() {
        data = (Data) getIntent().getSerializableExtra("cardData");

        hostname = "178.248.40.163";// "178.248.40.163";//185.60.32.41
        portNumber = 15001;//15001;12003
        isoMessage = new ISOMsg();
        packager = new CustomPackager();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();
        client(logon());

        iso=logon();

        Log.i("Show Data SwipeCard", data.getCardNumber() + "   " + data.getMac() + "  " + data.getPin() + "   " + data.getSecondTrackData() + "   " + data.getSerialNumberTerminal());
       // Log.i("shaMAC", data.getMac());


        try {
            Constant.fnp_enc_GenDefaultKeysEx("0000560400000000000001");
            Log.i("MacKey",Constant.MacKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }

    public String getTimeStamp() {
        long time = 0;

        try {

            Date date1 = new Date((new Date()).getTime());
            SimpleDateFormat formatNowDay = new SimpleDateFormat("dd", Locale.getDefault());
            SimpleDateFormat formatNowMonth = new SimpleDateFormat("MM", Locale.getDefault());
            SimpleDateFormat formatNowHours = new SimpleDateFormat("HH", Locale.getDefault());
            SimpleDateFormat formatNowMinute = new SimpleDateFormat("mm", Locale.getDefault());
            SimpleDateFormat formatNowSecond = new SimpleDateFormat("ss", Locale.getDefault());
            SimpleDateFormat formatGMT = new SimpleDateFormat("zzz", Locale.getDefault());

            String currentDay = formatNowDay.format(date1);
            String currentMonth = formatNowMonth.format(date1);
            String currentHours = formatNowHours.format(date1);
            String currentMinute = formatNowMinute.format(date1);
            String currentSecond = formatNowSecond.format(date1);
            String GMT = formatGMT.format(date1);

            String str_date = currentDay + currentMonth + "1975" + currentHours + currentMinute + currentSecond+ GMT;
            DateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmsszzz", Locale.getDefault());
            Date date = formatter.parse(str_date);
            time = (date.getTime() / 1000L) + 12476;
            Log.i("shaTimePEP", Constant.convertHexStringTo4Length(String.valueOf(time)));
            Log.i("shaTime", "Today is " + (date.getTime() / 1000L));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Constant.convertHexStringTo4Length(String.valueOf(time));
    }

    public ISOMsg logon() {

        try {
            isoMessage.setPackager(packager);
            isoMessage.set(0, "82");
            isoMessage.set(2, Constant.convertHexStringTo2Length("1"));
            isoMessage.set(3, getTimeStamp());
            isoMessage.set(5, Constant.convertHexStringTo4Length("118120"));
            isoMessage.set(7, "0000560400000000000001");
            isoMessage.set(8, Constant.convertHexStringTo2Length("0"));
            isoMessage.set(13, Constant.convertHexStringTo4Length("118120"));
            isoMessage.set(15, Constant.convertHexStringTo2Length("0"));
            isoMessage.set(40, "639d61a5");//"639d61a5

        } catch (ISOException e) {
            e.printStackTrace();
            Log.i("isoError", e.toString());

        }
        return isoMessage;
    }

    public void logonAnswer(ISOMsg isoMsg) throws ISOException {

        isoMsg.dump(System.out, "");
        byte[] receiveData = isoMsg.pack();
        Log.i("shaReceiveString", HexUtil.bcd2str(receiveData));
        Log.i("shaReceiveMessage is ", CustomISOUtil.hexdump(receiveData, 0, receiveData.length));
        if (isoMsg.getString(20).equals("00")) {
            Intent intent = new Intent(ClientISO.this, Successful.class);
            startActivity(intent);
        } else {

            Intent intent = new Intent(ClientISO.this, Unsuccessful.class);
            startActivity(intent);
        }
    }

    public void sendMessage(ISOMsg isoMsg) throws ISOException {
        byte[] data = isoMsg.pack();
        isoMsg.dump(System.out, "");
        Log.i("shaSendString", HexUtil.bcd2str(data));
        Log.i("shaSendMessage is ", CustomISOUtil.hexdump(data, 0, data.length));
    }


    @Override
    public ISOMsg readPack(String message) {
        ISOMsg isoMessage = new ISOMsg();
        try {

            byte[] bmsg = ISOUtil.hex2byte(message);

            isoMessage.setPackager(packager);
            isoMessage.unpack(bmsg);
        } catch (ISOException e) {
            e.printStackTrace();
            Log.i("ReadPackage", e.toString());
        }
        return isoMessage;
    }

    @Override
    public void printIsoMsg(ISOMsg isoMsg) {

        for (int i = 0; i <= isoMsg.getMaxField(); i++) {
            if (isoMsg.hasField(i)) {
                Log.i("shaPrint", "    Field-" + i + " : " + isoMsg.getString(i));
            }
        }

    }

    public void client(ISOMsg msg) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    CustomPostChannel channel = new CustomPostChannel(hostname, portNumber, packager);
                    channel.setTimeout(120000);
                    channel.connect();
                    channel.sendMessageLength(msg.pack().length);
                    if (channel.isConnected()) {
                        byte[] data = msg.pack();
                        channel.send(data);
                        sendMessage(msg);
                        ISOMsg isoMsg = channel.receive();
                        logonAnswer(isoMsg);
                    } else {
                        Log.i("shaConnect", "connect failed");
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                    Log.i("shaSocketException", e.toString());
                } catch (IOException | ISOException e) {
                    e.printStackTrace();
                    Log.i("shaISOException", e.toString());
                }
            }
        }).start();
    }

}