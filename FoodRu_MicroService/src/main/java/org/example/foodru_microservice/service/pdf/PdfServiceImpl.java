package org.example.foodru_microservice.service.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodru_microservice.service.mail.EmailService;
import org.example.foodru_microservice.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfServiceImpl implements PdfService {

    private final UploadService uploadService;
    private final EmailService emailService;
    @Qualifier("asyncS3EmailDataExporter")
    private final ExecutorService asyncS3EmailDataExporter;

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

            initializeData("danigpro1337@gmail.com", baos.toByteArray());

        } catch (Exception exception) {
            log.error("Error while creating pdf file: {}", exception.getMessage());
        }


    }

    //todo отрефакторить так , чтобы многопоточка была не в pdf service

    //todo спросить по поводу ByteArrayOutputStream , норм что я ее использую до ее закрытия?

    private void initializeData(String toAddress, byte[] bytes) {

        CompletableFuture<Void> emailFuture =
                CompletableFuture
                        .runAsync(() -> {
                            try {
                                emailService.sendEmailWithAttachment(toAddress, bytes);
                            } catch (MessagingException e) {
                                log.error("Error during email sending", e);
                            }
                        }, asyncS3EmailDataExporter);

        CompletableFuture<Void> s3Future = CompletableFuture
                .runAsync(() -> uploadService.uploadPdf(bytes), asyncS3EmailDataExporter);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(emailFuture, s3Future);
        try {
            allOf.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error during data initialization", e);
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

}