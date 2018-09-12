package com.baidu.ocr.demo.biz;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.ocr.demo.MessageManager;
import com.baidu.ocr.demo.task.AlarmTaskManager;
import com.baidu.ocr.demo.task.TaskEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author zhangchengju
 * 主要功能:
 * 创建日期 2018/9/10
 * 作者:longxian
 */
public class BackgroundService extends Service {
	//处理intent消息
	public static final int MSG_HANDLE_INTENT = 1000;
	//定时任务闹钟时间到了
	public static final String ACTION_ALARM_TASK_TIME_OVER = "ACTION_ALARM_TASK_TIME_OVER";

	//定时任务闹钟时间到了的ID
	public static final String EXTRA_ALARM_TASK_TIME_OVER_ID = "EXTRA_ALARM_TASK_TIME_OVER_ID";

	private ServiceHandler mServiceHandler;
	private Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		mServiceHandler = new ServiceHandler();
	}


	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (null != intent && null != intent.getAction()) {
			//消息处理
			Message msg = mServiceHandler.obtainMessage();
			msg.what = MSG_HANDLE_INTENT;
			msg.arg1 = startId;
			msg.obj = intent;
			mServiceHandler.sendMessage(msg);
		}
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private class ServiceHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			//处理其他模块转发的消息
			if (msg.what != MSG_HANDLE_INTENT) {
				return;
			}
			if (msg.obj instanceof Intent) {
				Intent intent = (Intent) msg.obj;
				String action = intent.getAction();
				if (ACTION_ALARM_TASK_TIME_OVER.equals(action)) {
					int task = intent.getIntExtra(EXTRA_ALARM_TASK_TIME_OVER_ID, -1);
					if (task == AlarmTaskManager.TASK_TEN_MIN) {
						//
						Log.d("juju", "定时任务");
						MessageManager.getInstance().refreshNewOrder();
						EventBus.getDefault().post(new TaskEvent());
					}
				}
			}
		}
	}

}
