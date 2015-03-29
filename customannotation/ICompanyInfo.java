package customannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention (RetentionPolicy.RUNTIME)
@interface ICompanyInfo {
	
	public enum CustType { ORACLE, CISCO }
	
	
	// API to be called outside.
	public CustType cust () default CustType.CISCO;
}
