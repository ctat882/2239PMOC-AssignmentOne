package au.edu.unsw.sltf.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {

	FileWriter fw;
	File file;
	BufferedWriter writer;
	
	public CsvWriter (String filename) throws IOException {
		String dir = System.getProperty("java.io.tmpdir");
		this.file = new File(dir + "\\" + filename );
		if(! file.exists()) {			
				file.createNewFile();			
		}
		fw = new FileWriter(this.file);		
		this.writer = new BufferedWriter(fw);
		writer.write("#RIC,Date[G],Time[G],GMT Offset,Type,Price,Volume,Bid Price,Bid Size,Ask Price,Ask Size");
		writer.newLine();
	}
	
	public void writeRow (MarketData data) throws IOException {
		writer.write(data.getSec() + "," +
					data.getDateString() + "," +
					data.getTimeString() + "," +
					data.getOffset() + "," +
					data.getEventType() + "," +
					data.getPrice() + "," +
					data.getVolume() + "," +
					data.getBidPrice() + "," +
					data.getBidSize() + "," +
					data.getAskPrice() + "," +
					data.getAskSize());				
				
	}
	public void closeFile () throws IOException {
		writer.close();
		fw.close();
	}
}
