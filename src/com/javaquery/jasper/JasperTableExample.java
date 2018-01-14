package com.javaquery.jasper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javaquery.bean.Item;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;

public class JasperTableExample {

	public static void main(String[] args) {

		try {

			String userHomeDirectory = System.getProperty("user.home");
			String outputFile = userHomeDirectory + File.separatorChar + "JasperTableExample.pdf";
			String outputCsvFile = userHomeDirectory + File.separatorChar + "JasperTableExample.csv";
			String outputXlsFile = userHomeDirectory + File.separatorChar + "JasperTableExample.xls";
			List<Item> listItems = new ArrayList<Item>();

			Item Iphone = new Item();
			Iphone.setName("Iphone 6s");
			Iphone.setPrice(6500.00);

			Item ipad = new Item();
			ipad.setName("Ipad Prod");
			ipad.setPrice(70000.00);

			Item itouch = new Item();
			itouch.setName("Itouch");
			itouch.setPrice(4000.00);

			listItems.add(Iphone);
			listItems.add(ipad);
			listItems.add(itouch);

			JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ItemDataSource", itemsJRBean);

			/*
			 * Using compiled version(.jasper) of Jasper report to generate PDF
			 */
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					"resources/com/javaquery/jasper/templates/template_Table.jasper", parameters,
					new JREmptyDataSource());

			/* outputStream to create PDF */
			OutputStream outputStream = new FileOutputStream(new File(outputFile));

			/* Write content to PDF file */
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

			System.out.println("File Generated");

			/**
			 * 3- export to CSV sheet
			 */
			//DEPRECATED :
			// JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(file);
			// JRCsvExporter csvExporter = new JRCsvExporter();
			// csvExporter.setParameter(JRExporterParameter.JASPER_PRINT,
			// jasperPrint);
			// csvExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,REPORT_DIRECTORY
			// + "/" + reportName + ".csv");
			// System.out.println("Exporting report...");
			// csvExporter.exportReport();

			/* outputStream to create Csv */
			
			OutputStream outputCsvStream = new FileOutputStream(new File(outputCsvFile));

			JRCsvExporter csvExporter = new JRCsvExporter();

			List<JasperPrint> jasperprints = new ArrayList<JasperPrint>();
			jasperprints.add(jasperPrint);
			csvExporter.setExporterInput(SimpleExporterInput.getInstance(jasperprints));

			csvExporter.setExporterOutput(new SimpleWriterExporterOutput(outputCsvStream));
			SimpleCsvExporterConfiguration exporterConfiguration = new SimpleCsvExporterConfiguration();
			// nothing to set here, but you could do things like
			// exporterConfiguration.setFieldDelimiter
			csvExporter.setConfiguration(exporterConfiguration);
			csvExporter.exportReport();
			
			System.out.println("CSV Done!");
			
			/* outputStream to create XLS */
			JRXlsExporter xlsExporter = new JRXlsExporter();
			OutputStream outputXlsStream = new FileOutputStream(new File(outputXlsFile));
			System.out.println("Etape1 Done!");
			//JRXlsExporter xlsExporter = new JRXlsExporter();
			System.out.println("Etape2 Done!");
			List<JasperPrint> jasperprintsXls = new ArrayList<JasperPrint>();
			jasperprintsXls.add(jasperPrint);
			//xlsExporter.setExporterInput(SimpleExporterInput.getInstance(jasperprintsXls));
			xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));

			//xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputXlsStream));
			xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputXlsStream));
			SimpleXlsExporterConfiguration exporterXlsConfiguration = new SimpleXlsExporterConfiguration();
			// nothing to set here, but you could do things like
			// exporterConfiguration.setFieldDelimiter	
			
			xlsExporter.setConfiguration(exporterXlsConfiguration);
			xlsExporter.exportReport();

			System.out.println("XLS Done!");
			

		} catch (JRException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}

	}

}
