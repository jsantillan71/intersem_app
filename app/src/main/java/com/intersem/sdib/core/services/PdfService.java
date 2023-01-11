package com.intersem.sdib.core.services;

public class PdfService {
  /*  public String file_path;
    private Context context;
    public File pdf_file;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 5, Font.BOLD);
    private Font fSubtitle = new Font(Font.FontFamily.TIMES_ROMAN, 4, Font.NORMAL);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 4, Font.NORMAL );
    private Font fMinText = new Font(Font.FontFamily.TIMES_ROMAN, 3, Font.NORMAL);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    public PdfService(Context context){
        this.context = context;
    }

    public void OpenDocument(){
        CreateFile();
        try{
            document = new Document(PageSize.B1);
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdf_file));
            document.open();
        }catch (Exception ex){
            Log.e("OpenDocument", ex.toString());
        }
    }
    private void CreateFile(){
        try{
            File folder = new File(Environment.getExternalStorageDirectory().toString(), "Download");
            if(folder.exists()){
                folder.delete();
            }
            folder.mkdir();
            pdf_file=new File(folder, "Ticket.pdf");
            file_path = pdf_file.getAbsolutePath();
        }catch (Exception ex){
            Log.w("CreateFile", ex.getMessage());
        }
    }
    public void CloseDocument(){
        document.close();
    }
    public void AddMetaData(String title, String subject, String author){
        try{
            document.addTitle(title);
            document.addSubject(subject);
            document.addAuthor(author);
        }catch (Exception ex){
            Log.w("AddMetaData", ex.getMessage());
        }
    }
    public void AddTitle(String title, String subTitle){
        try{
            paragraph = new Paragraph();
            addChildP(new Paragraph(title, fTitle));
            addChildP(new Paragraph(subTitle, fSubtitle));
            paragraph.setSpacingAfter(3);
            document.add(paragraph);
        }catch (Exception ex){
            Log.e("AddTitles", ex.toString());
        }
    }
    public void addText(ResponsePaymentModel.Response.Payment payment){
        try{
            paragraph = new Paragraph();
            paragraph.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            addChildP(new Paragraph("Fecha de pago :" + payment.getFecha(), fText));
            addChildP(new Paragraph("Monto de Pago :                    " + payment.getMonto(), fText));
            addChildP(new Paragraph("Tipo de Pago :              " + payment.getForma_pago(), fText));
            paragraph.setSpacingAfter(5);
            document.add(paragraph);
        }catch (Exception ex){
            Log.e("AddTitles", ex.toString());
        }
    }
    public void addClausules(String clausulas){
        try{
            paragraph = new Paragraph();
            paragraph.setAlignment(Paragraph.ALIGN_BOTTOM);
            addChildP(new Paragraph(clausulas, fMinText));
            paragraph.setSpacingAfter(5);
            document.add(paragraph);
        }catch (Exception ex){
            Log.e("AddTitles", ex.toString());
        }
    }
    public void addChildP(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }
    public void AddParagraph(String text){
        try{
            paragraph = new Paragraph(text, fText);
            paragraph.setSpacingAfter(5);
            document.add(paragraph);
        }catch (Exception ex){
            Log.e("AddParagraph", ex.toString());
        }
    }
    public void AddHigtParagraph(String text){
        try{
            paragraph = new Paragraph(text, fHighText);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        }catch (Exception ex){
            Log.e("AddParagraph", ex.toString());
        }
    }
*/
}
