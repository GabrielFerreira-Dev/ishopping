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

    @Value("classpath:reports/nota-fiscal.jrxml")
    private Resource invoice;


    @Value("classpath:reports/nota-fiscal.jrxml")
    private Resource logo;

    public byte[] generateInvoice(Order order) {
        try (InputStream inputStream = invoice.getInputStream()) {

            Map<String, Object> params = new HashMap<>();
            params.put("NOME", order.client().name());
            params.put("CPF", order.client().cpf());
            params.put("LOGRADOURO", order.client().address());
            params.put("NUMERO", order.client().numberAddress());
            params.put("BAIRRO", order.client().district());
            params.put("EMAIL", order.client().email());
            params.put("TELEFONE", order.client().phoneNumber());
            params.put("DATA_PEDIDO", order.data());
            params.put("TOTAL_PEDIDO", order.total());
            params.put("LOGO", logo.getInputStream());

            var dataSource = new JRBeanCollectionDataSource(order.items());

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
