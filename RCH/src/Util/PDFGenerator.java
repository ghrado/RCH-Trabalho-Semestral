package Util;

import Model.Cliente;
import Model.ItemOrdem;
import Model.Ordem;
import Model.Usuario;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Classe para gerar documentos PDF (Cotações e Faturas)
 */
public class PDFGenerator {
    
    private static final String PASTA_DOCUMENTOS = "documentos/";
    private static final String LOGO_RCH_PATH = "src/resources/logo_rch.png";
    private static final String LOGO_UEM_PATH = "src/resources/logo_uem.png";
    private static final Font FONT_TITULO = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font FONT_SUBTITULO = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private static final Font FONT_NORMAL = new Font(Font.FontFamily.HELVETICA, 12);
    private static final Font FONT_SMALL = new Font(Font.FontFamily.HELVETICA, 10);
    private static final String WATERMARK_TEXT = "by Ghrado";
    
    static {
        File pasta = new File(PASTA_DOCUMENTOS);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
    }
    
    public static String gerarCotacao(Ordem ordem, Usuario usuario) {
        try {
            String nomeArquivo = PASTA_DOCUMENTOS + "cotacao_" + ordem.getCodigo() + ".pdf";
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nomeArquivo));
            writer.setPageEvent(new WatermarkPageEvent());
            
            document.open();
            
            adicionarCabecalhoRCH(document, "COTAÇÃO");
            
