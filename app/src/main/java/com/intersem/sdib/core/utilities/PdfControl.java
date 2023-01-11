package com.intersem.sdib.core.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class PdfControl {
    private Context context;
    public File pdf_file;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private Font fTextImage = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
    private Font fTextBold = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    public PdfControl(Context context) {
        document = new Document(PageSize.LETTER);
        this.context = context;
    }

    public PdfControl(Context context, Rectangle rectangle) {
        document = new Document(rectangle);
        this.context = context;
    }

    public void OpenDocument(float[] margins) {
        try {
            document.setMargins(margins[0], margins[1], margins[2], margins[3]);
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdf_file));
            document.open();
        } catch (Exception ex) {
            Log.e("OpenDocument", ex.toString());
        }
    }

    public void CreateFile(String path, String file_name, float[] margins) {
        try {
            File fileBase = new File(path);
            // File folder = new File(path);
            if (!fileBase.exists()) {
                fileBase.mkdirs();
            }
            pdf_file = new File(fileBase, file_name);

            OpenDocument(margins);
        } catch (Exception ex) {
            Log.w("CreateFile", ex.getMessage());
        }
    }

    public void CloseDocument() {
        document.close();
    }

    public void AddMetaData(String title, String subject, String author) {
        try {
            document.addTitle(title);
            document.addSubject(subject);
            document.addAuthor(author);
        } catch (Exception ex) {
            Log.w("AddMetaData", ex.getMessage());
        }
    }

    public void addChildP(Paragraph childParagraph) {
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    public void addRectangleCenterText(String text, float left) {
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);

            PdfPTable pdfPTable = new PdfPTable(1);
            PdfPCell pdfPCell;

            pdfPCell = new PdfPCell(new Phrase(text, fHighText));
            pdfPCell.setPaddingBottom((float) 10);
            pdfPCell.setPaddingLeft((float) left);
            pdfPCell.setPaddingRight((float) 80);
            pdfPCell.setPaddingTop((float) 10);

            pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfPTable.addCell(pdfPCell);

            paragraph.add(pdfPTable);
            paragraph.setSpacingAfter((float) 18.58);
            document.add(paragraph);
        } catch (Exception ex) {
            throw new RuntimeException("Error en la generación de PDF");
        }
    }

    public void agregarEncabezadoMemoriaFotografica(String numero_reporte) {
        try {
            PdfPTable pdfPTable = new PdfPTable(1);
            PdfPCell pdfPCell;
            //Primer Frase
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            paragraph.setAlignment(Paragraph.ALIGN_LEFT);
            paragraph.add("REPORTE DE MANTENIMIENTO:");
            pdfPCell = new PdfPCell(paragraph);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPCell.setPaddingLeft(-4f);
            pdfPTable.addCell(pdfPCell);
            //Segunda Frase
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            paragraph.setAlignment(Paragraph.ALIGN_LEFT);
            paragraph.add(numero_reporte);
            pdfPCell = new PdfPCell(paragraph);
            pdfPCell.setPaddingLeft(-4f);
            pdfPCell.setPaddingTop(6f);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);
            //Tercera Frase
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            paragraph.setAlignment(Paragraph.ALIGN_LEFT);
            paragraph.add("----------------------------");
            pdfPCell = new PdfPCell(paragraph);
            pdfPCell.setPaddingLeft(-4f);
            pdfPCell.setPaddingTop(-4f);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);
            //Cuarta Frase
            paragraph = new Paragraph();
            paragraph.setFont(fTitle);
            paragraph.setAlignment(Paragraph.ALIGN_LEFT);
            paragraph.add("MEMORIA FOTOGRAFICA:");
            pdfPCell = new PdfPCell(paragraph);
            pdfPCell.setPaddingLeft(-4f);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPCell.setPaddingTop(15f);
            pdfPTable.addCell(pdfPCell);
            //Linea de Separacion
            paragraph = new Paragraph();
            paragraph.setFont(fTitle);
            paragraph.add(new LineSeparator());
            pdfPCell = new PdfPCell(paragraph);
            pdfPCell.setPaddingLeft(-4f);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPCell.setPaddingTop(8f);
            pdfPCell.setPaddingBottom(20f);
            pdfPCell.setPaddingRight(25.51f);
            pdfPTable.addCell(pdfPCell);
            document.add(pdfPTable);
        } catch (Exception ex) {
            throw new RuntimeException("Error en la generación de PDF");
        }
    }

    public void agregarEncabezadoEvidenciaFotografica(String numero_reporte) {
        try {
            PdfPTable pdfPTable = new PdfPTable(1);
            PdfPCell pdfPCell;
            //Primer Frase
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            paragraph.add(numero_reporte);
            pdfPCell = new PdfPCell(paragraph);
            pdfPCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);
            //Segunda Frase
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            paragraph.add("Orden No:__________________");
            pdfPCell = new PdfPCell(paragraph);
            pdfPCell.setPaddingTop(-10f);
            pdfPCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);
            //Segunda Frase
            paragraph = new Paragraph();
            paragraph.setFont(fTextBold);
            paragraph.add("Evidencia fotografica de");
            pdfPCell = new PdfPCell(paragraph);
            pdfPCell.setPaddingTop(-2f);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            pdfPTable.addCell(pdfPCell);
            paragraph = new Paragraph();
            paragraph.setFont(fTextBold);
            paragraph.add("Mantenimiento          ");
            pdfPCell = new PdfPCell(paragraph);
            pdfPCell.setPaddingTop(-2f);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            pdfPCell.setPaddingBottom(75f);
            pdfPTable.addCell(pdfPCell);
            document.add(pdfPTable);
        } catch (Exception ex) {
            throw new RuntimeException("Error en la generación de PDF");
        }
    }

    public void AddTableImgTwoColumns(Bitmap[] images_path, float margin_1_1, float margin_2_1,
                                      float margin_1_2, float margin_2_2, int img_width, int img_height,
                                      float spacingAfter,ServiceRequest.Fotografia[] fotografias) {
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);

            PdfPTable pdfPTable = new PdfPTable(new float[] {2,48,2,48 });
            PdfPCell pdfPCell;
            ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
            //Celda 1
            Bitmap resizedBitmap = BitmapFactory.decodeResource(
                                    context.getResources()
                                    ,Constants.obtenerDescripcionFografia(fotografias[0]));
            resizedBitmap = Bitmap.createScaledBitmap(resizedBitmap,300, (img_height), false);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 83, stream3);
            Image imagen = Image.getInstance(stream3.toByteArray());
            imagen.setAlignment(Element.ALIGN_LEFT);
            imagen.scalePercent((float) 5, (float) 5);

            pdfPCell = new PdfPCell();
            pdfPCell.addElement(imagen);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);
            pdfPCell.setPaddingLeft(margin_1_1);
            pdfPCell.setPaddingRight(margin_2_1);

            //Celda 2
            resizedBitmap = Bitmap.createScaledBitmap(images_path[0], (img_width), (img_height), false);
            stream3 = new ByteArrayOutputStream();
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 83, stream3);
            imagen = Image.getInstance(stream3.toByteArray());
            imagen.setAlignment(Element.ALIGN_CENTER);
            imagen.scalePercent((float) 5, (float) 5);
            //Creamos la celda
            pdfPCell = new PdfPCell();
            pdfPCell.addElement(imagen);
            //pdfPCell.setPaddingLeft(margin_1_1);
            //pdfPCell.setPaddingRight(margin_2_1);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);

            if (images_path[1] != null) {
                //Celda 1
                resizedBitmap = BitmapFactory.decodeResource(
                                            context.getResources(),
                                            Constants.obtenerDescripcionFografia(fotografias[1]));
                resizedBitmap = Bitmap.createScaledBitmap(resizedBitmap,300, (img_height), false);
                stream3 = new ByteArrayOutputStream();
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 83, stream3);
                imagen = Image.getInstance(stream3.toByteArray());
                imagen.setAlignment(Element.ALIGN_CENTER);
                imagen.scalePercent((float) 5, (float) 5);

                pdfPCell = new PdfPCell();
                pdfPCell.addElement(imagen);
                pdfPCell.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(pdfPCell);

                //Agregamos Imagen
                resizedBitmap = Bitmap.createScaledBitmap(images_path[1], (img_width), (img_height), false);
                stream3 = new ByteArrayOutputStream();
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 83, stream3);
                imagen = Image.getInstance(stream3.toByteArray());
                imagen.setAlignment(Element.ALIGN_CENTER);
                imagen.scalePercent((float) 5, (float) 5);

                //Agregamos Celda
                pdfPCell = new PdfPCell();
                pdfPCell.addElement(imagen);
                //pdfPCell.setPaddingLeft(margin_1_2);
                //pdfPCell.setPaddingRight(margin_2_2);
                pdfPCell.setBorder(Rectangle.NO_BORDER);
                pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                pdfPTable.addCell(pdfPCell);
            } else {
                PdfPCell cell_blank = new PdfPCell();
                cell_blank.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(cell_blank);
                pdfPTable.addCell(cell_blank);
            }
            paragraph.add(pdfPTable);
            paragraph.setSpacingAfter(spacingAfter);
            document.add(paragraph);
            document.add(new Chunk(""));
        } catch (Exception ex) {
            Log.e("createTable", ex.toString());
        }
    }

    public void addLineaDivisora() {
        try {
            PdfPTable pdfPTable = new PdfPTable(1);
            PdfPCell pdfPCell;
            paragraph = new Paragraph();
            paragraph.setFont(fTitle);
            paragraph.add(new LineSeparator());
            pdfPCell = new PdfPCell(paragraph);
            pdfPCell.setPaddingLeft(-4f);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPCell.setPaddingBottom(15f);
            pdfPCell.setPaddingRight(28.51f);
            pdfPTable.addCell(pdfPCell);
            document.add(pdfPTable);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void AddTableImgThreeColumns(Bitmap[] images_path, float margin_1_1, float margin_2_1,
                                        int img_width, int img_height,
                                        float spacingAfter, ServiceRequest.Fotografia[] fotografias) {
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            Paragraph texto = null;

            PdfPTable pdfPTable = new PdfPTable(3);
            PdfPCell pdfPCell;

            // Celda 1
            Bitmap resized_bitmap_1 = Bitmap.createScaledBitmap(images_path[0], (img_width), (img_height), false);

            ByteArrayOutputStream stream_1 = new ByteArrayOutputStream();
            resized_bitmap_1.compress(Bitmap.CompressFormat.JPEG, 83, stream_1);
            Image imagen_1 = Image.getInstance(stream_1.toByteArray());

            imagen_1.setAlignment(Element.ALIGN_CENTER);
            imagen_1.scalePercent((float) 5.1, (float) 5.1);

            texto = new Paragraph(Constants.obtenerTipoFografia(fotografias[0]));
            texto.setAlignment(Element.ALIGN_CENTER);
            texto.setFont(fTextImage);
            pdfPCell = new PdfPCell();
            pdfPCell.addElement(texto);
            pdfPCell.addElement(imagen_1);
            pdfPCell.setPaddingLeft(margin_1_1);
            pdfPCell.setPaddingRight(margin_2_1);
            pdfPCell.setPaddingTop(-5f);

            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);

            // Celda 2
            if (images_path[1] != null) {
                Bitmap resized_bitmap_2 = Bitmap.createScaledBitmap(images_path[1], (img_width), (img_height), false);

                ByteArrayOutputStream stream_2 = new ByteArrayOutputStream();
                resized_bitmap_2.compress(Bitmap.CompressFormat.JPEG, 83, stream_2);
                Image imagen_2 = Image.getInstance(stream_2.toByteArray());

                imagen_2.setAlignment(Element.ALIGN_CENTER);
                imagen_2.scalePercent((float) 5.1, (float) 5.1);
                texto = new Paragraph(Constants.obtenerTipoFografia(fotografias[1]));
                texto.setAlignment(Element.ALIGN_CENTER);
                texto.setFont(fTextImage);
                pdfPCell = new PdfPCell();
                pdfPCell.addElement(texto);
                pdfPCell.addElement(imagen_2);
                pdfPCell.setPaddingLeft(margin_1_1);
                pdfPCell.setPaddingRight(margin_2_1);
                pdfPCell.setPaddingTop(-5f);

                pdfPCell.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(pdfPCell);
            } else {
                PdfPCell cell_blank = new PdfPCell();
                cell_blank.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(cell_blank);
            }

            if (images_path[2] != null) {
                // Celda 3
                Bitmap resized_bitmap_3 = Bitmap.createScaledBitmap(images_path[2], (img_width), (img_height), false);

                ByteArrayOutputStream stream_3 = new ByteArrayOutputStream();
                resized_bitmap_3.compress(Bitmap.CompressFormat.JPEG, 83, stream_3);
                Image imagen_3 = Image.getInstance(stream_3.toByteArray());

                imagen_3.setAlignment(Element.ALIGN_CENTER);
                imagen_3.scalePercent((float) 5.1, (float) 5.1);

                texto = new Paragraph(Constants.obtenerTipoFografia(fotografias[2]));
                texto.setAlignment(Element.ALIGN_CENTER);
                texto.setFont(fTextImage);
                pdfPCell = new PdfPCell();
                pdfPCell.addElement(texto);
                pdfPCell.addElement(imagen_3);
                pdfPCell.setPaddingLeft(margin_1_1);
                pdfPCell.setPaddingRight(margin_2_1);
                pdfPCell.setPaddingTop(-5f);

                pdfPCell.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(pdfPCell);
            } else {
                PdfPCell cell_blank = new PdfPCell();
                cell_blank.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(cell_blank);
            }

            paragraph.add(pdfPTable);
            paragraph.setSpacingAfter(spacingAfter);
            document.add(paragraph);
        } catch (Exception ex) {
            Log.e("createTable", ex.toString());
        }
    }

    public void AddTableImgThreeColumnsEvidencia(Bitmap[] images_path, float margien_left, float margien_rigth, int img_width, int img_height,
                                                 float spacingAfter, ServiceRequest.Fotografia[] fotografias) {
        try{
            paragraph = new Paragraph();
            paragraph.setFont(fText);

            PdfPTable pdfPTable = new PdfPTable(3);
            PdfPCell pdfPCell;
            // Celda 1

            //Imagen
            Bitmap resized_bitmap_1 = Bitmap.createScaledBitmap (images_path[0], (img_width), (img_height), false);
            ByteArrayOutputStream stream_1 = new ByteArrayOutputStream();
            resized_bitmap_1.compress(Bitmap.CompressFormat.JPEG, 83, stream_1);
            Image imagen_1 = Image.getInstance(stream_1.toByteArray());
            imagen_1.setAlignment(Element.ALIGN_CENTER);
            imagen_1.scalePercent((float)5.1, (float)5.1);

            pdfPCell = new PdfPCell();
            pdfPCell.addElement(imagen_1);
            pdfPCell.setPaddingLeft(margien_left);

            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);

            // Celda 2
            if(images_path[1] != null){
                Bitmap resized_bitmap_2 = Bitmap.createScaledBitmap (images_path[1], (img_width), (img_height), false);

                ByteArrayOutputStream stream_2 = new ByteArrayOutputStream();
                resized_bitmap_2.compress(Bitmap.CompressFormat.JPEG, 83, stream_2);
                Image imagen_2 = Image.getInstance(stream_2.toByteArray());

                imagen_2.setAlignment(Element.ALIGN_CENTER);
                imagen_2.scalePercent((float)5.1, (float)5.1);

                pdfPCell = new PdfPCell();
                pdfPCell.addElement(imagen_2);
                pdfPCell.setPaddingLeft(-12);

                pdfPCell.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(pdfPCell);
            }else{
                PdfPCell cell_blank = new PdfPCell();
                cell_blank.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(cell_blank);
            }

            if(images_path[2] != null){
                // Celda 3
                Bitmap resized_bitmap_3 = Bitmap.createScaledBitmap (images_path[2], (img_width), (img_height), false);

                ByteArrayOutputStream stream_3 = new ByteArrayOutputStream();
                resized_bitmap_3.compress(Bitmap.CompressFormat.JPEG, 83, stream_3);
                Image imagen_3 = Image.getInstance(stream_3.toByteArray());

                imagen_3.setAlignment(Element.ALIGN_CENTER);

                imagen_3.scalePercent((float)5.1, (float)5.1);

                pdfPCell = new PdfPCell();
                pdfPCell.addElement(imagen_3);

                pdfPCell.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(pdfPCell);
            }else{
                PdfPCell cell_blank = new PdfPCell();
                cell_blank.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(cell_blank);
            }

            //Se agregan celdas de descripcion
            ByteArrayOutputStream stream_descripcion = new ByteArrayOutputStream();
            Bitmap resizedBitmap = BitmapFactory.decodeResource(
                    context.getResources()
                    ,Constants.obtenerDescripcionFografiaEvidencia(fotografias[0]));
            resizedBitmap = Bitmap.createScaledBitmap(resizedBitmap,(img_width + 8), 300, false);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 83, stream_descripcion);
            Image imagen = Image.getInstance(stream_descripcion.toByteArray());
            imagen.setAlignment(Element.ALIGN_CENTER);
            imagen.scalePercent((float) 5, (float) 5);
            pdfPCell = new PdfPCell();
            pdfPCell.addElement(imagen);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPCell.setPaddingTop(-2f);
            pdfPCell.setPaddingLeft(margien_left);
            pdfPTable.addCell(pdfPCell);

            if(images_path[1] != null){
                stream_descripcion = new ByteArrayOutputStream();
                resizedBitmap = BitmapFactory.decodeResource(
                        context.getResources()
                        ,Constants.obtenerDescripcionFografiaEvidencia(fotografias[1]));
                resizedBitmap = Bitmap.createScaledBitmap(resizedBitmap,(img_width + 8), 300, false);
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 83, stream_descripcion);
                imagen = Image.getInstance(stream_descripcion.toByteArray());
                imagen.setAlignment(Element.ALIGN_CENTER);
                imagen.scalePercent((float) 5, (float) 5);
                pdfPCell = new PdfPCell();
                pdfPCell.addElement(imagen);
                pdfPCell.setPaddingTop(-2f);
                pdfPCell.setBorder(Rectangle.NO_BORDER);
                pdfPCell.setPaddingLeft(-12);
                pdfPTable.addCell(pdfPCell);
            }else{
                PdfPCell cell_blank = new PdfPCell();
                cell_blank.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(cell_blank);
            }

            if(images_path[2] != null){
                stream_descripcion = new ByteArrayOutputStream();
                resizedBitmap = BitmapFactory.decodeResource(
                        context.getResources()
                        ,Constants.obtenerDescripcionFografiaEvidencia(fotografias[2]));
                resizedBitmap = Bitmap.createScaledBitmap(resizedBitmap,img_width + 8, 300, false);
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 83, stream_descripcion);
                imagen = Image.getInstance(stream_descripcion.toByteArray());
                imagen.setAlignment(Element.ALIGN_CENTER);
                imagen.scalePercent((float) 5, (float) 5);
                pdfPCell = new PdfPCell();
                pdfPCell.setPaddingTop(-2f);
                pdfPCell.addElement(imagen);
                pdfPCell.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(pdfPCell);
            }else{
                PdfPCell cell_blank = new PdfPCell();
                cell_blank.setBorder(Rectangle.NO_BORDER);
                pdfPTable.addCell(cell_blank);
            }

            paragraph.add(pdfPTable);
            paragraph.setSpacingAfter(spacingAfter);
            document.add(paragraph);
        }catch (Exception ex){
            Log.e("createTable", ex.toString());
        }
    }
}
