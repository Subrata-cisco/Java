package customannotation;

public class CiscoSystems implements ICompany{

	@Override
	public String getCEOName() {
		return "John";
	}

	@Override
	public String getTotalAsset() {
		return "100 B";
	}

}