            Paragraph info = new Paragraph();
            info.add(new Chunk("Número: ", FONT_SUBTITULO));
            info.add(new Chunk(ordem.getCodigo() + "\n", FONT_NORMAL));
            info.add(new Chunk("Data: ", FONT_SUBTITULO));
            info.add(new Chunk(ordem.getDataCriacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n", FONT_NORMAL));
            info.add(new Chunk("Processado por: ", FONT_SUBTITULO));
            info.add(new Chunk(usuario.getNome() + " (" + usuario.getId() + ")\n", FONT_NORMAL));
            info.add(new Chunk("Data/Hora Processamento: ", FONT_SUBTITULO));
            info.add(new Chunk(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n\n", FONT_NORMAL));
            document.add(info);
            
            adicionarDadosCliente(document, ordem.getCliente());
            
            adicionarTabelaItens(document, ordem.getItens());
            
            adicionarTotal(document, ordem.getValorTotal());
            
            adicionarRodapeUEM(document, "Esta cotação é válida por 30 dias.");
            
            document.close();
            return nomeArquivo;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String gerarFatura(Ordem ordem, Usuario usuario, int numeroCopia) {
        try {
            String nomeArquivo = PASTA_DOCUMENTOS + "fatura_" + ordem.getCodigo() + "_copia" + numeroCopia + ".pdf";
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nomeArquivo));
            writer.setPageEvent(new WatermarkPageEvent());
            
            document.open();
            
            adicionarCabecalhoRCH(document, "FATURA");
            
            Paragraph copiaInfo = new Paragraph();
            copiaInfo.setAlignment(Element.ALIGN_RIGHT);
            copiaInfo.add(new Chunk(numeroCopia == 1 ? "1ª VIA" : "2ª VIA", FONT_SUBTITULO));
            document.add(copiaInfo);
            document.add(new Paragraph("\n"));
            
            Paragraph info = new Paragraph();
            info.add(new Chunk("Número: ", FONT_SUBTITULO));
            info.add(new Chunk(ordem.getCodigo() + "\n", FONT_NORMAL));
            info.add(new Chunk("Data: ", FONT_SUBTITULO));
            info.add(new Chunk(ordem.getDataCriacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n", FONT_NORMAL));
            info.add(new Chunk("Status: ", FONT_SUBTITULO));
            info.add(new Chunk(ordem.getStatus().toString() + "\n", FONT_NORMAL));
            info.add(new Chunk("Processado por: ", FONT_SUBTITULO));
            info.add(new Chunk(usuario.getNome() + " (" + usuario.getId() + ")\n", FONT_NORMAL));
            info.add(new Chunk("Data/Hora Processamento: ", FONT_SUBTITULO));
            info.add(new Chunk(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n\n", FONT_NORMAL));
            document.add(info);
            
            adicionarDadosCliente(document, ordem.getCliente());
            
            adicionarTabelaItensComIVA(document, ordem.getItens());
            
            adicionarTotalComIVA(document, ordem.getValorTotal());
            
            adicionarRodapeUEM(document, "Obrigado pela sua preferência!");
            
            document.close();
            return nomeArquivo;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static void adicionarCabecalhoRCH(Document document, String titulo) throws DocumentException {
        try {
            // Adicionar logo RCH
            Image logoRCH = Image.getInstance(LOGO_RCH_PATH);
            logoRCH.scaleToFit(120, 80);
            logoRCH.setAlignment(Element.ALIGN_CENTER);
            document.add(logoRCH);
            document.add(new Paragraph("\n"));
        } catch (Exception e) {
            System.err.println("Erro ao carregar logo RCH: " + e.getMessage());
        }
        
        Paragraph header = new Paragraph();
        header.add(new Chunk("RCH - SISTEMA DE GESTÃO\n", FONT_TITULO));
        header.add(new Chunk(titulo + "\n\n", FONT_TITULO));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        
        LineSeparator line = new LineSeparator();
        document.add(new Chunk(line));
        document.add(new Paragraph("\n"));
    }
    
    private static void adicionarDadosCliente(Document document, Cliente cliente) throws DocumentException {
        Paragraph clienteInfo = new Paragraph();
        clienteInfo.add(new Chunk("DADOS DO CLIENTE\n", FONT_SUBTITULO));
        clienteInfo.add(new Chunk("Nome: ", FONT_NORMAL));
        clienteInfo.add(new Chunk(cliente.getNome() + "\n", FONT_NORMAL));
        clienteInfo.add(new Chunk("ID: ", FONT_NORMAL));
        clienteInfo.add(new Chunk(cliente.getId() + "\n", FONT_NORMAL));
        clienteInfo.add(new Chunk("Telefone: ", FONT_NORMAL));
        clienteInfo.add(new Chunk(cliente.getTelefone() + "\n", FONT_NORMAL));
        clienteInfo.add(new Chunk("Email: ", FONT_NORMAL));
        clienteInfo.add(new Chunk(cliente.getEmail() + "\n", FONT_NORMAL));
        clienteInfo.add(new Chunk("Endereço: ", FONT_NORMAL));
        clienteInfo.add(new Chunk(cliente.getEndereco() + "\n\n", FONT_NORMAL));
        document.add(clienteInfo);
    }
    
    private static void adicionarTabelaItens(Document document, List<ItemOrdem> itens) throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        
        PdfPCell cell;
        String[] headers = {"Código", "Descrição", "Quantidade", "Preço Unit.", "Subtotal"};
        for (String header : headers) {
            cell = new PdfPCell(new Phrase(header, FONT_SUBTITULO));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
        
        for (ItemOrdem item : itens) {
            table.addCell(new Phrase(item.getProduto().getCodigo(), FONT_SMALL));
            table.addCell(new Phrase(item.getProduto().getDescricao(), FONT_SMALL));
            table.addCell(new Phrase(String.valueOf(item.getQuantidade()), FONT_SMALL));
            table.addCell(new Phrase(String.format("%.2f MT", item.getPrecoUnitario()), FONT_SMALL));
            table.addCell(new Phrase(String.format("%.2f MT", item.getSubtotal()), FONT_SMALL));
        }
        
        document.add(table);
    }
    
    private static void adicionarTabelaItensComIVA(Document document, List<ItemOrdem> itens) throws DocumentException {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        
        PdfPCell cell;
        String[] headers = {"Código", "Descrição", "Quantidade", "Preço Unit.", "Subtotal", "IVA (16%)"};
        for (String header : headers) {
            cell = new PdfPCell(new Phrase(header, FONT_SUBTITULO));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
        
        for (ItemOrdem item : itens) {
            double iva = item.getSubtotal() * 0.16;
            table.addCell(new Phrase(item.getProduto().getCodigo(), FONT_SMALL));
            table.addCell(new Phrase(item.getProduto().getDescricao(), FONT_SMALL));
            table.addCell(new Phrase(String.valueOf(item.getQuantidade()), FONT_SMALL));
            table.addCell(new Phrase(String.format("%.2f MT", item.getPrecoUnitario()), FONT_SMALL));
            table.addCell(new Phrase(String.format("%.2f MT", item.getSubtotal()), FONT_SMALL));
            table.addCell(new Phrase(String.format("%.2f MT", iva), FONT_SMALL));
        }
        
        document.add(table);
    }
    
    private static void adicionarTotal(Document document, double total) throws DocumentException {
        Paragraph totalParagraph = new Paragraph();
        totalParagraph.setAlignment(Element.ALIGN_RIGHT);
        totalParagraph.add(new Chunk("TOTAL: ", FONT_SUBTITULO));
        totalParagraph.add(new Chunk(String.format("%.2f MT", total), FONT_TITULO));
        document.add(totalParagraph);
    }
    
    private static void adicionarTotalComIVA(Document document, double subtotal) throws DocumentException {
        double iva = subtotal * 0.16;
        double total = subtotal + iva;
        
        Paragraph totaisParagraph = new Paragraph();
        totaisParagraph.setAlignment(Element.ALIGN_RIGHT);
        totaisParagraph.add(new Chunk("Subtotal: ", FONT_NORMAL));
        totaisParagraph.add(new Chunk(String.format("%.2f MT\n", subtotal), FONT_NORMAL));
        totaisParagraph.add(new Chunk("IVA (16%): ", FONT_NORMAL));
        totaisParagraph.add(new Chunk(String.format("%.2f MT\n", iva), FONT_NORMAL));
        totaisParagraph.add(new Chunk("TOTAL: ", FONT_SUBTITULO));
        totaisParagraph.add(new Chunk(String.format("%.2f MT", total), FONT_TITULO));
        document.add(totaisParagraph);
    }
    
    private static void adicionarRodapeUEM(Document document, String mensagem) throws DocumentException {
        document.add(new Paragraph("\n\n"));
        Paragraph footer = new Paragraph(mensagem, FONT_SMALL);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);
        
        document.add(new Paragraph("\n"));
        
        try {
            // Adicionar logo UEM
            Image logoUEM = Image.getInstance(LOGO_UEM_PATH);
            logoUEM.scaleToFit(80, 60);
            logoUEM.setAlignment(Element.ALIGN_CENTER);
            document.add(logoUEM);
            document.add(new Paragraph("\n"));
        } catch (Exception e) {
            System.err.println("Erro ao carregar logo UEM: " + e.getMessage());
        }
        
        Paragraph copyright = new Paragraph();
        copyright.setAlignment(Element.ALIGN_CENTER);
        copyright.add(new Chunk("UEM - Universidade Eduardo Mondlane\n", FONT_SMALL));
        copyright.add(new Chunk("Todos os direitos reservados\n", FONT_SMALL));
        document.add(copyright);
    }
    
    static class WatermarkPageEvent extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte canvas = writer.getDirectContentUnder();
                
                canvas.saveState();
                
                PdfGState gs1 = new PdfGState();
                gs1.setFillOpacity(0.3f);
                canvas.setGState(gs1);
                
                ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                    new Phrase(WATERMARK_TEXT, new Font(Font.FontFamily.HELVETICA, 52, Font.BOLD, BaseColor.LIGHT_GRAY)),
                    document.getPageSize().getWidth() / 2,
                    document.getPageSize().getHeight() / 2,
                    45);
                
                canvas.restoreState();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
