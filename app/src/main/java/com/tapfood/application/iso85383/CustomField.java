package com.tapfood.application.iso85383;

import org.jpos.iso.ISOBinaryFieldPackager;
import org.jpos.iso.LiteralBinaryInterpreter;
import org.jpos.iso.NullPrefixer;


public class CustomField extends ISOBinaryFieldPackager {

    public CustomField(int len, String description) {
        super(len, description, LiteralBinaryInterpreter.INSTANCE, NullPrefixer.INSTANCE);
        checkLength(len, 65535);

    }
    public void setLength(int len)
    {
        checkLength(len, 65535);
        super.setLength(len);
    }

}
