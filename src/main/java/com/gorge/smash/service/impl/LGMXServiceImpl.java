package com.gorge.smash.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gorge.smash.model.entity.LGMXSong;
import com.gorge.smash.rest.exception.GorgePasContentException;
import com.gorge.smash.service.interf.LGMXService;
import com.gorge.smash.util.Constant;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class LGMXServiceImpl implements LGMXService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LGMXServiceImpl.class);
	
	private final String COMMENT = "Pour tous les morceaux où les lumières sont synchronisées,Garder une lumière de plateau plutôt faible permettra un meilleur effet des lumières des costumes. Notamment pour le premier morceau, les lumières des costumes commenceront faibles et démareront à leur pleine puissance après 1 minute environ. Garder la lumière de plateau assez faible au moins jusqu'à ce moment pour garder l'effet de suprise des costumes";

	private final String SETLIST_PREFIX = "setlist_";
	
	private final String SETLIST = "setlist";
	
	private final String CONDUITE_PREFIX = "conduite_";
	
	private final String CONDUITE = "conduite";
	
	private final String PDF = ".pdf";
	
	private final String TMP_PDF = "tmp.pdf";
	
	private final String BASE_DL_URL = "https://smash3000.ovh/lgmx/files/";
	
	private final Integer BASE_FONT_SIZE = 40;
	
	private final Integer PAGE_COUNT = 4;
	
