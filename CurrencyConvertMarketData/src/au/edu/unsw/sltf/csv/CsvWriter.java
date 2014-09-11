package au.edu.unsw.sltf.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
	FileWriter fw;
	File file;
	BufferedWriter writer;

	public CsvWriter(String filename) throws IOException {
		// String dir = System.getProperty("java.io.tmpdir");
		this.file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		fw = new FileWriter(this.file);
		this.writer = new BufferedWriter(fw);
		writer.write("#RIC,Date[G],Time[G],GMT Offset,Type,Price,Volume,Bid Price,Bid Size,Ask Price,Ask Size");
		writer.newLine();
	}

	public void writeRow(MarketData data) throws IOException {
		StringBuilder s = new StringBuilder();
		if (!data.getSec().isEmpty())
			s.append(data.getSec());
		s.append(',');
		if (!data.getDateString().isEmpty())
			s.append(data.getDateString());
		s.append(',');
		if (!data.getTimeString().isEmpty())
			s.append(data.getTimeString());
		s.append(',');
		if (!data.getOffset().isEmpty())
			s.append(data.getOffset());
		s.append(',');
		if (!data.getEventType().isEmpty())
			s.append(data.getEventType());
		s.append(',');
		if (!data.getCurrencyType().contentEquals("AUD") && !data.getPrice().isEmpty()) {
			s.append(data.getCurrencyType());
		}
		if (!(data.getActualPrice() == null))
			s.append(data.getActualPrice());
		s.append(',');
		
		
		if (!(data.getVolume() == null))
			s.append(data.getVolume());
		s.append(',');
		if (!data.getCurrencyType().contentEquals("AUD") && !data.getBidPrice().isEmpty()) {
			s.append(data.getCurrencyType());
		}
		if (!(data.getActualBidPrice() == null))
			s.append(data.getActualBidPrice());
		s.append(',');
		if (!(data.getBidSize() == null))
			s.append(data.getBidSize());
		s.append(',');
		if (!data.getCurrencyType().contentEquals("AUD") && !data.getAskPrice().isEmpty()) {
			s.append(data.getCurrencyType());
		}
		if (!(data.getActualAskPrice() == null))
			s.append(data.getActualAskPrice());
		s.append(',');
		if (!(data.getAskSize() == null))
			s.append(data.getAskSize());
		writer.write(s.toString());
		// writer.write(data.getSec() + "," + data.getDateString() + ","
		// + data.getTimeString() + "," + data.getOffset() + ","
		// + data.getEventType() + "," + data.getPrice() + ","
		// + data.getVolume() + "," + data.getBidPrice() + ","
		// + data.getBidSize() + "," + data.getAskPrice() + ","
		// + data.getAskSize());
		writer.newLine();
	}

	public void closeFile() throws IOException {
		writer.close();
		fw.close();
	}
}
