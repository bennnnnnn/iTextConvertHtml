package htmlFileToPDF;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.licensekey.LicenseKey;
import com.itextpdf.kernel.log.CounterFactory;
import com.itextpdf.kernel.log.DefaultCounter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Convert {

	public static void main(String[] args) throws IOException 
	{
		//LicenseKey.loadLicenseFile("C:\\itextkey-0.xml");
		CounterFactory.getInstance().setCounter(new AGPLWarningRemoverCounter ());
		//working folder
		final String folder = "C:\\html\\html";
		
		//destination folder
		final String folderDest = "C:\\html\\pdf";
		
		File repertoire = new File(folder);
		if( !repertoire.exists() || !repertoire.isDirectory())
		{
			System.out.println(folder+" not exist");
			System.exit(0);
		}
		
		File repertoireDest = new File(folderDest);
		if( !repertoireDest.exists() || !repertoireDest.isDirectory())
		{
			System.out.println(folderDest+" not exist");
			System.exit(0);
		}
		
		File[] fichiers = repertoire.listFiles();
		for (int f=0 ; f< fichiers.length ; f++)
		{ 
			File file = fichiers[f];
			
			String fileName = file.getName();
		    if(!("").equals(fileName) && file.exists())
	    	{
	    		try (BufferedReader br = new BufferedReader(new FileReader(file))) 
	    		{
	    			File pdfFile = new File(folderDest+"\\", fileName+".pdf");
	    			if(!pdfFile.exists())
	    			{
	    				pdfFile.createNewFile();
	    			}
    				StringBuffer sourceCOde = new StringBuffer();
			        String line;
	    		    while ((line = br.readLine()) != null) 
	    		    {
	    		    	sourceCOde.append(line);
	    		    }	
					
					ByteArrayOutputStream ba = new ByteArrayOutputStream();
					
					PdfWriter pdfWriter = new PdfWriter(ba);
						    		
					
					@SuppressWarnings("resource")
					PdfDocument pdfDoc = new PdfDocument(new PdfWriter(ba));
			    	Document doc = new Document(pdfDoc);
			        
			    	List<IElement> headerElements = HtmlConverter.convertToElements(sourceCOde.toString());
			        
			        for (IElement headerElement : headerElements) 
			        {
			            if (headerElement instanceof IBlockElement) 
			            {
			                doc.add((IBlockElement)headerElement);
			            }
			        }
			       
			        doc.flush();
			        doc.close();
			        
			        pdfDoc.close();
			        pdfWriter.flush();
			        pdfWriter.close();	
			        
			        FileOutputStream fos = new FileOutputStream(pdfFile);
					
					ba.writeTo(fos);
					
					fos.flush();
					fos.close();
					
					ba.close();
	    		}
		    }
		}	
	}
	public static class AGPLWarningRemoverCounter extends DefaultCounter {
	    @SuppressWarnings("unused")
		private static void plusOne() {
	        return;
	    }
	}

}
