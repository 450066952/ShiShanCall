package com.inch.rest.utils;

import com.google.common.base.Strings;
import com.inch.model.User;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExcelUtils {

        //表头
        private String title;
        //各个列的表头
        private String[] heardList;
        //需要填充的数据信息
        private List<User> data;
        //字体大小
        private int fontSize = 14;
        //行高
        private int rowHeight = 30;
        //列宽
        private int columWidth = 200;
        //工作表
        private String sheetName = "sheet1";

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String[] getHeardList() {
            return heardList;
        }

        public void setHeardList(String[] heardList) {
            this.heardList = heardList;
        }

        public List<User> getData() {
            return data;
        }

        public void setData(List<User> data) {
            this.data = data;
        }

        public int getFontSize() {
            return fontSize;
        }

        public void setFontSize(int fontSize) {
            this.fontSize = fontSize;
        }

        public int getRowHeight() {
            return rowHeight;
        }

        public void setRowHeight(int rowHeight) {
            this.rowHeight = rowHeight;
        }

        public int getColumWidth() {
            return columWidth;
        }

        public void setColumWidth(int columWidth) {
            this.columWidth = columWidth;
        }

        public String getSheetName() {
            return sheetName;
        }

        public void setSheetName(String sheetName) {
            this.sheetName = sheetName;
        }

        /**
         * 开始导出数据信息
         *
         */
        public byte[] exportExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
            //检查参数配置信息
            checkConfig();
            //创建工作簿
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建工作表
            HSSFSheet wbSheet = wb.createSheet(this.sheetName);
            //设置默认行宽
            wbSheet.setDefaultColumnWidth(20);

            // 标题样式（加粗，垂直居中）
            HSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中HSSFFont.
            HSSFFont fontStyle = wb.createFont();
            fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            fontStyle.setFontHeightInPoints((short)16);  //设置标题字体大小
            cellStyle.setFont(fontStyle);
            // 设置单元格上、下、左、右的边框线
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

            //在第0行创建rows  (表标题)
            HSSFRow title = wbSheet.createRow((int) 0);
            title.setHeightInPoints(30);//行高
            HSSFCell cellValue = title.createCell(0);
            cellValue.setCellValue(this.title);
            cellValue.setCellStyle(cellStyle);
            wbSheet.addMergedRegion(new CellRangeAddress(0,0,0,(this.heardList.length-1)));

            //设置表头样式，表头居中
            HSSFCellStyle style = wb.createCellStyle();
            //设置单元格样式
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            //设置字体
            HSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short) this.fontSize);
            style.setFont(font);
            // 设置单元格上、下、左、右的边框线
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            //在第1行创建rows
            HSSFRow row = wbSheet.createRow((int) 1);
            //设置列头元素
            HSSFCell cellHead = null;
            for (int i = 0; i < heardList.length; i++) {
                cellHead = row.createCell(i);
                cellHead.setCellValue(heardList[i]);
                cellHead.setCellStyle(style);
            }

            //设置表体内容样式
            HSSFCellStyle styleBody = wb.createCellStyle();
            //设置单元格样式
            styleBody.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleBody.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            //设置字体
            HSSFFont fontBody = wb.createFont();
            fontBody.setFontName("宋体");
//            fontBody.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            fontBody.setFontHeightInPoints((short) 13);  //设置标题字体大小
            styleBody.setFont(fontBody);
            // 设置单元格上、下、左、右的边框线
            styleBody.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleBody.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            styleBody.setBorderRight(HSSFCellStyle.BORDER_THIN);
            styleBody.setBorderTop(HSSFCellStyle.BORDER_THIN);

            //开始写入实体数据信息
            int a = 2;
            for (int i = 0; i < data.size(); i++) {
                int j = 0;
                HSSFRow roww = wbSheet.createRow(a);
                User user = data.get(i);
                HSSFCell cell = roww.createCell(j++);
                cell.setCellStyle(styleBody);
                cell.setCellValue(user.getName());

                cell = roww.createCell(j++);
                cell.setCellStyle(styleBody);
                cell.setCellValue(user.getSex());

                cell = roww.createCell(j++);
                cell.setCellStyle(styleBody);
                cell.setCellValue(user.getAge());

                cell = roww.createCell(j++);
                cell.setCellStyle(styleBody);
                cell.setCellValue(user.getPhoneNo());

                cell = roww.createCell(j++);
                cell.setCellStyle(styleBody);
                cell.setCellValue(user.getAddress());

                cell = roww.createCell(j++);
                cell.setCellStyle(styleBody);
                cell.setCellValue(user.getHobby());
                a++;
            }

            //导出数据
            try {
                //设置Http响应头告诉浏览器下载这个附件
                response.setHeader("Content-Disposition", "attachment;Filename=" + System.currentTimeMillis() + ".xls");
                OutputStream outputStream = response.getOutputStream();
                wb.write(outputStream);
                outputStream.close();
                return wb.getBytes();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new IOException("导出Excel出现严重异常，异常信息：" + ex.getMessage());
            }

        }

        /**
         * 检查数据配置问题
         *
         * @throws IOException 抛出数据异常类
         */
        protected void checkConfig() throws IOException {

            if (fontSize < 0 || rowHeight < 0 || columWidth < 0) {
                throw new IOException("字体、宽度或者高度不能为负值");
            }

            if (Strings.isNullOrEmpty(sheetName)) {
                throw new IOException("工作表表名不能为NULL");
            }
        }
}
