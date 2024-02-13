package com.example.College.Service.ServiceImpl;

import com.itextpdf.html2pdf.HtmlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;

@Service
public class PdfService {
    private final TemplateEngine templateEngine;
    private Logger logger = LoggerFactory.getLogger(PdfService.class);

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public InputStream createPdf() throws IOException {
        Context context = new Context();
        context.setVariable("name","Demo");
        String process =  templateEngine.process("EmailTemplate", context);
        File file = File.createTempFile("Demo",".pdf");
        HtmlConverter.convertToPdf(process,new FileOutputStream(file));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream in = new FileInputStream(file);
        FileInputStream  fis = new FileInputStream(file);
        return in;
    }
}
