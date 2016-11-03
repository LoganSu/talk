package com.youlb.job;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.youlb.biz.staticParam.IStaticParamBiz;
import com.youlb.utils.common.QiniuUtils;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.helper.DateHelper;

public class BackupFileJob implements Job{
	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		IStaticParamBiz staticParamBiz = (IStaticParamBiz) webApplicationContext.getBean("staticParamBiz");
		System.out.println(staticParamBiz);
		//获取静态参数七牛配置的 域名
		try {
//				StaticParam staticParam = staticParamBiz.getParamByKey("qiniu_upload");
//				String qiniu = staticParam.getFvalue();
			    //备份门口机记录照片
			    Calendar c = Calendar.getInstance();
			    c.add(Calendar.MONTH, -1);//前一个月的
			    Date date = c.getTime();
			    String dateStr = DateHelper.dateFormat(date, "yyyyMMdd");
//			    String dateStr ="";
			    String prefix = "DoorMachine/"+dateStr;
			    List<String> listFile = QiniuUtils.listFile(prefix);
			    if(listFile!=null&&!listFile.isEmpty()){
			    	for(String key :listFile){
				        URL url = new URL(QiniuUtils.URL+key);
				        DataInputStream dis = new DataInputStream(url.openStream());
				        String newImageName=SysStatic.QINIUBACKUP+key;
				        //建立一个新的文件
				        File file = new File(newImageName);
				        file.getParentFile().mkdirs();
				        file.createNewFile();
				        FileOutputStream fos = new FileOutputStream(file);
				        byte[] buffer = new byte[1024];
				        int length;
				        //开始填充数据
				        while((length = dis.read(buffer))>0){
				        	fos.write(buffer,0,length);
				        }
				        dis.close();
				        fos.close();
			    	}
			    }
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		   
	}

}
