package com.youlb.utils.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/** 
 * @ClassName: Xmltest.java 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2015年7月30日
 * 
 */
public class Xmltest {

	/**
	 * @param args
	 * @throws TransformerFactoryConfigurationError 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws TransformerFactoryConfigurationError, Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        //构建XML中的节点
        //<include>
        Element root = doc.createElement("include");
        //<user id="10000">
        Element userElement = doc.createElement("user");
        userElement.setAttribute("id", "111111");//用户id
        //<params>
        Element paramsElement = doc.createElement("params");
        //<param name="password" value="12345"/>
        Element param1Element = doc.createElement("param");
        param1Element.setAttribute("name", "password");
        param1Element.setAttribute("value", "12345");//密码
        //<param name="reverse-auth-user" value="111111"/>
        Element param2Element = doc.createElement("param");
        param2Element.setAttribute("name", "reverse-auth-user");
        param2Element.setAttribute("value", "111111");
        //<param name="reverse-auth-user" value="111111"/>
        Element param3Element = doc.createElement("param");
        param3Element.setAttribute("name", "reverse-auth-pass");
        param3Element.setAttribute("value", "12345");
        //<param name="reverse-auth-user" value="111111"/>
        Element param4Element = doc.createElement("param");
        param4Element.setAttribute("name", "vm-password");
        param4Element.setAttribute("value", "12345");
        //<param name="reverse-auth-user" value="111111"/>
        Element param5Element = doc.createElement("param");
        param5Element.setAttribute("name", "vm-enabled");
        param5Element.setAttribute("value", "true");
        //<param name="reverse-auth-user" value="111111"/>
        Element param6Element = doc.createElement("param");
        param6Element.setAttribute("name", "directory-exten-visible");
        param6Element.setAttribute("value", "true");
        //<param name="reverse-auth-user" value="111111"/>
        Element param7Element = doc.createElement("param");
        param7Element.setAttribute("name", "dial-string");
        param7Element.setAttribute("value", "{sip_invite_domain=${domain_name},leg_timeout=30,presence_id=${dialed_user}@${dialed_domain}}${sofia_contact(${dialed_user}@${dialed_domain})}");
        
        //<variables>
        Element variablesElement = doc.createElement("variables");
        //<variable name="domain_name" value="192.168.1.200"/>
        Element variable1Element = doc.createElement("variable");
        variable1Element.setAttribute("name", "domain_name");
        variable1Element.setAttribute("value", "192.168.1.200");
        Element variable2Element = doc.createElement("variable");
        variable2Element.setAttribute("name", "domain_uuid");
        variable2Element.setAttribute("value", "ee7a39e5-aedf-4de4-ab98-dbd7a1b4e5cb");
        Element variable3Element = doc.createElement("variable");
        variable3Element.setAttribute("name", "extension_uuid");
        variable3Element.setAttribute("value", "c96d0604-0590-4995-bbaa-3875ecd0c517");
        Element variable4Element = doc.createElement("variable");
        variable4Element.setAttribute("name", "toll_allow");
        variable4Element.setAttribute("value", "");
        Element variable5Element = doc.createElement("variable");
        variable5Element.setAttribute("name", "accountcode");
        variable5Element.setAttribute("value", "192.168.1.200");
        Element variable6Element = doc.createElement("variable");
        variable6Element.setAttribute("name", "user_context");
        variable6Element.setAttribute("value", "default");
        Element variable7Element = doc.createElement("variable");
        variable7Element.setAttribute("name", "directory-visible");
        variable7Element.setAttribute("value", "true");
        Element variable8Element = doc.createElement("variable");
        variable8Element.setAttribute("name", "limit_max");
        variable8Element.setAttribute("value", "5");
        
        doc.appendChild(root);
        root.appendChild(userElement);
        userElement.appendChild(paramsElement);
        paramsElement.appendChild(param1Element);
        paramsElement.appendChild(param2Element);
        paramsElement.appendChild(param3Element);
        paramsElement.appendChild(param4Element);
        paramsElement.appendChild(param5Element);
        paramsElement.appendChild(param6Element);
        paramsElement.appendChild(param7Element);
        userElement.appendChild(variablesElement);
        variablesElement.appendChild(variable1Element);
        variablesElement.appendChild(variable2Element);
        variablesElement.appendChild(variable3Element);
        variablesElement.appendChild(variable4Element);
        variablesElement.appendChild(variable5Element);
        variablesElement.appendChild(variable6Element);
        variablesElement.appendChild(variable7Element);
        variablesElement.appendChild(variable8Element);
         
        Transformer t=TransformerFactory.newInstance().newTransformer();
        //设置换行和缩进
        t.setOutputProperty(OutputKeys.INDENT,"yes");
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(new File("e:/text2.xml"))));
	}
	
	 static Document document = null;
	 static void createXML(String filename) throws ParserConfigurationException, Exception{
	        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	        document = documentBuilder.newDocument();
	        Element root = document.createElement("employees");
	        document.appendChild(root);
	        Element employee = document.createElement("employee");
	        Element name = document.createElement("name");
	        //textNode只有document能创建
	        name.appendChild(document.createTextNode("中文名"));
	        employee.appendChild(name);
	        Element sex = document.createElement("sex");
	        sex.appendChild(document.createTextNode("male"));
	        employee.appendChild(sex);
	       
	        Element age =document.createElement("age");
	        age.appendChild(document.createTextNode("13"));
	        employee.appendChild(age);
	        root.appendChild(employee);
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        //
	        DOMSource source = new DOMSource(document);
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
	        PrintWriter pw = new PrintWriter(new File(filename));
	        StreamResult streamResult = new StreamResult(pw);
	        transformer.transform(source,streamResult);
	 }

}
