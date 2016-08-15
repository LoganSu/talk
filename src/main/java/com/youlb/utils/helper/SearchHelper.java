package com.youlb.utils.helper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.youlb.entity.privilege.Operator;

/**
 * 
 * @Title:工具类  
 * @Desription:SearchHelper 查询条件
 * @Company:CSN
 * @ClassName:SearchHelper.java
 * @Author:zhucong
 * @CreateDate:2013-6-7 下午5:50:29  
 * @UpdateUser:zhucong  
 * @Version:0.1
 */
@SuppressWarnings("serial")
public class SearchHelper implements Serializable{
	/**属性名称*/
	private String attr;
	/**匹配条件*/
	private Type type;
	/**获取值的属性*/
	private String attrValueName;
	/**查询条件的连接类型 默认为and*/
	private LinkType linkType = LinkType.AND;
	
	/***
	 * 
	 * LinkType 查询连接类型	
	 * @author allan
	 * 2012-11-30 下午02:32:17
	 * @version 1.0
	 */
	public enum LinkType{
		AND,OR
	}
	/**
	 * <p>查询类型<p>
	 * <p>
	 * EQ:= GT:>  LT:< GE:>= LE<:= LIKE:like %**%
	 * NOTEQ:!= 
	 * NOTNULL:is not null
	 * ISNULL:is null
	 * BETWEEN:范围查找
	 * IN：in
	 * NOTIN :not in
	 * FETCH:内连接
	 * LEFTFETCH:左连接
	 * <p>BEGINLIKE:等效于 %**</p>
	 * <p>ENDLIKE:等效于 **%</p>
	 * </p> 
	 */
	public enum Type{
		EQ,GT,LT,GE,LE,LIKE,NOTEQ,NOTNULL,BETWEEN,IN,NOTIN,ISNULL,FETCH,LEFTFETCH,BEGINLIKE,ENDLIKE
	}
	
	public SearchHelper(){}
	/**
	 * 默认属性对应 Type.LIKE
	 * @param attr
	 */
	public SearchHelper(String attr){
		this(attr,Type.LIKE);
	}
	public SearchHelper(String attr,Type type){
		this.attr = "o."+attr;
		this.attrValueName = attr;
		this.type = type;
	}
	public String getAttr() {
		return attr;
	}
	
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	public String getAttrValueName() {
		return attrValueName;
	}
	public void setAttrValueName(String attrValueName) {
		this.attrValueName = attrValueName;
	}
	
	
	public LinkType getLinkType() {
		return linkType;
	}
	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
	}
	
	/**
	 * 
	 * @ClassName: SearchHelper.java
	 * @Description: 查询条件组成帮助
	 * @Author:zhucong
	 * @CreateDate:2013-6-7 下午5:51:45
	 * @param attrs 属性名称数组
	 * @param types 属性查询对应类型
	 * @return
	 */
	public static List<SearchHelper> getSearchList(String[] attrs,Type[] types){
		List<SearchHelper> searchList = new ArrayList<SearchHelper>();
		if( null != attrs&& null != types && (attrs.length == types.length)){
			for(int i=0;i<attrs.length;i++){
				SearchHelper s = new SearchHelper(attrs[i],types[i]);
				//s.setAttrValueName(attrs[i]);
				searchList.add(s);
			}
		}
		return searchList;
	}
	
	/**
	   * 拼接sql in语句  例  in(?,?,?,...)
	   * @param domainIds 域id集合
	   * @param item 过滤的数据库字段 有可能过滤domain表 也有可能过滤有domainid的中间表
	   * @return
	   */
	public static String jointInSqlOrHql(List<String> list,String item){
		  StringBuilder sb = new StringBuilder();
		      sb.append(" and ")
			  .append(item)
		      .append(" in (");
			  for(String id:list){
				  sb.append("?,");
			  }
			  //去掉最后一个逗号
			  sb.deleteCharAt(sb.length()-1);
			  sb.append(") ");
		  return sb.toString();
	  }
}
