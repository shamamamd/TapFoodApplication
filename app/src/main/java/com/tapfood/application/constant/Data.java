package com.tapfood.application.constant;

import java.io.Serializable;

public class Data implements Serializable {
    public String cardNumber;
    public String secondTrackData;
    public String pin;
    public String mac;
    public String serialNumberTerminal;

    public Data(){

    }


    public Data(String cardNumber, String secondTrackData, String pin, String mac, String serialNumberTerminal) {
        this.cardNumber = cardNumber;
        this.secondTrackData = secondTrackData;
        this.pin = pin;
        this.mac = mac;
        this.serialNumberTerminal = serialNumberTerminal;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getSecondTrackData() {
        return secondTrackData;
    }

    public void setSecondTrackData(String secondTrackData) {
        this.secondTrackData = secondTrackData;
    }
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSerialNumberTerminal() {
        return serialNumberTerminal;
    }

    public void setSerialNumberTerminal(String serialNumberTerminal) {
        this.serialNumberTerminal = serialNumberTerminal;
    }

}
