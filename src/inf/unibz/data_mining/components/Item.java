package inf.unibz.data_mining.components;

public class Item {
	
	String attributeName = null;
	Object attributeValue = null;
	int attributeOrder;
	int keyValue
	;
	
	
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
		return "{" + attributeName + " = " + attributeValue + "}";
	}
	
	public boolean equals(Item t){
		if(this.getAttributeName().equals(t.getAttributeName()) && this.getAttributeValue().equals(t.getAttributeValue()))
			return true;
		return false;
	}

	public int getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(int keyValue) {
		this.keyValue = keyValue;
	}

}
