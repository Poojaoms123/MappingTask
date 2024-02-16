package com.example.College.Service.ServiceImpl;


import com.example.College.Model.User;
import com.example.College.Repository.UserRepository;
import com.example.College.Service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.DataFormatter;


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

import java.io.InputStream;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    private Object User;

    @Override
    public String importExcel(MultipartFile file) throws Exception {
        try {
            if (file == null || file.isEmpty()) {
                throw new Exception("file is Null");
            }
            String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
            String Extension = FilenameUtils.getExtension(file.getOriginalFilename());
            System.out.println("filename is: " + fileName + " extension is: " + Extension);

            if (Extension.equalsIgnoreCase("xlsx") || Extension.equalsIgnoreCase("csv") || Extension.equalsIgnoreCase("xls")) {

                if (fileName.contains("..")) {
                    throw new Exception("Filename contains invalid" + fileName);
                }
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet worksheet = workbook.getSheetAt(0);
                for (int i = 0; i < worksheet.getPhysicalNumberOfRows(); i++) {
                    if (i > 0) {
                        XSSFRow row = worksheet.getRow(i);
                        try {
                            DataFormatter dataFormatter = new DataFormatter();

                            String userName = "";
                            String userEmail = "";
                            String userMobileNo = "";
                            if (row.getCell(1) != null) {
                                userName = String.valueOf(Optional.ofNullable(dataFormatter.formatCellValue(row.getCell(1))).orElse(null));
                                userName = userName.trim();
                            }
                            if (row.getCell(2) != null) {
                                userEmail = String.valueOf(Optional.ofNullable(dataFormatter.formatCellValue(row.getCell(2))).orElse(null));
                                userEmail = userEmail.trim();
                            }
                            if (row.getCell(3) != null) {
                                userMobileNo = String.valueOf(Optional.ofNullable(dataFormatter.formatCellValue(row.getCell(3))).orElse(null));
                                userMobileNo = userMobileNo.trim();
                            }
                            User user = new User();
                            user.setUserName(userName);
                            if (userRepository.existsByUserEmail(userEmail)) {
                                throw new Exception("Email already exits");
                            } else {
                                user.setUserEmail(userEmail);
                            }
                            if (userRepository.existsByUserMobileNo(userMobileNo)) {
                                throw new Exception("MobileNo already exits");
                            } else {
                                user.setUserMobileNo(userMobileNo);
                            }
                            userRepository.save(user);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return "imported";

    }

    @Override
    public InputStream exportExcel() throws Exception {
        List<User> user = userRepository.findAll();

        ByteArrayInputStream in=ExcelHelper.exportExcel(user);
        return in;
    }

}