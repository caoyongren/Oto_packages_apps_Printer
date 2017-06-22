package com.oto_packages_apps_printer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;

import com.oto_packages_apps_printer.APP;
import com.oto_packages_apps_printer.service.OpenthosPrintService;
import com.oto_packages_apps_printer.ui.ManagementActivity;
import com.oto_packages_apps_printer.util.LogUtils;


public class UsbPrinterReceiver extends BroadcastReceiver {
    private static final String TAG = "UsbPrinterReceiver";

    public UsbPrinterReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtils.d(TAG, "intent.getAction() ->" + action);

        if( action.equals(UsbManager.ACTION_USB_DEVICE_ATTACHED) || action.equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {

            if(APP.MANAGEMENT_ACTIVITY_ON_TOP) {
                Intent intent1 = new Intent(APP.getApplicatioContext(), ManagementActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra(APP.TASK, APP.TASK_ADD_NEW_PRINTER);
                APP.getApplicatioContext().startActivity(intent1);
            } else {
                Intent new_intent = new Intent(APP.getApplicatioContext(), OpenthosPrintService.class);
                new_intent.putExtra(APP.TASK, APP.TASK_DETECT_USB_PRINTER);
                APP.getApplicatioContext().startService(new_intent);
            }
        }
    }
}
