package com.youlb.entity.common;
import java.util.Collection;
import java.io.Serializable;
/**
 * 
 * @Title:分页对象  
 * @Desription:分页对象  
 * @Company:CSN
 * @ClassName:Pager.java
 * @CreateDate:2013-6-7 下午6:12:24  
 * @Version:0.1
 */
public class Pager implements Serializable {
    /**总行数*/
	private int totalRows;
	/**每页显示数*/
	private int pageSize = 10;
	/**当前页码*/
	private int currentPage;
	/**总页数*/
	private int totalPages;
	/**开始行数*/
	private int startRow;
	
	private int formNumber;
	private Collection pageData;
	private boolean isFor =false;

	public Pager() {
		this.currentPage = 1;
		this.startRow = 0;
	}
	
	public boolean getIsFor() {
		return isFor;
	}

	public void setIsFor(boolean isFor) {
		this.isFor = isFor;
	}

	public Collection getPageData() {
		return pageData;
	}

	public void setPageData(Collection pageData) {
		this.pageData = pageData;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
		if(pageSize==0){
			return;
		}
		this.totalPages = totalRows / pageSize;
		int mod = totalRows % pageSize;
		if (mod > 0) {
			this.totalPages++;
		}
		if (this.currentPage > totalPages){
			if(isFor){
				int forSize = currentPage%totalPages;
				if(forSize>0){
					currentPage=forSize;
				}else{
					currentPage = totalPages;
				}
			}else{
				this.currentPage = totalPages;
			}
		}
		this.startRow = (currentPage - 1) * pageSize;
		if (this.startRow < 0)
			startRow = 0;
		if (this.currentPage == 0 || this.currentPage < 0)
			currentPage = 1;
	}

	public int getFormNumber() {
		return formNumber;
	}

	public void setFormNumber(int formNumber) {
		this.formNumber = formNumber;
	}
	
	/**
	 * 将bootstrap插件的当前页码属性进行转换
	 * @param offset
	 */
	public void setPageNumber(int pageNumber) {
		this.currentPage = pageNumber;
	}
}
