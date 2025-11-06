package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.process.CrowdTaskDataProcess;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.exception.ParamException;

/**
 * 类说明：数据维护
 * 修改历史：
 */
public class CrowdTaskDataMgrAction extends BaseAction {
	@Autowired
	private CrowdTaskDataProcess crowdTaskDataProcess;
	
	/**
	 * 测试
	 */
	public String doTest(){
		
		return null;
	}
	
	
	/**
	 * 导入题库
	 * @return
	 */
	public String doImportLib(){
		try {
			crowdTaskDataProcess.importLib(getParameterMap());
		} catch(ParamException e){
			handleResult = false;
			setResult(e.getMessage());
		}catch (Exception e) {
			logger.error("导入题库失败",e);
			handleResult = false;
			setResult("导入题库失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 删除文件
	 * @return
	 */
	public String doDeleteUserCrowdTaskFile(){
		try {
			crowdTaskDataProcess.deleteUserCrowdTaskFile(getParameterMap());
		} catch(ParamException e){
			handleResult = false;
			setResult(e.getMessage());
		}catch (Exception e) {
			logger.error("删除文件失败",e);
			handleResult = false;
			setResult("删除文件失败");
		}
		
		print();
		
		return null;
	}
	
}
