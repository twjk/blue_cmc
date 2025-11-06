package com.qcmz.cmc.util;

import java.awt.Font;

import org.jfree.chart.StandardChartTheme;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Jun 10, 2014 5:10:51 PM
 * 修改历史：
 */
public class JFreeChartUtil {
	public static StandardChartTheme THEME_DEFAULT;
	static{
		THEME_DEFAULT = new StandardChartTheme("CN");
		//设置标题字体  
		THEME_DEFAULT.setExtraLargeFont(new Font("宋书",Font.BOLD,20));  
		//设置图例的字体  
		THEME_DEFAULT.setRegularFont(new Font("宋书",Font.PLAIN,12));  
		//设置轴向的字体  
		THEME_DEFAULT.setLargeFont(new Font("宋书",Font.PLAIN,15));
	}
}
