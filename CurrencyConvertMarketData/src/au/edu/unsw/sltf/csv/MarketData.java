package au.edu.unsw.sltf.csv;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * @author Corey Tattam
 * 
 */
public class MarketData {
	/** Security Code. Uppercase, 3 - 4 letters. */
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
	private String offset;
	/**
	 * Type of event. This is a string that identifies the type of event
	 * represented by this data row. For now, there are only two types of
	 * events: "Quote" and "Trade".
	 */
	private String eventType;
	/** Price. A decimal number that represents a price. */
	private String price;
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
	/** The currency prefix of the price field , default to AUD */
	private String currencyType;
	/** The Double representation of the given price, any prefix is removed*/
	private Double actualPrice;
	/** Joins the date and time fields */
	private Calendar timeStamp;

	public MarketData() {
		this.sec = "";
		this.date = null;
		this.time = null;
		this.offset = null;
		this.eventType = "";
		this.price = "";
		this.volume = null;
		this.bidPrice = null;
		this.bidSize = null;
		this.askPrice = null;
		this.askSize = null;
		this.currencyType = "AUD";
		this.actualPrice = null;
		this.timeStamp = null;
	}

	public MarketData(String sec, Calendar date, Calendar time, String offset,
			String eventType, String price, int volume, double bidPrice,
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
		// System.out.println("afterSetTime:" + this.date.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			this.date.setTime(sdf.parse(date));
			// System.out.println("afterSetTime:" + this.date.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getDateString() {
		// System.out.println("Before get Time:" + this.date.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String months = new DateFormatSymbols().getShortMonths()[this.date
				.get(Calendar.MONTH)];
		months = months.toUpperCase();
		// System.out.println("AFTER UPPERCASE:" + months);
		String datestring = sdf.format(this.date.getTime()) + "-"
		// + new DateFormatSymbols().getShortMonths()[this.date
		// .get(Calendar.MONTH)] + "-"
				+ months + "-" + this.date.get(Calendar.YEAR);
		// datestring.toUpperCase();
		return datestring;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		try {
			this.time.setTime(sdf.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		// String time = this.time.get(Calendar.HOUR_OF_DAY) + ":"
		// + this.time.get(Calendar.MINUTE) + ":"
		// + this.time.get(Calendar.SECOND) + "."
		// + this.time.get(Calendar.MILLISECOND);
		String timestring = sdf.format(this.time.getTime());
		return timestring;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
		if (Pattern.matches("^[A-Z]+[0-9]+[.]?[0-9]*$", price)) {
			this.setCurrencyType(price.substring(0, 3));
			this.setActualPrice(Double.parseDouble(price.substring(3, price.length())));
		}
		else {
			this.setActualPrice(Double.parseDouble(price));
		}
		
	}
	
	

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public Double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public Integer getBidSize() {
		return bidSize;
	}

	public void setBidSize(int bidSize) {
		this.bidSize = bidSize;
	}

	public Double getAskPrice() {
		return askPrice;
	}

	public void setAskPrice(double askPrice) {
		this.askPrice = askPrice;
	}

	public Integer getAskSize() {
		return askSize;
	}

	public void setAskSize(int askSize) {
		this.askSize = askSize;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public Double getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(Double actualPrice) {
		this.actualPrice = actualPrice;		
	}

	public Calendar getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp() {
		
		this.timeStamp = Calendar.getInstance();
		this.timeStamp.set(this.date.get(Calendar.YEAR),
				this.date.get(Calendar.MONTH),
				this.date.get(Calendar.DAY_OF_MONTH),
				this.time.get(Calendar.HOUR_OF_DAY),
				this.time.get(Calendar.MINUTE),
				this.time.get(Calendar.SECOND));
		this.timeStamp.set(Calendar.MILLISECOND, this.time.get(Calendar.MILLISECOND));
	}
	
	
}
