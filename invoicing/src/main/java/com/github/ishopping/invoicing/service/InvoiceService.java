package com.github.ishopping.invoicing.service;

import com.github.ishopping.invoicing.model.Order;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class InvoiceService {

    @Value("${classpath:reports/nota-fiscal.jrxml}")
    private Resource invoice;


    @Value("${classpath:reports/nota-fiscal.jrxml}")
    private Resource logo;

    public byte[] generateInvoice(Order order) {
        try (InputStream inputStream = invoice.getInputStream()) {

            Map<String, Object> params = new HashMap<>();
            params.put("name", order.client().name());
            params.put("cpf", order.client().cpf());
            params.put("address", order.client().address());
            params.put("numberAddress", order.client().numberAddress());
            params.put("district", order.client().district());
            params.put("email", order.client().email());
            params.put("phoneNumber", order.client().phoneNumber());
            params.put("dataOrder", order.data());
            params.put("total", order.total());

            var dataSource = new JRBeanCollectionDataSource(order.items());

            params.put("LOGO", logo.getInputStream());

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
