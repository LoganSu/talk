package com.youlb.biz.monitor;

import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.monitor.PointInfo;
import com.youlb.entity.monitor.RealTimeMonitor;
import com.youlb.utils.exception.BizException;

public interface IRealTimeMonitorBiz extends IBaseBiz<RealTimeMonitor> {
    /**
     * 获取告警地址信息
     * @param ids
     * @return
     */
	List<PointInfo> getData(String[] ids)throws BizException;
    /**
     * 获取告警信息发送短信
     * @param id
     * @return
     */
	RealTimeMonitor getWarnInfoById(String id)throws BizException;

}
