package com.youlb.controller.access;

import java.util.ArrayList;
import java.util.List;
/**
 * @ClassName: HouseTree.java 
 * @Description: 房产信息的公告封装数据对象，赛翼平台固定层级5级 ，地区/社区/楼栋/单元/房间 ，例：广东广州/XX社区/一栋/一单元/10008（房间名称和编号可以一样）
 * 注意：顶级只有children字段有值,其他字段都为null
 * @author pengjingyu
 * @date 2017-04-18
 * @version 1.0
 *
 */
public class HouseTree {
   /**节点层级  地区/社区/楼栋/单元/房间 ，分别对应 0 /1 / 2/ 3/ 4 （顶级null）*/
   public Integer level;
   /**
    * 节点呼叫号（数字类型，用于门口机拨号使用 例：0001。相同节点下面的编号不能重复）注：地区编号使用区号，例：广州-020，长沙-731*/
   public String num;
   /**节点名称（拼接成地址信息，app显示锁列表名称）*/
   public String domainName;
   /**子节点*/
   public List<HouseTree> children = new ArrayList<HouseTree>();
   /**住户列表（只有房间级别才能绑定数据）*/
   public List<Person> personList = new ArrayList<Person>();
   /**
    * 添加子节点
    * @param houseTree
    */
   public void addHouse(HouseTree houseTree){
	   children.add(houseTree);
   }
   /**
    * 添加住户
    * @param houseTree
    */
   public void addPerson(Person person){
	   personList.add(person);
   }
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
	public List<HouseTree> getChildren() {
		return children;
	}
	public void setChildren(List<HouseTree> children) {
		this.children = children;
	}
	public List<Person> getPersonList() {
		return personList;
	}
	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}
	   
	   
}
/**
 * @ClassName: Person.java 
 * @Description: 住户信息
 * @author pengjingyu
 * @date 2017-04-18
 * @version 1.0
 *
 */
class Person{

	   /**住户手机号码*/
	   public String phone;
	   /**住户姓名*/
	   public String name;
	   /**是否是户主  true：是  */
	   public Boolean householder=false;
	   
	   public Boolean getHouseholder() {
			return householder;
		}
		public void setHouseholder(Boolean householder) {
			this.householder = householder;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	   
}
