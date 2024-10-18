package com.example.edadil_microservice.service;


import com.example.edadil_microservice.model.response.PaymentReceipt;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PdfService {


    //todo убрать в другой сервис

    public void savePdf(String filePath, List<PaymentReceipt> response) throws IOException {

        Document document = new Document();
        PdfPTable table = new PdfPTable(5);
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        BaseFont bfComic = BaseFont.createFont("C:\\Users\\danil\\Desktop\\Fintech_Course\\Edadil_MicroService\\arial.ttf", BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED);

        Font font = new Font(bfComic, 12);
        table.addCell(new PdfPCell(new Phrase("название компании:", font)));
        table.addCell(new PdfPCell(new Phrase("адрес:", font)));
        table.addCell(new PdfPCell(new Phrase("список отсут продуктов:", font)));
        table.addCell(new PdfPCell(new Phrase("ингредиенты:", font)));
        table.addCell(new PdfPCell(new Phrase("стоимость:", font)));

        for (PaymentReceipt p : response) {
            table.addCell(new PdfPCell(new Phrase(p.getCompanyName(), font)));
            table.addCell(new PdfPCell(new Phrase(p.getAddress(), font)));
            table.addCell(new PdfPCell(new Phrase(p.getMissingIngredients().toString(), font)));
            table.addCell(new PdfPCell(new Phrase(p.getIngredients().toString(), font)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getCost()), font)));
        }

        document.add(table);
        document.close();
    }


}