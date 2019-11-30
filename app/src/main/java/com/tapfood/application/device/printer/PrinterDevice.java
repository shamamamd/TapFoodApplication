package com.tapfood.application.device.printer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;

import com.tapfood.application.constant.Constant;
import com.tapfood.application.device.BaseDeviceActivity;
import com.tapfood.application.device.card.SwipeCard;
import com.tapfood.application.tapFood.shoppingList.ShoppingListActivity;
import com.topwise.cloudpos.aidl.AidlDeviceService;
import com.topwise.cloudpos.aidl.printer.AidlPrinter;
import com.topwise.cloudpos.aidl.printer.AidlPrinterListener;
import com.topwise.cloudpos.aidl.printer.Align;
import com.topwise.cloudpos.aidl.printer.PrintTemplate;
import com.topwise.cloudpos.aidl.printer.TextUnit;
import com.topwise.cloudpos.data.PrinterConstant;
import com.topwise.tapfood.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrinterDevice extends BaseDeviceActivity {

    public static final String TAG = "Message Printer";
    private AidlPrinter printerDev = null;


    public final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));


    Context context;

    public PrinterDevice(Context context) {
        this.context = context;

    }

    @Override
    public void onDeviceConnected(AidlDeviceService serviceManager) {

        try {
            printerDev = AidlPrinter.Stub.asInterface(serviceManager.getPrinter());
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public void print() {

        try {

            String time = Constant.getCurTime();
            String date = Constant.getCurDate();

            PrintTemplate template = new PrintTemplate(context, SwipeCard.typeface);


           /* Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sssss);
            printerDev.addRuiImage(bitmap, 0)*/
            ;

            template.add(new TextUnit(" فوت کورد کوروش ", PrinterConstant.FontSize.XLARGE, Align.CENTER));
            template.add(1, new TextUnit(Constant.convertEnToFa("02155889966"), PrinterConstant.FontSize.LARGE, Align.LEFT),
                    1, new TextUnit(" شماره تلفن ", PrinterConstant.FontSize.LARGE, Align.RIGHT));
            template.add(1, new TextUnit(Constant.convertEnToFa(time), PrinterConstant.FontSize.LARGE, Align.LEFT),
                    1, new TextUnit(Constant.convertEnToFa(date), PrinterConstant.FontSize.LARGE, Align.RIGHT));

            template.add(new TextUnit("--------------------------------------"));

            template.add(new TextUnit(" شماره سفارش  " + Constant.convertEnToFa("85"), PrinterConstant.FontSize.XLARGE, Align.CENTER));

            template.add(1, new TextUnit(Constant.convertEnToFa("قیمت"), PrinterConstant.FontSize.LARGE, Align.LEFT),
                    1, new TextUnit("فی", PrinterConstant.FontSize.LARGE, Align.CENTER),
                    1, new TextUnit(" نام غذا ", PrinterConstant.FontSize.LARGE, Align.RIGHT));
            for (int i = 0; i < ShoppingListActivity.selectedFood.size(); i++) {

                template.add(1, new TextUnit(Constant.convertEnToFa(Constant.formatPrice(Integer.parseInt
                                (String.valueOf(Integer.parseInt(ShoppingListActivity.selectedFood.get(i).getSalePriceFood()) * ShoppingListActivity.selectedFood.get(i).getNumberFood())))) + " تومان ", PrinterConstant.FontSize.LARGE, Align.LEFT),
                        1, new TextUnit(Constant.convertEnToFa(String.valueOf(ShoppingListActivity.selectedFood.get(i).getNumberFood())), PrinterConstant.FontSize.LARGE, Align.CENTER),
                        1, new TextUnit(ShoppingListActivity.selectedFood.get(i).getNameFood(), PrinterConstant.FontSize.LARGE, Align.RIGHT));
            }

            template.add(new TextUnit("--------------------------------------"));

            template.add(new TextUnit(" رسید مشتری ", PrinterConstant.FontSize.XLARGE, Align.CENTER));
            template.add(1, new TextUnit(Constant.convertEnToFa("**0614"), PrinterConstant.FontSize.LARGE, Align.LEFT),
                    1, new TextUnit(" بانک پاسارگاد ", PrinterConstant.FontSize.LARGE, Align.RIGHT));
            template.add(1, new TextUnit(Constant.convertEnToFa("1690720"), PrinterConstant.FontSize.LARGE, Align.LEFT),
                    1, new TextUnit(" پایانه ", PrinterConstant.FontSize.LARGE, Align.RIGHT));
            template.add(1, new TextUnit(Constant.convertEnToFa("141509"), PrinterConstant.FontSize.LARGE, Align.LEFT),
                    1, new TextUnit(" پیگیری ", PrinterConstant.FontSize.LARGE, Align.RIGHT));
            template.add(1, new TextUnit(Constant.convertEnToFa("156089141509"), PrinterConstant.FontSize.LARGE, Align.LEFT),
                    1, new TextUnit(" مرجع ", PrinterConstant.FontSize.LARGE, Align.RIGHT));

            template.add(new TextUnit("--------------------------------------"));

            template.add(1, new TextUnit(Constant.convertEnToFa(Constant.formatPrice(ShoppingListActivity.totalPriceAllFoods)) + " تومان ", PrinterConstant.FontSize.LARGE, Align.LEFT),
                    1, new TextUnit(" مبلغ ", PrinterConstant.FontSize.LARGE, Align.RIGHT));
            printerDev.addRuiImage(template.getPrintBitmap(), 0);
            //  printerDev.addRuiImage(bitmap, 0);
            printerDev.printRuiQueue(mListen);

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    AidlPrinterListener mListen = new AidlPrinterListener.Stub() {
        @Override
        public void onError(int i) throws RemoteException {
            Log.i(TAG, "print_error_code" + i);
        }

        @Override
        public void onPrintFinish() throws RemoteException {
            String endTime = Constant.getCurTime();
            Log.i(TAG, "print_end_time" + endTime);

        }
    };


}
