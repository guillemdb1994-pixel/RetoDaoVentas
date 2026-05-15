package modelo;

public class Campos {
	protected String field;
	protected String type;
	protected Boolean isNull;
	protected String key;
	protected String defaultValue;
	protected String extra;
	public Campos(String field, String type, Boolean isNull,String key, String defaultValue, String extra) {
		super();
		this.field = field;
		this.type = type;
		this.isNull = isNull;
		this.key = key;
		this.defaultValue = defaultValue;
		this.extra = extra;
	}
	public Campos() {
		super();
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getIsNull() {
		return isNull;
	}
	public void setIsNull(Boolean isNull) {
		this.isNull = isNull;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String isDefault) {
		this.defaultValue = isDefault;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key=key;
	}
	@Override
	public String toString() {
		return "Campo:" + field + ", type=" + type + ", isNull=" + isNull + ", key=" + key +", isDefault=" + defaultValue
				+ ", extra=" + extra + "]";
	}
	
}
