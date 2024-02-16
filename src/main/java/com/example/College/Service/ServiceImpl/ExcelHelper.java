package com.example.College.Service.ServiceImpl;

import com.example.College.Model.User;
import com.example.College.Repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String SHEET = "data";
    static String[] HEADER = {"username", "Email", "mobile"};


    public static ByteArrayInputStream exportExcel(List<User> user) throws Exception {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);
            sheet.setColumnWidth(0,10000);
            sheet.setColumnWidth(1,10000);
            sheet.setColumnWidth(2,50000);
            Row headerRow = sheet.createRow(0);
            CellStyle cellStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            cellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFont(font);
            for (int i = 0; i < HEADER.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADER[i]);
                cell.setCellStyle(cellStyle);
            }
            int i = 1;
            for (User user1: user) {
                Row row = sheet.createRow(i++);
                row.createCell(0).setCellValue(user1.getUserName());
                row.createCell(1).setCellValue(user1.getUserEmail());
                row.createCell(2).setCellValue(user1.getUserMobileNo());

            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        }catch (Exception e){
            throw  new Exception(("Failed to export data"));
        }
    }
}

