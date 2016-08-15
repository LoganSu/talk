package com.youlb.dao.common;


import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.youlb.entity.common.Model;
import com.youlb.entity.common.Pager;
import com.youlb.utils.exception.BizException;


/**
 * 
 * @ClassName: BaseDaoBySql 
 * @Description: SQL查询类 
 * @author: pengjy 
 * @date: 2015年3月12日
 *
 */
public class BaseDaoBySql<T extends Model> extends BaseDaoTemplet<T> {
	/***
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: SQL查询类O
	 * @CreateDate:2013-9-17 上午10:15:43
	 * @param listData 数据列
	 * @param propertyName 属性名称
	 * @param myClass 类
	 * @return
	 * @throws Exception
	 */
	public <E> List<E> reflectDate(List<Object[]> listData ,List<String> propertyName, Class<E> myClass) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<E> returnList = new ArrayList<E>();

		for (Object[] dataArray : listData) {
			E entity = myClass.newInstance();
			
			for (int i = 0; i < propertyName.size(); i++) {
				String name = propertyName.get(i);
				Object value =dataArray[i];
				
				if(value==null){
					continue;
				}
				
				String getmethodName = "get"+name.substring(0,1).toUpperCase()+name.substring(1);
				Method getMethod=entity.getClass().getMethod(getmethodName);
				Class<?> paramType = getMethod.getReturnType();
				 
				String setmethodName = "set"+name.substring(0,1).toUpperCase()+name.substring(1);
				Method setMethod =	entity.getClass().getMethod(setmethodName,paramType);
				try {
				 if(paramType.equals(Integer.class)){
						 
						setMethod.invoke(entity,Integer.parseInt(value.toString()));
					} else if(paramType.equals(Double.class)) {
					 
						setMethod.invoke(entity, Double.parseDouble(value.toString()));
					} else if(paramType.equals(Float.class)) {
					 
						setMethod.invoke(entity, Float.parseFloat(value.toString()));
					} else if(paramType.equals(Date.class)) {
				 
						setMethod.invoke(entity,sdf.parse(value.toString()));
					} else if(paramType.equals(Boolean.class)) {
					 
						setMethod.invoke(entity,Boolean.parseBoolean(value.toString()));
					} else if(paramType.equals(String.class)) {
						 
						setMethod.invoke(entity, value.toString());
					}
				} catch (Exception e) {
					 
				}
				 
		}
		  returnList.add(entity);
	}
	return returnList;
}
	/**
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 通过SQL获取集合
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:18:55
	 * @param sql SQL语句
	 * @param values 参数集合
	 * @return
	 * @throws BizException
	 */
	public List pageFindBySql(String sql ,Object[] values) throws BizException {
		return pageFindBySql(sql, values, null, null, null);
	}
	/**
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 通过SQL分页获取集合
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:18:50
	 * @param sql SQL语句
	 * @param values 参数集合
	 * @param pager 分页对象
	 * @return
	 * @throws BizException
	 */
	public List pageFindBySql(String sql ,Object[] values, Pager pager) throws BizException {
		return pageFindBySql(sql, values, pager, null, null);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 通过SQL分页获取集合
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:20:03
	 * @param sql SQL语句
	 * @param values 参数集合
	 * @param startRow 开始行
	 * @param pageSize 每页行数
	 * @return
	 * @throws BizException
	 */
	public List pageFindBySql(String sql ,Object[] values,int startRow, int pageSize) throws BizException {
		Pager pager = new Pager();
		pager.setStartRow(startRow);
		pager.setPageSize(pageSize);
		return pageFindBySql(sql, values, pager, null, null);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 通过SQL分页获取集合
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:20:37
	 * @param sql SQL语句
	 * @param startRow 开始行
	 * @param pageSize 每页行数
	 * @return
	 * @throws BizException
	 */
	public List pageFindBySql(String sql ,int startRow, int pageSize) throws BizException {
		Pager pager = new Pager();
		pager.setStartRow(startRow);
		pager.setPageSize(pageSize);
		return pageFindBySql(sql, null, pager, null, null);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 通过SQL获取集合
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:21:08
	 * @param sql SQL语句
	 * @return
	 * @throws BizException
	 */
	public List pageFindBySql(String sql  ) throws BizException {
		return pageFindBySql(sql, null, null, null, null);
	}
	
	/**
	 * 获取当前session对象
	 * @return
	 */
	public Session getCurrSession() {
		return super.getSession();
	}
	
	/**
	 * 指定session对象，执行SQL语句
	 * @param sql SQL语句
	 * @param values 值集合
	 * @param session 指定的session对象
	 * @return
	 * @throws BizException
	 */
	public int updateSqlByDefSession(String sql, Object[] values, Session session) throws BizException {
		if(session != null) {
			Transaction tc = null;
			try {
				 SQLQuery sqlQuery = session.createSQLQuery(sql);
				 setParamList(sqlQuery, values);
				 tc = session.beginTransaction(); //打开事务
				 int result = sqlQuery.executeUpdate();
				 tc.commit(); //提交事件
				 return result;
			} catch (Exception e) {
				if(tc != null) {
					tc.rollback(); //出现异常，回滚事务
				}
//				throw new ApuException(sql + "执行失败");
			}
		}
		return 0;
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 通过SQL进行更新
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:21:24
	 * @param sql SQL语句
	 * @param values 参数集合
	 * @throws BizException
	 */
	public int updateSQL(String sql,Object[] values) throws BizException {
		try{
			 Session session =super.getSession();
			 SQLQuery sqlQuery = session.createSQLQuery(sql);
			 setParamList(sqlQuery, values);
			 return sqlQuery.executeUpdate();
		}catch(Exception e){
			logger.warn(e.getMessage(),e);
			throw new BizException(sql+"执行失败");
		}
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 通过SQL更新
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:21:46
	 * @param sql SQL语句
	 * @throws BizException
	 */
	public int updateSQL(String sql )throws BizException{
		return updateSQL( sql,null);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 通过SQL获取集合(带分页)，并获取传入的Pager对象
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:22:01
	 * @param sql SQL语句
	 * @param values 参数集合
	 * @param pager 分页对象
	 * @return
	 * @throws BizException
	 */
	public Map<String, Object> pageFindBySqlWithPager(String sql, Object[] values, Pager pager) throws BizException {
		return pageFindBySqlAndGetPager(sql, values, pager, null, null);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 通过SQL获取集合(带分页)(通过类，封装对象)，并获取传入的Pager对象
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:22:51
	 * @param sql SQL语句
	 * @param values 参数集合
	 * @param pager 分页对象
	 * @param targetClass 查询后需转对象的类名
	 * @return
	 * @throws BizException
	 */
	public Map<String, Object> pageFindBySql(String sql, Object[] values, Pager pager, Class targetClass) throws BizException {
		return pageFindBySqlAndGetPager(sql, values, pager, null, targetClass);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 通过SQL查询集合(带分页)(通过别名，封装对象)
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:23:40
	 * @param sql SQL语句
	 * @param values 参数集合
	 * @param pager 分页对象
	 * @param extraName 查询别名
	 * @param targetClass 查询后需转对象的类名
	 * @return
	 * @throws BizException
	 */
	private List pageFindBySql(String sql, Object[] values, Pager pager, String extraName, Class targetClass) throws BizException {
		Session session =super.getSession();
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		if(StringUtils.isNotBlank(extraName) && targetClass != null) {
			sqlQuery.addEntity(extraName, targetClass); //根据别名，封装查询结果集的对象
		} else if(targetClass != null) {
			sqlQuery.addEntity(targetClass); //封装查询结果集的对象
		}
		if (pager != null) {
			try {
				pager.setTotalRows(getCountBySql(sql, values));
			} catch (BizException e) {
				logger.warn(e.getMessage(),e);
				throw new HibernateException("--查询记录总数失败--", e);
			}
			sqlQuery.setFirstResult(pager.getStartRow());
			sqlQuery.setMaxResults(pager.getPageSize());
		}
		setParamList(sqlQuery,values);
		return sqlQuery.list();
	}
	
	public List pageFindBySql(String sql, Object[] values, Pager pager, boolean tranformFlag) throws BizException {
		Session session =super.getSession();
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		if(tranformFlag) {
			sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		}
		if (pager != null) {
			try {
				pager.setTotalRows(getCountBySql(sql, values));
			} catch (BizException e) {
				logger.warn(e.getMessage(),e);
				throw new HibernateException("--查询记录总数失败--", e);
			}
			sqlQuery.setFirstResult(pager.getStartRow());
			sqlQuery.setMaxResults(pager.getPageSize());
		}
		setParamList(sqlQuery,values);
		return sqlQuery.list();
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 通过SQL查询集合(带分页)(通过别名，封装对象)，并获取传入的Pager对象
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:24:06
	 * @param sql SQL语句
	 * @param values 参数集合
	 * @param pager 分页对象
	 * @param extraName 查询别名
	 * @param targetClass 查询后需转对象的类名
	 * @return
	 * @throws BizException
	 */
	private Map<String, Object> pageFindBySqlAndGetPager(String sql, Object[] values, Pager pager, String extraName, Class targetClass) throws BizException {
		Map<String, Object> mixMap = new HashMap<String, Object>();
		mixMap.put("resultList", pageFindBySql(sql, values, pager, extraName, targetClass));
		mixMap.put("pager", pager);
		return mixMap;
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 通过SQL获取行数
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:24:37
	 * @param sql SQL语句
	 * @param values 参数集合
	 * @return
	 * @throws BizException
	 */
	public Integer getCountBySql(String sql, Object[] values) throws BizException {
		try {
			StringBuilder sb = new StringBuilder();
			if(sql.indexOf("group by") != -1) {
				sb.append("select count(*) from (");
				int end = sql.indexOf(" order by");
				if(end != -1) {
					sb.append(sql.substring(0, end)).append(") res");
				} else {
					sb.append(sql).append(") res");
				}
			} else {
				int begin = sql.indexOf("from");
				int end = sql.indexOf(" order by");
				sb.append("select count(*) ");
				if(end != -1) {
					sb.append(sql.substring(begin, end));
				} else {
					sb.append(sql.substring(begin));
				}
			}
			//目标SQL
			String targetSql = sb.toString();
		 
			System.out.println(targetSql);
			Session session =super.getSession();
			Query query = session.createSQLQuery(targetSql);
			setParamList(query, values);
			
			System.currentTimeMillis();
			int count = ((Number)query.uniqueResult()).intValue();
			System.currentTimeMillis();
			 
			return count;
		} catch (Exception e) {
			logger.warn(e.getMessage(),e);
			throw new BizException("--------------- get count " + sql + " false", e);
		}
	}
	
	public Integer getCountBySql(String sql) throws BizException {
	    return getCountBySql(sql, null);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 参数设置
	 * @Author:pengjy
	 * @CreateDate:2013-6-7 下午5:24:57
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
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 添加目标对象(返回主键)
	 * @Author:pengjy
	 * @CreateDate:2013-6-20 上午11:00:15
	 * @param t 目标对象
	 * @return 返回主键
	 * @throws BizException
	 */
	public String addWithRet(T t) throws BizException {
		return (String)super.getHibernateTemplate().save(t);
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 批量添加目标对象(返回主键集合)
	 * @Author:pengjy
	 * @CreateDate:2013-6-20 上午11:01:57
	 * @param objList 目标对象集合
	 * @return 返回主键集合
	 * @throws BizException
	 */
	public List<String> addBatchWithRet(List<T> objList) throws BizException {
		List<String> idList = new ArrayList<String>();
		if(objList != null && !objList.isEmpty()) {
			for (T t : objList) {
				idList.add(addWithRet(t));
			}
		}
		return idList;
	}
	
	/**
	 * 
	 * @ClassName: BaseDaoBySql.java
	 * @Description: 单个查询
	 * @Author:pengjy
	 * @CreateDate:2013-7-17 上午9:25:50
	 * @param sql SQL语句
	 * @param values 参数集合
	 * @return
	 * @throws BizException
	 */
	public Object findObjectBySql(String sql, Object[] values)throws BizException{
		try {
			Session session = super.getSession();
			Query query = session.createSQLQuery(sql);
			setParamList(query, values); //参数设置
			return query.uniqueResult();
		} catch (Exception e) {
			logger.warn(e.getMessage(),e);
			throw new BizException("--------------- 查询单个对象失败",e);
		}
	}
}
