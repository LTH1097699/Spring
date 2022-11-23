package com.springbook.service;

import java.io.ByteArrayInputStream;
import java.util.Map;

public interface IExportPDFService {
	ByteArrayInputStream exportPDF(String templatename, Map<String, Object> data);
}
