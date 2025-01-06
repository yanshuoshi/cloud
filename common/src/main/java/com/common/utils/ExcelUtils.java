package com.common.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * excel工具类
 * @author lijianbin
 * @date 2021-02-20 11:03
 **/
@Slf4j
public class ExcelUtils {


    private ExcelUtils(){}
    private static ExcelUtils instance = new ExcelUtils();
    public static ExcelUtils getInstance(){
        return instance;
    }
    /**
     * 导入
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null){
            return new ArrayList<>();
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = new ArrayList<>();
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        }catch (Exception e){
            log.error("excel格式化异常");
        }
        return list;
    }

    /**
     * Excel表格导出
     * @param response  HttpServletResponse对象
     * @param pojoClass 实体类
     * @param dataList  Excel表格的数据
     * @param fileName  导出Excel的文件名
     * @param title     标题
     * @param sheetName 工作表名称
     */
    public static void exportExcel(HttpServletResponse response, Class<?> pojoClass, List<?> dataList, String fileName,
                                   String title, String sheetName) {
        try (Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title, sheetName), pojoClass, dataList)) {
            //准备将Excel的输出流通过response输出到页面下载,八进制输出流
            response.setContentType("application/octet-stream");
            if (StringUtils.isNotBlank(fileName)) {
                fileName = fileName + ".xls";
            } else {
                fileName = System.currentTimeMillis() + ".xls";
            }
            //设置导出Excel的名称
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            //刷新缓冲
            response.flushBuffer();
            //workbook将Excel写入到response的输出流中，供页面下载该Excel文件
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            log.error("Excel导出失败");
        }
    }

    /**
     * 将 List<Map<String,Object>> 类型的数据导出为 Excel
     * 默认 Excel 文件的输出路径为 项目根目录下
     * 文件名为 filename + 时间戳 + .xlsx
     *
     * @param mapList 数据源(通常为数据库查询数据)
     * @param filename   文件名前缀, 实际文件名后会加上日期
     * @param title   表格首行标题
     * @return  文件输出路径
     */
    public String  createExcel(List<Map<String, Object>> mapList,String filename, String title, HttpServletResponse response) {
        //获取数据源的 key, 用于获取列数及设置标题
        Map<String, Object> map = mapList.get(0);
        Set<String> stringSet = map.keySet();
        ArrayList<String> headList = new ArrayList<>(stringSet);

        //定义一个新的工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建一个Sheet页
        HSSFSheet sheet = wb.createSheet(title);
        //设置行高
        sheet.setDefaultRowHeight((short) (1 * 256));
        //为有数据的每列设置列宽
        for (int i = 0; i < headList.size(); i++) {
            sheet.setColumnWidth(i, 3000);
        }
        //设置单元格字体样式
        HSSFFont font = wb.createFont();
        font.setFontName("等线");
        font.setFontHeightInPoints((short) 16);
        // font.setBold(true);

        //在sheet里创建第一行，并设置单元格内容为 title (标题)
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headList.size() - 1));
        // 创建单元格文字居中样式并设置标题单元格居中
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        // cellStyle.setBorderBottom(BorderStyle.DASHED); //下边框
        // cellStyle.setBorderLeft(BorderStyle.DASHED);//左边框
        // cellStyle.setBorderTop(BorderStyle.DASHED);//上边框
        // cellStyle.setBorderRight(BorderStyle.DASHED);//右边框
        titleCell.setCellStyle(cellStyle);

        //获得表格第二行
        HSSFRow row = sheet.createRow(1);
        //根据数据源信息给第二行每一列设置标题
        for (int i = 0; i < headList.size(); i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(headList.get(i));
        }

        HSSFRow rows;
        HSSFCell cells;
        //循环拿到的数据给所有行每一列设置对应的值
        for (int i = 0; i < mapList.size(); i++) {
            //在这个sheet页里创建一行
            rows = sheet.createRow(i + 2);
            //给该行数据赋值
            for (int j = 0; j < headList.size(); j++) {
                String value = mapList.get(i).get(headList.get(j)).toString();
                cells = rows.createCell(j);
                cells.setCellValue(value);
            }
        }

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        // 使用项目根目录, 文件名加上时间戳
        // String path = System.getProperty("user.dir") + "\\" + filename + dateFormat.format(date) + ".xlsx";
        String path = filename + dateFormat.format(date) + ".xlsx";
        System.out.println("Excel文件输出路径: "+path);
        try {
            //准备将Excel的输出流通过response输出到页面下载
            //八进制输出流
            response.setContentType("application/octet-stream");
            //设置导出Excel的名称
            response.setHeader("Access-Control-Expose-Headers", "Content-disposition");//放开请求头 前端可取
            response.setHeader("Content-disposition", "attachment;filename=" +URLEncoder.encode(path,"utf-8"));
            //刷新缓冲
            response.flushBuffer();

            //workbook将Excel写入到response的输出流中，供页面下载该Excel文件
            wb.write(response.getOutputStream());

            // File file = new File(path);
            // FileOutputStream fileOutputStream = new FileOutputStream(file);
            // wb.write(fileOutputStream);
            // wb.close();
            // fileOutputStream.close();
            //关闭workbook
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }


    public static void main(String[] args) {
        System.out.println("数据加载...");
        List<Map<String, Object>> mapArrayList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("姓名", i);
            map.put("年龄", i);
            map.put("性别", i);
            mapArrayList.add(map);
        }
        System.out.println("数据加载完成...");
        // ExcelUtils.getInstance().createExcel(mapArrayList,"文件名","测试数据");

    }


}