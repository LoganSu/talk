package com.youlb.controller.access;

import java.util.List;

import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.exception.JsonException;


public class Test {

	public static void main(String[] args) {
		HouseTree tree = new HouseTree();
       HouseTree area = new HouseTree();
       area.setLevel(0);
       area.setNum("020");
       area.setDomainName("广东广州");
       
       
       HouseTree neighborhood = new HouseTree();
       neighborhood.setLevel(1);
       neighborhood.setNum("10001");
       neighborhood.setDomainName("A社区");
       
       HouseTree building = new HouseTree();
       building.setLevel(2);
       building.setNum("002");
       building.setDomainName("A栋");
       
       HouseTree unit = new HouseTree();
       unit.setLevel(3);
       unit.setNum("003");
       unit.setDomainName("A单元");
       HouseTree unit1 = new HouseTree();
       unit1.setLevel(3);
       unit1.setNum("002");
       unit1.setDomainName("B单元");
       
       HouseTree room = new HouseTree();
       room.setLevel(4);
       room.setNum("10008");
       room.setDomainName("10008");
       Person person = new Person();
       person.setName("李四");
       person.setPhone("15974105606");
       person.setHouseholder(true);
       room.addPerson(person);
       
       Person person2 = new Person();
       person2.setName("老王");
       person2.setPhone("15974105688");
       room.addPerson(person2);;
       
       HouseTree room1 = new HouseTree();
       room1.setLevel(4);
       room1.setNum("10009");
       room1.setDomainName("10009");
       Person person1 = new Person();
       person1.setName("张三");
       person1.setPhone("15974105601");
       person1.setHouseholder(true);
       room1.addPerson(person1);;
       
       unit.addHouse(room);
       unit.addHouse(room1);
       building.addHouse(unit);
       building.addHouse(unit1);
       neighborhood.addHouse(building);
       area.addHouse(neighborhood);
       tree.addHouse(area);
//       System.out.println(area);
       
       System.out.println(JsonUtils.toJson(area));
       
       
       try {
		System.out.println(JsonUtils.fromJson(JsonUtils.toJson(area), HouseTree.class));
	} catch (JsonException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
       for(HouseTree ta :tree.getChildren()){
    	   //add area
    	   System.out.println("插入数据..."+ta.getLevel()+"--"+ta.getDomainName()+"--"+ta.getNum());
    	   //flush
    	   if(!ta.getChildren().isEmpty()){
	    	   for(HouseTree tn:ta.getChildren()){
	    		   //add neighborhood
	    		   System.out.println("插入数据..."+tn.getLevel()+"--"+tn.getDomainName()+"--"+tn.getNum());
	    		   //flush
	    		   if(!tn.getChildren().isEmpty()){
	    			   for(HouseTree tb:tn.getChildren()){
	    				   //add building
	    				   System.out.println("插入数据..."+tb.getLevel()+"--"+tb.getDomainName()+"--"+tb.getNum());
	    				   //flush
	    				   if(!tb.getChildren().isEmpty()){
	    					   for(HouseTree tu:tb.getChildren()){
	    						   //add unit
	    						   System.out.println("插入数据..."+tu.getLevel()+"--"+tu.getDomainName()+"--"+tu.getNum());
	    						   //flush
	    						   if(!tu.getChildren().isEmpty()){
	    							   for(HouseTree tr:tu.getChildren()){
	    								   //add room
	    								   System.out.println("插入数据..."+tr.getLevel()+"--"+tr.getDomainName()+"--"+tr.getNum());
	    								   //flush
	    								   //add 住户
	    								   if(!tr.getPersonList().isEmpty()){
	    									   for(Person p :tr.getPersonList()){
	    										   System.out.println("插入住户数据..."+p.getHouseholder()+"--"+p.getName()+"--"+p.getPhone());
	    									   }
	    								   }
	    							   }
	    						   }
	    					   }
	    				   }
	    			   }
	    		   }
	    	   }
    	   }
    	   
       }
       
       
	}

}
