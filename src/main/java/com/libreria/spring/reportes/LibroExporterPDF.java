package com.libreria.spring.reportes;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.libreria.spring.entidades.Libro;
import com.libreria.spring.excepciones.MyException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LibroExporterPDF {

    public void exportar(List<Libro> libros, String nombreArchivo) throws MyException {
        Collections.sort(libros, Libro.compararPorTitulo);
        if(libros.size() == 0){
            throw new MyException("No hay libros registrados para descargar");
        }
        try {
            PdfWriter writer = new PdfWriter(nombreArchivo);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            Image logo = new Image(ImageDataFactory.create("classpath:/static/images/hoja.png"));
            logo.scaleToFit(20, 20);

            Paragraph header = new Paragraph("Tus libros en LibrarySB ").setFontSize(20).setTextAlignment(TextAlignment.CENTER);
            header.add(logo);
            header.setMarginBottom(15);
            header.setMarginTop(15);
            Table table = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
            table.addCell(new Cell().add(new Paragraph("Título")));
            table.addCell(new Cell().add(new Paragraph("Autor")));
            table.addCell(new Cell().add(new Paragraph("Editorial")));
            table.addCell(new Cell().add(new Paragraph("Fecha de alta")));

            for (int i = 0; i < 4; i++) {
                Cell cell = table.getCell(0, i);
                cell.setBackgroundColor(new DeviceRgb(82, 141, 50));
                cell.setFontColor(new DeviceRgb(255, 255, 255));
                cell.setBold();
            }

            document.add(header);

            for (Libro libro : libros) {
                table.addCell(new Cell().add(new Paragraph(libro.getTitulo())));
                table.addCell(new Cell().add(new Paragraph(libro.getAutor().getNombre())));
                table.addCell(new Cell().add(new Paragraph(libro.getEditorial().getNombre())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(libro.getAlta()))));
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            throw new MyException("Error al crear PDF.");
        }
    }
}

