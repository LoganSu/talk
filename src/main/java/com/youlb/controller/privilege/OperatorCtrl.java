package com.youlb.controller.privilege;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.privilege.IOperatorBiz;
import com.youlb.biz.privilege.IPrivilegeBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.baseInfo.Carrier;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.privilege.Role;
import com.youlb.utils.common.DES3;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.RegexpUtils;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

/** 
 * @ClassName: ManageUserContrl 
 * @Description: 管理用户控制类
 * @author: Pengjy
 * @date: 2015年5月27日
 * 
 */
@Controller
@RequestMapping("/mc/user")
@Scope("prototype")
public class OperatorCtrl extends BaseCtrl{
	/** 日志输出 */
	private static Logger logger = LoggerFactory.getLogger(OperatorCtrl.class);
	@Autowired
	private IOperatorBiz operatorBiz;
	public void setOperatorBiz(IOperatorBiz operatorBiz) {
		this.operatorBiz = operatorBiz;
	}
	@Autowired
    private IPrivilegeBiz privilegeBiz;
	public void setPrivilegeBiz(IPrivilegeBiz privilegeBiz) {
		this.privilegeBiz = privilegeBiz;
	}
	@Autowired
	private ServletContext servletContext;
	public ServletContext getServletContext() {
		return servletContext;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	/**
     * 主页面跳转
     * @return
     */
    @RequestMapping("/index.do")
	public String index(Model model,String carrierNum){
    	//爱社区特殊处理页面
    	if("asq".equals(carrierNum)){
    		model.addAttribute("module", "dweller");
    		return "/asqIndex";
    	}
    	if("1".equals(SysStatic.PLATFORMLEVEL)){
    		return "/oneLevelIndex";
    	}else{
    		return "/twoLevelIndex";
    	}
	}
    /**
     * 用户登入
     * @return
     */
    @RequestMapping(value="/hideLogin.do",method=RequestMethod.GET)
    public String hideLogin(HttpServletRequest request,HttpServletResponse response,HttpSession httpSession,Operator user,Model model){
    	Map<String,String> retMap = new HashMap<String, String>();
		try {
			PrintWriter writer = response.getWriter();
	    	//其他平台对接判断
	    	String ip = request.getRemoteAddr();
	    	String token = request.getParameter("token");
	    	String username = request.getParameter("username");
	    	String carrierNum = request.getParameter("carrierNum");
    		if(StringUtils.isNotBlank(ip)&&StringUtils.isNotBlank(token)&&StringUtils.isNotBlank(username)){
    			//验证ip
    			if(!SysStatic.ASQIPSLIST.contains(ip)){
    				retMap.put("code", "3");
        			retMap.put("message", "非法ip");
        			writer.write(JsonUtils.toJson(retMap));
        			return null;
    			}
    			//验证token
    			byte[] encode = DES3.encryptMode(SysStatic.ASQKEYBYTES, SysStatic.ASQPSW.getBytes());
    			String encodeStr = DES3.bytesToHexString(encode);
    			String md5 = DigestUtils.md5Hex(encodeStr+username+carrierNum);
    			//验证token
//    			if(!md5.equalsIgnoreCase(token)){
//    				retMap.put("code", "4");
//        			retMap.put("message", "非法请求");
//        			writer.write(JsonUtils.toJson(retMap));
//        			return null;
//    			}
				//设置默认的运营商
				Carrier carrier = new Carrier();
				carrier.setCarrierNum(carrierNum);
				user.setCarrier(carrier);
				//设置账号
				user.setLoginName(username);
				Operator loginUser = operatorBiz.hideLogin(user);
				if(loginUser!=null){
					httpSession.setAttribute(SysStatic.LOGINUSER, loginUser);
					return INDEX+"?carrierNum="+user.getCarrier().getCarrierNum();
				}else{
					retMap.put("code", "2");
	    			retMap.put("message", "用户名不存在");
				}
				
    		}else{
    			retMap.put("code", "1");
    			retMap.put("message", "所需参数为空");
    		}
    		writer.write(JsonUtils.toJson(retMap));
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (BizException e) {
			e.printStackTrace();
		}
			return null;
    	}
    
    /**
     * 用户登入
     * @return
     */
    @RequestMapping("/loginForm.do")
    public String login(HttpServletRequest request,HttpServletResponse response,HttpSession httpSession,Operator user,Model model){
    	//重定向到登录页面标识
    	httpSession.setAttribute("from_redirect", "from_redirect");
        if(StringUtils.isBlank(user.getLoginName())){
        	httpSession.setAttribute("errorMessg", "登录账号不能为空");
        	return "redirect:"+getRequest().getContextPath()+"/"+user.getCarrier().getCarrierNum()+"/login.do";
    	} 
        if(StringUtils.isBlank(user.getPassword())){
        	httpSession.setAttribute("errorMessg", "密码不能为空");
        	return "redirect:"+getRequest().getContextPath()+"/"+user.getCarrier().getCarrierNum()+"/login.do";
        } 
		if(StringUtils.isBlank(user.getVerificationCode())){
			httpSession.setAttribute("errorMessg", "验证码不能为空");
			return "redirect:"+getRequest().getContextPath()+"/"+user.getCarrier().getCarrierNum()+"/login.do";
        } 
		
		//判断用户是否被锁
    	List<String> list = (List<String>)servletContext.getAttribute("loockList");
    	if(list.contains(user.getCarrier().getCarrierNum()+user.getLoginName())){
    		httpSession.setAttribute("errorMessg", "当天内输入错误已经超过5次，账号已被锁");
    		return "redirect:"+getRequest().getContextPath()+"/"+user.getCarrier().getCarrierNum()+"/login.do";
    	}
		try {
		//获取验证码
		String code = operatorBiz.getVerificationCode(user,null);
		if(StringUtils.isBlank(code)){
			httpSession.setAttribute("errorMessg", "验证码超时，或用户名不正确");
			return "redirect:"+getRequest().getContextPath()+"/"+user.getCarrier().getCarrierNum()+"/login.do";
		}else{
			if(!code.equals(user.getVerificationCode())){
				httpSession.setAttribute("errorMessg", "验证码不正确");
				return "redirect:"+getRequest().getContextPath()+"/"+user.getCarrier().getCarrierNum()+"/login.do";
			}
		}
		
			if(user.getCarrier()==null||StringUtils.isBlank(user.getCarrier().getCarrierNum())){
				httpSession.setAttribute("errorMessg", "url错误！");
				//跳转到错误页面
				return "redirect:"+getRequest().getContextPath()+"/error.jsp";
			}
			Operator loginUser = operatorBiz.login(user,code);
		    	if(loginUser!=null){
		//    		//重复登入
		//    		if(loginUser.getLogStatus()!=null&&loginUser.getLogStatus().equals(1)){
		//    			httpSession.setAttribute("errorMessg", "该用户已经登入");
//		        		return LOGIN;
		//    		}
		    		httpSession.setAttribute(SysStatic.LOGINUSER, loginUser);
		    		httpSession.setAttribute("errorMessg", "");
		    		return INDEX;
		    	}else{
		    		httpSession.setAttribute("errorMessg", "用户名或密码错误");
		    		//获取用户的错误次数
		    		Object obj = httpSession.getAttribute("errorCount");
		    		if(obj!=null){
		    			int errorCount = (int)obj;
		    			if(errorCount>=5){
		    				//获取项目中被锁的用户名
	    					list.add(user.getCarrier().getCarrierNum()+user.getLoginName());//运营商+用户名
	    					servletContext.setAttribute("loockList", list);
		    				httpSession.setAttribute("errorMessg", "当天内输入错误已经超过5次");
		    			}else{
		    				httpSession.setAttribute("errorCount", ++errorCount);
		    			}
		    		}else{
		    			httpSession.setAttribute("errorCount", 1);
		    		}
		    		if(user.getCarrier()==null){
		    			return "redirect:"+getRequest().getContextPath()+"/error.jsp";
		    		}
		    		return "redirect:"+getRequest().getContextPath()+"/"+user.getCarrier().getCarrierNum()+"/login.do";
		    	}
		} catch (ParseException | IOException | JsonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BizException e) {
			logger.error("获取验证码失败");
			e.printStackTrace();
		}
		return message;
    }
    /**
     * 退出系统
     * @return
     */
    @RequestMapping("/loginOut.do")
    public String loginOut(HttpSession httpSession,Operator user,Model model){
    	 user = (Operator) httpSession.getAttribute(SysStatic.LOGINUSER);
    	httpSession.invalidate();//设置session失效
    	return "redirect:"+getRequest().getContextPath()+"/"+user.getCarrier().getCarrierNum()+"/login.do";
    }
    /**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/toSaveOrUpdate.do")
   	public String toSaveOrUpdate(String[] ids,Operator user,Model model){
    	Operator loginUser = getLoginUser();
    	//先获取参数值
    	String isCarrier = user.getIsCarrier();
    	//更新
    	if(ids!=null&&ids.length>0){
    	    try {
				user = operatorBiz.get(ids[0]);
				model.addAttribute("user", user);
			} catch (BizException e) {
				logger.error("获取单个数据失败");
				e.printStackTrace();
			}
    	}
    	//运营商用户管理
    	if(StringUtils.isNotBlank(isCarrier)){
    		return "/carrierOperator/addOrEdit";
    	} 
		try {
			List<Role> roleList = operatorBiz.getRoleList(loginUser,user);
			model.addAttribute("roleList", roleList);
		} catch (BizException e) {
			logger.error("获取角色数据失败");
			e.printStackTrace();
		}
   		return "/operator/addOrEdit";
   	}
	 /**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/toChangePws.do")
   	public String changePws(){
   		return "/operator/changePws";
   	}
    
    /**
     * 修改密码
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/changePws.do")
    @ResponseBody
    public String changePws(HttpSession httpSession,Operator user,Model model){
    	try {
    		Operator loginUser = getLoginUser();
    			
    		if(StringUtils.isBlank(user.getNewPassword())||StringUtils.isBlank(user.getSurePassword())){
    			super.message = "密码不能为空！";
    			 return  super.message;
    		}
			if(user.getNewPassword().length()<5||user.getSurePassword().length()<5){
				super.message = "新密码不能少于5个字符！！";
				return  super.message;
			}
			if(!user.getNewPassword().equals(user.getSurePassword())){
				super.message = "密码输入不一致！";
				return  super.message;
			}
			String id = operatorBiz.getUser(user,loginUser);
			if(StringUtils.isNotBlank(id)){
				operatorBiz.update(user, id);
				super.message = "ok";
//				httpSession.invalidate();//设置session失效
				return  super.message;
	//    	    	   user = (Operator) httpSession.getAttribute(SysStatic.LOGINUSER);
	//    	    	   return "redirect:"+getRequest().getContextPath()+"/"+user.getCarrier().getCarrierNum()+"/login.do";
			}else{
				super.message = "原始密码错误！";
				return  super.message;
			}
	    			 
		} catch (Exception e) {
			super.message = e.getMessage();
			e.printStackTrace();
		}
    	 return  super.message;
    }
    /**
     * 保存
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String saveOrUpdate(Operator user,Model model){
    	try {
    		Operator loginUser = getLoginUser();
    		if(StringUtils.isBlank(user.getLoginName())||!RegexpUtils.checkNumAndLetter(user.getLoginName(), 6, 11)){
    			super.message = "登入名必需由6~11个数字或字母组成";
				return  super.message;
    		}
    		if(StringUtils.isBlank(user.getId())){
    			//添加的时候检查用户名是否存在
    			boolean  b=operatorBiz.chickLoginNameExist(user,loginUser);
    			if(b){
    				super.message = "用户名已经存在！";
    				return  super.message;
    			}
    			
    		}
    		if(StringUtils.isBlank(user.getPhone())){
    			super.message = "手机号码不能为空";
   			    return  super.message;
    		}else{
    			if(!RegexpUtils.checkMobile(user.getPhone())){
    				super.message = "请填写正确的手机号码";
       			    return  super.message;
    			}
    		}
    		
    		//创建用户的时候必须绑定至少一个角色
    		if(user.getRoleIds()==null||user.getRoleIds().isEmpty()){
    			super.message = "请选择至少一个角色，如果没有角色请创建！";
    			 return  super.message;
    		}
    		operatorBiz.saveOrUpdate(user,loginUser);
		} catch (Exception e) {
			super.message = "操作失败！";
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
				operatorBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	 
	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Operator user){
		List<Operator> list = new ArrayList<Operator>();
		try {
			list = operatorBiz.showList(user,getLoginUser());
		} catch (Exception e) {
			//TODO log
		}
		return setRows(list);
	}
	/**
	 * 显示运营商用户table数据
	 * @return
	 */
	@RequestMapping("/carrierShowList.do")
	@ResponseBody
	public  Map<String, Object> carrierShowList(Operator user){
		List<Operator> list = new ArrayList<Operator>();
		try {
			list = operatorBiz.carrierShowList(user,getLoginUser());
		} catch (Exception e) {
			logger.error("查询运营商用户列表出错");
		}
		return setRows(list);
	}
	
	 /**
     * 跳转到重置密码页面
     * @return
     */
    @RequestMapping("/toUpdatePasw.do")
   	public String toUpdatePasw(String[] ids,Model model){
    	if(ids!=null&&ids.length>0){
			try {
				Operator user = operatorBiz.get(ids[0]);
				model.addAttribute("user", user);
			} catch (BizException e) {
				logger.error("获取单个数据失败");
				e.printStackTrace();
			}
    	}
   		return "/carrierOperator/updatePasw";
   	}
    /**
     * 重置密码
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/updatePasw.do")
    @ResponseBody
    public String updatePasw(Operator user,Model model){
    	try {
    		if(StringUtils.isBlank(user.getNewPassword())||StringUtils.isBlank(user.getPassword())){
    			super.message = "密码不能为空！";
    			 return  super.message;
    		}
			if(user.getNewPassword().length()<5||user.getPassword().length()<5){
				super.message = "新密码不能少于5个字符！！";
				return  super.message;
			}
			if(!user.getNewPassword().equals(user.getPassword())){
				super.message = "密码输入不一致！";
				return  super.message;
			}
    		
    		
//    		if(StringUtils.isNotBlank(user.getNewPassword())&&StringUtils.isNotBlank(user.getPassword())&&
//    			user.getNewPassword().equals(user.getPassword())){
    			operatorBiz.update(user);
//    		}else{
//    			super.message = "密码输入不一致！";
//    			logger.info(super.message);
//    		}
		} catch (Exception e) {
			super.message = "重置密码出错！";
			e.printStackTrace();
			logger.error(super.message);
		}
    	 return  super.message;
    }
    /**
     * 获取手机验证码
     * @param user
     * @return
     */
    @RequestMapping("/getVerificationCode.do")
    @ResponseBody
    public String getVerificationCode(Operator user){
    	try {
    		//判断账号是否存在
    		boolean b = operatorBiz.chickLoginNameExist(user);
    		if(!b){
    			super.message = "请填写正确的账号";
    			logger.error(super.message);
    			return super.message;
    		}
    		//获取验证码
    		String code = operatorBiz.getVerificationCode(user,null);
    		if(StringUtils.isNotBlank(code)){
    			logger.info("运营商："+user.getCarrier().getCarrierNum()+",账号："+user.getLoginName()+"，验证码为"+code);
    			System.out.println("运营商："+user.getCarrier().getCarrierNum()+",账号："+user.getLoginName()+"，验证码为"+code);
    			super.message = "验证码24小时有效，请稍后再试！";
    			logger.error(super.message);
    			return super.message;
    		}
			operatorBiz.getVerificationCode(user,"1440");//十分钟有效期(测试一天有效)935133
			
			Map<String,Map<String,Object>> map =  (Map<String, Map<String, Object>>) servletContext.getAttribute("exceedSendSmsMap");
    		Map<String,Object> subMap = map.get(user.getCarrier().getCarrierNum()+user.getLoginName());
    		if(subMap!=null){
    			Integer count = (Integer) subMap.get("count");
    			if(count>5){
    				super.message = "您发送验证码太频繁，请休息一下再试！";
    				return super.message;
    			}else{
    				++count;
    				subMap.put("count", count);
    				subMap.put("time", new Date());
    				map.put(user.getCarrier().getCarrierNum()+user.getLoginName(), subMap);
    			}
    		}else{
    			Map<String,Object> newMap = new HashMap<String, Object>();
    			newMap.put("count", 1);
    			newMap.put("time", new Date());
    			map.put(user.getCarrier().getCarrierNum()+user.getLoginName(), newMap);
    		}
		} catch (IOException | JsonException e) {
			e.printStackTrace();
			super.message = "验证码发送失败！";
			logger.error(super.message);
		} catch (BizException e) {
			super.message = "验证码发送失败！";
			logger.error(super.message);
			e.printStackTrace();
		}  
    	return super.message;
    }
    
    
}
