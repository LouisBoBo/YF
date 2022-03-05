package com.yssj.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class CloseUtils {
	
	public CloseUtils(){}
	
	/**
	 * 关闭输入�?
	 * */
	public static void closeInputStream(InputStream... ins){
		if(ins != null && ins.length > 0){
			for(InputStream in : ins){
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 关闭输入通道
	 * */
	public static void closeInputChannel(FileChannel... inChannels){
		if(inChannels != null && inChannels.length > 0){
			for(FileChannel in : inChannels){
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 关闭输出�?
	 * */
	public static void closeOutputStream(OutputStream... ous){
		if(ous != null && ous.length > 0){
			for(OutputStream out : ous){
				if(out != null){
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 关闭输出通道
	 * */
	public static void closeOutputChannel(FileChannel... outChannels){
		if(outChannels != null && outChannels.length > 0){
			for(FileChannel out : outChannels){
				if(out != null){
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	
}