//	private final String BASE_DL_URL = "http://localhost:8080/lgmx/files/";
	
	private void addTableHeader(PdfPTable table) {
	    Stream.of("N°", "Titre", "BPM", "Couleur 1", "Couleur 2", "Sync", "Notes")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);
	        header.setPhrase(new Phrase(columnTitle));
	        table.addCell(header);
	    });
	}
	
	private final static String blankNotNull(String s)
	{
		return (s == null || s == "null") ? "" : s;
	}
	
	private final static PdfPCell makeCenterCell(String text)
	{
		text = blankNotNull(text);
		
		Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 14, BaseColor.BLACK);
		
		PdfPCell pcell = new PdfPCell(new Phrase(text, font));
		pcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setPaddingTop(7);
		pcell.setPaddingBottom(7);
		
		return pcell;
	}
	
	private final static PdfPCell makeLeftCell(String text, Font font)
	{
		text = blankNotNull(text);

		PdfPCell pcell = new PdfPCell(new Phrase(text, font));
		pcell.setBorder(0);
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		pcell.setPaddingTop(5);
		pcell.setPaddingBottom(5);
		
		return pcell;
	}
	
	
	private void addRows(PdfPTable table, LGMXSong s) {
		
		table.addCell(makeCenterCell(s.getOrder()+""));
		table.addCell(makeCenterCell(s.getTitle()));
		table.addCell(makeCenterCell(s.getBpm()+""));
		table.addCell(makeCenterCell(s.getColor1()));
		table.addCell(makeCenterCell(s.getColor2()));
		table.addCell(makeCenterCell((s.getSynchro() == null || !s.getSynchro()) ? "Non" : "Oui"));
		table.addCell(makeCenterCell(s.getNotes()));	
	}
	
	private Integer getOptimalFontSize(List<LGMXSong> songs, Integer fontSize)
	{
		Document setlist = new Document(PageSize.A4.rotate());
		try {
			PdfWriter writer = PdfWriter.getInstance(setlist, new FileOutputStream(TMP_PDF));

			setlist.open();
			Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, fontSize, BaseColor.BLACK);
			
			
			PdfPTable table = new PdfPTable(new float[] {1,1});
			table.setWidthPercentage(100);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
			table.getDefaultCell().setBorder(0);

			songs.forEach((LGMXSong s) -> {
				if(!s.getTitle().contains(Constant.RAPPEL))
				{
					table.addCell(makeLeftCell(s.getTitle(), font));
					table.addCell(makeLeftCell(s.getTitle(), font));
				}
			});
			
			setlist.add(table);
			

			if(writer.getPageNumber() > 1)
			{
				setlist.close();	
				return getOptimalFontSize(songs, fontSize - 1);
			}
			else {
				setlist.close();	
				return fontSize;
			}
			
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Map<String,String> makeFullPDF(List<LGMXSong> songs) throws GorgePasContentException{
		

		Map<String,String> result = new HashMap<>();
		
		if(songs == null || songs.isEmpty())
		{
			return result;
		}
	 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH'h'mm");
		Date today = Calendar.getInstance().getTime();
		String conduiteFileName = CONDUITE_PREFIX + sdf.format(today) + PDF;		
		
		Document conduite = new Document(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(conduite, new FileOutputStream(conduiteFileName));
	
			conduite.open();
			
			PdfPTable table = new PdfPTable(new float[] {1,4,2,3,3,2,10});
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			addTableHeader(table);

			songs.forEach((LGMXSong s) -> {
				if(!s.getTitle().contains(Constant.RAPPEL))
				{
					addRows(table, s);	
				}
			});
			
			try {
				conduite.add(table);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			conduite.add(new Paragraph(""));
			
			conduite.add(new Paragraph(""));
			
			conduite.add(new Paragraph(COMMENT));
			
			//Insert Page BREAK
			conduite.newPage();
			
			Integer fontSize = getOptimalFontSize(songs, BASE_FONT_SIZE);
		
			conduite.open();
			Font setlistFont = FontFactory.getFont(FontFactory.COURIER_BOLD, fontSize, BaseColor.BLACK);
			
			
			PdfPTable setlistTable = new PdfPTable(new float[] {1,1});
			setlistTable.setWidthPercentage(100);
		    setlistTable.setSpacingBefore(0f);
		    setlistTable.setSpacingAfter(0f);
			setlistTable.getDefaultCell().setBorder(0);

			songs.forEach((LGMXSong s) -> {
				if(!s.getTitle().contains(Constant.RAPPEL))
				{
					setlistTable.addCell(makeLeftCell(s.getTitle(), setlistFont));
					setlistTable.addCell(makeLeftCell(s.getTitle(), setlistFont));
				}
			});
			
			
			try {
			
			for(Integer i = 0; i < PAGE_COUNT; i++)
			{
				conduite.add(setlistTable);
			}
		
			
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			conduite.close();
			
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		try {
			String download_link = BASE_DL_URL + URLEncoder.encode(conduiteFileName, StandardCharsets.UTF_8.name());
			result.put(CONDUITE, download_link);
		} catch (UnsupportedEncodingException e) {
			
		}

			return result;
	}
		
	@Override
	public Map<String, String> makeAndUploadPDF(List<LGMXSong> songs) throws GorgePasContentException {

		Map<String,String> result = new HashMap<>();
		
		if(songs == null || songs.isEmpty())
		{
			return result;
		}
	 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH'h'mm");
		Date today = Calendar.getInstance().getTime();
		String setlistFileName = SETLIST_PREFIX + sdf.format(today) + PDF;
		String conduiteFileName = CONDUITE_PREFIX + sdf.format(today) + PDF;		
		
		try {
			String download_link = BASE_DL_URL + URLEncoder.encode(setlistFileName, StandardCharsets.UTF_8.name());
			result.put(SETLIST, download_link);
		} catch (UnsupportedEncodingException e) {
			
		}
		
		Document conduite = new Document(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(conduite, new FileOutputStream(conduiteFileName));
	
			conduite.open();
			Font font = FontFactory.getFont(FontFactory.HELVETICA, 32, BaseColor.BLACK);
			
			PdfPTable table = new PdfPTable(new float[] {1,4,2,3,3,3,2,10});
			addTableHeader(table);

			songs.forEach((LGMXSong s) -> {
				if(!s.getTitle().contains(Constant.RAPPEL))
				{
					addRows(table, s);	
				}
			});
			
			try {
				conduite.add(table);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			conduite.close();
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String download_link = BASE_DL_URL + URLEncoder.encode(conduiteFileName, StandardCharsets.UTF_8.name());
			result.put(CONDUITE, download_link);
		} catch (UnsupportedEncodingException e) {
			
		}

			return result;
	}
}
