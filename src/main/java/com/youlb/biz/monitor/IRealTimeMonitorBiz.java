package com.youlb.biz.monitor;

import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.monitor.PointInfo;
import com.youlb.entity.monitor.RealTimeMonitor;

public interface IRealTimeMonitorBiz extends IBaseBiz<RealTimeMonitor> {
    /**
     * 获取告警地址信息
     * @param ids
     * @return
     */
	List<PointInfo> getData(String[] ids);

}
