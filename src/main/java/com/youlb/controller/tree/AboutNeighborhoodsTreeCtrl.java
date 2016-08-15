package com.youlb.controller.tree;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.common.Domain;
import com.youlb.entity.houseInfo.Area;
import com.youlb.entity.houseInfo.Building;
import com.youlb.entity.houseInfo.Neighborhoods;
import com.youlb.entity.houseInfo.Room;
import com.youlb.entity.houseInfo.Unit;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.vo.QJson;
import com.youlb.entity.vo.QTree;
import com.youlb.utils.common.SysStatic;


/**
 * 
* @ClassName: AboutNeighborhoodsTreeCtrl.java 
* @Description: 只显示社区的树 
* @author: Pengjy
* @date: 2016年7月11日
 */
 
@Controller
@RequestMapping("/mc/aboutNeighborhoodsTree")
@Scope("prototype")
public class AboutNeighborhoodsTreeCtrl  extends BaseCtrl{
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private IDomainBiz domainBiz;
	 
    @Autowired
    private BaseDaoBySql<Area> areaSqlDao;
    @Autowired
    private BaseDaoBySql<Neighborhoods> neighborSqlDao;
    @Autowired
    private BaseDaoBySql<Building> buildingSqlDao;
    @Autowired
    private BaseDaoBySql<Unit> unitSqlDao;
    @Autowired
    private BaseDaoBySql<Room> roomSqlDao;
    
    
	public void setAreaSqlDao(BaseDaoBySql<Area> areaSqlDao) {
		this.areaSqlDao = areaSqlDao;
	}
	public void setNeighborSqlDao(BaseDaoBySql<Neighborhoods> neighborSqlDao) {
		this.neighborSqlDao = neighborSqlDao;
	}
	public void setBuildingSqlDao(BaseDaoBySql<Building> buildingSqlDao) {
		this.buildingSqlDao = buildingSqlDao;
	}
	public void setUnitSqlDao(BaseDaoBySql<Unit> unitSqlDao) {
		this.unitSqlDao = unitSqlDao;
	}
	public void setRoomSqlDao(BaseDaoBySql<Room> roomSqlDao) {
		this.roomSqlDao = roomSqlDao;
	}
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	/**
	 *  * 树状数据返回
	 * @param isDweller 住户信息选择房间参数
	 * @return
	 */
    
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody 
    public QJson tree() {
		//获取根路径
		String path = servletContext.getContextPath();
		Operator loginUser = getLoginUser();
		QJson json = new QJson();
		QTree t = new QTree();
		t.setText("");
		List<Domain> topList = domainBiz.showList(new Domain(),loginUser);
		List<QTree> children = getDomainList(topList,loginUser,path);
		t.setUrl("checkfalse");//url字段标识不需要显示多选框
		t.setChildren(children);
		t.setChecked(true);
		json.setMsg("OK");
		json.setObject(t);
		json.setSuccess(true);
		json.setType("1");
		return json;
	}
	/**
	 * 
	 * @param list
	 * @param loginUser
	 * @param path
	 * @param isDweller
	 * @return
	 */
	 
	private List<QTree> getDomainList(List<Domain> list, Operator loginUser,String path) {
		List<QTree> treeList = new ArrayList<QTree>();
    	if(list!=null&&!list.isEmpty()){
			for(Domain d:list){
				List<Domain> dlist = domainBiz.getDomainByParentId(d.getId(),loginUser,path);
				QTree tree = new QTree();
					tree.setText(d.getRemark());
					tree.setId(d.getId());
					tree.setLayer(d.getLayer());
					String url = "";
					List<QTree> tList = getDomainList(dlist, loginUser, path);
					if(SysStatic.NEIGHBORHOODS.equals(d.getLayer())){
						url = path+"/mc/aboutNeighborhoods/aboutNeighborhoodsshowPage.do?module=aboutNeighborhoodsTable&modulePath=/aboutNeighborhoods&neighborDomainId="+d.getId()+"&aa="+new Date().getTime();
						tList=null;
					}
					tree.setChildren(tList);
					tree.setUrl(url);
					treeList.add(tree);
			}
		}
		return treeList;
	}
}
