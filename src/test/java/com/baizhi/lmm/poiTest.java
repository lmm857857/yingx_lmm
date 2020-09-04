package com.baizhi.lmm;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.lmm.entity.poiEmp;
import com.baizhi.lmm.entity.poiTeacher;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontFormatting;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class poiTest {
    //简单写出
    @Test
    public void testpoi(){
        //创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作表 参数：工作表的名字 默认名字 sheet0 sheet1 ....
        HSSFSheet sheet = workbook.createSheet();
        //创建一行 参数：行下表（下表从零开始）
        HSSFRow row = sheet.createRow(0);
        //创建一个单元格 参数：单元格下表
        HSSFCell cell = row.createCell(0);
        //给单元格设置内容
        cell.setCellValue("内容测试");
        //导出单元格
        try {
            workbook.write(new FileOutputStream(new File("E://yingx-poi测试.xls")));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //集合写出
    @Test
    public void testpois(){
        //模拟一些数据
        ArrayList<poiEmp> emps = new ArrayList<>();
        emps.add(new poiEmp("1","ming",22,new Date()));
        emps.add(new poiEmp("2","ming1",23,new Date()));
        emps.add(new poiEmp("3","ming2",24,new Date()));

        //创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建一个工作表 参数：工作表的名字 默认名字 sheet0 sheet1 ....
        HSSFSheet sheet = workbook.createSheet();

        //设置列宽 参数：列索引 列值（换算*256）
        sheet.setColumnWidth(3,20*256);

        //创建字体样式
        HSSFFont font = workbook.createFont();
            //设置字号
            font.setFontHeightInPoints((short) 20);
            //设置字体
            font.setFontName("微软雅黑");
            //设置粗体
            font.setBold(true);
            //颜色
            font.setColor(Font.COLOR_RED);
            //斜体
            font.setItalic(true);
            //下划线
            font.setUnderline(FontFormatting.U_SINGLE);

        //创建样式对象
        HSSFCellStyle cellStyle1 = workbook.createCellStyle();
            cellStyle1.setAlignment(HorizontalAlignment.CENTER);//居中样式
            cellStyle1.setFont(font);//将设置好的字体样式给样式对象

        //标题行
        HSSFRow titlerow = sheet.createRow(0);

        //行的高度 参数：行高度 1/20
        titlerow.setHeight((short)(20*20));

        //创建标题行单元格
        HSSFCell cell1 = titlerow.createCell(0);

        //给单元格设置指定样式
        cell1.setCellStyle(cellStyle1);

        //赋值
        cell1.setCellValue("测试表");

        //合并单元格 参数：firstRow行开始 lastRow行结束 firstCol列开始 lastCol列结束
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, 3);
        CellRangeAddress cellAddresses1 = new CellRangeAddress(2, 6, 4, 4);

        //将设置好的单元格策略赋给sheet
        sheet.addMergedRegion(cellAddresses);
        sheet.addMergedRegion(cellAddresses1);



        //目录行
        String[] ti={"Id","名字","年龄","生日"};


        //创建一行 参数：行下表（下表从零开始）
        HSSFRow row = sheet.createRow(1);

        //行的高度 参数：行高度 1/20
        row.setHeight((short)(15*20));

        //遍历数组 处理目录数据
        for (int i = 0; i < ti.length; i++) {
            //创建一个单元格 参数：单元格下标
            HSSFCell cell = row.createCell(i);
            //给单元格设置内容
            cell.setCellValue(ti[i]);
        }

        //创建一个日期格式对象
        HSSFDataFormat format = workbook.createDataFormat();

        //创建日期格式
        short formats = format.getFormat("yyyy年MM月dd日");

        //创建样式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        //将设计好的日期格式给样式对象
        cellStyle.setDataFormat(formats);

        //处理数据行
        //遍历数据
        for (int i = 0; i < emps.size(); i++) {

            //遍历一条数据 创建一行
            HSSFRow rows = sheet.createRow(i + 2);

            //创建单元格 给单元格赋值
            rows.createCell(0).setCellValue(emps.get(i).getId());
            rows.createCell(1).setCellValue(emps.get(i).getName());
            rows.createCell(2).setCellValue(emps.get(i).getAge());

            //创建日期单元格
            HSSFCell cell = rows.createCell(3);

            //给单元格设置指定的样式
            cell.setCellStyle(cellStyle);

            //给单元格赋值
            cell.setCellValue(emps.get(i).getDate());
        }

        //导出单元格
        try {
            workbook.write(new FileOutputStream(new File("E://yingx-poi测试.xls")));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读入
    @Test
    public void inport(){
        //获取表格中的数据 读入程序
        //插入数据库

        try {
            //获取要导入的文件
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("E://yingx-poi测试.xls")));

            //根据文档获取工作表
            HSSFSheet sheet = workbook.getSheet("sheet0");

            //获取最后一行下表
            //int lastRowNum = sheet.getLastRowNum();
            //System.out.println(lastRowNum);

            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                //获取行
                HSSFRow row = sheet.getRow(i);

                //获取行数据
                String id = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                double ages = row.getCell(2).getNumericCellValue();
                int age = (int) ages;
                Date date = row.getCell(3).getDateCellValue();

                poiEmp poiEmp = new poiEmp(id, name, age, date);
                //向数据库插入(调用插入方法OK)
                //System.out.println("向数据库插入数据"+poiEmp);
            }
            //获取单元格
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //esaypoi单表导出简单测试
    @Test
    public void easyPoi(){
        //模拟一些数据
        ArrayList<poiEmp> emps = new ArrayList<>();
        emps.add(new poiEmp("1","ming",22,new Date()));
        emps.add(new poiEmp("2","ming1",23,new Date()));
        emps.add(new poiEmp("3","ming2",24,new Date()));

        //导出的参数 参数：标题，工作表名
        ExportParams exportParams = new ExportParams("计算机一班学生", "学生");
        //配置工作表参数 参数；导出参数对象,要导出的对象，导出的数据集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,poiEmp.class, emps);

        try {
            //导出
            workbook.write(new FileOutputStream(new File("E://yingx-EasyPoi测试.xls")));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //esaypoi多表导出简单测试
    @Test
    public void easyPois(){
        //模拟一些数据
        ArrayList<poiEmp> emps = new ArrayList<>();
        emps.add(new poiEmp("1","ming",22,new Date()));
        emps.add(new poiEmp("2","ming1",23,new Date()));
        emps.add(new poiEmp("3","ming2",24,new Date()));
        //"领导啊"
        poiTeacher teacher1 = new poiTeacher("1", "大佬", 25, new Date(), emps);
        poiTeacher teacher2 = new poiTeacher("2", "小佬", 25, new Date(), emps);
        ArrayList<poiTeacher> poiTeachers = new ArrayList<>();
        poiTeachers.add(teacher1);
        poiTeachers.add(teacher2);
        //导出的参数 参数：标题，工作表名
        ExportParams exportParams = new ExportParams("计算机一班学生", "学生");
        //配置工作表参数 参数；导出参数对象,要导出的对象，导出的数据集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, poiTeacher.class, poiTeachers);

        try {
            //导出
            workbook.write(new FileOutputStream(new File("E://yingx-EasyPoi测试.xls")));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //easyPoi导入
    @Test
    public void easyInport() {
        //配置导入的相关参数
        ImportParams params = new ImportParams();
        params.setTitleRows(1); //表格标题所占行 默认0
        params.setHeadRows(2);  //表头所占行 表头行数 默认1

        try {
            FileInputStream fileInputStream = new FileInputStream(new File("E://yingx-EasyPoi测试.xls"));

            List<poiTeacher> list = ExcelImportUtil.importExcel(fileInputStream,poiTeacher.class, params);

            for (poiTeacher poiTeacher : list) {
                System.out.println("教师数据"+poiTeacher);
                List<poiEmp> emps = poiTeacher.getEmps();
                for (poiEmp emp : emps) {
                    System.out.println("学生数据"+emp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
