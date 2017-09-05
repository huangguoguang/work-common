package com.huangguang.work.xml.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * XML工具类 XML与其他格式互相转换
 * @author huangguang
 *
 */
public class XMLUtil {
	
	/**
	 * 将map转换为xml
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String convertMapToXML(SortedMap<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set<?> es = map.entrySet();
		Iterator<?> it = es.iterator();
		while(it.hasNext()) {
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append("<" + key + ">" + value + "</" + key + ">");
		}
		sb.append("</xml>");
		return sb.toString();
	}
	
	/**
	 * jdk1.8遍历
	 * @param map
	 * @return
	 */
	public static String convertMapToXMLHigh(SortedMap<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		map.forEach((key, value) -> {
			sb.append("<" + key + ">" + value + "</" + key + ">");
		});
		sb.append("</xml>");
		return sb.toString();
	}
	
	/**
	 * 将xml解析成map
	 * @param xml
	 * @return
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public static String convertXMLToMap(String xml) throws IOException, JDOMException {
		xml = xml.replaceFirst("encoding = \".*\"", "encoding = \"UTF-8\"");
		if (null == xml || "".equals(xml)) {
			return null;
		}
		System.out.println(xml);
		Map<String, Object> map = new HashMap<String, Object>();
		InputStream in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(in);
		Element root = document.getRootElement();
		List<Element> list = root.getChildren();
		list.forEach(element -> {
			String key = element.getName();
			String value = "";
			List<Element> children = element.getChildren();
			if (!children.isEmpty()) {
				value = getChildrenText(map, children);
			} else {
				value = element.getTextNormalize();
				map.put(key, value);
			}
		});
		return map.toString();
	}
	
	public static String getChildrenText(Map<String, Object> map, List<Element> children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			children.forEach(child -> {
				String key = child.getName();
				String value = "";
				if (!child.getChildren().isEmpty()) {
					value = getChildrenText(map, child.getChildren());
				} else {
					value = child.getTextNormalize();
					map.put(key, value);
				}
			});
		}
		return sb.toString();
	}

	public static void main(String[] args) throws IOException, JDOMException {
//		Map<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("one", "first");
//		paramMap.put("two", "second");
//		paramMap.put("three", "third");
//		System.out.println(paramMap);
//		SortedMap<String, String> map = new TreeMap<String, String>();
//		map.putAll(paramMap);
//		System.out.println(map);
//		System.out.println(convertMapToXML(map));
//		System.out.println(convertMapToXMLHigh(map));
		
		String xml = "<xml><appid><a>aaaa</a><b>bbbbceshi</b></appid><attach>支付测试</attach><body>APP支付测试</body><mch_id>10000100</mch_id><nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>"  
                + "<notify_url>http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php</notify_url><out_trade_no>1415659990</out_trade_no><spbill_create_ip>14.23.150.211</spbill_create_ip>"  
                + "<total_fee>1</total_fee><trade_type>APP</trade_type><sign>0CB01533B8C1EF103065174F50BCA001</sign></xml>";  
		System.out.println(xml);
		System.out.println(convertXMLToMap(xml));
	}

}
