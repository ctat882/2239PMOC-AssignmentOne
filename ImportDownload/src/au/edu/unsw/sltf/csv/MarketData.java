package au.edu.unsw.sltf.csv;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Corona
 * 
 */
public class MarketData {
	/** Security Code. Uppercase, 3 - 4 lettes. */
	private String sec;
	/** GMT date in dd-mmm-yyyy format (e.g. 09-MAY-1999 or 25-JUL-2001). */
	private Calendar date;
	/**
	 * GMT time in hh:mm:ss.sss format (e.g. 07:24:26.463) The hh goes from 00
	 * (midnight) to 23 (11PM).
	 */
	private Calendar time;
	/**
	 * The GMT offset in hours. If this offset is added to the GMT date and
	 * time, you then have the local time of when the event represented by the
	 * data row occurred.
	 */
	private Integer offset;
	/**
	 * Type of event. This is a string that identifies the type of event
	 * represented by this data row. For now, there are only two types of
	 * events: "Quote" and "Trade".
	 */
	private String eventType;
	/** Price. A decimal number that represents a price. */
	private Double price;
	/** Volume. An integer indicating the volume (e.g. of a trade). */
	private Integer volume;
	/** Bid Price. A decimal number that represents a bid price. */
	private Double bidPrice;
	/** Bid Size. An integer that represents a bid size. */
	private Integer bidSize;
	/** Ask Price. A decimal number that represents an ask price. */
	private Double askPrice;
	/** Ask Size. An integer that represents an ask size. */
	private Integer askSize;

	public MarketData() {
		this.sec = "";
		this.date = null;
		this.time = null;
		this.offset = null;
		this.eventType = "";
		this.price = null;
		this.volume = null;
		this.bidPrice = null;
		this.bidSize = null;
		this.askPrice = null;
		this.askSize = null;
	}

	public MarketData(String sec, Calendar date, Calendar time, int offset,
			String eventType, double price, int volume, double bidPrice,
			int bidSize, double askPrice, int askSize) {
		super();
		this.sec = sec;
		this.date = date;
		this.time = time;
		this.offset = offset;
		this.eventType = eventType;
		this.price = price;
		this.volume = volume;
		this.bidPrice = bidPrice;
		this.bidSize = bidSize;
		this.askPrice = askPrice;
		this.askSize = askSize;
	}

	public String getSec() {
		return sec;
	}

	public void setSec(String sec) {
		this.sec = sec;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			this.date.setTime(sdf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getDateString () {
		String date = this.date.get(Calendar.DAY_OF_MONTH) + "-" +
						new DateFormatSymbols().getShortMonths()[this.date.get(Calendar.MONTH)] + "-" +
						this.date.get(Calendar.YEAR);
		return date;
						
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
		try {
			this.date.setTime(sdf.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getTimeString () {
		String time = this.time.get(Calendar.HOUR_OF_DAY) + ":" +
						this.time.get(Calendar.MINUTE) + ":" + 
						this.time.get(Calendar.SECOND) + ":" +
						this.time.get(Calendar.MILLISECOND);
		return time;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public int getBidSize() {
		return bidSize;
	}

	public void setBidSize(int bidSize) {
		this.bidSize = bidSize;
	}

	public double getAskPrice() {
		return askPrice;
	}

	public void setAskPrice(double askPrice) {
		this.askPrice = askPrice;
	}

	public int getAskSize() {
		return askSize;
	}

	public void setAskSize(int askSize) {
		this.askSize = askSize;
	}

}
