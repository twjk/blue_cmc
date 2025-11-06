package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.qcmz.cmc.entity.CmcTransLog;
import com.qcmz.cmc.service.ITransLogService;
import com.qcmz.framework.office.AbstractExcelExportPageByLast;
import com.qcmz.framework.util.DateUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransLogExport extends AbstractExcelExportPageByLast{
	private ITransLogService transLogService;
	
	public TransLogExport() {
		super();
	}

	public TransLogExport(ITransLogService transLogService, String fileName) {
		super(fileName);
		this.transLogService = transLogService;
	}

	/** 
	 * 获取导出的数据
	 * @param cond
	 * @return
	 * 修改历史：
	 */
	@Override
	protected List<Object> findData(Map<String, ?> cond, Object last, int pageSize) {
		Long lastId = null;
		if(last == null){
			lastId = (Long) cond.get("lastId");
		}else{
			lastId = ((CmcTransLog) last).getLogid();
		}
		Long maxId = (Long) cond.get("maxId");
		
		List<Object> list = new ArrayList<Object>();
		list.addAll(transLogService.findLog(lastId, maxId, pageSize));
		return list;
	}

	/** 
	 * 设置每一行的数据
	 * @param row
	 * @param obj
	 * 修改历史：
	 */
	@Override
	protected void setCellValue(Row row, Object obj) {
		CmcTransLog log = (CmcTransLog) obj;
		Cell cell = row.createCell(0);
		cell.setCellValue(log.getFromlang());
		cell = row.createCell(1);
		cell.setCellValue(log.getSrc());
		cell = row.createCell(2);
		cell.setCellValue(log.getTolang());
		cell = row.createCell(3);
		cell.setCellValue(log.getDst());
		cell = row.createCell(4);
		cell.setCellValue(DateUtil.formatDateTime(log.getStarttime()));
		cell = row.createCell(5);
		cell.setCellValue(DateUtil.formatDateTime(log.getEndtime()));
		cell = row.createCell(6);
		cell.setCellValue(log.getProxyid());
		cell = row.createCell(7);
		cell.setCellValue(log.getToken());
		cell = row.createCell(8);
		cell.setCellValue(log.getReqcountry());
	}

	/** 
	 * 设置表头
	 * @param sheet
	 * @return
	 * 修改历史：
	 */
	@Override
	protected Row setColTitle(Sheet sheet) {
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("原文语言");
		cell = row.createCell(1);
		cell.setCellValue("原文");
		cell = row.createCell(2);
		cell.setCellValue("译文语言");
		cell = row.createCell(3);
		cell.setCellValue("译文");
		cell = row.createCell(4);
		cell.setCellValue("开始时间");
		cell = row.createCell(5);
		cell.setCellValue("结束时间");
		cell = row.createCell(6);
		cell.setCellValue("代理编号");
		cell = row.createCell(7);
		cell.setCellValue("用户");
		cell = row.createCell(8);
		cell.setCellValue("国家");
		return row;
	}
}
