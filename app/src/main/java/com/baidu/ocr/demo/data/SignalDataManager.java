package com.baidu.ocr.demo.data;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.baidu.ocr.demo.util.PreferencesUtils;

import java.util.List;

/**
 * @author zhangchengju
 * 主要功能:
 * 创建日期 2018/9/11
 * 作者:longxian
 */
public class SignalDataManager {

	//每次拍照的原数据
	private static final String KEY_SOURCE_SIGN_DATA = "KEY_SOURCE_SIGN_DATA";

	//每次拍照后比上一次拍照新增的数据
	private static final String KEY_ADDED_SIGN_DATA = "KEY_ADDED_SIGN_DATA";

	//每次拍照的时间间隔
	private static final String KEY_SIGN_TIME_INETRVAL = "KEY_SIGN_TIME_INETRVAL";

	public static void saveSourceSignals(Context context, String resultJson) {
		SignalDataModel signalDataModel = JSON.parseObject(resultJson, SignalDataModel.class);
		if (signalDataModel != null) {
			PreferencesUtils.setObject(context, KEY_SOURCE_SIGN_DATA, signalDataModel);
		}
	}


	public static SignalDataModel readSourceSignals(Context context) {
		return PreferencesUtils.getObject(context, KEY_SOURCE_SIGN_DATA);
	}

	public static void saveLastAddedSignals(Context context, List<SignalDataModel.WordResult> newWordResults) {
		if (newWordResults != null && newWordResults.size() != 0) {
			PreferencesUtils.setObject(context, KEY_ADDED_SIGN_DATA, newWordResults);
		}
	}

	public static List<SignalDataModel.WordResult> readLastAddedSignals(Context context) {
		return PreferencesUtils.getObject(context, KEY_ADDED_SIGN_DATA);
	}

	public static void savaTimeInterval(Context context, String string) {

		try {
			int interval = Integer.valueOf(string);
			PreferencesUtils.putInt(context, KEY_SIGN_TIME_INETRVAL, interval);
		} catch (Exception e) {

		}
	}

	public static int getTimeInterval(Context context) {
		return PreferencesUtils.getInt(context, KEY_SIGN_TIME_INETRVAL, 30);
	}
}
