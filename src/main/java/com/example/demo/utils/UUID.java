package com.example.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UUID {

	public static String uuId() {
		java.util.UUID uuid = java.util.UUID.randomUUID();
		String result = uuid.toString();
		return result.replaceAll("-", "");
	}
	
	public static void main(String[] args) {
		// 内容
		String value = "if(!strLink){document.write('< a href=\"./201911/t20191104_4354454.shtml\" target=\"_blank\">北京市海淀区人民政府关于印发《海淀区道路环境卫生工作分级分类实施方案（2019年修订）》的通知</ a>')}";

		// 匹配规则
		String reg = "href=(.*?)target";
		Pattern pattern = Pattern.compile(reg);

		// 内容 与 匹配规则 的测试
		Matcher matcher = pattern.matcher(value);

		if( matcher.find() ){
			// 包含前后的两个字符
			System.out.println(matcher.group());
			// 不包含前后的两个字符
			System.out.println( matcher.group(1) );
		}else{
			System.out.println(" 没有匹配到内容....");
		}
	}
}
