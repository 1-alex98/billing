package com.billing.app.domain.billing.application;

import com.billing.app.domain.billing.core.model.Bill;
import com.billing.app.domain.billing.core.model.Item;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.stream.Stream;

@Component
public class BillToPDFMapper {
    public void generatePDF(OutputStream outputStream, Bill bill) throws DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        document.open();

        PdfPTable table = getBillTable(bill);

        document.add(table);
        document.close();
    }

    private PdfPTable getBillTable(Bill bill) {
        PdfPTable table = new PdfPTable(6);
        addTableHeader(table);
        bill.getItems()
                .forEach(item -> addItemRow(item, table));
        addSumRow(table, bill);
        return table;
    }

    private void addItemRow(Item item, PdfPTable table) {
        table.addCell(String.valueOf(item.getQuantity()));
        table.addCell((item.isImported() ? "imported " : "") + item.getName());
        table.addCell("%.2f€".formatted(item.getPrice() / 100.0));
        table.addCell("%.2f€".formatted(item.getSumRawPrice() / 100.0));
        table.addCell("%.2f€".formatted(item.getSumTaxes() / 100.0));
        table.addCell("%.2f€".formatted(item.getSumAfterTaxesPrice() / 100.0));
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("#", "name", "price", "sum prices", "taxes", "total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addSumRow(PdfPTable table, Bill bill) {
        emptyRow(table);
        table.addCell("Sum");
        table.addCell(" ");
        table.addCell(" ");
        table.addCell("%.2f€".formatted(bill.getSumRawPrice() / 100.0));
        table.addCell("%.2f€".formatted(bill.getSumTaxes() / 100.0));
        table.addCell("%.2f€".formatted(bill.getSumAfterTaxesPrice() / 100.0));
    }

    private void emptyRow(PdfPTable table) {
        table.addCell(" ");
        table.addCell(" ");
        table.addCell(" ");
        table.addCell(" ");
        table.addCell(" ");
        table.addCell(" ");
    }
}
