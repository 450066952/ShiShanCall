package com.socket.server.socket.pub;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Command {

	private static final Logger logger = Logger.getLogger(Command.class);
	public static final short START_TAG = 0x4E4D; // 开始符 2字节
	/** 版本，4字节 1001 */
	public static final byte[] VERSION = { 0x01, 0x00, 0x00, 0x01 }; // 版本 4字节
	public static final long BACKUP = 0xFFFFFFFF; // 备用8字节

	public static final String DEFAULT_CHARSET = "UTF-8"; // Charset.forName("UTF-8");

	// 应答字符
	public static final byte RESPONSE_MUSTNOT = 0x00; // 不响应
	public static final byte RESPONSE_MUST = 0x01; // 必须响应
	public static final int ERROR_NO_ERROR = 0x00000000; // 无错误
	// public static final int ERROR_USER_NOT_EXIST = 0xF0000001; // 用户不存在
	// public static final int ERROR_USER_PWD_ERROR = 0xF0000002; // 密码错误
	// public static final int ERROR_USER_READ_GROUP = 0xF0000003; // 读取用户分组错误
	// public static final int ERROR_USER_READ_FRIENDS = 0xF0000004; // 读取用户好友错误
	// public static final int ERROR_ADD_FRIEND_TO_DB = 0xF0000005; //
	// 添加好友到数据库错误
	// public static final int ERROR_DEL_FRIEND_FM_DB = 0xF0000006; //
	// 从数据库中删除好友错误
	// public static final int ERROR_VERIFY_PWD_ERROR = 0xF0000007; //
	// 验证用户名或密码错误
	// public static final int ERROR_SAVE_NEW_PWD_ERR = 0xF0000008; // 保存用户新密码错误
	// public static final int ERROR_NETWORK_DISCONN = 0xF0000009; // 网络断开
	// public static final int ERROR_CONNECT_FAILURE = 0xF000000A; // 用户连接服务器失败
	// public static final int ERROR_LOGIN_TIMEOUT = 0xF000000B; // 用户登陆超时
	// public static final int ERROR_USER_OVERMAX = 0xF000000D; // 超过最大连接数限制

	// 用户状态定义 4字节刚好是INT
	public static final byte NULL_ZERO_BYTE = '\0'; // 0X00

	// 最大数据包
	public static int MAX_COMMAND_LENGTH = 2048 * 1000;
	public static int HEAD_LENGTH = 26;
	public static int TAIL_LENGTH = 1;
	public static final int pagesize = 24;
	public static int MAX_COMMAND_BODY_LENGTH = MAX_COMMAND_LENGTH
			- HEAD_LENGTH - TAIL_LENGTH;

	// 时间处理
	public DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	public static String[] datePattern = { "yyyyMMddHHmmss" };
	

	// --------------------------------------------------
	/** 客户端登陆服务端 */
	public static final int COMMAND_USER_LOGIN = 0x0001;//
	/** 客户端登陆返回登录结果 */
	public static final int COMMAND_USER_LOGIN_RESULT = 0x0002;//
	/** 返回notice信息 */
	public static final int COMMAND_NOTICE = 0x0003;//
	/** 返回homework信息 */
	/** 返回Welcome信息 */
	public static final int COMMAND_WELCOME = 0x0007;//
	/** 返回校园通知信息 */
	public static final int COMMAND_SCHOOLNOTICE = 0x0009;//
	/** 删除班级通知 */
	public static final int COMMAND_DEL_NOTICE = 0x00b;//11
	/** 删除Welcome信息 */
	public static final int COMMAND_DEL_WELCOME = 0x00f;//15
	/** 修改相册 */
	public static final int COMMAND_GALLERY = 0x001e;//30
	/** 删除校园通知信息 */
	public static final int COMMAND_DEL_SCHOOLNOTICE = 0x0011;//17
	/**返回视频信息 */
	public static final int COMMAND_VIDEO = 0x0012;//18
	/** 删除视频 */
	public static final int COMMAND_DEL_VIDEO = 0x0013;//19
	/** 开关机时间 */
	public static final int COMMAND_ONOFF_TIME = 0x0014;//20
	/**客户端接收ok*/
	public static final int COMMAND_OK = 0x0017;//23
	/**客户端第一次登陆请求数据*/
	public static final int COMMAND_FIRST_INIT = 0x0018;//24
	/**返回学校基本信息*/
	public static final int COMMAND_SCHOOL_INFO = 0x0019;//25
	/**服务端通知客户端进行数据初始化*/
	public static final int COMMAND_SEND_INIT = 0x001a;//26
	/** 客户端发送心跳包 */
	public static final int COMMAND_HEARTBEAT = 0x001b;//27
	/** 发送升级命令*/
	public static final int COMMAND_UPGRADED = 0x001d;//29
	/** 通知客户端 五项评比有更新  需要重新从客户端获取*/
	public static final int COMMAND_HONOR_UPDATE = 0x0021;//33
	/**通知客户端模式变换 */
	public static final int COMMAND_MODEL = 0x0024;//36
	/** 返回PIC宣传--照片--信息 */
	public static final int COMMAND_PIC_PHOTO = 0x0025;//37
	/** 删除PIC宣传--照片--信息 */
	public static final int COMMAND_DEL_PIC_PHOTO = 0x0026;//38
	/** 修改PIC宣传*/
	public static final int COMMAND_PIC = 0x0027;//39
	/** 返回小学生护导安排表*/
	public static final int COMMAND_GUIDE = 0x0028;//40
	/**教师端发送签到信息*/
	public static final int COMMAND_SEND_TECH_SIGN = 0x002c;//44
	/** 删除PIC相册 */
	public static final int COMMAND_DEL_PIC_ALL = 0x002d;//45
	/** 返回CourseTime时间信息 */
	public static final int COMMAND_COURSE_Time = 0x002e;//46
	/**服务端通知客户端进行数据初始化*/
	public static final int COMMAND_SEND_INIT_SCHOOL = 0x002f;//47


	/*喊号*/
	public static final int COMMAND_SOCOAL_SEND_CALL = 0x0030;//48
	/*开始办理*/
	public static final int COMMAND_SOCOAL_START_BUS = 0x0031;//49
	/*结束办理*/
	public static final int COMMAND_SOCOAL_STOP_BUS = 0x0032;//50
	/*开始签名*/
	public static final int COMMAND_SOCOAL_START_SIGN = 0x0033;//51
	/*暂停服务*/
	public static final int COMMAND_SOCOAL_STOP_SERVICE = 0x0034;//52

	/*获取设备对应窗口信息*/
	public static final int COMMAND_SOCOAL_GET_WINDOW= 0x0035;//53
	/*重新叫号*/
	public static final int COMMAND_SOCOAL_REPEAT_cALL= 0x0036;//54

	/**返回工作人员的信息给评价器*/
	public static final int COMMAND_SEND_STAFF = 0x0037;//55
	/**上传评价**/
	public static final int COMMAND_SEND_EVA = 0x0038;//56

	/**PC 登录**/
	public static final int COMMAND_PC_LOGIN = 0x0039;//57
	/**返回pc叫号列表*/
	public static final int COMMAND_NUM_LIST = 0x003a;//58

	/**通知PC端有新用户取号*/
	public static final int COMMAND_PC_GET_NUM = 0x003b;//59

	/**通知PC端被其他账号踢下线*/
	public static final int COMMAND_OTHER_LOGIN = 0x003c;//60

	/**通知PC端 该窗口已经有人登录了*/
	public static final int COMMAND_WINDOW_HAS_LOGIN = 0x003d;//61


	/** 大数据登陆服务端 */
	public static final int COMMAND_DATA_USER_LOGIN = 0x003e;//62
	/** 大数据登陆返回登录结果 */
	public static final int COMMAND_DATA_USER_LOGIN_RESULT = 0x003f;//63
	/** 窗口人员收到差评 */
	public static final int COMMAND_REC_NOT_SATISFY = 0x0040;//64
	/** 窗口人员收到评价 */
	public static final int COMMAND_REC_EVA = 0x0041;//65
	/**请求 窗口状态*/
	public static final int COMMAND_WIN_STATE = 0x0042;//66
	/**pc端有掉线情况*/
	public static final int COMMAND_PC_LOG_OUT = 0x0043;//67
	/**修改基础信息*/
	public static final int COMMAND_UPDATE_BASIC = 0x0044;//68

	/**取号机取号*/
	public static final int COMMAND_GET_CODE = 0x0045;//69

	/**发通知给指定的人*/
	public static final int COMMAND_SEND_MSG = 0x0046;//70

		/**请求离线消息*/
	public static final int COMMAND_HISTORY = 0x0047;//71


	
	/** 演示接口-删除欢迎词 */
	public static final int COMMAND_DEMO_DEL_WELCOME = 0x0201;//513
	/** 演示接口-删除视频 */
	public static final int COMMAND_DEMO_DEL_VIDEO = 0x0202;//514
	/** 演示接口-删除回家作业 */
	public static final int COMMAND_DEMO_DEL_HOMEWORK = 0x0203;//515
	/** 演示接口-删除班级通知 */
	public static final int COMMAND_DEMO_DEL_NOTICE = 0x0204;//516
	/** 演示接口-添加作品 */
	public static final int COMMAND_DEMO_ADD_SATIRE = 0x0205;//517
	/** 演示接口-切换课程表和班级信息卡风格**/
	public static final int COMMAND_DEMO_LESSION_CHANGE = 0x0206;//518
	/** 演示接口-关闭电视机**/
	public static final int COMMAND_DEMO_SHUTDOWN_TV = 0x0207;//519
	/** 演示接口-开始视频直播**/
	public static final int COMMAND_DEMO_START_PLAY_VIDEO = 0x0208;//520
	/** 演示接口-停止视频直播**/
	public static final int COMMAND_DEMO_STOP_PLAY_VIDEO = 0x0209;//519
	/** 演示接口-开始视频推流**/
	public static final int COMMAND_DEMO_START_PUSH_VIDEO = 0x020a;//522
	/** 演示接口-停止视频推流**/
	public static final int COMMAND_DEMO_STOP_PUSH_VIDEO = 0x020b;//523
	
	

	
	

	// 业务逻辑关键字------end----

	public int command;
	protected int length; // 数据体长度
	protected byte checksum = 0;
	protected short year;
	protected byte[] time = new byte[5];
	protected byte response = RESPONSE_MUSTNOT;

	public String reciverTime;

	public String getReciverTime() {
		return reciverTime;
	}

	public void setReciverTime(String reciverTime) {
		this.reciverTime = reciverTime;
	}

	/**
	 * 用来生成Command的构造器
	 */
	protected Command(int command) {

		this.command = command;
		setDate(new Date());
	}

	protected Command(int command,Date date) {

		this.command = command;
		setDate(date);
	}


	/**
	 * 用以解析的构造器
	 * 
	 * @param command
	 * @param buffer
	 * @param ack
	 */
	protected Command(int command, ChannelBuffer buffer, byte ack) {

		this.command = command;
		this.response = ack;
		parseBody(buffer);
		setDate(new Date());// 接收到的时间
	}

	/**
	 * 解析数据，head已经被除掉了
	 */
	protected abstract void parseBody(ChannelBuffer buffer);

	/**
	 * 
	 * 
	 * 名称 长度 格式 描述 开始符 2 Hex 数据标识：4EH 4DH。 版本号 4 Hex 表示协议的版本号：0x01, 0x00,
	 * 0x00，0x00 备用 8 Hex 用于兼容后续版本协议的备用数据，初始为0xFF 日期 4 YYYYMMDD
	 * 数据传送时的日期，如：20070508，表示2007年5月8日 时间 3 HHMMSS 数据传送时的时间，如：080607，表示上午8点6分7秒
	 * 命令字 4 Hex 《见命令清单的描述》 是否应答 1 Hex 表示是否需要接收方应答此指令。1：应答，0：不应答
	 * 
	 * 
	 *
	 * 
	 * @param buffer
	 */
	protected void putHead(ChannelBuffer buffer) {
		buffer.writeShort(START_TAG);
		buffer.writeInt(0);
	}

	/**
	 * 写入命令包含的数据内容
	 * 
	 * @param buffer
	 */
	protected abstract void putBody(ChannelBuffer buffer);

	public void fill(ChannelBuffer buffer) {
		checksum = 0;
		length = 0;
		putHead(buffer);

		// ChannelBuffer body =
		// ChannelBuffers.dynamicBuffer(MAX_COMMAND_BODY_LENGTH);
		ChannelBuffer body = ChannelBuffers.dynamicBuffer();
		// 一部分head当body
		body.writeBytes(VERSION);// 4字节
		body.writeLong(BACKUP);// 8字节
		body.writeShort(ChannelBuffers.swapShort(year)); // 2字节
		body.writeBytes(time);// -----5字节吧
		body.writeInt(getSwapInt(command));// 4字节
		body.writeByte(response);// 1字节
		putBody(body);
		// System.out.println("WriteIndex:"+body.writerIndex());
		byte[] bodyBytes = new byte[body.writerIndex()];
		body.getBytes(0, bodyBytes);
		// 数据体加密
		byte[] encryptBodyBytes = encryptBody(bodyBytes);
		// buffer.writeInt(encryptBodyBytes.length);
		buffer.writeBytes(encryptBodyBytes);
		length = encryptBodyBytes.length;
		putTail(buffer);
	}

	// 20150917 ---------tony-------
	public void fill2(ChannelBuffer buffer, ChannelBuffer buffer2) {

		// ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

		checksum = 0;
		length = 0;
		putHead(buffer);

		// ChannelBuffer body =
		// ChannelBuffers.dynamicBuffer(MAX_COMMAND_BODY_LENGTH);
		ChannelBuffer body = ChannelBuffers.dynamicBuffer();
		// 一部分head当body
		body.writeBytes(VERSION);// 4字节
		body.writeLong(BACKUP);// 8字节
		body.writeShort(ChannelBuffers.swapShort(year)); // 2字节
		body.writeBytes(time);// 3字节-----5字节吧
		// System.out.println("Head:"+ChannelBuffers.hexDump(buffer));
		body.writeInt(getSwapInt(command));// 5字节
		body.writeByte(response);// 1字节

		body.writeBytes(buffer2);
		// System.out.println("WriteIndex:"+body.writerIndex());
		byte[] bodyBytes = new byte[body.writerIndex()];
		body.getBytes(0, bodyBytes);
		// 数据体加密
		byte[] encryptBodyBytes = encryptBody(bodyBytes);
		// buffer.writeInt(encryptBodyBytes.length);
		buffer.writeBytes(encryptBodyBytes);
		length = encryptBodyBytes.length;

		putTail(buffer);
	}

	/**
	 * 发文件
	 * 
	 * @param buffer
	 * @param filelenth
	 */
	public void fillAppendFileSize(ChannelBuffer buffer, int filelenth) {
		checksum = 0;
		length = 0;
		putHead(buffer);

		// ChannelBuffer body =
		// ChannelBuffers.dynamicBuffer(MAX_COMMAND_BODY_LENGTH);
		ChannelBuffer body = ChannelBuffers.dynamicBuffer();
		// 一部分head当body
		body.writeBytes(VERSION);// 4字节
		body.writeLong(BACKUP);// 8字节
		body.writeShort(ChannelBuffers.swapShort(year)); // 2字节
		body.writeBytes(time);// 3字节-----5字节吧
		// System.out.println("Head:"+ChannelBuffers.hexDump(buffer));
		body.writeInt(getSwapInt(command));// 5字节
		body.writeByte(response);// 1字节
		putBody(body);

		// add by tony 加入文件长度-----20150921
		// body.writeInt(getSwapInt(filelenth));

		// System.out.println("WriteIndex:"+body.writerIndex());
		byte[] bodyBytes = new byte[body.writerIndex()];
		body.getBytes(0, bodyBytes);
		// 数据体加密
		byte[] encryptBodyBytes = encryptBody(bodyBytes);
		// buffer.writeInt(encryptBodyBytes.length);
		buffer.writeBytes(encryptBodyBytes);
		length = encryptBodyBytes.length;

		length += filelenth;// add by tony 加入文件长度-----20150921

		putTail(buffer);
	}

	/**
	 * 
	 * 将数据体中前后两个字节调换,并计算checksum,实际协议中只进行异或运算
	 * 
	 * @param b
	 * @return
	 */
	protected byte[] encryptBody(byte[] b) {
		// int i = 0 ,j = 0;
		// for(int n = b.length >> 1 ; i < n ; i++, j+=2){
		// byte temp = b[j];
		// b[j] = b[j + 1];
		// b[j + 1] = temp;
		// checksum = (byte) (checksum ^ b[j] ^ b[j + 1]);
		// }
		// if(i < b.length - 1)
		// checksum = (byte) (checksum ^ b[b.length - 1] );
		for (byte bb : b) {
			checksum = (byte) (checksum ^ bb);
		}
		return b;
	}

	/**
	 * 写入XOR值和数据体长度
	 * 
	 * @param buffer
	 */
	protected void putTail(ChannelBuffer buffer) {
		// buffer.writeByte(checksum); // xor值
		buffer.setInt(2, getSwapInt(length)); // 最后写入数据体长度， 从第三字节写入int
	}

	public void setDate(Date date) {
		String ds = dateFormat.format(date);
		byte[] bytes = new byte[5];
		for (int i = 2; i < 7; i++) {
			bytes[i - 2] = (byte) Integer.parseInt(ds.substring(i * 2,
					i * 2 + 2));
		}
		// System.out.println(paserDate(bytes));
		this.time = bytes;
		this.year = Short.parseShort(ds.substring(0, 4));
	}

	// public Date paserDate(byte[] dateBytes){
	// StringBuilder buffer = new StringBuilder();
	// for(int i = 0 ; i < 7 ; i++){
	// int b = dateBytes[i];
	// if(b < 10) buffer.append("0"+b);
	// else buffer.append(b);
	// }
	// try {
	// return DateUtils.parseDate(buffer.toString(),datePattern );
	// } catch (ParseException e) {
	// throw new RuntimeException("Date Pattern Must yyyyMMddHHmmss",e);
	// }
	// }

	public boolean isMustResponse() {
		return response == RESPONSE_MUST;
	}

	public void setMustResponse(boolean mustResponse) {
		this.response = mustResponse ? RESPONSE_MUST : RESPONSE_MUSTNOT;
	}

	/**
	 * 协议中Int类型都换了位,低位换到高位
	 * 
	 * @param i
	 * @return
	 */
	public int getSwapInt(int i) {
		return ChannelBuffers.swapInt(i);
	}

	public void writeInt(ChannelBuffer buffer, int i) {
		buffer.writeInt(getSwapInt(i));
	}

	/**
	 * 打印当前Command的Byte信息
	 */
	public void dumpCommand() {
		// ChannelBuffer b = ChannelBuffers.dynamicBuffer();
		// fill(b);
		// logger.debug(ChannelBuffers.hexDump(b).toUpperCase());
	}

	/**
	 * 在buffer当中读取String的值，读到0x00为止 '\0'
	 * 
	 * @param buffer
	 * @return
	 */
	public String readString(ChannelBuffer buffer) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte b = 0;
		while ((b = buffer.readByte()) != 0x00) {
			out.write(b);
		}
		try {
			return new String(out.toByteArray(), DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "出错";
	}

	/**
	 * 根据长度来读取
	 * @param buffer
	 * @param length
	 * 要读取的长度
	 * @return
	 */
	public String readString(ChannelBuffer buffer, int length) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte b = 0;
		int i = 0;
		for (; i < length; i++) {
			b = buffer.readByte();
			out.write(b);
		}

		try {
			return new String(out.toByteArray(), DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "出错";
	}

	public boolean readBoolean(ChannelBuffer buffer) {
		byte b = 0;
		b = buffer.readByte();
		return b == 0x00 ? false : true;
	}

	public String readBytes2String(ChannelBuffer buffer, int length) {
		byte[] version = new byte[length];// 4字节
		buffer.readBytes(version);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int b = version[i];
			sb.append(b);
		}
		return sb.toString();
	}

	/**
	 * byte[] 转为String
	 */
	public static void printHexString(String hint, byte[] b) {
		// System.out.print(hint);
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			// System.out.print(hex.toUpperCase() + " ");
		}
		// System.out.println("");
	}

	/**
	 * 
	 * @param b
	 *            byte[]
	 * @return String
	 */
	public static String Bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}

		return ret;
	}

	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
	 * 
	 * @param src0
	 *            byte
	 * @param src1
	 *            byte
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	/**
	 * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF,
	 * 0xD9}
	 * 
	 * @param src
	 *            String
	 * @return byte[]
	 */
	public static byte[] HexString2Bytes(String src) {
		byte[] ret = new byte[8];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < 8; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		
		ChannelBuffer body = ChannelBuffers.dynamicBuffer();
//		// 一部分head当body
//		body.writeBytes(VERSION);// 4字节
//		body.writeLong(BACKUP);// 8字节
//		body.writeShort(ChannelBuffers.swapShort(year)); // 2字节
//		body.writeBytes(time);// 3字节-----5字节吧
		body.writeInt(20);// 5字节
		
		
//		logger.debug("buffer hex--------old---yuanshi-------------- is:"+ ChannelBuffers.hexDump(body).toUpperCase());
		
		System.out.println(ChannelBuffers.hexDump(body).toUpperCase());
		
		
		
//		String ds = dateFormat.format(date);
//		
//		public void setDate(Date date) {
		//yyyyMMddHHmmss
			String ds = "20160720102709";
			byte[] bytes = new byte[5];
			for (int i = 2; i < 7; i++) {
				
				System.out.println(ds.substring(i * 2,
						i * 2 + 2)+"===");
				bytes[i - 2] = (byte) Integer.parseInt(ds.substring(i * 2,
						i * 2 + 2));
			}

			System.out.println(111);
//			 System.out.println((bytes));
//			bytes;

			System.out.println(Short.parseShort(ds.substring(0, 4))+"*****");
//		}
		
		
		
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		byte[] rs = "你好123456".getBytes(DEFAULT_CHARSET);
		
		buffer.writeBytes(rs);
		buffer.writeByte(NULL_ZERO_BYTE);
		
		System.out.println(ChannelBuffers.hexDump(buffer).toUpperCase());
	
		System.out.println(rs.length);		

	}

	/**
	 * add by tony
	 * 
	 * @param buffer
	 * @param value
	 */
	public void writeString(ChannelBuffer buffer, String value) {

		if (!"".equals(StringUtils.trimToEmpty(value))) {
			try {
				byte[] rs = value.getBytes(DEFAULT_CHARSET);
				buffer.writeBytes(rs);
				buffer.writeByte(NULL_ZERO_BYTE);
			} catch (UnsupportedEncodingException UE) {
				UE.fillInStackTrace();
			}
		} else {
			// 为空的时候需要写一个结束符号20110619
			buffer.writeByte(NULL_ZERO_BYTE);
		}
	}

	/**
	 * add by tony
	 * 
	 * @param buffer
	 * @return
	 */
	public int readInt(ChannelBuffer buffer) {
		return getSwapInt(buffer.readInt());
	}

	/**
	 * add by tony 根据buffer的长度来读取,返回一个byte[]
	 * 
	 * @param buffer
	 * @param leng
	 * @return
	 * 
	 */
	public static byte[] readFileByteArry(ChannelBuffer buffer) {
		int leng = buffer.writerIndex();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			buffer.readBytes(out, leng);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * add by tony 根据buffer的长度来读取,返回一个byte[]
	 * 
	 * @param buffer
	 * @param leng
	 * @return
	 * 
	 */
	public static byte[] readFileByteArryLength(ChannelBuffer buffer, int leng) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			buffer.readBytes(out, leng);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	public static String bufferArrToString(byte[] arr) {

		StringBuffer buff = new StringBuffer();
		for (int j = 0; j < arr.length; j++) {
			buff.append(arr[j]);
		}
		return buff.toString();
	}
}