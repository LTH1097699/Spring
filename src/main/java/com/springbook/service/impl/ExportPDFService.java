package com.springbook.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.springbook.service.IExportPDFService;

@Service
public class ExportPDFService implements IExportPDFService{
	private Logger logger = LoggerFactory.getLogger(ExportPDFService.class);
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private ServletContext servletContext;

	@Override
	public ByteArrayInputStream exportPDF(String templatename, Map<String, Object> data) {
		Context context = new Context();
		context.setVariables(data);
		String path = servletContext.getRealPath("/");
		
		String htmlContent = templateEngine.process(templatename, context);
		ByteArrayInputStream byteArrayInputStream = null;
		
		try {
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			
			ITextRenderer renderer = new ITextRenderer();
			ITextFontResolver resolver = renderer.getFontResolver();
			resolver.addFont(path+"/template/admin/font/DejaVuSans.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			
			renderer.setDocumentFromString(htmlContent);
			renderer.layout();
			renderer.createPDF(arrayOutputStream,false);
			renderer.finishPDF();
			byteArrayInputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());
		} catch (DocumentException | IOException e) {
			logger.error(e.getMessage(),e);		
		}
		return byteArrayInputStream;
	}

}
