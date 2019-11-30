package com.tapfood.application.device;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.topwise.cloudpos.aidl.AidlDeviceService;
import java.util.List;

public abstract class BaseDeviceActivity extends AppCompatActivity {


	private static final String TAG = "sha-BaseDeviceActivity";
	public static final String TOPWISE_SERVICE_ACTION = "topwise_cloudpos_device_service";
	private long oldTime = -1;
	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder serviceBinder) {
			Log.d(TAG, "aidlService");
			if (serviceBinder != null) {
				AidlDeviceService serviceManager = AidlDeviceService.Stub.asInterface(serviceBinder);
				onDeviceConnected(serviceManager);
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d(TAG, "AidlService");
		}
	};
	public void bindService() {
		Intent intent = new Intent();
		intent.setAction(TOPWISE_SERVICE_ACTION);
		final Intent eintent = new Intent(createExplicitFromImplicitIntent(this, intent));
		boolean flag = bindService(eintent, conn, Context.BIND_AUTO_CREATE);
		if (flag) {
			Log.d(TAG, "sss");
		} else {
			Log.d(TAG, "ss");
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		bindService();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		oldTime = -1;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.unbindService(conn);
	}
	public abstract void onDeviceConnected(AidlDeviceService serviceManager);
	public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
		// Retrieve all services that can match the given intent
		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

		// Make sure only one match was found
		if (resolveInfo == null || resolveInfo.size() != 1) {
			return null;
		}

		// Get component info and create ComponentName
		ResolveInfo serviceInfo = resolveInfo.get(0);
		String packageName = serviceInfo.serviceInfo.packageName;
		String className = serviceInfo.serviceInfo.name;
		ComponentName component = new ComponentName(packageName, className);

		// Create a new intent. Use the old one for extras and such reuse
		Intent explicitIntent = new Intent(implicitIntent);

		// Set the component to be explicit
		explicitIntent.setComponent(component);

		return explicitIntent;
	}
}
