package com.yssj.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {


    public static void copyString(Context context, String string) {

        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", string);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);

    }


    /****
     * 判断是否要更新
     * @param formerVersion 服务器上版本号
     * @param currVersion  当前版本号
     * @return
     */
    public static boolean isDownload(String formerVersion, String currVersion) {
        formerVersion = formerVersion.replace("v", "");
        currVersion = currVersion.replace("v", "");
        String[] fs = formerVersion.split("\\.");
        String[] cs = currVersion.split("\\.");
        for (int i = 0; i < fs.length; i++) {
            if (Integer.valueOf(fs[i]) > Integer.valueOf(cs[i])) {
                return true;
            } else if (Integer.valueOf(fs[i]) < Integer.valueOf(cs[i])) {
                return false;
            }
        }
        return false;
    }

    public static void initShareText(TextView tv1, TextView tv2, TextView tv3, TextView tv4) {

//		String textStr1 = "本月已成功邀请 <font color=\"#FF0000\">" + 100 + "</font>人";
        tv1.getPaint().setFakeBoldText(true);
        tv1.setLineSpacing(0, 1.3f);
        tv2.setLineSpacing(0, 1.3f);
        tv3.setLineSpacing(0, 1.3f);
        tv4.setLineSpacing(0, 1.3f);


        String textStr2 = "<b>2.好友每消费一次。你可得<font color=#FF0000>2元提现现金</font>奖励。邀请越多奖励越多。</b>";
        tv2.setText(Html.fromHtml(textStr2));

        String textStr3 = "<b>3.分享到3个以上微信群，成功几率<font color=#FF0000>提升200%</font>。</b>";
        tv3.setText(Html.fromHtml(textStr3));

        String textStr4 = "<b>4.可以告诉你的好友，注册即可领<font color=#FF0000>18元任务红包</font>，可微信提现哦。</b>";
        tv4.setText(Html.fromHtml(textStr4));


    }


    /**
     * is null or its length is 0
     *
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0, return true, else return false.
     */
    public static boolean isEmpty(String str) {
        return (null == str || str.length() == 0);
    }

    /***
     * 判断手机格式
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;

		/*String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		CharSequence inputStr = phoneNumber;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}*/

        if (phoneNumber.length() == 11) {
            isValid = true;
        }

        return isValid;
    }

    /***
     * 判断用户id格式
     */
    public static boolean isRefereeValid(String referee) {
        boolean isValid = false;

        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(referee);

        if ((referee.length() < 10 || referee.length() == 10) && m.matches()) {
            isValid = true;
        }

        return isValid;
    }

    /***
     * 判断邮箱格式
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }
    /***
     * 得到距离
     * 打折时间的毫秒数
     * @return
     */
