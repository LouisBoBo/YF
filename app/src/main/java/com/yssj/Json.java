package com.yssj;



public interface Json {
	String token = "token";
	String message = "message";
	String status = "status";
	/*
	 * ================================ 用户 ================================
	 */
	String userinfo = "userinfo";

	public interface JUserInfo {
		
		 String user_id = "user_id";// N Integer 11 主键 自动生成
		 String phone = "phone";// Y String 20 手机号码
		 String nickname = "nickname";// Y String 50 昵称
		 String email = "email";
		 String user_type = "user_type";
		//--------------------------------------------新加
		 String city = "city";	//Y	Integer		城市编号	关联areatbl 
		 String user_name = "user_name";	//N	String	50	真实姓名	
		 String user_ident	= "user_ident";//Y	String	25	身份证	
		// String home_address = "home_address";	//Y	String	255	家庭地址	
		// String occupation	= "occupation";//Y	String	20	职业	
		 String age = "age";	//Y	Integer		年龄	
		 String gender = "gender";	//Y	Int	1	性别	（0=保密，1=男，2=女）
		 String remarks = "remarks";	//Y	String		备注	
		 String birthday = "birthday";	//Y	Date		生日	
		 String pic = "pic";	//Y	String	200	用户头像	
		 String account = "account";	//Y	Integer		城市编号	关联areatbl 
		 
		String email_status = "email_status";
		String street = "street";//街道
		String area = "area";
		String province = "province";

		String add_date ="add_date";
			
	}
	/** 返回信息 **/
	String retInfo = "ReturnInfo";
	public interface RetInfo{
		String status = "status";
		String message = "message";
		String pwdflag = "pwdflag";
		String num = "num";
	}
	
	
	
	
	String loginreturn = "loginreturn";
	public interface LoginReturn{
		String token = "token";
		String account = "account";
	}
}