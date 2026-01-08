package com.github.ishopping.invoicing;

import com.github.ishopping.invoicing.bucket.BucketFile;
import com.github.ishopping.invoicing.bucket.BucketService;
import com.github.ishopping.invoicing.model.Order;
import com.github.ishopping.invoicing.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class GeneratorInvoiceService {

    private final InvoiceService invoiceService;
    private final BucketService bucketService;

    public void generate(Order order) {
        log.info("Invoice generated for the order {}", order.id());

        try {
            byte[] byteArray = invoiceService.generateInvoice(order);

            String nameFile = String.format("invoice_order_%d.pdf", order.id());

            var file = new BucketFile(
                    nameFile, new ByteArrayInputStream(byteArray), MediaType.APPLICATION_PDF, byteArray.length
            );

            bucketService.upload(file);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }
}