//	public static long getNowTime(long discount_time){
//		Date dt= new Date();
//		Long time= dt.getTime();//这就是距离1970年1月1日0点0分0秒的毫秒数
//		time = discount_time - time;
//		return time;
//	}

    /***
     * 毫秒转换为日期
     * @param time
     * @return
     */
    public static String timeToDate(long time) {
        Date d = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);

    }

    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }

        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    public static String getShareContent(String nickName) {
//    	final String[] shareTitle= new String[]{
//    			"千件美衣一折起","扫进来，下一个仙女就是你","全场包邮，一折起","进来看看，总有一件你想要的","时尚girl的穿衣手册/指南",
//    			"便宜得要命，更多时尚爆款等你来淘","美的人已经进来了，你还在犹豫什么","不当吃瓜群众，得了便宜还能赚钱","买前必看，今年最时尚的美衣都在这","爱要存在，只给你推荐最好的",
//    			"吃下这枚安利——《撩汉必备穿衣法则》","美衣已到货，拆开就能穿上啦！","无可取代，风情万种只为你","想变美，这里有更多选择","让你一秒变女神的神器","甜美升级，工作撩汉两不误",
//    			nickName+"不想说话，并向你丢了一堆美衣","幸福的烦恼，万件美衣任你挑","一言不合就开车，老司机带你打开时尚大门","靠搭配，没有黄金比例也迷人","拒绝吃土，百元美衣任你挑"
//    			};
//    	final String[] shareTitle= new String[]{
//    			"时尚不贵，一件到位","衣柜里不能没有这件衣服","教你正确地打开时尚大门","看看最近潮人们都在穿什么","GET到重点了，这么穿很时髦",
//    			"好身材，都是搭配出来的","对，你就少这一件","穿对了，你就是女王","时尚搭配，好看不贵","你不能错过的经典单品",
//    			"这些单品省去搭配烦恼","平价也有大牌范","一秒变时尚女王","原来这样才叫会穿","轻松穿出完美身材","这件衣服让你穿搭不失手",
//    			"不推荐这件衣服，良心会痛","可以时尚，还可以穿得上","不犯同样的错，便宜也有好货","好的穿搭，让你时尚感爆棚 "
//    	};
        final String[] shareTitle = new String[]{
                "你没有看错，Zara专柜当季新款29元包邮!", "MANGO一线大牌，呆萌价29元起！仅限2天！", "买衣预算不够？VERO MODA,H&M等一线大牌疯狂折扣29包邮！",
                "不要花高价乱买衣服了，30元即可买到GAP，欧时力等一线大牌，快来！", "比去专柜买便宜多了！H&M时尚单品限时特惠29元！",
                "价格低到没朋友！优衣库女装仅售25元！", "谁说大牌就一定贵！Forever21特价26元包邮！", "GAP大牌女装新品发售，最低24元包邮！"
        };
        int x = (int) (Math.random() * shareTitle.length);
        return shareTitle[x];
    }

    public static String getShareContentNew() {
//    	final String[] shareTitle= new String[]{
//    			"时尚不贵，一件到位","衣柜里不能没有这件衣服","教你正确地打开时尚大门","看看最近潮人们都在穿什么","GET到重点了，这么穿很时髦",
//    			"好身材，都是搭配出来的","对，你就少这一件","穿对了，你就是女王","时尚搭配，好看不贵","你不能错过的经典单品",
//    			"这些单品省去搭配烦恼","平价也有大牌范","一秒变时尚女王","原来这样才叫会穿","轻松穿出完美身材","这件衣服让你穿搭不失手",
//    			"不推荐这件衣服，良心会痛","可以时尚，还可以穿得上","不犯同样的错，便宜也有好货","好的穿搭，让你时尚感爆棚 "
//    	};
        final String[] shareTitle = new String[]{
                "你没有看错，Zara专柜当季新款29元包邮!", "MANGO一线大牌，呆萌价29元起！仅限2天！", "买衣预算不够？VERO MODA,H&M等一线大牌疯狂折扣29包邮！",
                "不要花高价乱买衣服了，30元即可买到GAP，欧时力等一线大牌，快来！", "比去专柜买便宜多了！H&M时尚单品限时特惠29元！",
                "价格低到没朋友！优衣库女装仅售25元！", "谁说大牌就一定贵！Forever21特价26元包邮！", "GAP大牌女装新品发售，最低24元包邮！"
        };
        int x = (int) (Math.random() * shareTitle.length);
        return shareTitle[x];
    }


    public static char getVirtualName() {
        char virtualName[] = {'一', '娅', '清', '帆', '娇', '娉', '嘉', '三', '昊', '萍', '」', '明', '小', '成', '树', '向', '舒', '专', '尔', '昕', '世', '刖', '列', '则', '√', '君', '娜', '东', '思', '丞', '星', '娟', ' ', '映', '怡', '倡', '娣', '尤', '春', '娥', '营', '倩', '琬', '帮', '爰', '昱', '爱', '琳', '琴', '水', '永', '常', '丹', '琼', '丽', '鸾', '鸿', '桂', '际', '陈', '婉', '义', '汉', '晋', '晓', '塔', '展', '晖', '湘', '瑛', '瑜', '瑞', '江', '鹤', '书', '婧', '豪', '恬', '景', '驰', '葱', '艳', '平', '晴', '婵', '灵', '瑶', '晶', '婷', '幸', '艺', '智', '恺', '瑾', '艾', '承', '沁', '沂', '梅', '隅', '庆', '璇', '钊', '炎', '于', '云', '梓', '应', '颖', '暖', '肖', '亚', '媛', '炜', '芝', '粟', '钟', '红', '梦', '悦', '纪', '芬', '京', '庭', '纯', '钰', '花', '育', '芳', '芷', '芸', '芹', '人', '品', '雄', '雅', '蓉', '惋', '立', '苏', '经', '苑', '泓', '蓓', '裕', '高', '胜', '惠', '棠', '章', '滢', '竣', '嫣', '令', '勤', '若', '曦', '嫦', '继', '雨', '雪', '仪', '绪', '绫', '铭', '园', '雯', '英', '哲', '裴', '维', '银', '瓶', '曹', '建', '曼', '国', '泽', '曾', '开', '洁', '崇', '月', '有', '愉', '紊', '锐', '蔓', '夕', '优', '会', '茜', '贝', '霞', '伟', '生', '张', '素', '蔡', '欣', '贤', '津', '锦', '大', '木', '天', '太', '紫', '甫', '振', '焱', '洲', '茹', '政', '儿', '千', '商', '歆', '荇', '午', '光', '煊', '腊', '歌', '华', '李', '敏', '子', '齐', '青', '卓', '祖', '静', '楚', '奚', '赛', '潜', '～', '束', '正', '荣', '彤', '彦', '学', '慧', '照', '杨', '浩', '彩', '轩', '八', '杭', '杰', '兰', '筱', '影', '佳', '卷', '海', '蝾', '长', '睿', '卿', '宁', '如', '妃', '善', '超', '文', '宇', '薇', '莉', '玉', '安', '冉', '妍', '美', '薏', '榕', '林', '龙', '熙', '宝', '依', '玟', '涟', '冠', '宣', '群', '厦', '润', '馨', '冬', '玮', '斯', '新', '冰', '辰', '玲', '涵', '家', '德', '妹', '莹', '方', '羽', '禾', '秀', '菁', '珂', '心', '淇', '采', '秉', '菊', '珊', '俊', '友', '秋', '富', '凌', '翎', '柏', '金', '科', '淑', '燕', '志', '诗', '姗', '进', '姝', '保', '连', '俞', '翠', '忠', '凤', '知', '旦', '巧', '韧', '旭', '可', '柯', '路', '翰', '石', '诺', '淼',
                'a', '0', 'A', 'b', 'B', 'c', 'C', 'd', 'D', '1', 'e', 'E', 'f', 'F', 'g', 'G', '2', 'h', 'H', 'i', 'I', 'j', '3', 'J', 'k', 'K', 'l', 'L', '4', 'm', 'M', 'n', 'N', '5', 'o', 'O', 'p', 'P', '6', 'q', 'Q', 'r', 'R', '7', 's', 'S', 't', '8', 'T', 'u', 'U', 'v', 'V', 'w', 'W', 'x', '9', 'X', 'y', 'Y', 'z', 'Z'};
        int x = (int) (Math.random() * virtualName.length);
        return virtualName[x];
    }

    public static String[]  getSignJLlistItemTaskCount() {
        String[] signNum = {"52", "67", "83", "72", "55", "59", "92", "102", "33", "61", "39", "65", "76", "73", "53", "83", "92", "87", "95", "112", "101", "93", "121", "117", "105", "35", "58", "62", "55", "71"};
        return signNum;
    }

    public static String[]  getSignJLlistItemTaskMoney() {
        String[] signMoney = {"92.56", "119.26", "147.74", "128.16", "97.9", "105.02", "163.76", "181.56", "58.74", "108.58", "69.42", "115.7", "135.28", "129.94", "94.34", "147.74", "163.76", "154.86", "169.1", "199.36", "179.78", "165.54", "215.38", "208.26", "186.9", "62.3", "103.24", "110.36", "97.9", "126.38"};
        return signMoney;
    }





    public static int getVirtualAwards() {
        int virtualAwards[] = {2, 4, 7, 10, 12, 15, 18, 20, 23, 28, 30, 32, 35, 37, 40, 45, 53, 65, 78, 80, 90};
        int x = (int) (Math.random() * virtualAwards.length);
        return virtualAwards[x];
    }

    public static int getCiriCount() {
        int virtualAwards[] = {15, 16, 17, 18, 19};
        int x = (int) (Math.random() * virtualAwards.length);
        return virtualAwards[x];
    }

    public static int getCiriMoney() {
        int virtualAwards[] = {25, 30, 35, 40, 45, 50};
        int x = (int) (Math.random() * virtualAwards.length);
        return virtualAwards[x];
    }

    //获取范围内的随机整数
    public static int  getRandomInt(int min, int max){
        if (max < min) {
            return min;
        }
        int num = (int) (Math.random() * (max - min)) + min;
        return num;
    }


    public static String getVirtualDecimalAwards() {
        String virtualAwards[] = {".05", ".10", ".15", ".20", ".25", ".30", ".35", ".40", ".45", ".50", ".55", ".60", ".65", ".70", ".75", ".80", ".85", ".90", ".95"};
        int x = (int) (Math.random() * virtualAwards.length);
        return virtualAwards[x];
    }

    public static int getVirtualIntegerAwards() {
        int x = (int) (Math.random() * (100 - 20) + 20);
        return x;
    }

    /**
     * 生成获得额度的随机数（小数 ）
     *
     * @return
     */
    public static double getVirtualDoubleEduAwards() {
        double x = (Math.random() * (6000 - 200) + 200) / 100;
        return x;
    }

    /**
     * 提现额度随机小数
     */
    public static String getVirtualDecimalAwardsWithdrawal() {
        String virtualAwards[] = {".55", ".56", ".57", ".58", ".59", ".65", ".66", ".67", ".68", ".69", ".75", ".76", ".77",
                ".78", ".79", ".85", ".86", ".87", ".88", ".89", ".95", ".96", ".97", ".98", ".99"};
        int x = (int) (Math.random() * virtualAwards.length);
        return virtualAwards[x];
    }

    //自动生成评论的默认图片
    public static String getDefaultImg() {
        String defaultImgArray[] = {
                "00e93901213fb80ed4129e1e33d12f2eb838943c.jpg",
                "0b55b319ebc4b745639a5b76cbfc1e178a821547.jpg",
                "0df431adcbef7609ed9165562bdda3cc7dd99e65.jpg",
                "1-140Z51032590-L.jpg",
                "1-15022PZ1030-L.jpg",
                "1-15022Q022050-L.jpg",
                "1-15041G419410-L.jpg",
                "1-15041G426280-L.jpg",
                "1-15061Z641250-L.jpg",
                "1-1505291Z2380-L.jpg",
                "1-1506161F4450-L.jpg",
                "1-1506161KU80-L.jpg",
                "1-150523100Q40-L.jpg",
                "1-1502061115170-L.jpg",
                "1-1502150950440-L.jpg",
                "1-1502152046340-L.jpg",
                "1-1502152119120-L.jpg",
                "1-1504121015250-L.jpg",
                "1-1505231004350-L.jpg",
                "1-1506141435450-L.jpg",
                "1-1512211535350-L.jpg",
                "1-1602031119350-L.jpg",
                "1b4c510fd9f9d72a1f3deed4d12a2834359bbb9a.jpg",
                "2_0Z31605562464.jpg",
                "2_091Q61202Z20.jpg",
                "2_01261A30Sc1.jpg",
                "2-150Q61646440-L.jpg",
                "2-14100QIZ30-L.jpg",
                "2-14110Q349580-L.jpg",
                "2-14112G13G00-L.jpg",
                "2-14112Q434520-L.jpg",
                "2-15012Q504000-L.jpg",
                "2-1312041AF10-L.jpg",
                "2-1409151K0170-L.jpg",
                "2-1409231I2590-L.jpg",
                "2-1412221P3260-L.jpg",
                "2-1501221HK10-L.jpg",
                "2-1501231A0120-L.jpg",
                "2-1503161KP20-L.jpg",
                "2-1505091H3080-L.jpg",
                "2-140914113K20-L.jpg",
                "2-140915111P20-L.jpg",
                "2-140922155R40-L.jpg",
                "2-150120130G60-L.jpg",
                "2-1409131036390-L.jpg",
                "2-1409261551460-L.jpg",
                "2-1411121444100-L.jpg",
                "2-1411221535020-L.jpg",
                "2-1411231045150-L.jpg",
                "2-1412012001430-L.jpg",
                "2-1412031916190-L.jpg",
                "2-1412061040490-L.jpg",
                "2-1501061329220-L.jpg",
                "2-1501091649430-L.jpg",
                "2-1503021949330-L.jpg",
                "2-1505291513240-L.jpg",
                "2-1510301500420-L.jpg",
                "2f738bd4b31c8701a0db8b10217f9e2f0608ffd2.jpg",
                "4a36acaf2edda3cc1a9b81d607e93901203f92cc.jpg",
                "4afbfbedab64034f4d744cacaac379310b551dda.jpg",
                "4afbfbedab64034f9474f3e1aac379310b551d9f.jpg",
                "4b90f603738da977106a694bb451f8198618e30d.jpg",
                "5bafa40f4bfbfbed8ccfc4b67df0f736aec31f60.jpg",
                "6a600c338744ebf8911eea95dcf9d72a6059a729.jpg",
                "6a600c338744ebf834504828ddf9d72a6059a787.jpg",
                "6c224f4a20a446233bb294859d22720e0df3d7d8.jpg",
                "6f061d950a7b02086a1f011764d9f2d3562cc875.jpg",
                "08f790529822720efd419ded7fcb0a46f21fab1e.jpg",
                "8b13632762d0f703ccd96b150dfa513d2797c594.jpg",
                "8c1001e93901213fed475fb450e736d12f2e9565.jpg",
                "9d82d158ccbf6c81214e1d0fb93eb13532fa40df.jpg",
                "9e3df8dcd100baa10cb08f5f4110b912c9fc2eb2.jpg",
                "9e3df8dcd100baa176ef765c4210b912c9fc2e11.jpg",
                "9e3df8dcd100baa13540b5554210b912c9fc2ef4.jpg",
                "9f2f070828381f30feff454daf014c086f06f074.jpg",
                "9f2f070828381f301b779cc1ad014c086e06f068.jpg",
                "9f510fb30f2442a70f77c453d743ad4bd01302ce.jpg",
                "32fa828ba61ea8d378b169e6920a304e241f5844.jpg",
                "35a85edf8db1cb1397c8b5b9d854564e93584bf3.jpg",
                "35a85edf8db1cb1398f8c0dad854564e93584b62.jpg",
                "55e736d12f2eb938ea1d4304d3628535e4dd6fd7.jpg",
                "72f082025aafa40f58e521bdaf64034f78f019aa.jpg",
                "72f082025aafa40f8118d8b5af64034f79f019ff.jpg",
                "95eef01f3a292df5658e95e3ba315c6035a873f2.jpg",
                "241f95cad1c8a78679af7ae96209c93d71cf504b.jpg",
                "342ac65c10385343abccbed79513b07ecb8088c0.jpg",
                "359b033b5bb5c9ea5b4b3204d039b6003bf3b398.jpg",
                "810a19d8bc3eb135f794676ba31ea8d3fc1f448b.jpg",
                "0824ab18972bd407202121137e899e510eb3097d.jpg",
                "838ba61ea8d3fd1f24f1dd2d344e251f95ca5f28.jpg",
                "8644ebf81a4c510fa9360e7a6459252dd52aa5e4.jpg",
                "9358d109b3de9c82748f306b6a81800a18d84368.jpg",
                "78310a55b319ebc4a8ce9af98626cffc1e17161f.jpg",
                "83025aafa40f4bfbed987d40054f78f0f736185d.jpg",
                "472309f7905298221c7fc70ad2ca7bcb0b46d4af.jpg",
                "622762d0f703918fce02b360543d269758eec44d.jpg",
                "8718367adab44aedc9a1af8cb71c8701a08bfbef.jpg",
                "a2cc7cd98d1001e983cf9da5bd0e7bec55e797ad.jpg",
                "a8ec8a13632762d09cc52d15a5ec08fa503dc6a5.jpg",
                "a8ec8a13632762d096a35751a5ec08fa503dc6bb.jpg",
                "a71ea8d3fd1f4134703af8b7201f95cad0c85eee.jpg",
                "a686c9177f3e6709bb5338ac38c79f3df8dc5515.jpg",
                "a8014c086e061d95f9772ada7ef40ad162d9ca1b.jpg",
                "ac4bd11373f08202757a1f954efbfbedaa641bab.jpg",
                "adaf2edda3cc7cd9a3cd46ce3d01213fb90e91c0.jpg",
                "b7fd5266d0160924386cdf48d70735fae6cd3413.jpg",
                "b90e7bec54e736d176f19d609f504fc2d46269f3.jpg",
                "b90e7bec54e736d1902d04a29e504fc2d562695a.jpg",
                "b219ebc4b74543a96e4bc33a18178a82b80114f7.jpg",
                "b219ebc4b74543a9324b85db1a178a82b9011450.jpg",
                "c2cec3fdfc039245120bd0e88294a4c27c1e259e.jpg",
                "c75c10385343fbf2c2c76371b37eca8064388ffd.jpg",
                "cc11728b4710b9120e2a3f1ac5fdfc039345226f.jpg",
                "cefc1e178a82b901b014f2d4768da9773812ef98.jpg",
                "cf1b9d16fdfaaf51355d7151895494eef11f7aa6.jpg",
                "d31b0ef41bd5ad6e0ef6ac0e87cb39dbb7fd3cc3.jpg",
                "d52a2834349b033b231b10ba11ce36d3d539bd3b.jpg",
                "d52a2834349b033b1731cd3d10ce36d3d439bd9f.jpg",
                "d058ccbf6c81800ab10541eab73533fa838b4776.jpg",
                "d1160924ab18972bd1c3eda7e5cd7b899f510ae5.jpg",
                "dbb44aed2e738bd45ad7a359a58b87d6267ff9e0.jpg",
                "dbb44aed2e738bd475d8c026a58b87d6277ff928.jpg",
                "dbb44aed2e738bd47415c346a78b87d6277ff995.jpg",
                "f703738da97739124564bc97fe198618377ae282.jpg",
                "f9198618367adab40be1e68a8ed4b31c8601e4c2.jpg",
                "fcfaaf51f3deb48f0c6f9828f61f3a292cf578c5.jpg",
                "fcfaaf51f3deb48fc87cdec9f41f3a292df5788b.jpg",
                "003caff3fc432116e70637a4e72a0e6f.jpg",
                "0069d04c17b28e3613cb5ffc2c5fd1c7.jpg",
                "00adc71d4386ee00cd6cf91ce76a7e6c.jpg",
                "00f63d8718cae90f5f28b7705535fcea.jpg",
                "06fd7f2b2108a9673a45d640945701bf.jpg",
                "0cc623c89ac97959de53fd33d2085a2a.jpg",
                "0e689ef49e51092465d3f49bd45fdc23.jpg",
                "11cd13cc12cc161f82d5fed035402973.jpg",
                "127f1c4004fd233fa630e115758cfa4d.jpg",
                "1393298880b21389a5ea2cefb557295e.jpg",
                "1504dc33a158ac3c3132d7bb37bd00e6.jpg",
                "15a638cb5d4b64f4aef3ed3fe735f236.jpg",
                "15b30b7a6ea0ab631fd6e091ff114ebb.jpg",
                "1f2b9f534bf838e631219123b7d9dfca.jpg",
                "20febae243f5a4c59e3f414cf2113467.jpg",
                "289c2e08d1de909475cf6f4a23c76346.jpg",
                "28eed47ce21be744ef5b12af152c00a5.jpg",
                "2a6eb172822bd8e5dd046945f94f17cd.jpg",
                "2fdc20f271ea9b21ee1ac6c518bbd927.jpg",
                "340ed9c8434c3eaa8bbf9bfb527b7161.jpg",
                "3fa1a3248d6a1ee87723bc62ccd12cce.jpg",
                "40fd31fb06418fc80d1e80b7e417977e.jpg",
                "414dd2f8107e5b2c435ce7ef41afa527.jpg",
                "442e5d5ef1a77b46e76e799c46c1c0a3.jpg",
                "46d51549d2455f86ab693963ff307a3f.jpg",
                "4b30c1011ee9742175e344a54be2e691.jpg",
                "4cc6aa5e2cb51d290d8340277431008f.jpg",
                "4fe423208f1ae50064aa1dceabdd76c2.jpg",
                "500288c814c44f6d24945b905a40d94f.jpg",
                "51f00b0f317e2f67e599516c3f6544d0.jpg",
                "5668a95f3112335c9b3eb0b97fab5184.jpg",
                "57db3d55de4b6fe6af00a1b9b020ec30.jpg",
                "58bf276f7dafc707cc742e3ec7375be1.jpg",
                "5c1da81c8da33dcad27def5c1609239b.jpg",
                "5e6d5ff09829cafdd8e32398578ca3c7.jpg",
                "627a8f19930f0dadae2b40645f1ab6ce.jpg",
                "6dc2618a53e97b233923c2a37ff0a696.jpg",
                "6ee370f811ea10bc8976899982417829.jpg",
                "6f6272901407bec142b18619626c9bfe.jpg",
                "6f718073167e8756997e631619fca2e4.jpg",
                "7349107275d39f9c68c65c6479a4016c.jpg",
                "79e53fe167e5006d93dd1a5447083080.jpg",
                "86ab3a2f55e8997598c53c04e6f62b88.jpg",
                "8718367adab44aed591d1ac0b41c8701a18bfb47.jpg",
                "885c41f764890c747581fcb04801aed8.jpg",
                "88eb6c99665f95af4f38f5ce84fc3db7.jpg",
                "89b9b1e03dc53d2b513892829d2b745d.jpg",
                "8e045f8a5d79ab22529272a3be793a8f.jpg",
                "8e1392a6832ba1eaf1949d340870d2b7.jpg",
                "8f65ddc0dd6a70d347fd34492340906e.jpg",
                "950837696c511141b469e90c3c549588.jpg",
                "9ba94460ddb6bb2472bbe3f49b664f1a.jpg",
                "9bc9b4ca5aa89cf1b06a48625dbd7299.jpg",
                "9e338e2c2dbff34ab2d9f196483284c1.jpg",
                "a0e89276879cbbe9c8573f313ae81507.jpg",
                "a9baa184faeebdfed2f9fb680ad974dd.jpg",
                "aacd354fd90c5d85a0f4fa67ba92b511.jpg",
                "b15d8bf6e2d8cf446bb1e23c539f72b1.jpg",
                "b26e371b0b6865ce32bd071233924ec2.jpg",
                "b27372786191fa8d1eb0b70657036142.jpg",
                "b6bfaa56f92972e59164f339d5b9c0e9.jpg",
                "b745f68f8f6ead40f2853892bbf24604.jpg",
                "bb49858913f69df822e5ee605858945a.jpg",
                "c170f29d4c29a4b5d6cc1c44cc678ad3.jpg",
                "c1d8e42282cf10a5dfac2864facfb211.jpg",
                "c4de9c499b42c99f34cfc42daa4b255a.jpg",
                "c697c6a5cc6fa9e86d7da4378fb8a01a.jpg",
                "c78d0ab1589983b26887c47908bc65c8.jpg",
                "cdecf61d2d8d2ec01816e65785411ce9.jpg",
                "cf0d4e20f5eb53c3940b6024267d61c4.jpg",
                "d46852dbe54a5c1c897fd71522aa6622.jpg",
                "dad61fc72a28350da2afa3875a3d5b01.jpg",
                "dd57601b66c24a3776f024b5ad3fb12a.jpg",
                "e031b0944ecb30262c73622274556755.jpg",
                "e3e911853614cfa5d6b31d54109992e7.jpg",
                "e768285b6e9b24b1d8e01bb5694d47a9.jpg",
                "e8b4caffa8c678c85ca50d23e3f72650.jpg",
                "eb6cad5e01bca43f44d7d936c72c61e1.jpg",
                "eebbdeee8ead64c721310f0c593202f0.jpg",
                "ef3db4a14db7d7e7101a30e790052586.jpg",
                "f037cedd5dc88a8193fcea91d4b17968.jpg",
                "f195aa1c86286b16f23ba4478cd5692b.jpg",
                "f246e4f9962c59f98879d625d270b156.jpg",
                "f65e0fc4fdb1872f8c7cc5cd79164c13.jpg",
                "f8995c081fbed9858cf12791c5722221.jpg",
                "fa1cd951c695c89745499907bde23a84.jpg",
                "fed2034f6b0be580bb384e98d959631e.jpg",
                "ffcebdfb695fe0d6b739ba5aacfb2d0d.jpg",
                "u=1454263356,41204913&fm=23&gp=0.jpg",
                "u=1782171325,1283049313&fm=23&gp=0.jpg",
                "u=2420669275,1149109575&fm=23&gp=0.jpg",
                "u=2550477077,794252701&fm=23&gp=0.jpg",
                "u=4210208584,451612848&fm=23&gp=0.jpg",
                "v.jpg"
        };
        int x = (int) (Math.random() * defaultImgArray.length);
        return defaultImgArray[x];
    }


    private static String ogss[][] = new String[][]{
            {"13人抽中千元大奖，共计1.3万元", "18人抽中千元大奖，共计1.8万元", "20人抽中千元大奖，共计2万元"},
            {"22人抽中千元大奖，共计2.2万元", "25人抽中千元大奖，共计2.5万元", "27人抽中千元大奖，共计2.7万元"},
            {"29人抽中千元大奖，共计2.9万元", "36人抽中千元大奖，共计3.6万元", "41人抽中千元大奖，共计4.1万元"},
            {"45人抽中千元大奖，共计4.5万元", "50人抽中千元大奖，共计5万元", "58人抽中千元大奖，共计5.8万元"},
            {"62人抽中千元大奖，共计6.2万元", "66人抽中千元大奖，共计6.6万元", "71人抽中千元大奖，共计7.1万元"},
            {"75人抽中千元大奖，共计7.5万元", "77人抽中千元大奖，共计7.7万元", "80人抽中千元大奖，共计8万元"},
            {"82人抽中千元大奖，共计8.2万元", "88人抽中千元大奖，共计8.8万元", "92人抽中千元大奖，共计9.2万元"}
    };

    /**
     * 获取 引导付款泡泡文案
     *
     * @param now
     * @return
     */
    public static String getGuidePayString(long now) {
        int a = 0;//0-6
        int b = 0;//0-2
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(now));
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                a = 0;
                break;
            case Calendar.TUESDAY:
                a = 1;
                break;
            case Calendar.WEDNESDAY:
                a = 2;
                break;
            case Calendar.THURSDAY:
                a = 3;
                break;
            case Calendar.FRIDAY:
                a = 4;
                break;
            case Calendar.SATURDAY:
                a = 5;
                break;
            case Calendar.SUNDAY:
                a = 6;
                break;
            default:
                break;
        }
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour >= 9 && hour < 13) {
            b = 0;
        } else if (hour >= 13 && hour < 16) {
            b = 1;
        } else if (hour >= 16/*&&hour<22*/) {
            b = 2;
        } else if (hour < 9) {
            if (a == 0) {
                a = 6;
            } else {
                a = a - 1;
            }
            b = 2;
        }

        return ogss[a][b];

    }


    /**
     * @param textView
     * @param map
     * @param isColor  是否需要变色处理
     * @param isBold   变色部分是否加粗处理
     */
    public static void setColorTextString(TextView textView, HashMap<String, Object> map, boolean isColor, boolean isBold) {
        String text = (String) map.get("text");
        if (TextUtils.isEmpty(text)) {
            return;
        }
        String newText = "";
        List<HashMap<String, String>> list = (List<HashMap<String, String>>) map.get("list");
        if (list == null || list.size() <= 0) {
            return;
        }
        List<Integer[]> listColorIndext = new ArrayList<Integer[]>();//需要变颜色的位置下标
        if (text.startsWith("${replace}") && text.endsWith("${replace}")) {

            String[] texts = text.split("\\$\\{replace\\}");
            for (int i = 0; i < texts.length; i++) {
                String value = list.get(i).get("value");
                newText += value + texts[i];
                Integer[] indext = new Integer[2];
                indext[0] = newText.indexOf(value);
                indext[1] = newText.indexOf(value) + value.length();
                listColorIndext.add(indext);
            }
            String value = list.get(list.size() - 1).get("value");
            newText += value;
            Integer[] indext = new Integer[2];
            indext[0] = newText.indexOf(value);
            indext[1] = newText.indexOf(value) + value.length();
            listColorIndext.add(indext);

        } else if (text.startsWith("${replace}")) {

            String[] texts = text.split("\\$\\{replace\\}");
            for (int i = 0; i < texts.length; i++) {
                String value = list.get(i).get("value");
                newText += value + texts[i];
                Integer[] indext = new Integer[2];
                indext[0] = newText.indexOf(value);
                indext[1] = newText.indexOf(value) + value.length();
                listColorIndext.add(indext);
            }

        } else if (text.endsWith("${replace}")) {
            String[] texts = text.split("\\$\\{replace\\}");
            for (int i = 0; i < texts.length; i++) {

                String value = list.get(i).get("value");
                newText += texts[i] + value;
                Integer[] indext = new Integer[2];
                indext[0] = newText.indexOf(value);
                indext[1] = newText.indexOf(value) + value.length();
                listColorIndext.add(indext);
            }
        } else {
            String[] texts = text.split("\\$\\{replace\\}");
            for (int i = 0; i < texts.length - 1; i++) {
                String value = list.get(i).get("value");
                newText += texts[i] + value;
                Integer[] indext = new Integer[2];
                indext[0] = newText.indexOf(value);
                indext[1] = newText.indexOf(value) + value.length();
                listColorIndext.add(indext);
            }
            newText += texts[texts.length - 1];
        }
        if (isColor) {
            SpannableString textSpan = new SpannableString(newText);
            for (int i = 0; i < listColorIndext.size(); i++) {
                String color = list.get(i).get("color");
                String[] colors = color.split(",");
                int r = 0;
                try {
                    r = Integer.parseInt(colors[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int g = 0;
                try {
                    g = Integer.parseInt(colors[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int b = 0;
                try {
                    b = Integer.parseInt(colors[2]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textSpan.setSpan(new ForegroundColorSpan(Color.rgb(r, g, b)),
                        listColorIndext.get(i)[0], listColorIndext.get(i)[1],
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                if (isBold) {
                    textSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                            listColorIndext.get(i)[0], listColorIndext.get(i)[1],
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }


            }

            textView.setText(textSpan);
        } else {
            textView.setText(newText);
        }


    }


}
