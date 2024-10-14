package com.perjpasample.jpapersistance.main.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.perjpasample.jpapersistance.main.model.UserModel;
import com.perjpasample.jpapersistance.main.repository.UserRepository;
import com.perjpasample.jpapersistance.security.Model.SessionModel;
import com.perjpasample.jpapersistance.security.service.SessionService;
import com.perjpasample.jpapersistance.util.KeyPairUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private PdfExtractionService pdfExtractionService;

    @Autowired
    private KeyPairUtil keyPairUtil;
    
    
    // public String login(String username, String password) {
    //     Optional<UserModel> userOpt = userRepository.findByUsername(username);

    //     if (userOpt.isPresent()) {
    //         UserModel user = userOpt.get();
        
    //     // Load the user's private key (make sure you handle exceptions appropriately)
    //     PrivateKey privateKey = keyPairUtil.getPrivateKey();

    //     // Decrypt the password stored in the database
    //     String decryptedPassword = keyPairUtil.decryptingPassword(user.getPassword(), privateKey);
        
    //     // Compare the decrypted password with the input password
    //     if (decryptedPassword.equals(password)) {
    //             // Check for existing session and delete if it exists
    //             sessionService.findByUserId(user.getId()).ifPresent(sessionService::delete);

    //             // Create a new session
    //             String sessionToken = UUID.randomUUID().toString();
    //             SessionModel session = new SessionModel();
    //             // session.setUser(user);
    //             session.setSessionToken(sessionToken);
    //             session.setCreatedAt(Instant.now().getEpochSecond());
    //             session.setExpiresAt(Instant.now().getEpochSecond() + 3600); // 1 hour later
    //             sessionService.save(session);
    //             return sessionToken;
    //         }
    //     }
    //     return null;
    // }

    public UserModel saveUser(UserModel userModel) {
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userRepository.save(userModel);
    }

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }

    public UserModel getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserModel updateUserById(Integer id, UserModel userModel) {
        userModel.setId(id);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userRepository.save(userModel);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public String exportReport(String reportFormat) throws IOException, JRException {
        String path = "C:\\output\\";
        List<UserModel> userModel = userRepository.findAll();
        File file = ResourceUtils.getFile("classpath:Simple_Blue.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(userModel);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Dale");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if (reportFormat.equalsIgnoreCase("pdf")) {
            String pdfPath = path + "\\Simple_Blue.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
            String extractedText = pdfExtractionService.extractTextFromPdf(pdfPath);
            createExcelFromExtractedData(extractedText, path + "\\Simple_Blue.xlsx");
            return "Report generated in path: " + pdfPath + ", extracted text: " + extractedText;
        }
        // XLSX export
        if (reportFormat.equalsIgnoreCase("xlsx")) {
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path + "\\Simple_Blue.xlsx"));

            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(true);
            exporter.setConfiguration(configuration);

            exporter.exportReport();
        }
        return "report generated in path: " + path;
    }

    private void createExcelFromExtractedData(String extractedText, String excelPath) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Extracted Data");
            String[] rows = extractedText.split("\n");
            int rowNum = 0;
            for (String rowData : rows) {
                Row row = sheet.createRow(rowNum++);
                String[] columns = rowData.split(",");
                for (int i = 0; i < columns.length; i++) {
                    row.createCell(i).setCellValue(columns[i].trim());
                }
            }
            try (FileOutputStream outputStream = new FileOutputStream(excelPath)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] exportPdfReport() throws FileNotFoundException, JRException {
        List<UserModel> userModel = userRepository.findAll();
        File file = ResourceUtils.getFile("classpath:Simple_Blue.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(userModel);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Dale");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        return outputStream.toByteArray();
    }

    public String convertPdfToExcel(String pdfPath, String excelPath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("PDF Data");

        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue(text);

            try (FileOutputStream fileOut = new FileOutputStream(excelPath)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            workbook.close();
        }

        return "Excel file generated at: " + excelPath;
    }

}
