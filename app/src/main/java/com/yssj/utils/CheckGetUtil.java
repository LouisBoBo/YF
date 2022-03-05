package com.yssj.utils;
/**
 * */
public class CheckGetUtil
{
	public static int[] getLine(int height, int width)
	{
		int [] tempCheckNum = {0, 0 ,0, 0, 0};
		for(int i = 0; i < 4; i+=2)
			{
			tempCheckNum[i] = (int) (Math.random() * width);
			tempCheckNum[i + 1] = (int) (Math.random() * height);
			}
		return tempCheckNum;
	}
	
	public static int[] getPoint(int height, int width)
	{
		int [] tempCheckNum = {0, 0, 0, 0, 0};
		tempCheckNum[0] = (int) (Math.random() * width);
		tempCheckNum[1] = (int) (Math.random() * height);
		return tempCheckNum;
	}
}
