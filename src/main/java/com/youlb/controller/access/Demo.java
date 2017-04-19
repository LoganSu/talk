package com.youlb.controller.access;

import com.youlb.utils.common.JsonUtils;

public class Demo {

	public static void main(String[] args) {
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
		       
//		       System.out.println(area);
		       
		       System.out.println(JsonUtils.toJson(area));

	}

}
