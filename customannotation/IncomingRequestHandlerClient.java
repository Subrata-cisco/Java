package customannotation;

import java.lang.reflect.Field;

import SecurityManager.SecurityManagerExample;
import customannotation.ICompanyInfo.CustType;

/**
 * Client file to test custom annotation.
 * @author subratas
 *
 */
public class IncomingRequestHandlerClient {
	
	public static void main(String[] args) throws Exception {
		IncomingRequestHandlerClient handler = new IncomingRequestHandlerClient();
		handler.showItWorks();
	}
	
	
	private void showItWorks() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		CompanyDetails connObj = new CompanyDetails();
		Class conn = connObj.getClass();
		
		for (Field method : conn.getDeclaredFields()) {
			ICompanyInfo annotation = method
					.getAnnotation(ICompanyInfo.class);
			if (annotation != null) {
				CustType type = annotation.cust();
				if (type == CustType.CISCO) {
					method.setAccessible(true);
					method.set(connObj,new CiscoSystems());
					connObj.printCompanyDetails();
				} else {
					System.out
							.println("IncomingRequestHandler.main() Invalid customer !!");
				}
			}
		}
		
	}
}
