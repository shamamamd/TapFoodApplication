package com.tapfood.application.device.PinPad;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.tapfood.application.constant.Constant;
import com.tapfood.application.tapFood.shoppingList.ShoppingListActivity;
import com.topwise.cloudpos.aidl.pinpad.AidlPinpad;
import com.topwise.cloudpos.aidl.pinpad.GetPinListener;
import com.topwise.tapfood.R;

public class KeyBoard extends Dialog{//
	private static final byte[] DEFAULT_RANDOM = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09};
	private byte[] randNum;
	private TextView et_pin;
	private Window window;
	private Button[] btn = new Button[13];
	private Context context;
	private GetPinListener listener;
	private PinPad pinPad;
	private AidlPinpad pinpad;
	private Bundle param;
	public  TextView txtShowStar , price,txtPass;
	Typeface typeface;


	public KeyBoard(Context context, AidlPinpad pinpad, Bundle param, GetPinListener listener) {
		super(context, R.style.MyDialog);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.pinpad = pinpad;
		this.param = param;
		try {
			pinpad.setPinKeyboardMode(0);
			this.randNum = pinpad.getButtonNum();
		} catch (RemoteException e) {
			e.printStackTrace();
			randNum = DEFAULT_RANDOM;
		}
        this.listener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		View keyboardView = LayoutInflater.from(context).inflate(R.layout.dialog_custom_keyboard, null);
		setContentView(keyboardView);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        typeface=Typeface.createFromAsset(this.context.getAssets(),"fonts/iransans_mobile_bold.ttf");


		btn[0] = keyboardView.findViewById(R.id.btn0);
        btn[1] = keyboardView.findViewById(R.id.btn1);
        btn[2] = keyboardView.findViewById(R.id.btn2);
        btn[3] = keyboardView.findViewById(R.id.btn3);
        btn[4] = keyboardView.findViewById(R.id.btn4);
        btn[5] = keyboardView.findViewById(R.id.btn5);
        btn[6] = keyboardView.findViewById(R.id.btn6);
        btn[7] = keyboardView.findViewById(R.id.btn7);
        btn[8] = keyboardView.findViewById(R.id.btn8);
        btn[9] = keyboardView.findViewById(R.id.btn9);
        btn[10] =keyboardView.findViewById(R.id.btn_cancel);
        btn[11] =keyboardView.findViewById(R.id.btn_delete);
        btn[12] =keyboardView.findViewById(R.id.btn_enter);
        //set font
        btn[0].setTypeface(typeface);
        btn[1].setTypeface(typeface);
        btn[2].setTypeface(typeface);
        btn[3].setTypeface(typeface);
        btn[4].setTypeface(typeface);
        btn[5].setTypeface(typeface);
        btn[6].setTypeface(typeface);
        btn[7].setTypeface(typeface);
        btn[8].setTypeface(typeface);
        btn[9].setTypeface(typeface);
        btn[10].setTypeface(typeface);
        btn[11].setTypeface(typeface);
        btn[12].setTypeface(typeface);

        txtShowStar=findViewById(R.id.showPass);
        price=findViewById(R.id.salePriceFood);
        txtPass=findViewById(R.id.txtPass);

        txtShowStar.setTypeface(typeface);
        price.setTypeface(typeface);
        txtPass.setTypeface(typeface);

        price.setText(Constant.convertEnToFa( Constant.formatPrice(ShoppingListActivity.totalPriceAllFoods)+"  "+"تومان"));
        updateKeyBoard();
	}

	@Override
	public void show() {
		super.show();
		Log.d("caixh","show");
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus){

			Log.d("caixh","onWindowFocusChanged");
			param.putByteArray("pin_key_layout",getKeyLayout());
			new Thread() {
				public void run() {

					try {
						Log.d("caixh","getPin");
						pinpad.getPin(param, new GetPinListener.Stub(){

							@Override
							public void onInputKey(int i, String s) throws RemoteException {
								listener.onInputKey(i,s);
								pinHandler.obtainMessage(0x00, Constant.getStar(i)).sendToTarget();


							}

							@Override
							public void onError(int i) throws RemoteException {
								listener.onError(i);
								onClose();
							}

							@Override
							public void onConfirmInput(byte[] bytes) throws RemoteException {
								listener.onConfirmInput(bytes);
								onClose();
							}

							@Override
							public void onCancelKeyPress() throws RemoteException {
								listener.onCancelKeyPress();
								onClose();

							}

							@Override
							public void onStopGetPin() throws RemoteException {
								listener.onStopGetPin();
								onClose();
							}
						});
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.d("caixh","RemoteException");
						onClose();
					}
				}
			}.start();
		}
	}

	private byte[] getKeyLayout() {
		//int statusH = getStatusBarHeight();
		byte[] layouts = new byte[13*8];
		for(int i=0;i<13;i++) {
        	int[] location = new int[2];
        	btn[i].getLocationOnScreen(location);
			Log.d("caixh","location="+location[0]+","+location[1]);
			Log.d("caixh","w,h="+btn[i].getWidth()+","+btn[i].getHeight());
			layouts[8*i] = getHighByte(location[0]);
			layouts[8*i+1] = getLowByte(location[0]);
			layouts[8*i+2] = getHighByte(location[1]);//-statusH
			layouts[8*i+3] = getLowByte(location[1]);
			layouts[8*i+4] = getHighByte(location[0]+btn[i].getWidth());
			layouts[8*i+5] = getLowByte(location[0]+btn[i].getWidth());
			layouts[8*i+6] = getHighByte(location[1]+btn[i].getHeight());
			layouts[8*i+7] = getLowByte(location[1]+btn[i].getHeight());
		}
		return layouts;
	}

	private byte getHighByte(int value) {
		return (byte) ((value >> 8) & 0xff);
	}

	private byte getLowByte(int value) {
		return (byte) (value & 0xff);
	}

	private void updateKeyBoard(){
		Log.d("caixh","updateKeyBoard");
		for(int i=0;i<10;i++) {
			btn[i].setTag(i);
			btn[i].setText(Constant.convertEnToFa(String.valueOf(randNum[i]))+"");
		}
	}

	public void onShow(final int length) {
		// TODO Auto-generated method stub
		Handler handlerThree=new Handler(Looper.getMainLooper());
		handlerThree.post(new Runnable(){
            public void run(){
            	String star = "";
				for(int i = 0;i < length;i++) {
					star = star.concat("*");
				}

		    	txtShowStar.setText(star);
            }
        });

	}

	public  void onClose() {
		// TODO Auto-generated method stub
		Handler handlerThree=new Handler(Looper.getMainLooper());
        handlerThree.post(new Runnable(){
            public void run(){
            	dismiss();
            }
        });
	}



	private Handler pinHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.obj != null) {
				txtShowStar.setText(msg.obj.toString());
			}
		}
	};


}

