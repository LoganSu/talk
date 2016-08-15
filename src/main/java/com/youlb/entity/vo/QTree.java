package com.youlb.entity.vo;

import java.util.List;

/** 
 * @ClassName: Tree.java 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2015年8月6日
 * 
 */
public class QTree {
    private String id;
    private String url;// tree组件一般用于菜单，url为菜单对应的地址
    private String text;// 显示文字
    private boolean checked = false;// 是否选中
    private List<QTree> children;// 子tree
    private Integer layer;
    private boolean showCheckedName=true;//是否需要多选框的name值
    private String showSign;//多选框前面的符号 默认是是 + -
	 
	 
	public String getShowSign() {
		return showSign;
	}
	public void setShowSign(String showSign) {
		this.showSign = showSign;
	}
	public boolean isShowCheckedName() {
		return showCheckedName;
	}
	public void setShowCheckedName(boolean showCheckedName) {
		this.showCheckedName = showCheckedName;
	}
	public Integer getLayer() {
		return layer;
	}
	public void setLayer(Integer layer) {
		this.layer = layer;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<QTree> getChildren() {
		return children;
	}
	public void setChildren(List<QTree> children) {
		this.children = children;
	}
    
    
}
