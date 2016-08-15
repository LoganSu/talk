package com.youlb.controller.chars;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.util.JSONUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.vo.CharVo;
import com.youlb.entity.vo.Series;
import com.youlb.utils.common.JsonUtils;

/** 
 * @ClassName: CharsCtrl.java 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2016-1-27
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/mc/chars")
public class CharsCtrl  extends BaseCtrl {

   /**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/line.do")
   	public String line(Model model,String url){
//    	CharVo charVo = new CharVo("WWW", "", "", "");
    	model.addAttribute("url", url);
   		return "/chars/line";
   	}
    /**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/data.do")
   	public String data(Model model){
    	CharVo charVo = new CharVo("line","折线图", "仓库出入", "月份", "数量");
    	charVo.setWidth(600);
    	charVo.setHeight(400);
    	List<String> categoriesList = new ArrayList<String>();
    	categoriesList.add("Jan");
    	categoriesList.add("Feb");
    	categoriesList.add("Mar");
    	categoriesList.add("Apr");
    	categoriesList.add("May");
    	categoriesList.add("Jun");
    	categoriesList.add("Jul");
    	categoriesList.add("Aug");
    	categoriesList.add("Sep");
    	categoriesList.add("Oct");
    	categoriesList.add("Nov");
    	categoriesList.add("Dec");
    	charVo.setCategories(categoriesList);
    	
    	List<Series> dataList = new ArrayList<Series>();
    	Series series = new Series("一号","");
    	List<Number> data = new ArrayList<Number>();
    	data.add(212);
    	data.add(321);
    	data.add(231);
    	data.add(312);
    	data.add(560);
    	data.add(532);
    	data.add(680);
    	data.add(277);
    	data.add(546);
    	data.add(561);
    	data.add(841);
    	data.add(630);
    	series.setData(data);
//    	String ss = JsonUtils.toJson(series);
//    	System.out.println(ss);
    	dataList.add(series);
    			
    	List<Number> data1 = new ArrayList<Number>();
    	Series series1 = new Series("二号","");
    	data1.add(22);
    	data1.add(121);
    	data1.add(531);
    	data1.add(212);
    	data1.add(660);
    	data1.add(332);
    	data1.add(630);
    	data1.add(377);
    	data1.add(46);
    	data1.add(261);
    	data1.add(541);
    	data1.add(1730);
    	series1.setData(data1);
//    	String ss1 = JsonUtils.toJson(series1);
//    	System.out.println(ss1);
    	dataList.add(series1);
    	
    	charVo.setDataList(dataList);
//    	String json = JsonUtils.toJson(charVo.getDataList());
//    	System.out.println(json);
    	model.addAttribute("charVo", JsonUtils.toJson(charVo));
    	return "/chars/line";
   	}
    /**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/column.do")
   	public String column(){
    	System.out.println("column");
   		return "/chars/column";
   	}
    /**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/pie.do")
   	public String pie(){
    	System.out.println("pie");
   		return "/chars/pie";
   	}
}
