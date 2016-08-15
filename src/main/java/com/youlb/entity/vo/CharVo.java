package com.youlb.entity.vo;

import java.util.List;

/** 
 * @ClassName: LineChars.java 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2016-1-29
 * 
 */
public class CharVo {
	/**
	 * 
	 */
	public CharVo() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param title  主标题
	 * @param subtitle  副标题
	 * @param xAxisTitle X轴标题
	 * @param yAxisTitle Y轴标题
	 */
	public CharVo(String type,String title,String subtitle,String xAxisTitle,String yAxisTitle) {
		this.type=type;
		this.title=title;
		this.subtitle=subtitle;
		this.xAxisTitle=xAxisTitle;
		this.yAxisTitle=yAxisTitle;
	}
	 /**图形类别*/
	 private String type;
	 /**标题*/
	 private String title;
	 /**副标题*/
	 private String subtitle;
	 /**x轴标题*/
	 private String xAxisTitle;
	 /**y轴标题*/
	 private String yAxisTitle;
	 /**X轴类别名称*/
	 private List<String> categories;
	 /**数据对象集合*/
	 private List<Series> dataList;
	 /**div宽度 单位px*/
	 private Integer width;
	 /**div高度 单位px*/
	 private Integer height;
	 
	 
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getxAxisTitle() {
		return xAxisTitle;
	}
	public void setxAxisTitle(String xAxisTitle) {
		this.xAxisTitle = xAxisTitle;
	}
	public String getyAxisTitle() {
		return yAxisTitle;
	}
	public void setyAxisTitle(String yAxisTitle) {
		this.yAxisTitle = yAxisTitle;
	}
	public List<Series> getDataList() {
		return dataList;
	}
	public void setDataList(List<Series> dataList) {
		this.dataList = dataList;
	}
	 
	 
	 
}
