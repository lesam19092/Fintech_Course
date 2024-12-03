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
import org.example.foodru_microservice.model.consts.Fonts;
import org.example.foodru_microservice.model.consts.PdfConstans;
import org.example.foodru_microservice.model.entity.User;
import org.example.foodru_microservice.service.kafka.dto.PaymentReceiptResponse;
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

    @Qualifier("asyncS3EmailDataExporter")
    private final ExecutorService asyncS3EmailDataExporter;
    private final UploadService uploadService;
    private final EmailService emailService;


    @Override
    public void generateAndSendPdfReport(PaymentReceiptResponse response, User user) {


        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (Document document = new Document()) {
                PdfWriter.getInstance(document, baos);
                document.open();
                BaseFont bfComic = BaseFont.createFont(Fonts.ARIAL, BaseFont.IDENTITY_H,
                        BaseFont.EMBEDDED);
                Font font = new Font(bfComic, 12);
                PdfPTable table = createPdfTable(font, response);
                document.add(table);
            }

            sendDataToEmailAndStorage(user, baos.toByteArray());

        } catch (Exception exception) {
            log.error("Error while creating pdf file: {}", exception.getMessage());
        }
    }


    private void sendDataToEmailAndStorage(User user, byte[] bytes) {

        CompletableFuture<Void> emailFuture =
                CompletableFuture
                        .runAsync(() -> {
                            try {
                                emailService.sendEmailWithAttachment(user.getEmail(), bytes);
                            } catch (MessagingException e) {
                                throw new RuntimeException(e);
                            }
                        }, asyncS3EmailDataExporter);

        CompletableFuture<Void> s3Future = CompletableFuture
                .runAsync(() -> uploadService.uploadPdf(bytes, user.getId()), asyncS3EmailDataExporter);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(emailFuture, s3Future);
        try {
            allOf.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error during data initialization", e);
        }
    }


    private PdfPTable createPdfTable(Font font, PaymentReceiptResponse response) {
        PdfPTable table = new PdfPTable(5);
        List<String> headers = List.of(
                PdfConstans.COMPANY_NAME,
                PdfConstans.ADDRESS,
                PdfConstans.INGREDIENTS,
                PdfConstans.MISSING_INGREDIENTS,
                PdfConstans.COST
        );
        List<String> data = List.of(
                response.getCompanyName(),
                response.getAddress(),
                response.getIngredients().toString(),
                response.getMissingIngredients().toString(),
                String.valueOf(response.getCost())
        );

        headers.forEach(header -> addCellToTable(table, header, font));
        data.forEach(value -> addCellToTable(table, value, font));

        return table;
    }

    private void addCellToTable(PdfPTable table, String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        table.addCell(cell);
    }

}