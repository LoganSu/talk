package com.youlb.biz.auth2;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.auth2.MyClientDetails;
import com.youlb.utils.exception.BizException;
@Service("clientSqlBiz")
public class MyClientDetailsService implements ClientDetailsService {
    
	@Autowired
	private BaseDaoBySql<MyClientDetails> clientSqlDao;
	public void setClientSqlDao(BaseDaoBySql<MyClientDetails> clientSqlDao) {
		System.out.println(clientSqlDao);
		this.clientSqlDao = clientSqlDao;
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId)throws ClientRegistrationException {
		if(StringUtils.isNotBlank(clientId)){
			try {
				String sql = "select client_id,client_secret,scope from oauth_client_details where client_id=?";
				List<Object[]> list = (List<Object[]>) clientSqlDao.pageFindBySql(sql, new Object[]{clientId});
				if(list!=null&&!list.isEmpty()){
					for(Object[] obj:list){
						MyClientDetails client = new MyClientDetails();
						client.setClientId(obj[0]==null?"":(String)obj[0]);
						client.setClientSecret(obj[1]==null?"":(String)obj[1]);
						client.setScope(obj[2]==null?"":(String)obj[2]);
						return client;
					}
				}
			} catch (BizException e) { 
				e.printStackTrace();
			}
		} 
		return null;
	}

}
