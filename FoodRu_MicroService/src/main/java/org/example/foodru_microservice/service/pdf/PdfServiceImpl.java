package org.example.foodru_microservice.service.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.service.upload.UploadService;
import org.springframework.stereotype.Service;
import com.lowagie.text.Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {

    private final UploadService uploadService;

    @Override
    public void savePdf(String filePath, List<Object> response) throws IOException {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();
            BaseFont bfComic = BaseFont.createFont("FoodRu_MicroService\\font\\arial.ttf", BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED);
            Font font = new Font(bfComic, 12);

            PdfPTable table = new PdfPTable(5);
            table.addCell(new PdfPCell(new Phrase("название компании:", font)));
            table.addCell(new PdfPCell(new Phrase("адрес:", font)));
            table.addCell(new PdfPCell(new Phrase("список отсут продуктов:", font)));
            table.addCell(new PdfPCell(new Phrase("ингредиенты:", font)));
            table.addCell(new PdfPCell(new Phrase("стоимость:", font)));
            /*for (PaymentReceipt p : response) {
                table.addCell(new PdfPCell(new Phrase(p.getCompanyName(), font)));
                table.addCell(new PdfPCell(new Phrase(p.getAddress(), font)));
                table.addCell(new PdfPCell(new Phrase(p.getMissingIngredients().toString(), font)));
                table.addCell(new PdfPCell(new Phrase(p.getIngredients().toString(), font)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getCost()), font)));
            }*/
            document.add(table);

            document.close();

            byte[] pdfBytes = baos.toByteArray();
            System.out.println(pdfBytes.length);
            uploadService.uploadPhoto(pdfBytes);
        } catch (Exception exception) {
            System.out.println("fdsfsd");
        }

    }
}