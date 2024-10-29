package org.example.foodru_microservice.service.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodru_microservice.service.upload.UploadService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfServiceImpl implements PdfService {

    private final UploadService uploadService;

    public void savePdf(List<Object> response) {

        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (Document document = new Document()) {
                PdfWriter.getInstance(document, baos);
                document.open();
                BaseFont bfComic = BaseFont.createFont("C:\\Users\\danil\\Desktop\\Fintech_Course\\FoodRu_MicroService\\font\\arial.ttf", BaseFont.IDENTITY_H,
                        BaseFont.EMBEDDED);
                Font font = new Font(bfComic, 12);
                PdfPTable table = createPdfTable(font, response);
                document.add(table);
            }
            uploadService.uploadPdf(baos.toByteArray());
        } catch (Exception exception) {
            log.error("Error while creating pdf file: {}", exception.getMessage());
        }
    }

    private PdfPTable createPdfTable(Font font, List<Object> response) {
        PdfPTable table = new PdfPTable(5);
        table.addCell(new PdfPCell(new Phrase("финтех:", font)));
        table.addCell(new PdfPCell(new Phrase("адрес:", font)));
        table.addCell(new PdfPCell(new Phrase("список отсут продуктов:", font)));
        table.addCell(new PdfPCell(new Phrase("ТЕСТИМ:", font)));
        table.addCell(new PdfPCell(new Phrase("ааааааааааааа:", font)));
    /*for (PaymentReceipt p : response) {
        table.addCell(new PdfPCell(new Phrase(p.getCompanyName(), font)));
        table.addCell(new PdfPCell(new Phrase(p.getAddress(), font)));
        table.addCell(new PdfPCell(new Phrase(p.getMissingIngredients().toString(), font)));
        table.addCell(new PdfPCell(new Phrase(p.getIngredients().toString(), font)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getCost()), font)));
    }*/
        return table;
    }

    //todo сделать через многопоточку отправку писем и добавление в s3
}