package com.youlb.dao.common;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.youlb.entity.common.BaseModel;
import com.youlb.entity.common.Model;
import com.youlb.entity.common.Pager;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.OrderHelper;
import com.youlb.utils.helper.SearchHelper;
import com.youlb.utils.helper.SearchHelper.LinkType;
import com.youlb.utils.helper.SearchHelper.Type;



/**
 * 
 * @Title:工具类
 * @Desription:所有dao的父类
 * @ClassName:BaseDaoTemplet.java
 * @CreateDate:2015-6-7 下午5:25:50  
 * @Version:0.1
 */
public class BaseDaoTemplet<T extends Model> extends HibernateDaoSupport{
	public static final Integer DELFLAG = 0;
	/**Log4j日志对象*/
	Logger log=Logger.getLogger(BaseDaoTemplet.class);
	/**实体类对象*/
	private Class<T> entityClass;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	public BaseDaoTemplet(){
		
	}
	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	 
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}
	public void setNamedParameterJdbcTemplate(
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 添加对象
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:26:50
	 * @param object 目标对象
	 * @throws BizException
	 */
	public Serializable add(T object) throws BizException {
		try {
			return super.getHibernateTemplate().save(object);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("添加出错!", e);
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 保存或修改指定的对象，当指定对象的标识为null时，保存对象
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:27:08
	 * @param object 目标对象
	 * @throws BizException
	 */
	public void saveOrUpdate(T object) throws BizException {
		try {
			super.getHibernateTemplate().saveOrUpdate(object);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("更新出错！", e);
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 修改对象
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:27:23
	 * @param object 目标对象
	 * @throws BizException
	 */
	public void update(T object) throws BizException {
		try {
			super.getHibernateTemplate().update(object);
		} catch (Exception e) {
			throw new BizException("修改 出错", e);
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 合并对象。如在一个session中有2个标识相同的对象，把2个对象的属性合并后保存
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:27:36
	 * @param object 目标对象
	 * @throws BizException
	 */
	public void merge(T object) throws BizException {
		try {
			super.getHibernateTemplate().merge(object);
		} catch (Exception e) {
			throw new BizException("修改出错", e);
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 修改指定对象的指定属性值
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:27:54
	 * @param obj 需要修改的对象
	 * @param propertyName 需要修改的属性名称
	 * @param propertyValue 指定修改为的新值
	 * @param sh 指定的修改条件
	 * @throws BizException
	 */
	public int update(T obj,String propertyName,Object propertyValue,SearchHelper sh)throws BizException{
		List<Object> valueList = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder("update ").append(obj.getClass().getName()).append(" o set o.");
		sb.append(propertyName).append(" = ?").append(" where ");
		valueList.add(propertyValue);
		Type type = sh.getType();
		String property = sh.getAttrValueName();//获取属性
		Object value = getPropertyValue(obj, property);
		sb.append(sh.getAttr());
		if(Type.EQ == type){
			sb.append(" = ?");
			valueList.add(value);
		}
		//System.out.println(sb.toString());
		return this.update(sb.toString(), valueList.toArray());	
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 修改指定对象的指定属性值,条件为对象标识
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:28:12
	 * @param obj 需要修改的对象
	 * @param propertyName 需要修改的属性名称
	 * @param propertyValue 指定修改为的新值
	 * @throws BizException
	 */
	public int update(BaseModel obj,String propertyName,Object propertyValue)throws BizException{
		List<Object> valueList = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder("update ").append(obj.getClass().getName()).append(" o set o.");
		sb.append(propertyName).append(" = ?").append(" where ");
		valueList.add(propertyValue);	
		sb.append("o.id = ?");
		valueList.add(obj.getId());
		//System.out.println(sb.toString());
		return this.update(sb.toString(), valueList.toArray());	
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 按条件逻辑删除对象
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:28:28
	 * @param obj 需要删除的对象
	 * @param sh 删除条件
	 * @throws BizException
	 */
	public int deleteLogic(T obj,SearchHelper sh)throws BizException{
		return this.update(obj,"delFlag",DELFLAG,sh);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 根据标识逻辑删除对象
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:29:38
	 * @param id 主键
	 * @throws BizException
	 */
	public int deleteLogic(Serializable id)throws BizException{
		String hql = "update "+entityClass.getName()+" o set o.delFlag = "+DELFLAG +" where o.id=?";
		if(id==null||StringUtils.isBlank(id.toString())){
			return -1;
		}
		try{
			return update(hql, new Object[]{id});
		}catch (Exception e) {
			throw new BizException("删除对象失败!",e);
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 批量逻辑删除对象
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:29:55
	 * @param ids 主键集合
	 * @throws BizException
	 */
	public void deleteLogic(Serializable[] ids)throws BizException{
		if(ids==null||ids.length==0){
			throw new BizException("请选择您要删除的记录！");
		}
		for (Serializable id : ids) {
			deleteLogic(id);
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 根据id获取对象信息
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:30:13
	 * @param id 主键
	 * @return
	 * @throws BizException
	 */
	public T get(Serializable id) throws BizException {
		try {
			if(id==null||StringUtils.isBlank(id.toString())){
				throw new BizException("请选择你要获取详细信息的记录!");
			}
			T t = (T)super.getHibernateTemplate().get(entityClass, id);
			if(t==null){
				throw new BizException("您查询的记录已经不存在！");
			}
			return t;
		} catch (Exception e) {
			if(e instanceof BizException){
				throw (BizException)e;
			}else{
				throw new BizException("--------------- get" + entityClass.getName() + " by id=" + id + " false", e);
			}
		}
	}

	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 根据id物理删除对象
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:30:24
	 * @param id 主键
	 * @throws BizException
	 */
	public void delete(Serializable id) throws BizException {
		try {
			Object obj = get(id);
			if(obj!=null){
				super.getHibernateTemplate().delete(obj);
			}
		} catch (Exception e) {
			throw new BizException("--------------- delete " + entityClass.getName() + " by id=" + id + " false", e);
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 批量删除记录。物理删除
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:30:37
	 * @param ids 主键集合
	 * @throws BizException
	 */
	public void delete(Serializable[] ids)throws BizException{
		if(ids==null||ids.length==0){
			throw new BizException("请选择您要删除的记录！");
		}
		for (Serializable id : ids) {
			delete(id);
		}
	}

	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 快速删除对象，采取直接的delete 语句执行 物理删除
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:30:53
	 * @param obj 条件值
	 * @param propertyName 删除条件
	 * @throws BizException
	 */
	public int delete(Serializable obj,String propertyName)throws BizException{
		String hql = "delete "+entityClass.getName()+" where "+propertyName+" =?";
		try {
			return this.update(hql, new Object[] { obj });
		} catch (Exception e) {
			throw new BizException("--------------- delete " + entityClass.getName() + " by propertyName=" + propertyName + " false", e);
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 批量查询
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:31:17
	 * @param hql HQL语句
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("rawtypes")
	public List find(String hql) throws BizException {
		try {
			return super.getHibernateTemplate().find(hql);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("--------------- execute " + hql + " flase", e);
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 单个查询
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:31:32
	 * @param hql HQL语句
	 * @param values 参数集合
	 * @return
	 * @throws BizException
	 */
	public T findObject(String hql,Object[] values)throws BizException{
		try {
			Session session = super.getSession();
			Query query = session.createQuery(hql);
			int i=0;
			if(values!=null){
				for (Object object : values) {
					query.setParameter(i, object);
					i++;
				}
			}
			return (T)query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException("--------------- 查询单个对象失败",e);
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 分页查询
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:31:57
	 * @param queryString HQL语句
	 * @param pager 分页对象
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("rawtypes")
	public List pageFind(final String queryString, final Pager pager) throws BizException {
		return pageFind(queryString, null, pager);
	}

	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 分页查询
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:33:16
	 * @param queryString HQL语句
	 * @param values 参数集合
	 * @param pager 分页对象
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List pageFind(final String queryString, final Object[] values, final Pager pager) throws BizException {
		try {
			return (List) super.getHibernateTemplate().execute(new HibernateCallback<List>() {
				public List<T> doInHibernate(Session session) throws HibernateException {
					Query queryObject = session.createQuery(queryString);
					if(pager!=null&&pager.getPageSize()!=0){
						try {
							pager.setTotalRows(getCount(queryString,values));
						} catch (BizException e) {
							//e.printStackTrace();
							throw new HibernateException("--查询记录总数失败--",e);
						}
						queryObject.setFirstResult(pager.getStartRow());
						queryObject.setMaxResults(pager.getPageSize());
					}
					setParamList(queryObject,values);
					List list = queryObject.list();
					if(!list.isEmpty()){
						//设置分页组件对象
						Object obj = list.get(0);
						if(obj instanceof Object[]) {
							Object[] objArrs = (Object[])obj;
							for(Object searchObj : objArrs) {
								if(searchObj instanceof Model) {
									setObjPager(searchObj, pager);
									break;
								}
							}
						} else if(obj instanceof Model) {
							setObjPager(obj, pager);
						}
					}
					return list;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException("--------------- 分页查询" + queryString + " 出错",e);
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 设置分页对象
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:33:55
	 * @param obj 目标对象
	 * @param pager 分页对象
	 */
	private void setObjPager(Object obj, Pager pager) {
		if(obj instanceof Model){
			try {
				//System.out.println("className:"+obj.getClass());
				Method method = obj.getClass().getMethod("setPager", Pager.class);
				method.invoke(obj, pager);
				if(pager!=null){
					//System.out.println("pager:"+((Model)obj).getPager().getTotalRows());
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}
	
	private void setParamList(Query query, Map<String, Object> params){
		if (params != null && !params.isEmpty()) {
			Iterator iter = params.keySet().iterator();
			while (iter.hasNext()) {
				String parameterName = (String) iter.next();
				if (params.get(parameterName) instanceof List<?>) {
					query.setParameterList(parameterName, (List<String>) params.get(parameterName));
				} else if (params.get(parameterName) instanceof Date) { 
					query.setTimestamp(parameterName, (Date) params
							.get(parameterName));
				} else {
					query.setParameter(parameterName, params
							.get(parameterName));
				}
			}
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 参数设置
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:34:32
	 * @param query query对象
	 * @param values 参数集合
	 */
	private void setParamList(Query query,Object[] values){
		if (values != null) {
			//参数下标
			int index = 0;
			for (int i = 0; i < values.length; i++) {
				Object obj = values[i];
				if(obj instanceof List){
					//如果参数类型为集合类型，进行循环赋值
					List params = (List)obj;
					for (Object param : params) {
						query.setParameter(index, param);
						index++;
					}
				}else{
					query.setParameter(index, obj);
					index++;
				}
			}
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 执行指定的查询语句
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:34:58
	 * @param hql HQL语句
	 * @param values 参数集合
	 * @return
	 * @throws BizException
	 */
	public List find(String hql, Object[] values) throws BizException {
		try {
			return pageFind(hql, values,null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException("--------------- execute " + hql + " false", e);
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 执行指定的查询语句
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:36:06
	 * @param hql HQL语句
	 * @param currPage 当前页
	 * @param pageSize 每页行数
	 * @return
	 * @throws BizException
	 */
	public List find(String hql,int currPage,int pageSize) throws BizException {
		try {
			Pager pager = new Pager();
			pager.setStartRow(pageSize);
			pager.setPageSize(pageSize);
			return pageFind(hql, null,pager);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("--------------- execute " + hql + " false", e);
		}
	}
	
	public List find(String hql,Object[] values,int currPage,int pageSize) throws BizException {
		try {
			Pager pager = new Pager();
			pager.setStartRow(pageSize);
			pager.setPageSize(pageSize);
			return pageFind(hql, values,pager);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("--------------- execute " + hql + " false", e);
		}
	}
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 执行指定的修改语句。该方法中的hql语句必须为delete或者update
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:36:38
	 * @param hql 执行语句
	 * @param values hql参数
	 * @return
	 * @throws BizException
	 */
	public int update(String hql, Object[] values) throws BizException {
		try {
			Session session = this.getSession();
			Query query = session.createQuery(hql);
//			if (values != null) {
//				for (int i = 0; i < values.length; i++) {
//					query.setParameter(i, values[i]);
//				}
//			}
			setParamList(query, values);
			return query.executeUpdate();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("--------------- execute " + hql + " flase", e);
		}
	}
	
	/**
	 * 修改并清空session
	 * @param hql 执行语句
	 * @param values hql参数
	 * @return
	 * @throws BizException
	 */
	public int updateAndClearSession(String hql, Object[] values) throws BizException {
		
		try {
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			setParamList(query, values);
			int result = query.executeUpdate();
			session.clear();
			return result;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("--------------- execute " + hql + " flase", e);
		}
	}
	
	public int executeSql(String sql,Object[] values)throws BizException{
		try {
			Session session = this.getSession();
			SQLQuery query = session.createSQLQuery(sql);
			setParamList(query, values);
			return query.executeUpdate();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("--------------- execute " + sql + " false", e);
		}
	}
	
	public List executeQuerySql(String sql,Object[] values,Class entityClass)throws BizException{
		try {
			Session session = this.getSession();
			SQLQuery query = session.createSQLQuery(sql);
			if(entityClass!=null){
				query.addEntity(entityClass);
			}
			setParamList(query, values);
			return query.list();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("--------------- execute " + sql + " false", e);
		}
	}
	
	public List<T> executeQuerySql(String sql,Object[] values,T entityClass)throws BizException{
		try {
			Session session = this.getSession();
			SQLQuery query = session.createSQLQuery(sql);
		 
			setParamList(query, values);
			return query.list();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("--------------- execute " + sql + " false", e);
		}
	}
	 
	public List executeQuerySql(String sql )throws BizException{
		try {
			Session session = this.getSession();
			SQLQuery query = session.createSQLQuery(sql);
			return query.list();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("--------------- execute " + sql + " false", e);
		}
	}
	
	public List executeQuerySql(String sql,Object[] values)throws BizException{
		try {
			Session session = this.getSession();
		 
			SQLQuery query = session.createSQLQuery(sql);
		 
			if(values!=null){
				setParamList(query, values);
			}
			return query.list();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("--------------- execute " + sql + " false", e);
		}
	}
	
	public List executeQuerySqlMap(String sql,Object[] values)throws BizException{
		try {
			Session session = this.getSession();
		 
			SQLQuery query = session.createSQLQuery(sql);
		 
			if(values!=null){
				setParamList(query, values);
			}
			 
			return query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("--------------- execute " + sql + " false", e);
		}
	}
	
	public List executeQuerySqlMap(String sql,Map<String,Object> values)throws BizException{
		try {
			Session session = this.getSession();
		 
			SQLQuery query = session.createSQLQuery(sql);
		 
			if(values!=null){
				setParamList(query, values);
			}
			 
			return query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("--------------- execute " + sql + " false", e);
		}
	}
	
	/***
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description:  sql查询 并封装对象
	 * @Author:JJB
	 * @CreateDate:2014-8-4 下午5:24:54
	 * @param sql
	 * @param values
	 * @param objectClass
	 * @return
	 * @throws BizException
	 */
	public <T> List<T> executeQuerySqlMap(String sql,Object[] values,Class<T> objectClass,Integer bigDecimalLength)throws BizException{
		try {
			Session session = this.getSession();
		 
			SQLQuery query = session.createSQLQuery(sql);
			 
			if(values!=null){
				setParamList(query, values);
			}
			List list = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			List<T> returnList = new ArrayList<T>(); 
			for (Object object : list) {
					Map<String,Object> map = (Map<String, Object>) object;
					returnList.add(setObject(map, objectClass,2));
				}
			  return  returnList;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("--------------- execute " + sql + " false", e);
		}
	}
	public <T> List<T> executeQuerySqlMap(String sql,Object[] values,Class<T> objectClass)throws BizException{
	
		return executeQuerySqlMap(sql, values, objectClass,2);
	 }
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: TODO
	 * @Author:JJB
	 * @CreateDate:2014-8-4 下午5:41:36
	 * @param map
	 * @param objectClass
	 * @param bigDecimalLength 保留多少位小数
	 * @return
	 */
	public  <T>  T setObject( Map<String, Object> map ,Class<T> objectClass ,Integer bigDecimalLength){
		   String methodNameSet ="";
			 String methodNameGet =""; 
		   try {
			   T object = objectClass.newInstance();
				for (String key :  map.keySet()) {
					     methodNameSet = "set" +key.substring(0, 1).toUpperCase() + key.substring(1);
					   methodNameGet = "get" +key.substring(0, 1).toUpperCase() + key.substring(1);
					 Method	getmethod = objectClass.getMethod(methodNameGet);
					 Method setmethod = objectClass.getMethod(methodNameSet, getmethod.getReturnType());
					 if(map.get(key)==null){
						 continue;
					 }
					 if(getmethod.getReturnType().equals(BigDecimal.class)){
						 setmethod.invoke(object, new BigDecimal(map.get(key).toString()).setScale(bigDecimalLength, BigDecimal.ROUND_HALF_UP));
					 }else if(getmethod.getReturnType().equals(Integer.class)){
						  setmethod.invoke(object, ((Number) map.get(key)).intValue() );
					 }else if (getmethod.getReturnType().equals(Double.class)){
						 setmethod.invoke(object, ((Number) map.get(key)).doubleValue() );
					 }  else{
						 setmethod.invoke(object, map.get(key).toString());
					 }
				 }
			  return object;
		} catch (Exception e) {
			System.out.println(methodNameGet);
			e.printStackTrace();
		}
		return null;  
	}
	
	public List executeQuerySql(String sql,Map<String, Object> params)throws BizException{
		try {
			Session session = this.getSession();
			SQLQuery query = session.createSQLQuery(sql);
			setParamList(query, params);
			return query.list();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BizException("--------------- execute " + sql + " false", e);
		}
	}
	
	public List executeJdbcTemplateQuery(String sql,Object[] values,RowMapper<?> rowMapper)throws BizException{
		try{
			return jdbcTemplate.query(sql, values, rowMapper);
		}catch(Exception e){
			e.printStackTrace();
			throw new BizException("------------execute "+sql+" false",e);
		}
	}
	
	public List<Map<String,Object>> executeJdbcTemplateQuery(String sql,Object[] values)throws BizException{
		try{
			 
			return jdbcTemplate.queryForList(sql, values);
		}catch(Exception e){
			throw new BizException("------------execute "+sql+" false",e);
		}
	}
	
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description:  批量执行sql语句
	 * @Author:JJB
	 * @CreateDate:2014-7-17 下午4:44:27
	 * @param sql  执行sql语句
	 * @param values  
	 * @return   返回数组长度与Map value长度一样 受影响的行的每个更新的批处理中的数字
	 * @throws BizException
	 */
	public int[] executenamedParameterJdbcTemplateBatchUpdate(String sql,Map<String, Object>[] values)throws BizException{
		try{
			return 	namedParameterJdbcTemplate.batchUpdate(sql, values);
		}catch(Exception e){
			e.printStackTrace();
			throw new BizException("------------execute "+sql+" false",e);
		}
	}
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 执行sql语句
	 * @Author:JJB
	 * @CreateDate:2014-7-22 上午11:36:53
	 * @param sql
	 * @param values
	 * @return
	 * @throws BizException
	 */
	public int executenamedParameterJdbcTemplateUpdate(String sql,Map<String, Object> values)throws BizException{
		try{
			return	namedParameterJdbcTemplate.update(sql, values);
		 }catch(Exception e){
			e.printStackTrace();
			throw new BizException("------------execute "+sql+" false",e);
		}
	}
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 修改指定对象的属性值
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:37:27
	 * @param property 属性集合
	 * @param values 参数集合
	 * @param id 主键
	 * @param objectClass 目标Class对象
	 * @param idName id名称
	 */
	private void update(String[] property,Object[] values,Serializable id,Class objectClass,String idName){
		
	}

	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 返回指定查询语句的记录数量
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:38:12
	 * @param hql HQL语句
	 * @param values 参数集合
	 * @return
	 * @throws BizException
	 */
	public Integer getCount(String hql, Object[] values) throws BizException {
		try {
			//获取count Hql语句
			int begin = hql.indexOf("from");
			int end = hql.indexOf(" order ");
			StringBuilder hqlCount = new StringBuilder("select count(*) ");
			if(end==-1){
				hqlCount.append(hql.substring(begin));
			}else{
				hqlCount.append(hql.substring(begin,end));
			}
			String hqlCountStr = hqlCount.toString().replace("fetch", " ");
			////System.out.println("hqlCount = "+ hqlCountStr);
			Long beginTime = System.currentTimeMillis();
			
			List list =find(hqlCountStr, values);
			Long endTime = System.currentTimeMillis();
			//System.out.println("count执行时间="+(endTime-beginTime));
			int countNumber=0;
			if(!list.isEmpty()) {
				if(hql.indexOf("group by") != -1) {
					countNumber=list.size();
				} else {
					countNumber = ((Number) list.get(0)).intValue();
				}
			}
			return countNumber;
//			return ((Number) find(hqlCountStr, values).get(0)).intValue();
		} catch (Exception e) {
			throw new BizException("--------------- get count " + hql + " false", e);
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 按条件获取指定对象的纪录总数
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:38:34
	 * @param entity 实体类对象
	 * @param shs 查询帮助集合
	 * @return
	 * @throws BizException
	 */
	public Integer getCount(Object entity,List<SearchHelper> shs)throws BizException{
		List<Object> valueList = new ArrayList<Object>();
		OrderHelper helper = null;
		String hql = getQueryHql(entity, shs, helper, valueList );
		return getCount(hql, valueList.toArray());
	}
	
	public List find(String selectClause, String fromClause, Object[] values, OrderHelper orderHelper, Pager pager) throws BizException {
		return find(selectClause, fromClause, values, orderHelper, pager, null, null, null);
	}
	
	public List find(String selectClause, String fromClause, Object[] values, OrderHelper orderHelper, Pager pager, Class entityClass) throws BizException {
		return find(selectClause, fromClause, values, orderHelper, pager, entityClass, null, null);
	}
	
	 
	public List find(String selectClause, String fromClause, Object[] values, OrderHelper orderHelper, Pager pager, RowMapper<?> rowMapper) throws BizException {
		return find(selectClause, fromClause, values, orderHelper, pager, null, rowMapper, null);
	}
	
	public List find(String selectClause, String fromClause, Map<String, Object> params, OrderHelper orderHelper, Pager pager) throws BizException {
		return find(selectClause, fromClause, null, orderHelper, pager, null, null, params);
	}
	
	public List find(String selectClause, String fromClause, Map<String, Object> params, OrderHelper orderHelper, Pager pager, Class entityClass) throws BizException {
		return find(selectClause, fromClause, null, orderHelper, pager, entityClass, null, params);
	}
	
	public List find(String selectClause, String fromClause, Map<String, Object> params, OrderHelper orderHelper, Pager pager, RowMapper<?> rowMapper) throws BizException {
		return find(selectClause, fromClause, null, orderHelper, pager, null, rowMapper, params);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 分页查询SQL
	 * @Author:Squall
	 * @CreateDate:2014-7-30 下午12:17:21
	 * @param selectClause select语句，不包含from的表连接语句和条件语句
	 * @param fromClause from语句，从from开始的表连接语句和条件语句
	 * @param values 参数
	 * @param orderHelper 排序对象
	 * @param pager 分页对象
	 * @param entityClass 每行映射的对象类
	 * @param rowMapper
	 * @return
	 * @throws BizException
	 */
	public List find(String selectClause, String fromClause, Object[] values, OrderHelper orderHelper, Pager pager, Class entityClass, RowMapper<?> rowMapper, Map<String, Object> params) throws BizException {
		if(pager!=null&&pager.getPageSize()!=0){
			if(params != null) {
				pager.setTotalRows(getCountForSql(fromClause, params));
			} else {
				pager.setTotalRows(getCountForSql(fromClause, values));
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ( ");
		sb.append("select ROW_NUMBER() over(order by ");
		sb.append(orderHelper.getPropertyName().substring(2));
		if(orderHelper.getType() == OrderHelper.Type.ASC) {
			sb.append(" asc ");
		} else {
			sb.append(" desc ");
		}
		sb.append(") as rowNum,* from ( ");
		sb.append(selectClause);
		sb.append(" ");
		sb.append(fromClause);
		sb.append(" ) as _temp_ ) as _res_ ");
		sb.append("where _res_.rowNum between ");
		sb.append(pager.getStartRow());
		sb.append(" and " + pager.getCurrentPage()*pager.getPageSize());
		if(entityClass != null) {
			return executeQuerySql(sb.toString(), values, entityClass);
		}
		if(rowMapper != null) {
			if(params != null) {
				
			}
			return executeJdbcTemplateQuery(sb.toString(), values, rowMapper);
		}
		if(params != null) {
			return executeQuerySql(sb.toString(), params);
		}
		return executeQuerySql(sb.toString(), values);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 分页查询SQL 返回对象
	 * @Author:JJB
	 * @CreateDate:2014-8-4 下午5:12:00
     * @param selectClause select语句，不包含from的表连接语句和条件语句
	 * @param fromClause from语句，从from开始的表连接语句和条件语句
	 * @param values 参数
	 * @param orderHelper 排序对象
	 * @param pager 分页对象
	 * @param T 每行映射的对象类
	 * @param params
	 * @return
	 * @throws BizException
	 */
	public  <T> List<T> findsqlReturnT(String selectClause, String fromClause, Object[] values, OrderHelper orderHelper, Pager pager, Class<T> objectClass) throws BizException {
		 
    	return findsqlReturnT(selectClause, fromClause, "", values, orderHelper, pager, objectClass);
	 
		 
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 分页查询SQL 返回对象
	 * @Author:JJB
	 * @CreateDate:2014-8-4 下午5:12:00
     * @param selectClause select语句，不包含from的表连接语句和条件语句
	 * @param fromClause from语句，从from开始的表连接语句和条件语句
	 * @param withClause with表达式
	 * @param values 参数
	 * @param orderHelper 排序对象
	 * @param pager 分页对象
	 * @param T 每行映射的对象类
	 * @param params
	 * @return
	 * @throws BizException
	 */
	public  <T> List<T> findsqlReturnT(String selectClause, String fromClause, String withClause, Object[] values, OrderHelper orderHelper, Pager pager, Class<T> objectClass) throws BizException {
		if(pager!=null&&pager.getPageSize()!=0){
			pager.setTotalRows(getCountForSql(fromClause,withClause, values));
		}
		StringBuilder sb = new StringBuilder();
		sb.append(withClause);
		sb.append("select * from ( ");
		sb.append("select ROW_NUMBER() over(order by ");
		sb.append(orderHelper.getPropertyName().substring(2));
		if(orderHelper.getType() == OrderHelper.Type.ASC) {
			sb.append(" asc ");
		} else {
			sb.append(" desc ");
		}
		sb.append(") as rowNum,* from ( ");
		sb.append(selectClause);
		sb.append(" ");
		sb.append(fromClause);
		sb.append(" ) as _temp_ ) as _res_ ");
		sb.append("where _res_.rowNum between ");
		sb.append(pager.getStartRow());
		sb.append(" and " + pager.getCurrentPage()*pager.getPageSize());
		 
    	return   executeQuerySqlMap(sb.toString(), values, objectClass);
	 
		 
	}
	
	public String getOrderByPart(String sql) {  
        String loweredString = sql.toLowerCase();  
        int orderByIndex = loweredString.indexOf("order by");  
        if (orderByIndex != -1) {  
            // if we find a new "order by" then we need to ignore  
            // the previous one since it was probably used for a subquery  
            return sql.substring(orderByIndex);  
        } else {  
            return "";  
        }  
    }
	
	public Integer getCountForSql(String sql, String withClause, Object[] values) {
		int count = 0;
		StringBuilder sb = new StringBuilder();
		sb.append(withClause);
		sb.append("select count(*) ");
		if(sql.contains("order by")){
			sb.append(sql.substring(sql.indexOf("from"),sql.lastIndexOf("order by")-1));
		}else{
			sb.append(sql.substring(sql.indexOf("from")));
		}

		count = jdbcTemplate.queryForObject(sb.toString(),values,Integer.class);
		return count;
	}
	
	public Integer getCountForSql(String sql, Object[] values) {
		return getCountForSql(sql, "", values);
	}
	
	public Integer getCountForSql(String sql, Map<String, Object> params) {
		int count = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1) ");
		sb.append(sql);
		Session session = this.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		setParamList(query, params);
		count = (Integer)query.uniqueResult();
		return count;
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 按照指定的条件查询符合条件的记录
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:38:53
	 * @param obj 查询对象
	 * @param sh 查询条件
	 * @return 第一个查询对象
	 * @throws BizException
	 */
	public T findObject(T obj,SearchHelper sh)throws BizException{
		List<SearchHelper> shs = new ArrayList<SearchHelper>();
		shs.add(sh);
		return findObject(obj, shs);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 按照指定的条件查询符合条件的记录
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:39:19
	 * @param obj 查询对象
	 * @param shs 查询条件
	 * @return 第一个查询对象
	 * @throws BizException
	 */
	public T findObject(T obj,List<SearchHelper> shs)throws BizException{
		OrderHelper helper = null;
		Pager pager = new Pager();
		pager.setPageSize(1);
		List list = find(obj,shs,helper,pager);
		if(list!=null&&list.size()>0){
			return (T)list.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 跟据指定的条件分页查询符合条件的记录
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:39:41
	 * @param obj 查询对象
	 * @param shs 查询条件
	 * @param orderHelper 排序帮助对象
	 * @param pager 分页对象
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("rawtypes")
	public List<T> find(T obj,List<SearchHelper> shs,OrderHelper orderHelper,Pager pager)throws BizException{
		try{
			List<Object> valueList = new ArrayList<Object>();	
			String hql = getQueryHql(obj, shs, orderHelper,valueList);
			return this.pageFind(hql, valueList.toArray(),pager);
		}catch (Exception e) {
		 
			log.error(e);
			throw new BizException("按条件查询对象出错",e);
		}
	}
	
	public List<T> find(T obj,List<SearchHelper> shs,List<OrderHelper> orderHelperList,Pager pager)throws BizException{
		return find(obj, shs, orderHelperList, pager, false);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 跟据指定的条件分页查询符合条件的记录
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:40:10
	 * @param obj 查询对象
	 * @param shs 查询条件
	 * @param orderHelperList 排序帮助对象集合
	 * @param pager 分页对象
	 * @param isDistinct 是否唯一
	 * @return
	 * @throws BizException
	 */
	public List<T> find(T obj,List<SearchHelper> shs,List<OrderHelper> orderHelperList,Pager pager, boolean isDistinct)throws BizException{
		try{
			List<Object> valueList = new ArrayList<Object>();	
			String hql = getQueryHql(obj, shs, orderHelperList,valueList, isDistinct);
			//List list = this.pageFind(hql, valueList.toArray(),pager);
			return this.pageFind(hql, valueList.toArray(),pager);
		}catch (Exception e) {
			throw new BizException("按条件查询对象出错",e);
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 跟据指定的条件查询符合条件的记录
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:40:48
	 * @param obj 查询对象
	 * @param shs 查询条件
	 * @param orderHelper 排序帮助对象
	 * @return
	 * @throws BizException
	 */
	public List<T> find(T obj,List<SearchHelper> shs,OrderHelper orderHelper)throws BizException{
		return find(obj, shs, orderHelper,null);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 跟据指定的条件查询符合条件的记录
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:41:35
	 * @param obj 查询对象
	 * @param shs 查询条件
	 * @return
	 * @throws BizException
	 */
	public List<T> find(T obj,List<SearchHelper> shs)throws BizException{
		return find(obj, shs, null);
	}
	
	private String getQueryHql(Object obj,List<SearchHelper> shs,List<OrderHelper> orderHelperList,List<Object> valueList) throws BizException{
		return getQueryHql(obj, shs, orderHelperList, valueList, false);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 获取HQL语句
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:42:32
	 * @param obj 查询对象
	 * @param shs 查询条件
	 * @param orderHelperList 排序帮助对象集合
	 * @param valueList 参数值集合
	 * @param isDistinct 是否唯一
	 * @return
	 * @throws BizException 
	 */
	private String getQueryHql(Object obj,List<SearchHelper> shs,List<OrderHelper> orderHelperList,List<Object> valueList, boolean isDistinct) throws BizException{
		if(obj==null){
			throw new BizException("obj为空");
		}
		Class cl = obj.getClass();
		StringBuilder sb = new StringBuilder();
		if(isDistinct) {
			sb.append("select distinct o ");
		}
		sb.append("from "+cl.getName()+" o ");
		boolean isfirst = true;
		try{
			//设置查询语句
			if(shs!=null){
				for (SearchHelper sh : shs) {
					Type type = sh.getType();
					String property = sh.getAttr();//获取属性
					//获取条件查询连接类型
					LinkType linkType = sh.getLinkType();
					String linkStr = " and ";
					if(linkType.equals(LinkType.OR)){
						linkStr = " or ";
					}
					//fetch，left fetch连接处理
					if(Type.FETCH==type||Type.LEFTFETCH==type){
						if(Type.FETCH==type){
							sb.append(" join fetch ").append(property);
						}else{
							sb.append(" left join fetch ").append(property);
						}
						continue;
					}else if(isfirst){
						sb.append(" where 1=1 ");
						isfirst = false;
					}
							
					String propertyValueName = sh.getAttrValueName();
					Object propertyValue = getPropertyValue(obj, propertyValueName);//获取属性值
					
					//如果属性值为空且查询的条件不为该属性值不为is not null 则 跳出
					////System.out.println(propertyValue+"..");
					if(propertyValue==null&Type.NOTNULL!=type&Type.BETWEEN!=type&Type.IN!=type&Type.ISNULL!=type&Type.NOTIN!=type){
						continue;
					}
					if(Type.BETWEEN!=type&&Type.IN!=type&Type.NOTIN!=type){
						sb.append(linkStr).append(property);
					}
					if(Type.EQ == type){
						sb.append(" =?");
					}else if(Type.GT == type){
						sb.append(" > ?");
					}else if(Type.LT == type){
						sb.append(" < ?");
					}else if(Type.GE == type){
						sb.append(" >= ?");
					}else if(Type.LIKE == type){
						sb.append(" like ?");
						propertyValue = "%"+propertyValue+"%";
					}else if(Type.BEGINLIKE == type){
						sb.append(" like ?");
						propertyValue = "%"+propertyValue;
					}else if(Type.ENDLIKE == type){
						sb.append(" like ?");
						propertyValue = propertyValue+"%";
					}else if(Type.LE == type){
						sb.append(" <=?");
					}else if(Type.NOTEQ == type){
						sb.append(" !=?");
					}else if(Type.ISNULL == type){
						sb.append(" is null ");
						propertyValue = null;
					}
					else if(Type.NOTNULL == type){
						sb.append(" is not null");
						propertyValue = null;
					}else if(Type.BETWEEN == type){
						propertyValue = null;
						String minProperty = propertyValueName+"Min";
						String maxProperty=propertyValueName+"Max";
						Object minpropertyValue = getPropertyValue(obj, minProperty);
						Object maxpropertyValue = getPropertyValue(obj, maxProperty);
						if(minpropertyValue!=null&maxpropertyValue!=null){
							sb.append(linkStr).append(property);
							sb.append(" between ? and ?");
							valueList.add(minpropertyValue);
							valueList.add(maxpropertyValue);
						}else if(minpropertyValue!=null){
							sb.append(linkStr).append(property);
							sb.append(" >=?");
							valueList.add(minpropertyValue);
						}else if(maxpropertyValue!=null){
							sb.append(linkStr).append(property);
							sb.append(" <=?");
							valueList.add(maxpropertyValue);
						}
					}else if(Type.IN == type){
						propertyValue = null;
						String inProperty = propertyValueName+"List";
						List inpropertyValue = (List)getPropertyValue(obj, inProperty);
						if(inpropertyValue!=null&&inpropertyValue.size()>0){
							//处理oracle中in不能超过1000的情况
							sb.append(linkStr).append("(");
							int size = inpropertyValue.size();
							int incount = size/1000;
							int count = size%1000;
							if(incount>0){
								for(int i=1;i<=incount;i++){
									if(i!=1){
										sb.append(" or ");
									}
									sb.append(property).append(" in (");
									for(int j=0;j<1000;j++){
										if(j!=999){
											sb.append(" ?,");
										}else{
											sb.append(" ? ");
										}
									}
									sb.append(")");
								}
								if(count!=0){
									sb.append(" or ").append(property).append(" in (");
									for(int i=1;i<=count;i++){
										if(i!=count){
											sb.append(" ?,");
										}else{
											sb.append(" ? ");
										}
									}
									sb.append(")");
								}
							}else{
								sb.append(property).append(" in (");
								for(int i=1;i<=count;i++){
									if(i!=count){
										sb.append(" ?,");
									}else{
										sb.append(" ? ");
									}
								}
								sb.append(")");
							}
							sb.append(")");
							/*sb.append(linkStr);
							sb.append(" (");
							int size = inpropertyValue.size()-1;
							for(int i=0;i<=size;i++){
								if(i!=size){
									sb.append(property).append(" = ? or ");
								}else{
									sb.append(property).append(" = ? ");
								}
							}
							sb.append(")");*/
							valueList.add(inpropertyValue);
						}
					}else if(Type.NOTIN == type){
						propertyValue = null;
						String inProperty = propertyValueName+"List";
						List inpropertyValue = (List)getPropertyValue(obj, inProperty);
						if(inpropertyValue!=null&&inpropertyValue.size()>0){
							sb.append(linkStr).append(property);
							sb.append(" not in (");
							int size = inpropertyValue.size()-1;
							for(int i=0;i<=size;i++){
								if(i!=size){
									sb.append(" ?,");
								}else{
									sb.append(" ? ");
								}
							}
							sb.append(")");
							valueList.add(inpropertyValue);
						}
					}
					if(propertyValue!=null){
						valueList.add(propertyValue);
					}
				}
			}
			if(orderHelperList!=null&&orderHelperList.size()!=0){
				sb.append(" order by ");
				for (OrderHelper orderHelper : orderHelperList) {
					if(OrderHelper.Type.ASC == orderHelper.getType())
						sb.append(orderHelper.getPropertyName()).append(" asc ,");
					else
						sb.append(orderHelper.getPropertyName()).append(" desc ,");
				}
				if(sb.lastIndexOf(",")!=-1){
					return sb.substring(0, sb.lastIndexOf(","));
				}
			}
			
			return sb.toString();
		}catch (Exception e) {
			throw new BizException("封装查询语句出错",e);
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 获取执行的hql语句
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:43:23
	 * @param obj 查询对象
	 * @param shs 查询条件
	 * @param orderHelper 排序帮助对象
	 * @param valueList 参数值集合
	 * @return
	 * @throws BizException 
	 */
	private String getQueryHql(Object obj,List<SearchHelper> shs,OrderHelper orderHelper,List<Object> valueList) throws BizException{
		List<OrderHelper> orderHelperList = new ArrayList<OrderHelper>();
		if(orderHelper!=null){
			orderHelperList.add(orderHelper);
		}
		return getQueryHql(obj, shs, orderHelperList, valueList);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 获取指定对象的指定属性值
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:44:45
	 * @param obj 指定的javaBean对象
	 * @param property 指定的属性。请保证在指定对象下存在get...（）方法
	 * @return
	 * @throws BizException 
	 */
	@SuppressWarnings("unchecked")
	private Object getPropertyValue(Object obj,String property) throws BizException{
		String[] propertyChain = property.split("\\.");//获取属性链
		////System.out.println("propertyChain:"+propertyChain.length);
		Object result = null;
		for (String pro : propertyChain) {
			////System.out.println("属性："+pro);
			if(obj==null){
				break;
			}
			String methodName = "get"+pro.substring(0,1).toUpperCase()+pro.substring(1);
			Class cl = obj.getClass();
			try {
				Method method = cl.getMethod(methodName, null);
				result = method.invoke(obj, null);
				////System.out.println(methodName+":"+result);
				if(result!=null){
					String resultStr = result.toString().trim();
					if(!resultStr.equals("")){
						obj = result;
						continue;
					}
				}
				result = null;
				obj = null;
			} catch (Exception e) {
				//e.printStackTrace();
				throw new BizException("不存在该方法 "+methodName);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoTemplet.java
	 * @Description: 修改排序
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:45:07
	 * @param ids 主键集合
	 * @param nums 展现次序
	 * @throws BizException 
	 */
	public void updateGradation(Serializable[] ids,int[] nums) throws BizException{
		String hql = "update "+ entityClass.getName()+" o set o.gradation =? where o.id=?";
		for(int i =0;i<ids.length;i++){
			update(hql, new Object[]{nums[i],ids[i]});
		}
	}
}
