package com.qcmz.cmc.process;

import java.io.File;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.ProxyMap;
import com.qcmz.cmc.entity.CmcProxy;
import com.qcmz.cmc.ws.provide.vo.SpeechAsrBean;
import com.qcmz.cmc.ws.provide.vo.SpeechAsrResult;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.util.FilePathUtil;
import com.qcmz.framework.util.FileTypeUtil;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.StringUtil;

@Component
public class SpeechDataProcess {
	@Autowired
	private SpeechProcess speechProcess;
	@Autowired
	private ProxyMap proxyMap;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private int successCount = 0;
	private int failCount = 0;
	private int ignoreCount = 0;
	private int seq = 0;
	
	/**
	 * 语音识别
	 * @param params
	 * @exception ParamException
	 */
	public void asr(Map<String, String> params){
		String lang = params.get("lang");
		Long proxyId = Long.valueOf(params.get("proxyId"));
		if(proxyId==null){
			throw new ParamException("存储路径有误");
		}
		
		String baseDir = params.get("baseDir");
		if(StringUtil.isBlankOrNull(baseDir) 
				|| !FileUtil.newDir(baseDir).exists()){
			throw new ParamException("路径有误");
		}

		logger.info("开始识别");
		
		successCount = 0;
		failCount = 0;
		ignoreCount = 0;
		seq = 0;
		asr(lang, new File(baseDir), proxyId);
		
		StringBuilder sbResult = new StringBuilder("语音识别完成，结果：")
				.append("【共计,").append(seq).append("】")
				.append("【成功,").append(successCount).append("】")
				.append("【失败,").append(failCount).append("】")
				.append("【跳过,").append(ignoreCount).append("】")
				;
		
		logger.info(sbResult);
	}
	
	/**
	 * 语音失败
	 */
	private void asr(String lang, File file, Long proxyId){
		if(!file.exists()) return;
		
		if(file.isDirectory()){
			for (File f : file.listFiles()) {
				asr(lang, f, proxyId);
			}
		}else{
			try {
				String fileType = FileTypeUtil.getFileType(file.getPath());
				if(!"wav".equals(fileType)) return;
				
				seq++;
				
				CmcProxy proxy = proxyMap.getBean(proxyId);
				String asrResultFilePath = new StringBuilder()
						.append(file.getParent())
						.append(FilePathUtil.FILE_SEP).append(FilePathUtil.getOnlyFileName(file.getPath()))
						.append("_").append(proxy.getProxycode()).append(".txt")
						.toString();
				File asrResultFile = new File(asrResultFilePath);
				if(asrResultFile.exists()){
					ignoreCount++;
					logger.info(seq+"：跳过");
					return;
				}
				
				SpeechAsrResult asrResult = speechProcess.asr(new SpeechAsrBean(lang, file, proxyId));
				FileUtil.stringToFile(asrResult.getText(), asrResultFilePath);
				logger.info(seq+"：语音识别成功【"+file.getPath()+"】，成功识别【"+(++successCount)+"】个");
			} catch (Exception e) {
				logger.error(seq+"：语音识别失败【"+file.getPath()+"】，识别失败【"+(++failCount)+"】个");
			}
		}
	}
}
