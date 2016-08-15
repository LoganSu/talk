/**
 * Copyright 2015 Unionbon' Studio
 * All right Reserved.
 * Created on 2015年11月11日.
 * Created by garyxin.
 */

package com.youlb.entity.access;

import java.util.ArrayList;
import java.util.List;

/**
 * @author garyxin on 2015年11月11日.
 *
 */
public class BlackListData {
	private List<BlackCardData> bcl;

	public BlackListData() {
		this.bcl = new ArrayList<BlackCardData>();
	}

	public List<BlackCardData> getBcl() {
		return bcl;
	}
	
	public void setBcl(List<BlackCardData> bcl) {
		this.bcl = bcl;
	}
	
	public void addBc(BlackCardData bl) {
		bcl.add(bl);
	}

	public static class BlackCardData {
		private Integer typ;		// 操作类型:挂失-1/解挂-0
		private String  cid;		// 卡序列号
		public BlackCardData() {}
		public BlackCardData(Integer type, String cid) {
			this.typ = type;
			this.cid = cid;
		}
		
		public Integer getTyp() {
			return typ;
		}
		
		public void setTyp(Integer type) {
			this.typ = type;
		}
		
		public String getCid() {
			return cid;
		}
		
		public void setCid(String cid) {
			this.cid = cid;
		}
	}
}
