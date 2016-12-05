package com.youlb.utils.common;

import java.util.List;

/** 
 * @ClassName: SysStatic 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2015年5月27日
 * 
 */
public class SysStatic {
	/**卡片结束时间提前N天为快到期卡片*/
	public static final Integer NEARDAY = 3;
	/**权限模块被选择的角色和权限*/
	public static final Integer CHECKED = 1;
	/**新建运营商时创建默认角色描述*/
	public static final String COMMON_ROLE_DESCRAPTION = "本运营商用户角色创建和建立基础数据";
	/**后台登录session key*/
	public static String LOGINUSER="loginUser";
	/**管理员用户名*/
	public static String ADMIN="admin";
	/**普通管理员*/
	public static Integer NORMALADMIN=1;
	/**特殊管理员 */
	public static Integer SPECIALADMIN=2;
	
	/**区域级*/
	public static Integer AREA=0;
	/**社区级*/
	public static Integer NEIGHBORHOODS=1;
	/**楼栋级*/
	public static Integer BUILDING=2;
	/**单元级*/
	public static Integer UNIT=3;
	/**房间级*/
	public static Integer ROOM=4;
	
	/**房产信息域*/
	public static String HOUSEINFO="1";
	/**初始化系统运营商、角色、用户公用id值*/
	public static String COMMON_ID_VALUE="1";
	/**普通运营商*/
	public static String NORMALCARRIER="1";
	/**特殊运营商  顶级运营商*/
	public static String SPECIALCARRIER="2";
	/**普通运营商初始化权限*/
	public static String NORMALPRIVILEGE="1";
	/**特殊运营商初始化权限  顶级运营商*/
	public static String SPECIALPRIVILEGE="2";
	
	public static String one="1";
	public static String two="2";
	public static String three="3";
	public static String four="4";
	public static String five="5";
	public static String six="6";
	public static String seven="7";
	public static String eight="8";
	public static String nine="9";
	public static String ten="10";
	public static String eleven="11";
	public static String twelve="12";
	
	/**卡片类型为ic卡*/
	public static String ICCARD="1";
	/**卡片类型为身份证*/
	public static String IDCARD="2";
	/**卡片类型为银行卡*/
	public static String BANKCARD="3";
	/**项目根路径*/
	public static String PATH="";
	
	/**卡片状态为激活状态*/
	public static final String LIVING = "1";
	/**卡片状态为挂失状态*/
	public static final String LOSS = "2";
	/**卡片状态为注销状态*/
	public static final String CANCEL = "3";
	
	/**手机app保存目录*/
	public static final String PHONE = "phone/";
	/**门口机app保存目录*/
	public static final String DEVICE = "device/";
	/**管理机app保存目录*/
	public static final String MANAGE_DEVICE = "manageDevice/";
	/**物业app保存目录*/
	public static final String MANAGEMENT = "management/";
	/**第三方app保存目录*/
	public static final String OTHERAPP = "otherApp/";
	/**广告资源文件根目录*/
	public static String ADDIR = "";
	/**图片目录*/
	public static final String PICTURE = "picture/";
	/**视频目录*/
	public static final String VIDEO = "video/";
	
	/**接口地址端口*/
	public static String HTTP = "";
	
	/**私钥*/
	public static String PRIVATEKEY="";
	/**房间默认密码*/
	public static final String ROOMDEFULTPASSWORD = "123456";
	/**门口机图片备份地址*/
	public static String FILEUPLOADIP = "";
	/**门口机登录*/
	public static byte[] KEYBYTES = {};
	/**爱社区des3秘钥*/
	public static byte[] ASQKEYBYTES;
	/**爱社区秘钥*/
	public static String ASQPSW;
	/**爱社区ip地址*/
	public static List<String> ASQIPSLIST;


	/**web 版本*/
	public static String VERSION;
	/**需要过滤的特殊字符*/
	public static String[] SPECIALSTRING;
	/**七牛图片备份地址*/
	public static String QINIUBACKUP;
	/**一级平台地址*/
	public static String FIRSTSERVER;
	
}
