package com.youlb.utils.helper;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.youlb.entity.common.Model;
import com.youlb.utils.helper.OrderHelper.Type;

public class OrderHelperUtils {
	public static List<OrderHelper> getOrderHelper(String propertyName, Boolean isAsc) {
		OrderHelper helper = new OrderHelper(propertyName, isAsc?Type.ASC:Type.DESC);
		return Arrays.asList(helper);
	}
	
	public static OrderHelper getSingleOrderHelper(String propertyName, Boolean isAsc) {
		return new OrderHelper(propertyName, isAsc?Type.ASC:Type.DESC);
	}
	
	/**
     * 排序公共方法
     * @param sb String容器 
     * @param model Model类型
     * @param alias 别名 例："t."
     * @param defaut 默认排序方式 例："t.createTime desc"
     * @return  组装好的sql或hql
     */
	public static StringBuilder getOrder(StringBuilder sb,Model model,String alias,String defaut){
		if(sb==null){
			return new StringBuilder();
		}
		sb.append(" order by ");
		if(StringUtils.isNotBlank(model.getOrder())&&StringUtils.isNotBlank(model.getSort())){
			String sort = model.getSort();
			if(sort.contains("Str")){
				sort = sort.substring(0, sort.indexOf("Str"));
			}
			sb.append(alias+sort+" "+model.getOrder());
		}else{
			sb.append(defaut);
		}
		return sb;
	}

}
