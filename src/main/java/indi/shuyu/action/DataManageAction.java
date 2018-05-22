package indi.shuyu.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontFormatting;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import indi.shuyu.common.base.Base;
import indi.shuyu.common.base.FileUtils;
import indi.shuyu.common.base.JacksonUtils;
import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.common.base.StringUtils;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.UserDao;
import indi.shuyu.model.entity.Data;
import indi.shuyu.model.entity.Export;
import indi.shuyu.model.entity.Search;
import indi.shuyu.system.logger.LogDash;

@Controller
@RequestMapping("/data/export")
public class DataManageAction {
	
	private final static Logger logger = LoggerFactory.getLogger(DataManageAction.class);
	LogDash ld;
	
	@Autowired  
	private UserDao ud;  
	
	@Autowired  
	private HttpSession session; 
	
	//private String base = "D:/apache-tomcat-7.0.82/webapps/activity/WEB-INF/excels/";
	private int MAX_NUM = 100;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void statisticActivity(@RequestParam(value="paramStr", required=false) String paramStr, String type, HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		// 下载文件的mime类型
		response.setContentType("application/vnd.ms-excel"); 

		// 文件的打开方式 inline在线打开 attachment
		String agent = request.getHeader("User-Agent");
		String filename = FileUtils.encodeDownloadFilename("导出数据.xlsx", agent);
		response.setHeader("content-disposition", "attachment;fileName="+filename);
		ServletOutputStream outputStream = response.getOutputStream();
		
		// 获取模板 在当前项目
//		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//		long date = System.currentTimeMillis();
//		int random = (int) (Math.random()*10000);
//		String templatePath = request.getServletContext().getRealPath(File.separator) + "excels/"+ File.separator  + date + random +  ".xlsx"
		//File file = new File(templatePath);
		//FileInputStream fileInputStream = new FileInputStream(templatePath);
		
		
		String  role = Base.getLoginUserOrRole(session);
		Search params = new Search();
	    if (paramStr != null && paramStr.length() > 0) {
	   	   params = JacksonUtils.toObject(paramStr, Search.class);
	    }
	    params.setCreateUser(role);
	    Timestamp t = Base.toTimeStamp();
	    
	    params.setSysTime(t);
		
	    int tp = 0;
	    if (type != null && !type.equals("")) tp = Integer.valueOf(type);
	    
		export(outputStream, tp, params, ud);
		
    }	
	
	private void export(ServletOutputStream outputStream, int type, Search s, UserDao ud) 
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		
		SXSSFWorkbook wb = new SXSSFWorkbook(MAX_NUM);
		SXSSFSheet sh = wb.createSheet();
		
		wb.setCompressTempFiles(true);
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	
		 // 单元格样式
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        //设置自动换行
        cellStyle.setWrapText(true);
		
        GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		HashMap<String, String> column = gv.getExportColumn(type);
		
		Row firstRow = sh.createRow(0); 

		int numForColumn = 0;
		for (Map.Entry<String, String> entry : column.entrySet()) {

			Cell cell =  firstRow.createCell(numForColumn++); 
			cell.setCellValue(entry.getKey());
	    }
		//设置自动列宽
		sh.trackAllColumnsForAutoSizing();
        for (int i = 0; i < numForColumn; i++) {
            
            sh.setColumnWidth(i, sh.getColumnWidth(i)*17/10);
        }
		
		
		Class DataClass = Class.forName("indi.shuyu.model.entity.Data");
		
		List<Data> lists = getExportList(type, s, ud);
		
		if (lists != null) {
			
			int len = lists.size();
			for(int i = 1; i <= len; i++) {
				
				Row row = sh.createRow(i);
				
				Data data = lists.get(i - 1);
				
				numForColumn = 0;
				for (Map.Entry<String, String> entry : column.entrySet()) {
					
					Cell cell =  row.createCell(numForColumn++); 
					String key = entry.getValue();
					

					Method getAttrFunc = DataClass.getMethod("get" + StringUtils.upperFirstChar(key));
					Object obj = getAttrFunc.invoke(data);
					
					if (obj != null) {
						
						String value = obj.toString();
						if (key.equals("isSeriesAwardForJoinActivity")) {
							value = value.equals("1") ? "连续奖励" : "单次奖励";
						}
						cell.setCellValue(value);
					} else {
						cell.setCellValue("");
					}
				
			    }
			}
			
			try {
				wb.write(outputStream);
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}
	}
	
	private List<Data> getExportList(int type, Search s, UserDao ud) {
		
		List<Data> list = null;
		
		switch(type) {
		
			case 0:case 1:
				list = ud.selectUserEarnAwardListForExport(s);
				break;
			case 2:
				list = ud.selectStatisticInActivityDimensionForExport(s);
				break;
			case 3:
				list = ud.selectStatisticInPrizeDimensionForExport(s);
				break;
			case 4:
				list = ud.selectStatisticInUserDimensionForExport(s);
				break;
			default:
				break;
		}
		
		return list;
	}
}
