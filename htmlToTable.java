private static Table htmlToTable( String commentairegeneral)  throws IOException 
{
	Style normal8Helvetica = new Style().setFont(PdfFontFactory.createFont(FontConstants.HELVETICA)).setFontSize(8);
	
	@SuppressWarnings("deprecation")
	Table tableText = new Table(1);
	tableText.addStyle(normal8Helvetica);
	
   List<IElement> headerElements = HtmlConverter.convertToElements(commentairegeneral);
	
	for (IElement headerElement : headerElements) {
		// Making sure we are adding blocks to canvas
		if (headerElement instanceof IBlockElement) {
			//canvas.add((IBlockElement)headerElement);
			tableText.addCell(new Cell().add((IBlockElement) headerElement).setBorder(Border.NO_BORDER));
		}
	}

	return tableText;
}