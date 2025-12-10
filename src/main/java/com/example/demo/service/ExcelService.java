package com.example.demo.service;

import com.example.demo.model.RegistrationForm;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ExcelService {
    
    private static final String FILE_PATH = "registrations.xlsx";
    private static final String SHEET_NAME = "Registrations";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public void saveToExcel(RegistrationForm form) throws IOException {
        Workbook workbook;
        Sheet sheet;
        
        File file = new File(FILE_PATH);
        
        if (file.exists()) {
            FileInputStream inputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) {
                sheet = workbook.createSheet(SHEET_NAME);
                createHeaderRow(sheet);
            }
        } else {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet(SHEET_NAME);
            createHeaderRow(sheet);
        }
        
        int lastRowNum = sheet.getLastRowNum();
        Row row = sheet.createRow(lastRowNum + 1);
        
        row.createCell(0).setCellValue(form.getName());
        row.createCell(1).setCellValue(form.getPhoneNumber());
        row.createCell(2).setCellValue(form.getAddress());
        row.createCell(3).setCellValue(form.getInvitedThrough());
        row.createCell(4).setCellValue(form.getReferencedBy());
        row.createCell(5).setCellValue(LocalDateTime.now().format(formatter));
        
        // Auto-size columns
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }
        
        try (FileOutputStream outputStream = new FileOutputStream(FILE_PATH)) {
            workbook.write(outputStream);
        }
        
        workbook.close();
    }
    
    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Name", "Phone Number", "Address", "Invited Through", "Referenced By", "Registration Date"};
        
        CellStyle headerStyle = sheet.getWorkbook().createCellStyle();
        Font headerFont = sheet.getWorkbook().createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }
}