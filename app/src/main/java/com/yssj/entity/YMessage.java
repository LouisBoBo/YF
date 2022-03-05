//package com.yssj.entity;
//
//import com.yssj.activity.R;
//import com.yssj.app.ParcelCompat;
//
//import android.content.Context;
//import android.os.Parcel;
//import android.os.Parcelable;
//
///** 具体消息 */
//public class YMessage implements Parcelable{
//
//	// 状态   0 未发送  1 发送中  2 已送达   3 已阅读（如果是发送给别人的就是对方是否阅读，如果是别人给自己发送的就是自己是否阅读）
//	public static final int STATE_UNSEND = 0;
//	public static final int STATE_SENDING = 1;
//	public static final int STATE_SENDED = 2;
//	public static final int STATE_READ = 3;
//	public static final int STATE_UNREAD = 4;
//	
//	private long id;
//	
//	private String messageId;
//	private long time;
//	private String content;
//
//	/** 发送方 */
//	private UserInfo sender;
//	/** 接收方 */
//	private UserInfo receiver;
//
//	private boolean isRead;
//
//	public static final int TYPE_TEXT = 0;//30
//	public static final int TYPE_PICTURE = 1;//31
//	public static final int TYPE_RECORD = 2;//32
//	public static final int TYPE_LOCATION = 3;//33
//	public static final int TYPE_DYNAMIC_FACE = 4;//34
//	public static final int TYPE_READ = 5;
//	
//	public static enum MsgTypeSource{
//		
//		FRIEND_TYPE_TEXT(30),
//		FRIEND_TYPE_PICTURE(31),
//		FRIEND_TYPE_RECORD(32),
//		FRIEND_TYPE_LOCATION(33),
//		FRIEND_TYPE_DYNAMIC_FACE(34),
//		
//		BOX_TYPE_TEXT(0),
//		BOX_TYPE_PICTURE(1),
//		BOX_TYPE_RECORD(2),
//		BOX_TYPE_LOCATION(3),
//		BOX_TYPE_DYNAMIC_FACE(4);
//		
//		private int value = 0;
//
//	    private MsgTypeSource(int value) {    //    必须是private的，否则编译错误
//	        this.value = value;
//	    }
//
//	    public static MsgTypeSource valueOf(int value) {    //    手写的从int到enum的转换函数
//	        switch (value) {
//	        case 30:
//	            return FRIEND_TYPE_TEXT;
//	        case 31:
//	            return FRIEND_TYPE_PICTURE;
//	        case 32:
//	        	return FRIEND_TYPE_RECORD;
//	        case 33:
//	        	return FRIEND_TYPE_LOCATION;
//	        case 34:
//	        	return FRIEND_TYPE_DYNAMIC_FACE;
//	        	
//	        case 40:
//	        	return BOX_TYPE_TEXT;
//	        case 41:
//	        	return BOX_TYPE_PICTURE;
//	        case 42:
//	        	return BOX_TYPE_RECORD;
//	        case 43:
//	        	return BOX_TYPE_LOCATION;
//	        case 44:
//	        	return BOX_TYPE_DYNAMIC_FACE;
//	        	
//	        default:
//	            return null;
//	        }
//	    }
//
//	    public int value() {
//	        return this.value;
//	    }
//	}
//	
//	
//	public static final int TYPE_DATING_INVITATION=20;//(约会邀请)
//	
//	
//	
//	// 0为普通消息，1为图片，2为声音，3为位置，4为动态表情,5为即时通话 , 8刷新黑名单
//	private int type;
//
//	// 大小
//	private long size;
//
//	// 附件
//	private Attach attach;
//	
//	// 长度
//	private int duration;
//	
//	
//	private int state;
//	private boolean isHide;
//	
//	private double latitude; // 维度
//	private double longitude; // 经度
//	
//	private String groupId;	// 隶属于哪个群组，如果没有则为""
//	private String groupName;
//	private String groupAvatar;
//	
//	public static final int DIRECTION_SEND = 1;
//	public static final int DIRECTION_RECEIVE = 2;
//	private int direction;	// 信息的方向，1为发送，2为接收
//	
//
//	
//	public boolean isHide() {
//		return isHide;
//	}
//	public void setHide(boolean isHide) {
//		this.isHide = isHide;
//	}
//	public YMessage() {
//		super();
//	}
//	public String getMessageId() {
//		return messageId;
//	}
//	public void setMessageId(String messageId) {
//		this.messageId = messageId;
//	}
//	public long getTime() {
//		return time;
//	}
//	public void setTime(long time) {
//		this.time = time;
//	}
//	public UserInfo getSender() {
//		return sender;
//	}
//	public void setSender(UserInfo sender) {
//		this.sender = sender;
//	}
//	public UserInfo getReceiver() {
//		return receiver;
//	}
//	public void setReceiver(UserInfo receiver) {
//		this.receiver = receiver;
//	}
//	public String getContent() {
//		return getContent(null, false);
//	}
//	public String getContent(Context context, boolean isShort) {
//		if (isShort && context != null) {
//			switch (type) {
//				case TYPE_PICTURE :
//					return context.getString(R.string.message_picture);
//				case TYPE_RECORD :
//					return context.getString(R.string.message_voice);
//				case TYPE_LOCATION :
//					return context.getString(R.string.message_location);
//				case TYPE_DYNAMIC_FACE :
//					return context.getString(R.string.message_dynamic_face);
//			 }
//		}
//		return content;
//	}
//	public void setContent(String content) {
//		this.content = content;
//	}
//	public boolean getIsRead() {
//		return isRead;
//	}
//	public void setIsRead(boolean isRead) {
//		this.isRead = isRead;
//	}
//	public int getType() {
//		return type;
//	}
//	public void setType(int type) {
//		this.type = type;
//	}
//	public long getSize() {
//		return size;
//	}
//	public void setSize(long size) {
//		this.size = size;
//	}
//	public Attach getAttach() {
//		return attach;
//	}
//	public void setAttach(Attach attach) {
//		this.attach = attach;
//	}
//	public int getDuration() {
//		return duration;
//	}
//	public void setDuration(int duration) {
//		this.duration = duration;
//	}
//	public int getState() {
//		return state;
//	}
//	public void setState(int state) {
//		this.state = state;
//	}
//	public long getId() {
//		return id;
//	}
//	public void setId(long id) {
//		this.id = id;
//	}
//	
//	public double getLatitude() {
//		return latitude;
//	}
//	public void setLatitude(double latitude) {
//		this.latitude = latitude;
//	}
//	public double getLongitude() {
//		return longitude;
//	}
//	public void setLongitude(double longitude) {
//		this.longitude = longitude;
//	}
//	public String getGroupId() {
//		return groupId;
//	}
//	public void setGroupId(String groupId) {
//		this.groupId = groupId;
//	}
//	
//	public int getDirection() {
//		return direction;
//	}
//	public void setDirection(int direction) {
//		this.direction = direction;
//	}
//	public String getGroupName() {
//		return groupName;
//	}
//	public void setGroupName(String groupName) {
//		this.groupName = groupName;
//	}
//	public String getGroupAvatar() {
//		return groupAvatar;
//	}
//	public void setGroupAvatar(String groupAvatar) {
//		this.groupAvatar = groupAvatar;
//	}
//
//	public static final Parcelable.Creator<YMessage> CREATOR =  new Creator<YMessage>() {
//		
//		@Override
//		public YMessage[] newArray(int size) {
//			return new YMessage[size];
//		}
//		
//		@Override
//		public YMessage createFromParcel(Parcel source) {
//			return new YMessage(source);
//		}
//	};
//
//	private YMessage(Parcel source) {
//		ParcelCompat pc = new ParcelCompat(source);
//		id = pc.readInt();
//		messageId = pc.readString();
//		time = pc.readLong();
//		content = pc.readString();
//		sender = pc.readParcelable(UserInfo.class.getClassLoader());
//		receiver = pc.readParcelable(UserInfo.class.getClassLoader());
//		isRead = pc.readBoolean();
//		type = pc.readInt();
//		size = pc.readLong();
//		attach = pc.readParcelable(Attach.class.getClassLoader());
//		duration = pc.readInt();
//		state = pc.readInt();
//		isHide = pc.readBoolean();
//		groupId = pc.readString();
//		direction = pc.readInt();
//		
//	}
//	
//	@Override
//	public int describeContents() {
//		return 0;
//	}
//	
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		ParcelCompat pc = new ParcelCompat(dest);
//
//		pc.writeLong(id);
//		pc.writeString(messageId);
//		pc.writeLong(time);
//		pc.writeString(content);
////		pc.writeParcelable(sender, flags);
////		pc.writeParcelable(receiver, flags);
//		pc.writeBoolean(isRead);
//		pc.writeInt(type);
//		pc.writeLong(size);
//		pc.writeParcelable(attach, flags);
//		pc.writeInt(duration);
//		pc.writeInt(state);
//		pc.writeBoolean(isHide);
//		pc.writeString(groupId);
//		pc.writeInt(direction);
//		
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (o instanceof YMessage) {
//			return this.id == ((YMessage) o).id;
//		}
//		return super.equals(o);
//	}
//	
//	
//	
//
//}
