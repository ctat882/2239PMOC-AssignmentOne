package au.edu.unsw.sltf.currencyconversion;

public class CurrencyData {
	private String code;
	private Double unitsPerAUD;
	private Double audPerUnit;
	
	public CurrencyData () {
		this.code = "";
		this.unitsPerAUD = null;
		this.audPerUnit = null;
	}

	/**
	 * @param code
	 * @param unitsPerAUD
	 * @param audPerUnit
	 */
	public CurrencyData(String code, Double unitsPerAUD, Double audPerUnit) {
		super();
		this.code = code;
		this.unitsPerAUD = unitsPerAUD;
		this.audPerUnit = audPerUnit;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the unitsPerAUD
	 */
	public Double getUnitsPerAUD() {
		return unitsPerAUD;
	}

	/**
	 * @param unitsPerAUD the unitsPerAUD to set
	 */
	public void setUnitsPerAUD(Double unitsPerAUD) {
		this.unitsPerAUD = unitsPerAUD;
	}

	/**
	 * @return the audPerUnit
	 */
	public Double getAudPerUnit() {
		return audPerUnit;
	}

	/**
	 * @param audPerUnit the audPerUnit to set
	 */
	public void setAudPerUnit(Double audPerUnit) {
		this.audPerUnit = audPerUnit;
	}
	
	
}
