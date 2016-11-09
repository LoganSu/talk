package com.youlb.controller.countManage;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.countManage.IDeviceCountBiz;
import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.controller.infoPublish.TodayNewsCtrl;
import com.youlb.entity.countManage.DeviceCount;
import com.youlb.entity.countManage.QRInfo;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.DES3;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.MatrixToImageWriter;
import com.youlb.utils.common.QiniuUtils;
import com.youlb.utils.common.RegexpUtils;
import com.youlb.utils.common.SHAEncrypt;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;
import com.youlb.utils.helper.DateHelper;

/** 
 * @ClassName: DeviceCountCtrl.java 
 * @Description: 设备帐号 
 * @author: Pengjy
 * @date: 2015-11-24
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/mc/deviceCount")
public class DeviceCountCtrl extends BaseCtrl {
	/** 日志输出 */
	private static Logger logger = LoggerFactory.getLogger(DeviceCountCtrl.class);
    @Autowired
	private IDeviceCountBiz deviceCountBiz;
	public void setDeviceCountBiz(IDeviceCountBiz deviceCountBiz) {
		this.deviceCountBiz = deviceCountBiz;
	}
	@Autowired
    private IDomainBiz domainBiz;
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
	}


	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(DeviceCount deviceCount){
		List<DeviceCount> list = new ArrayList<DeviceCount>();
		Operator loginUser = getLoginUser();
		try {
			list = deviceCountBiz.showList(deviceCount, loginUser);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return setRows(list);
	}
	

	/**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/toSaveOrUpdate.do")
   	public String toSaveOrUpdate(String[] ids,DeviceCount deviceCount,Model model){
    	
    	if(ids!=null&&ids.length>0){
    		try {
				deviceCount = deviceCountBiz.get(ids[0]);
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		//domainId转换成entityid
//    		Domain domain = domainBiz.get(deviceCount.getDomainId());
//    		if(domain!=null){
//    			deviceCount.setDomainId(domain.getEntityId());
//    		}
		}
//    	Operator loginUser = getLoginUser();
    	//获取权限列表
//    	List<Privilege> privilegeList = carrierBiz.getPrivilegeList(loginUser,role);
//    	model.addAttribute("privilegeList", privilegeList);
    	model.addAttribute("deviceCount", deviceCount);
   		return "/deviceCount/addOrEdit";
   	}
    
    /**
     * 保存
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String saveOrUpdate(DeviceCount deviceCount,Model model){
    	try {
    		if(StringUtils.isNotBlank(deviceCount.getWarnPhone())){
    			if(!RegexpUtils.checkMobile(deviceCount.getWarnPhone())){
    				super.message = "请填写正确的手机号码！";
    				return super.message;
    			}
    		}
    		if(StringUtils.isNotBlank(deviceCount.getWarnEmail())){
    			if(!RegexpUtils.checkEmail(deviceCount.getWarnEmail())){
    				super.message = "请填写正确的邮箱！";
    				return super.message;
    			}
    		}
    		if(StringUtils.isBlank(deviceCount.getId())&&(deviceCount.getTreecheckbox()==null||deviceCount.getTreecheckbox().size()!=1)){
    			return  "只能选择一个域！";
    		}
    		if(StringUtils.isNotBlank(deviceCount.getId())){
    			DeviceCount d = deviceCountBiz.get(deviceCount.getId());
    			deviceCount.setCountPsw(d.getCountPsw());
    			deviceCount.setEndTime(d.getEndTime());
    			deviceCount.setRamdonCode(d.getRamdonCode());
    			deviceCount.setSipNum(d.getSipNum());
    		}
    		deviceCountBiz.saveOrUpdate(deviceCount);
//    		super.message = "添加成功！";
		} catch (BizException e) {
			super.message = e.getMessage();
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			super.message = "操作失败！";
			e.printStackTrace();
		} catch (ParseException e) {
			super.message = "操作失败！";
			e.printStackTrace();
		} catch (IOException e) {
			super.message = "操作失败！";
			e.printStackTrace();
		} catch (JsonException e) {
			super.message = "操作失败！";
			e.printStackTrace();
		}
    	 return  super.message;
    }
    
    /**
     * 删除
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/delete.do")
	@ResponseBody
	public String deleteUser(String[] ids,Model model){
		if(ids!=null&&ids.length>0){
			try {
				deviceCountBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	
	/**
     * 设置密码
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/toSetPsw.do")
	public String toSetPsw(DeviceCount deviceCount,Model model){
		
		model.addAttribute("deviceCount",deviceCount);
		return "/deviceCount/toSetPsw";
	}
	/**
     * 设置密码
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/setPsw.do")
	@ResponseBody
	public String setPsw(DeviceCount deviceCount,Model model){
			try {
				deviceCountBiz.update(deviceCount);
			} catch (Exception e) {
				super.message =  "设置密码出错";
			}
		return super.message;
	}
	/**
     * 获取账号信息
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/getDeviceCount.do")
	@ResponseBody
	public DeviceCount getDeviceCount(String id){
		DeviceCount deviceCount = new DeviceCount();
		try {
			if(StringUtils.isNotBlank(id)){
				deviceCount = deviceCountBiz.get(id);
				Operator loginUser = getLoginUser();
				 //设置添加人
	    		String operator = "";
	    		//普通管理员 的真实姓名已经包含 admin
	    		if(SysStatic.NORMALADMIN.equals(loginUser.getIsAdmin())){
	    			operator= loginUser.getRealName();
	    		}else{
	    			operator = loginUser.getRealName()+"("+loginUser.getLoginName()+")";
	    		}		
				deviceCount.setPrintPerson(operator);
				deviceCount.setPrintTime(DateHelper.dateFormat(new Date(), "yyyy-MM-dd"));
				deviceCount.setEndTime(deviceCount.getEndTime().substring(0, 10));
			}
		} catch (Exception e) {
			super.message =  "获取账号信息出错";
		}
		return deviceCount;
	}
	
	
	/**
     * 跳转到派单页面
     * @return
     */
    @RequestMapping("/toSendOrders.do")
   	public String toSendOrders(String[] ids,DeviceCount deviceCount,Model model){
    	
    	if(ids!=null&&ids.length>0){
    		try {
	    		deviceCount = deviceCountBiz.get(ids[0]);
	    		Operator loginUser = getLoginUser();
	    		 //设置添加人
	    		String operator = "";
	    		//普通管理员 的真实姓名已经包含 admin
	    		if(SysStatic.NORMALADMIN.equals(loginUser.getIsAdmin())){
	    			operator= loginUser.getRealName();
	    		}else{
	    			operator = loginUser.getRealName()+"("+loginUser.getLoginName()+")";
	    		}		
	    		deviceCount.setPrintPerson(operator);
	    		deviceCount.setPrintTime(DateHelper.dateFormat(new Date(), "yyyy-MM-dd"));
	    		String orderNum;
					orderNum = deviceCountBiz.getOrderNum();
	    		deviceCount.setOrderNum(orderNum);
	    	    model.addAttribute("deviceCount", deviceCount);
    		} catch (BizException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}//获取派单序列
   	   }
    	return "/deviceCount/sendOrders";
    }
    /**
     * 派单页面保存
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/sendOrders.do")
    @ResponseBody
    public String sendOrders(HttpServletRequest request,DeviceCount deviceCount,Model model){
    	try {
    		String orderNum = deviceCount.getOrderNum();
    		if(StringUtils.isNotBlank(deviceCount.getEndTime())){
    			Date endDate = DateHelper.strParseDate(deviceCount.getEndTime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
    			if(endDate.getTime()<new Date().getTime()){
    				//截止日期要大于当前日期
            			return "2";
    			}
    			deviceCount = deviceCountBiz.get(deviceCount.getId());
    			if(StringUtils.isNotBlank(deviceCount.getEndTime())&&DateHelper.strParseDate(deviceCount.getEndTime(), "yyyy-MM-dd HH:mm:ss").getTime()>new Date().getTime()){
    				//在有效期内不能重复派单
        			return "4";
    			}
    			if(SysStatic.two.equals(deviceCount.getCountStatus())){
    				//账号未激活
        			return "3";
    			}
//    			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			QRInfo qr = new QRInfo();
//    			String endTime = deviceCount.getEndTime();
    			String count = deviceCount.getDeviceCount();
    			String psw = deviceCount.getCountPsw();
    			//判断密码是否已经设置
    			if(StringUtils.isBlank(psw)){
        			return "1";
    			}
    			String ip =SysStatic.HTTP;//接口地址
//    			String port = request.getServerPort()+"";
    			StringBuilder sb = new StringBuilder();
    			sb.append(count).append(" ").append(psw).append(" ").append(ip);
    			//3des加密
    			byte[] encryptMode = DES3.encryptMode(SysStatic.KEYBYTES, sb.toString().getBytes());
//    			String encode = DES3.bytesToHexString(encryptMode);
    			//转成16进制字符串
    			qr.setD(DES3.bytesToHexString(encryptMode));//加密后的的字符串
    			//获取公钥
    			qr.setT(endDate.getTime());//设置截止日期
    			//设置随机数
    			String uuid = UUID.randomUUID().toString().replace("-", "");
    			//对t,d字段签名MD5
    			String sign = SHAEncrypt.digestString(qr.getT()+qr.getD()+uuid, "MD5");
    			logger.info("T:"+qr.getT());
    			logger.info("D:"+qr.getD());
    			logger.info("uuid:"+uuid);
    			logger.info("sign:"+sign);
    			logger.info("执行时间:"+DateHelper.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
    			qr.setS(sign);//设置签名
    			byte[] uuidDes3 = DES3.encryptMode(SysStatic.KEYBYTES, uuid.getBytes());
    			String ramdonCode = DES3.bytesToHexString(uuidDes3);
    			//生成二维码图
    			String imgName = new Date().getTime()+"";//用时间戳命名是因为防止浏览器不重新加载新图片
//    			System.out.println(JsonUtils.toJson(qr));
    			String path = TodayNewsCtrl.class.getClassLoader().getResource("").getPath();
	    		path=path.substring(0,path.indexOf("WEB-INF"));
	    		String temsPath = path+"/tems/";
    			MatrixToImageWriter.createQRCode(JsonUtils.toJson(qr),temsPath,imgName);
    			File file = new File(temsPath+imgName+".jpg");
    			//把文件上传到七牛
    			int code = QiniuUtils.upload(file.getAbsolutePath(), "web/qrDir/"+imgName+".jpg");
    			if(code==200){
    				String qrPath = QiniuUtils.URL+"web/qrDir/"+imgName+".jpg";
    				deviceCountBiz.updateById(DateHelper.dateFormat(endDate, "yyyy-MM-dd HH:mm:ss"),ramdonCode,orderNum,qrPath,deviceCount.getId());
//    				System.out.println(file.delete());
	            	logger.info("删除临时二维码图片：："+file.delete());
    				return qrPath;
    			}
    			 return  "";
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	 return  null;
    }
 
    
    public static void main(String[] args) throws NoSuchAlgorithmException {
    	
    	String ss = "14613407990008461aa4d9440e35e388c92d132293f9d888610c831a8e29b45db2a1e30e2eecd188d8e6b7d55ac96c7f35b4b756277e042935826d136dfad99f0ab2618871e17";
    	
    	//12e424e1691b4d31c7120b2cb0fbaa53
    	
    	byte[] uuidDes3 = DES3.encryptMode(DES3.hexStringToBytes("11224c568813403828257951cbdd556677297498304036e2"), "41c24b6a55544e358ada06dbcc568dba".getBytes());
		String ramdonCode = DES3.bytesToHexString(uuidDes3);
		System.out.println(ramdonCode);
//    	final byte[] KEYBYTES = {0x11, 0x22, 0x4C, 0x56, (byte) 0x88, 0x13,
//			 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD,
//			 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36,(byte) 0xE2};
//    	
//    	String bytesToHexString = DES3.bytesToHexString(KEYBYTES);
//    	System.out.println(bytesToHexString);
//    	try {
//            String ss="8461aa4d9440e35e388c92d132293f9d888610c831a8e29b45db2a1e30e2eecd188d8e6b7d55ac96c7f35b4b756277e0810039530f75d4f199f0ab2618871e17";
//            byte[] hexStringToBytes = DES3.hexStringToBytes(ss);
//            byte[] decryptMode = DES3.decryptMode(SysStatic.KEYBYTES, hexStringToBytes);
//            
//            System.out.println(new String(decryptMode));
//    	     String content = "http://192.168.1.231:8080/appManage/singleDownPhone.do";
//    	     String path = "d:/qr";
    	     
//    	     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//    	     Map<EncodeHintType,String> hints = new HashMap<EncodeHintType,String>();
//    	     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//    	     BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
//    	     File file1 = new File(path,"aa2a.jpg");
//    	     if(!file1.exists()){
//    	    	 file1.createNewFile();
//    	     }
//    	     MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
//    	     MatrixToImageWriter.createQRCode(content, path, "1111");
    	     
//    	 } catch (Exception e) {
//    	     e.printStackTrace();
//    	 }
	}
}
