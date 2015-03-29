package customannotation;

import customannotation.ICompanyInfo.CustType;

/**
 * ICompany is injected with CiscoSystems in the client class IncomingRequestHandler.
 * 
 * @author Subratas
 *
 */
public class CompanyDetails {
	
	@ICompanyInfo(cust = CustType.CISCO)
	private ICompany company= null;
		
	
	public void printCompanyDetails() {
		System.out
				.println("CompanyConnection.validateInComingRequest() CEO name ::"
						+ company.getCEOName()
						+ " Company total asset ::"
						+ company.getTotalAsset());
	}

}
