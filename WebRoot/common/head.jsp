<%@ page language="java" pageEncoding="UTF-8"%>
	<!-- Font Awesome 图标库 - 必须在最前面加载 -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	<script>
		// 确保Font Awesome加载完成
		(function() {
			var link = document.querySelector('link[href*="font-awesome"]');
			if (link) {
				link.onload = function() {
					console.log('✅ Font Awesome CSS已加载');
					// 触发自定义事件
					if (typeof window.dispatchEvent !== 'undefined') {
						window.dispatchEvent(new Event('fontawesome-loaded'));
					}
				};
			}
		})();
	</script>
	<!-- Google Fonts - Inter 现代字体 -->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
	<!-- 原有样式文件 -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/ticket.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/validatorAuto.css" />
	<!-- 现代化样式覆盖 -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/modern.css?v=2.7" />
	<script language='javascript' src="<%=request.getContextPath() %>/javascript/datePicker/WdatePicker.js"></script>
	<script language='javascript' src="<%=request.getContextPath() %>/javascript/jquery-1.11.0.min.js"></script>
	<script language='javascript' src="<%=request.getContextPath() %>/javascript/datamgr_common.js"></script>
	<script language='javascript' src="<%=request.getContextPath() %>/javascript/objcheck.js"></script>
	<script language='javascript' src="<%=request.getContextPath() %>/javascript/common.js"></script>
	<script language='javascript' src="<%=request.getContextPath() %>/javascript/formValidator.js"></script>
	<script language='javascript' src="<%=request.getContextPath() %>/javascript/formValidatorRegex.js"></script>
	<script language='javascript' src="<%=request.getContextPath() %>/javascript/MyValidator.js"></script>
	<div id="coverLayer" style="z-index:99999">正在处理...</div>
	<script type="text/javascript">var contextPath = "<%=request.getContextPath() %>"</script>