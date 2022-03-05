package com.yssj.utils;

import java.util.HashMap;

import com.yssj.ui.activity.MainMenuActivity;


//传入用户的活力值powerCoount 返回用户等级   0普通   1青铜  2白银 3黄金
public class GetUserGrade {
	public static int getUserGrade(int powerCoount) {
		int userGrade;
		int rank1, rank2, rank3;// 活力值等级的分割点

		HashMap<String, String> map = MainMenuActivity.userGradeTable;
		try {
			rank1 = Integer.parseInt((String) (map.get("1")).split(",")[0].split("-")[0].trim());
			rank2 = Integer.parseInt((String) (map.get("2")).split(",")[0].split("-")[0].trim());
			rank3 = Integer.parseInt((String) (map.get("3")).split(",")[0].trim());
		} catch (Exception e) {
			rank1 = 40;
			rank2 = 100;
			rank3 = 300;
		}

		if (powerCoount < rank1) {// 普通会员

			userGrade = 0;
		} else if (powerCoount >= rank1 && powerCoount < rank2) {// 青铜会员
			userGrade = 1;
		} else if (powerCoount >= rank2 && powerCoount < rank3) {// 白银会员
			userGrade = 2;
		} else {// 黄金会员
			userGrade = 3;
		}

		return userGrade;

	}

}
