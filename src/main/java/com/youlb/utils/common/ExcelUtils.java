package com.youlb.utils.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelUtils {
   
   /**
    * 读取Excel表格表头的内容
    * @param InputStream
    * @return String 表头内容的数组
    */
   public static String[] readExcelTitle(InputStream is) {
       try {
    	   POIFSFileSystem fs = new POIFSFileSystem(is);
    	   HSSFWorkbook wb = new HSSFWorkbook(fs);
    	   HSSFSheet sheet = wb.getSheetAt(0);
    	   HSSFRow row = sheet.getRow(0);
	       // 标题总列数
	       int colNum = row.getPhysicalNumberOfCells();
	       System.out.println("colNum:" + colNum);
	       String[] title = new String[colNum];
	       for (int i = 0; i < colNum; i++) {
	           //title[i] = getStringCellValue(row.getCell((short) i));
	           title[i] = getCellFormatValue(row.getCell(i));
	       }
            return title;
       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
   }
   
   /**
    * 读取Excel数据内容封装对象 
    * @param is
    * @param clazz 对象字段顺序跟表格顺序一样
    * @return
    * @throws IllegalAccessException
    * @throws IllegalArgumentException
    * @throws InvocationTargetException
    * @throws InstantiationException
    * @throws ParseException
    * @throws NoSuchMethodException
    * @throws SecurityException
    * @throws IOException 
    */
   public static <T extends Object> List<T> readExcelContent(InputStream is,Class<T> clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, ParseException, NoSuchMethodException, SecurityException, IOException {
	       List<T> list = new ArrayList<T>();
    	   POIFSFileSystem fs = new POIFSFileSystem(is);
    	   HSSFWorkbook  wb = new HSSFWorkbook(fs);
	       HSSFSheet sheet = wb.getSheetAt(0);
	       // 得到总行数
	       int rowNum = sheet.getLastRowNum();
	       HSSFRow row = sheet.getRow(0);
	       Field[] fields = clazz.getDeclaredFields();
	       // 正文内容应该从第二行开始,第一行为表头的标题
	       for (int i = 1; i <= rowNum; i++) {
    	       row = sheet.getRow(i);
    	       T t = clazz.newInstance();
        	   int cell=0;
        	   for(Field field:fields){
        		   //如果获取参数值为空则继续循环
                   String value = getCellFormatValue(row.getCell(cell)).trim();
                   if(value==null){
                       continue;
                   }
        		   //参数值
                   Object[] paramValue =new Object[1];
                   if(field.getType().toString().equals("class java.lang.String")){
                       paramValue[0]=value;
                   }
                   if(field.getType().toString().equals("class java.lang.Integer")){
                       paramValue[0]=new Integer(value);
                   }
                   if(field.getType().toString().equals("class java.lang.Double")){
                       paramValue[0]=new Double(value);
                   }
                   if(field.getType().toString().equals("class java.util.Date")){
                       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                       paramValue[0]=sdf.parse(value);
                   }
                   //参数类型
                   Class[] paramType= {field.getType()};
        		   String name = field.getName();
        		   StringBuilder sb = new StringBuilder();
        		   sb.append("set");
        		   sb.append(name.substring(0, 1).toUpperCase());
        		   sb.append(name.substring(1));
        		   Method method = clazz.getDeclaredMethod(sb.toString(), paramType);
        		   method.invoke(t, paramValue);
        		   cell++;
        	   }
        	   list.add(t);
	       }
       return list;
   }
   
   
   
   /**
    * 根据HSSFCell类型设置数据
    * @param cell
    * @return
    */
   private static String getCellFormatValue(HSSFCell cell) {
       String cellvalue = "";
       if (cell != null) {
           // 判断当前Cell的Type
           switch (cell.getCellType()) {
           // 如果当前Cell的Type为NUMERIC
           case HSSFCell.CELL_TYPE_NUMERIC:
           case HSSFCell.CELL_TYPE_FORMULA: {
               // 判断当前的cell是否为Date
               if (HSSFDateUtil.isCellDateFormatted(cell)) {
                   //这样子的data格式是不带带时分秒的：2011-10-12
                   Date date = cell.getDateCellValue();
                   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   cellvalue = sdf.format(date);
               }
               // 如果是纯数字
               else {
                   // 取得当前Cell的数值
                   cellvalue = String.valueOf(cell.getNumericCellValue());
               }
               break;
           }
           // 如果当前Cell的Type为STRIN
           case HSSFCell.CELL_TYPE_STRING:
               // 取得当前的Cell字符串
               cellvalue = cell.getRichStringCellValue().getString();
               break;
           // 默认的Cell值
           default:
               cellvalue = " ";
           }
       } else {
           cellvalue = "";
       }
       return cellvalue;

   }
   
   
   public static <T extends Object>String exportExcel(String title, String[] headersName,String[] headersId,List<T> dtoList,String file) {
		//表头
		Map<Integer,String> map = new HashMap<Integer,String>();
		int key=0;
		for (int i = 0; i < headersName.length; i++) {
			if (!headersName[i].equals(null)) {
				map.put(key, headersName[i]);
				key++;
			}
		}
		//字段
		Map<Integer,String> zdMap = new HashMap<Integer,String>();
		int value = 0;
		for (int i = 0; i < headersId.length; i++) {
			if (!headersId[i].equals(null)) {
				zdMap.put(value, headersId[i]);
				value++;
			}
		}
		// 声明一个工作薄
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(title);
		sheet.setDefaultColumnWidth((short)15);
		// 生成一个样式  
		HSSFCellStyle style = wb.createCellStyle();
		HSSFRow row = sheet.createRow(0);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short)12);
        font.setBold(true);//字体增粗
		style.setFont(font);
		
		HSSFCell cell;
		Collection<String> c = map.values();
		Iterator<String> it = c.iterator();
		//根据选择的字段生成表头
		int size = 0;
		while (it.hasNext()) {
		    cell = row.createCell(size);
			cell.setCellValue(it.next().toString());
			cell.setCellStyle(style);
			size++;
		}
		//字段		
		Collection<String> zdC = zdMap.values();
		Iterator<T> labIt = dtoList.iterator();
		int zdRow =0;
		while (labIt.hasNext()) {
			int zdCell = 0;
			zdRow++;
			row = sheet.createRow(zdRow);
			T l = (T) labIt.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = l.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
//				System.out.println(fieldName);
				Iterator<String> zdIt = zdC.iterator();
				while (zdIt.hasNext()) {
					if (zdIt.next().equals(fieldName)) {
						String getMethodName = "get"+ fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
						Class<T> tCls = (Class<T>) l.getClass();
						try {
							Method getMethod = tCls.getMethod(getMethodName,new Class[] {});
							Object val = getMethod.invoke(l, new Object[] {});
//							System.out.println(fields[i].getName());
//							System.out.println(val);
							String textVal = null;
							if (val!= null) {
								textVal = val.toString();
							}else{
								textVal = "";
							}
							row.createCell(zdCell).setCellValue(textVal);
							zdCell++;
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
				
			}
		}
		
		try {
			FileOutputStream xls = new FileOutputStream(file);
			wb.write(xls); 
			xls.close();
//			JOptionPane.showMessageDialog(null, "导出成功!");
		} catch (FileNotFoundException e) {
//			JOptionPane.showMessageDialog(null, "导出失败!");
			e.printStackTrace();
		} catch (IOException e) {
//			JOptionPane.showMessageDialog(null, "导出失败!");
			e.printStackTrace();
		}
		return file;
	}
}
