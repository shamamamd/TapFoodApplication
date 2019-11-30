package com.tapfood.application.iso85383;

import org.jpos.iso.IFA_BITMAP;
import org.jpos.iso.IFB_BITMAP;
import org.jpos.iso.IFB_LLHBINARY;
import org.jpos.iso.IFB_LLLBINARY;
import org.jpos.iso.IFB_LLLHBINARY;
import org.jpos.iso.IFE_BITMAP;
import org.jpos.iso.ISOBasePackager;
import org.jpos.iso.ISOFieldPackager;

public class CustomPackager  extends ISOBasePackager {
    private static final boolean pad = false;
    protected ISOFieldPackager fld[] = {
            new CustomField(1, "MESSAGE TYPE INDICATOR"),
            new IFB_BITMAP(5, "BIT MAP"),
            /** 002 **/new CustomField(2, "SYSTEM TRACE AUDIT NUMBER"),
            /** 003 **/new CustomField(4, "Date And Time"),
            /** 004 **/new IFB_LLHBINARY(255, "AMOUNT, TRANSACTION"),
            /** 005 **/new CustomField(4, "CARD ACCEPTOR TERMINAL IDENTIFICACION"),
            /** 006 **/new CustomField(8, "TRANSMISSION DATE AND TIME"),
            /** 007 **/new IFB_LLHBINARY(255, "SECURITY RELATED CONTROL INFORMATION"),
            /** 008 **/new CustomField(2, "SYSTEM TRACE AUDIT NUMBER"),
            /** 009 **/new IFB_LLHBINARY(255, "CONVERSION RATE, SETTLEMENT"),
            /** 010 **/new CustomField(1, "PAYEE"),
            /** 011 **/new IFB_LLHBINARY(255, "ACQUIRING INSTITUTION IDENT CODE"),
            /** 012 **/new CustomField(6, "DATE, LOCAL TRANSACTION"),
            /** 013 **/new CustomField(4, "CARD ACCEPTOR IDENTIFICATION CODE"),
            /** 014 **/new CustomField(2, "DATE, EXPIRATION"),
            /** 015 **/new CustomField(2, "Application Version"),
            /** 016 **/new CustomField(4, "DATE, CONVERSION"),
            /** 017 **/new CustomField(4, "DATE, CAPTURE SWITCH"),
            /** 018 **/new IFB_LLHBINARY(255, "MERCHANTS TYPE"),
            /** 019 **/new CustomField(1, "ACQUIRING INSTITUTION COUNTRY CODE"),
            /** 020 **/new CustomField(1, "RESPONSE CODE"),
            /** 021 **/new IFB_LLHBINARY(255, "salesperson"),
            /** 022 **/new IFB_LLHBINARY(255, "buyer"),
            /** 023 **/new IFB_LLLHBINARY(65535, "CARD SEQUENCE NUMBER"),
            /** 024 **/new CustomField(2, "NETWORK INTERNATIONAL IDENTIFIEER"),
            /** 025 **/new CustomField(4, "POINT OF SERVICE CONDITION CODE"),
            /** 026 **/new CustomField(1, "Dial indicator"),
            /** 027 **/new IFB_LLHBINARY(255, "AUTHORIZATION IDENTIFICATION RESP LEN"),
            /** 028 **/new IFB_LLHBINARY(255, "AMOUNT, TRANSACTION FEE"),
            /** 029 **/new IFB_LLHBINARY(255, "AMOUNT, SETTLEMENT FEE"),
            /** 030 **/new IFB_LLHBINARY(255, "AMOUNT, TRANSACTION PROCESSING FEE"),
            /** 031 **/new IFB_LLHBINARY(255, "AMOUNT, SETTLEMENT PROCESSING FEE"),
            /** 032 **/new IFB_LLHBINARY(255, "ACQUIRING INSTITUTION IDENT CODE"),
            /** 033 **/new IFB_LLHBINARY(255, "FORWARDING INSTITUTION IDENT CODE"),
            /** 034 **/new IFB_LLHBINARY(255, "PAN EXTENDED"),
            /** 035 **/new IFB_LLHBINARY(255, "TRACK 2 DATA"),
            /** 036 **/new IFB_LLHBINARY(255, "TRACK 3 DATA"),
            /** 037 **/new IFB_LLLHBINARY(65535, "RETRIEVAL REFERENCE NUMBER"),
            /** 038 **/new IFB_LLLHBINARY(65535, "AUTHORIZATION IDENTIFICATION RESPONSE"),
            /** 039 **/new IFB_LLLHBINARY(65535, "RESPONSE CODE"),
            /** 040 **/new CustomField(4, "MESSAGE AUTHENTICATION CODE FIELD"),

    };

    public CustomPackager() {
        super();
        setFieldPackager(fld);
    }


}