package au.edu.unsw.sltf.csv;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CsvDownloader {
	private URL address;
	private String filename;
	
	
	public CsvDownloader (URL address) {
		this.address = address;
		this.filename = this.address.getFile();
		// Sourced from http://runnable.com/Uu83dm5vSScIAACw/download-a-file-from-the-web-for-java-files-and-save
		
	}
	
	public void downloadCsv() throws IOException {
		InputStream in = new BufferedInputStream(this.address.openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		 while (-1!=(n=in.read(buf)))
		 {
		    out.write(buf, 0, n);
		 }
		 out.close();
		 in.close();
		 byte[] response = out.toByteArray();
		 
		 String dir = System.getProperty("java.io.tmpdir");
		 FileOutputStream fos = new FileOutputStream(dir + "\\" + this.filename);
		 fos.write(response);
		 fos.close();
	}
}
