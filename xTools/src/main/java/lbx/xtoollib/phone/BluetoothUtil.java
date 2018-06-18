package lbx.xtoollib.phone;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import lbx.xtoollib.XTools;


/**
 * @author lbx
 * @date 2016/12/19.
 */

public class BluetoothUtil {

    private static BluetoothUtil bluetoothUtil;
    private boolean isConnected;
    private BluetoothSocket socket;
    public BluetoothAdapter adapter;
    private boolean isRegisterMessageGet;

    private BluetoothUtil() {
    }

    public static BluetoothUtil getInstance() {
        if (bluetoothUtil == null) {
            synchronized (BluetoothUtil.class) {
                if (bluetoothUtil == null) {
                    bluetoothUtil = new BluetoothUtil();
                }
            }
        }
        return bluetoothUtil;
    }

    public void search(Context context) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (!adapter.isEnabled()) {
            adapter.enable();
        }
        Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        //300为蓝牙设备可见时间
        enable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        context.startActivity(enable);
        //查找蓝牙设备
        adapter.startDiscovery();
    }

    public void connect(BluetoothDevice device) throws IOException {
        // 固定的UUID
        final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
        UUID uuid = UUID.fromString(SPP_UUID);
        socket = device.createRfcommSocketToServiceRecord(uuid);
        socket.connect();
        isConnected = socket.isConnected();
    }

    public void send(final String message) {
        XTools.AppUtil().runOnOtherThread(new Runnable() {
            @Override
            public void run() {
                sendMessage(message);
            }
        });
    }

    public void registerMsgRecord(final Handler handler) {
        if (isRegisterMessageGet) {
            return;
        }
        XTools.AppUtil().runOnOtherThread(new Runnable() {
            @Override
            public void run() {
                isRegisterMessageGet = true;
                getMessage(handler);
            }
        });
    }

    private void sendMessage(String message) {
        if (isConnected) {
            try {
                OutputStream outStream = socket.getOutputStream();
                outStream.write(XTools.MathUtil().hexStringToByte(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getMessage(Handler handler) {
        while (true) {
            SystemClock.sleep(10);
            try {
                String message = "";
                byte b[] = new byte[1];
                InputStream inputStream = socket.getInputStream();
                int line;
                while ((line = inputStream.read(b)) != -1) {
                    message += XTools.MathUtil().bytesToHexString(b);
                }
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                Message msg = handler.obtainMessage();
                msg.what = 0x011;
                msg.obj = message;
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//    class BluetoothReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                String name = device.getName();
//                String address = device.getAddress();
//                xLogUtil.e("name = " + name + "   address = " + address);
//            }
//        }
//    }

//    private void initBroadcast() {
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(BluetoothDevice.ACTION_FOUND);
//        receiver = new MainActivity.BluetoothReceiver();
//        registerReceiver(receiver, filter);
//    }
}
