package com.qcmz.cmc.web.action;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.cache.ApiKeyMap;
import com.qcmz.cmc.constant.Constant;
import com.qcmz.cmc.dao.IApiKeyDao;
import com.qcmz.cmc.vo.ApiKeyBean;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.util.DateUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Jun 9, 2014 4:36:55 PM
 * 修改历史：
 */
public class ApiKeyAction extends BaseAction {
	@Autowired
	private IApiKeyDao apiKeyDao;
	@Autowired
	private ApiKeyMap apiKeyMap;
	
	private String graphURL;

	//ApiKey今日访问量
	public String doTodayCallCount(){
		try {
			//组织数据
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			Long count = null;
			String type = null;
			List<ApiKeyBean> list = apiKeyMap.findAll();
			Map<Long, Long> timesMap = apiKeyDao.findTodayCallCount();
			for (ApiKeyBean bean : list) {
				count = timesMap.get(bean.getKeyid());
				if(count==null && bean.getStatus().equals(Constant.NORMAL_STATUS_OFF)){
					continue;
				}else if(count==null){
					count = 0L;
				}
				type = apiKeyMap.getBean(bean.getKeyid()).getApitype();
				dataset.addValue(count, type, type+bean.getKeyid());
			}
			
			JFreeChart chart = ChartFactory.createBarChart3D(
					"Today Visits"	//图表标题
					, "ApiKey"		//横轴标题
					, "Visits"	//纵轴标题
					, dataset		//数据集
					, PlotOrientation.VERTICAL	//图表方向
					, true			//是否显示图例标识
					, false			//是否显示tooltips
					, false			//是否支持超链接
			);
			
			//显示
			CategoryPlot plot = chart.getCategoryPlot();
			plot.setNoDataMessage(Constant.NODATA);

			//柱状图显示数值
			BarRenderer renderer = new BarRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_RIGHT));
			renderer.setItemLabelAnchorOffset(5D);
			plot.setRenderer(renderer);
			
			CategoryAxis domainAxis = plot.getDomainAxis(); 
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);	//Label倾斜度，文字较多时使用
			
			//数据轴数据标签的显示格式 
			NumberAxis na = (NumberAxis) plot.getRangeAxis(); 
	        na.setAutoRangeIncludesZero(true); 
	        na.setNumberFormatOverride( new DecimalFormat("#0")); 
			
			//生成图片，可配置长度和宽度
			String filename = ServletUtilities.saveChartAsPNG(chart, 760, 450, null, request.getSession());
			graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return QUERY;
	}
	
	//ApiKey每日访问量
	public String doDayCallCount(){
		try {
			//组织数据
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			int days = 31;
			Date today = DateUtil.truncDate(new Date());
			Date endDate = DateUtil.addDay(today, -1);
			Date beginDate = DateUtil.addDay(today, -days);
			Long count = null;
			
			Map<String, Map<String, Long>> map = apiKeyDao.findTypeCallCount(beginDate, endDate);
			
			//计算每天所有key访问量
			Map<String, Long> allMap = new HashMap<String, Long>();
			for (String keyType : map.keySet()) {
				Map<String, Long> typeMap = map.get(keyType);
				for (String day : typeMap.keySet()) {
					count = typeMap.get(day);
					if(allMap.get(day)==null){
						allMap.put(day, count);
					}else{
						allMap.put(day, allMap.get(day)+count);
					}
				}
			}
			map.put("ALL", allMap);
			
			//封装数据集
			String day = null;
			for (String keyType : map.keySet()) {
				Map<String, Long> typeMap = map.get(keyType);
				for(int i=0;i<days;i++){
					day = DateUtil.format(DateUtil.addDay(beginDate, i));
					count = typeMap.get(day);
					if(count==null){
						count = 0L;
					}
					dataset.addValue(count, keyType, day.substring(5));
				}
			}
			
			JFreeChart chart = ChartFactory.createLineChart(
					"Per Day Visits"	//图表标题
					, "Date"		//横轴标题
					, "Visits"	//纵轴标题
					, dataset		//数据集
					, PlotOrientation.VERTICAL	//图表方向
					, true			//是否显示图例标识
					, false			//是否显示tooltips
					, false			//是否支持超链接
			);
			
			//显示
			CategoryPlot plot = chart.getCategoryPlot();
			
			CategoryAxis domainAxis = plot.getDomainAxis(); 
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);	//Label倾斜度，文字较多时使用
			
			//数据轴数据标签的显示格式 
			NumberAxis na = (NumberAxis) plot.getRangeAxis(); 
	        na.setAutoRangeIncludesZero(true); 
	        na.setNumberFormatOverride( new DecimalFormat("#0")); 
	        
			//生成图片，可配置长度和宽度
			String filename = ServletUtilities.saveChartAsPNG(chart, 760, 450, null, request.getSession());
			graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return QUERY;
	}
	
	public String getGraphURL() {
		return graphURL;
	}

	public void setGraphURL(String graphURL) {
		this.graphURL = graphURL;
	}
	
}
