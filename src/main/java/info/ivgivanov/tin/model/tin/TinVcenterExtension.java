package info.ivgivanov.tin.model.tin;

public class TinVcenterExtension {
	
	private String label;
	private String summary;
	private String key;
	private String version;
	
	
	public String getLabel() {
		return label;
	}
	private void setLabel(String label) {
		this.label = label;
	}
	public String getSummary() {
		return summary;
	}
	private void setSummary(String summary) {
		this.summary = summary;
	}
	public String getKey() {
		return key;
	}
	private void setKey(String key) {
		this.key = key;
	}
	public String getVersion() {
		return version;
	}
	private void setVersion(String version) {
		this.version = version;
	}
	
	
	public TinVcenterExtension(String label, String summary, String key, String version) {
		
		this.setLabel(label);
		this.setSummary(summary);
		this.setKey(key);
		this.setVersion(version);
		
	}

}
