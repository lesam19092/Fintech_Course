package org.example.foodru_microservice.service.pdf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface PdfService {

    void savePdf(List<Object> response) throws IOException;
}
