package com.youlb.biz.infoPublish.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.infoPublish.ITodayNewsBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.common.ResultDTO;
import com.youlb.entity.infoPublish.TodayNews;
import com.youlb.entity.infoPublish.TodayNewsDto;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;


@Service("todayNewsBiz")
public class TodayNewsImpl implements ITodayNewsBiz {
    @Autowired
	private BaseDaoBySql<TodayNews> todayNewsSqlDao;
	public void setTodayNewsSqlDao(BaseDaoBySql<TodayNews> todayNewsSqlDao) {
		this.todayNewsSqlDao = todayNewsSqlDao;
	}
	/** 日志输出 */
	private static Logger logger = LoggerFactory.getLogger(TodayNewsImpl.class);
	@Override
	public String save(TodayNews target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	
	public void update(TodayNews target) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Serializable id) throws BizException {
		todayNewsSqlDao.delete(id);

	}

	@Override
	public void delete(String[] ids) throws BizException {
		if(ids!=null){
			for(String id:ids){
				delete(id);
			}
		}

	}

	@Override
	public TodayNews get(Serializable id) throws BizException {
		TodayNews todayNews = todayNewsSqlDao.get(id);
		String hql = "select da.fdomainid from t_domain_todaynews da where da.ftodaynewsid=?";
		List<String> domainIds = todayNewsSqlDao.pageFindBySql(hql, new Object[]{id});
		todayNews.setTreecheckbox(domainIds);
		return todayNews;
	}

	@Override
	public List<TodayNews> showList(TodayNews target, Operator loginUser)throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> valuse = new ArrayList<Object>();
		sb.append("select * from (SELECT i.id id,i.ftitle title,i.fpictureurl pictureUrl,i.fnewsurl newsUrl,i.ftargetdevice targetDevice,i.fcreatetime createTime,")
	    .append("i.fstatus status,i.fpublish_time publishTime,i.fpublish_operator publishOperator,i.fadd_operator addOperator,case when i.fcarrierid=? then true else false end self ")
//		.append("i.finfosign infoSign,i.finfodetail infoDetail,i.fsendtype sendType,i.fcreatetime createTime,i.fexpdate expDate ")
		.append("from t_todaynews i INNER JOIN t_domain_todaynews tdi on tdi.ftodaynewsid=i.id where 1=1 ");
		valuse.add(loginUser.getCarrier().getId());

