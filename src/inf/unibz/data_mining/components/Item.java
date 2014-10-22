package inf.unibz.data_mining.components;

public class Item {
	
	String attributeName = null;
	Object attributeValue = null;
	int attributeOrder;
	
	
	public int getAttributeOrder() {
		return attributeOrder;
	}

	public void setAttributeOrder(int attributeOrder) {
		this.attributeOrder = attributeOrder;
	}

	public Item(String attributeName, Object attributeValue)
	{
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public Object getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(Object attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String toString(){
		return "{" + attributeName + ", " + attributeValue + "}";
	}

}
