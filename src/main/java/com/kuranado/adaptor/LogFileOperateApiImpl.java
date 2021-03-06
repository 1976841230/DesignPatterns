package com.kuranado.adaptor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 *
 * @Author: Xinling Jing
 * @Date: 2018-12-23 19:10
 */
public class LogFileOperateApiImpl implements LogFileOperateApi {

	// 默认的日志路径
	private String logFilePathName = "/Users/jing/Code/GitHub/DesignPatterns/src/main/resources/AdaptorLog.log";

	public LogFileOperateApiImpl(String logFilePathName) {
		if (logFilePathName != null && logFilePathName.trim().length() > 0) {
			this.logFilePathName = logFilePathName;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LogModel> readLogFile() {
		File file;
		ObjectInputStream objectInputStream = null;
		List<LogModel> logModels = null;
		try {
			file = new File(logFilePathName);
			if (file.exists()) {
				InputStream inputStream = new FileInputStream(file);
				if (inputStream.available() != 0) {
					objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
					logModels = (List<LogModel>) objectInputStream.readObject();
				} else {
					return null;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (objectInputStream != null) {
				try {
					objectInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return logModels;
	}

	@Override
	public void writeLogFile(List<LogModel> logModels) {
		ObjectOutputStream objectOutputStream = null;
		File file;
		try {
			file = new File(logFilePathName);
			objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			objectOutputStream.writeObject(logModels);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