		//普通用户过滤运营商
		List<String> domainIds = loginUser.getDomainIds();
		if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds, " tdi.fdomainid "));
			valuse.add(domainIds);
		}
		sb.append(" group by i.id,i.ftitle,i.fpictureurl,i.fnewsurl,i.ftargetdevice,i.fcreatetime,i.fstatus,i.fpublish_time,i.fpublish_operator,i.fadd_operator)t where 1=1");
		
		if(StringUtils.isNotBlank(target.getTitle())){
			sb.append(" and title like ?");
			valuse.add("%"+target.getTitle()+"%");
		}
		if(StringUtils.isNotBlank(target.getTargetDevice())){
			sb.append(" and targetDevice = ?");
			valuse.add(target.getTargetDevice());
		}
		if(StringUtils.isNotBlank(target.getStartTime())){
			sb.append(" and to_char(createTime,'yyyy-MM-dd') >= ?");
			valuse.add(target.getStartTime());
		}
		if(StringUtils.isNotBlank(target.getEndTime())){
			sb.append(" and to_char(createTime,'yyyy-MM-dd') <= ?");
			valuse.add(target.getEndTime());
		}
		OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime desc");
		List<Object[]> listObj = todayNewsSqlDao.pageFindBySql(sb.toString(), valuse.toArray(), target.getPager());
		List<TodayNews> list = new ArrayList<TodayNews>();
		if(listObj!= null&&!listObj.isEmpty()){
			for(Object[] obj:listObj){
				TodayNews todayNews = new TodayNews();
				todayNews.setPager(target.getPager());
				todayNews.setId(obj[0]==null?"":(String)obj[0]);
				todayNews.setTitle(obj[1]==null?"":(String)obj[1]);
				todayNews.setPictureUrl(obj[2]==null?"":(String)obj[2]);
				todayNews.setNewsUrl(obj[3]==null?"":(String)obj[3]);
				todayNews.setTargetDevice(obj[4]==null?"":(String)obj[4]);
				todayNews.setCreateTime(obj[5]==null?null:(Date)obj[5]);
				todayNews.setStatus(obj[6]==null?"":(String)obj[6]);
				todayNews.setPublishTime(obj[7]==null?null:(Date)obj[7]);
				todayNews.setPublishOperator(obj[8]==null?"":(String)obj[8]);
				todayNews.setAddOperator(obj[9]==null?"":(String)obj[9]);
				todayNews.setSelf(obj[10]==null?null:(Boolean)obj[10]);
				list.add(todayNews);
			}
		}
		return list;
	}

	@Override
	public void saveOrUpdate(TodayNews todayNews, Operator loginUser) throws IllegalAccessException, InvocationTargetException, ClientProtocolException, IOException, ParseException, JsonException, BizException {
		//目标设备暂时是手机客户端
		todayNews.setTargetDevice("2");
		//设置添加人
		String operator = "";
		//普通管理员 的真实姓名已经包含 admin
		if(SysStatic.NORMALADMIN.equals(loginUser.getIsAdmin())){
			operator= loginUser.getRealName();
		}else{
			operator = loginUser.getRealName()+"("+loginUser.getLoginName()+")";
		}
		todayNews.setAddOperator(operator);
		//获取推送标签(标签就是domainid)
		List<String> tagList = getTagList(todayNews,loginUser);
		//add
		if(StringUtils.isBlank(todayNews.getId())){
			//设置运营商
			todayNews.setCarrierId(loginUser.getCarrier().getId());
			String id = (String) todayNewsSqlDao.add(todayNews);
			if(tagList!=null&&!tagList.isEmpty()){
				//把需要指定域保存中间表 全部推送保存的是社区级doaminid 指定范围的保存的是指定domainid
				String sql="insert into t_domain_todaynews(ftodaynewsid,fdomainid) values (?,?)";
				for(String domainid:tagList){
					todayNewsSqlDao.executeSql(sql, new Object[]{id,domainid});
				}
			}
		}else{
		    //update	
			todayNewsSqlDao.update(todayNews);
			//删除中间表纪录
			String delete = "delete from t_domain_todaynews where ftodaynewsid=?";
			todayNewsSqlDao.executeSql(delete, new Object[]{todayNews.getId()});
			if(tagList!=null&&!tagList.isEmpty()){
				//把需要指定域保存中间表 全部推送保存的是社区级doaminid 指定范围的保存的是指定domainid
				String sql="insert into t_domain_todaynews(ftodaynewsid,fdomainid) values (?,?)";
				for(String domainid:tagList){
					todayNewsSqlDao.executeSql(sql, new Object[]{todayNews.getId(),domainid});
				}
			}
			
		}
		
	}
	
	  /**
     * 发布
     * @param ids
     * @param loginUser
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws UnsupportedEncodingException
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ParseException
     * @throws JsonException
	 * @throws BizException 
     * @see com.youlb.biz.infoPublish.ITodayNewsBiz#publish(java.lang.String[], com.youlb.entity.privilege.Operator)
     */
	@Override
	public void publish(String[] ids, Operator loginUser)throws IllegalAccessException, InvocationTargetException,UnsupportedEncodingException, ClientProtocolException, IOException,ParseException, JsonException, BizException {
		if(ids!=null){
			for(String id:ids){
				TodayNews todayNews = get(id);
				TodayNewsDto dto = new TodayNewsDto();
				Date publishTime = new Date();
				todayNews.setPublishTime(publishTime);
				//封装参数对象
				BeanUtils.copyProperties(dto, todayNews);
				//获取推送标签(标签就是domainid)
				List<String> tagList = todayNews.getTreecheckbox();
				if(tagList==null||tagList.isEmpty()){
					throw new BizException("未找到任何标签");
				}
				
				dto.setTagList(tagList);
				//全部发布 标签就是社区id
				if("2".equals(todayNews.getSendType())){
					//获取社区id
					List<String> neibTagList = getNeibTagList(tagList);
					dto.setNeibTagList(neibTagList);
					logger.info("neibTagList："+Arrays.toString(neibTagList.toArray()));
				}else{
					dto.setNeibTagList(tagList);
				}
				logger.info("tagList："+Arrays.toString(tagList.toArray()));
				logger.info("todayNews:"+JsonUtils.toJson(dto));
				//调用信息推送接口
				CloseableHttpClient httpClient = HttpClients.createDefault();
				//公告 通知需要透传消息
				HttpPost request = new HttpPost(SysStatic.HTTP+"/publish/web_pushTodaynews.json");
				List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
				//获取服务器ip
				formParams.add(new BasicNameValuePair("todayNews", JsonUtils.toJson(dto)));
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
				request.setEntity(uefEntity);
				CloseableHttpResponse response = httpClient.execute(request);
				if(response.getStatusLine().getStatusCode()==200){
					HttpEntity entity_rsp = response.getEntity();
					ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
					if(resultDto!=null){
						if(!"0".equals(resultDto.getCode())){
							throw new BizException("信息推送"+resultDto.getMsg());
						}
					}
					String update ="update TodayNews set publishOperator=?,publishTime=?,status=? where id=?";
					//设置添加人
					String operator = "";
					//普通管理员 的真实姓名已经包含 admin
					if(SysStatic.NORMALADMIN.equals(loginUser.getIsAdmin())){
						operator= loginUser.getRealName();
					}else{
						operator = loginUser.getRealName()+"("+loginUser.getLoginName()+")";
					}
					todayNewsSqlDao.update(update, new Object[]{operator,publishTime,"1",id});
				}else{
					throw new BizException("信息推送失败！");
				}
			}
		}
	}
	
	/**
	 * h获取社区id集合
	 * @param tagList
	 * @return
	 * @throws BizException 
	 */
    private List<String> getNeibTagList(List<String> tagList) throws BizException {
    	StringBuilder sql = new StringBuilder("WITH RECURSIVE r AS (SELECT d.* FROM t_domain d where 1=1 ");
    	sql.append(SearchHelper.jointInSqlOrHql(tagList, " d.id "));
		sql.append("union SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid ) SELECT r.id FROM r where r.flayer=1");
		return todayNewsSqlDao.pageFindBySql(sql.toString(), new Object[]{tagList});
	}
	/**
	 * 获取门口机channelid
	 * @param treecheckbox
	 * @param loginUser 全部时过滤运营商
	 * @return
	 * @throws BizException 
	 */
	 
	private List<String> getTagList(TodayNews target,Operator loginUser) throws BizException {
		 //全部推送==是按该运营商下面的所以社区tag
		 if("1".equals(target.getSendType())){
			 StringBuilder sb = new StringBuilder();
			 List<Object> values = new ArrayList<Object>();
			 //特殊admin 全部的域
			 if(SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())){
				 sb.append("select t.id from t_domain t where t.flayer='1'");
			 }else{
				 List<String> domainIds = loginUser.getDomainIds();
				 sb.append("select t.id from t_domain t where t.id in(");
				 for(String domainId:domainIds){
					 sb.append("?,");
					 values.add(domainId);
				 }
				 sb.deleteCharAt(sb.length()-1);
				 sb.append(") and t.flayer='1'");
			 }
			 List<String> tagList = todayNewsSqlDao.pageFindBySql(sb.toString(), values.toArray());
			 return tagList;
	     //选择一个域对象直接就是标签		 
		 }else if("2".equals(target.getSendType())){
			 List<String> treecheckbox = target.getTreecheckbox();
			 return treecheckbox;
		 }
		return null;
	}
  
}
