package com.tapfood.application.iso85383;

import org.jpos.iso.ISOMsg;

public interface Pepiso {


    ISOMsg readPack(String message);
    void printIsoMsg(ISOMsg isoMsg);



}
