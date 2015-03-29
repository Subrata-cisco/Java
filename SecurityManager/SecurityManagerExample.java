package SecurityManager;

public class SecurityManagerExample extends SecurityManager{
	public static void main(String[] args) {

		// set the policy file as the system security policy
		System.setProperty("java.security.policy", "file:/C:/Users/subratas/Documents/GitHub/Java/SecurityManager/java.policy");

		// create a security manager
		SecurityManagerExample sm = new SecurityManagerExample();

		// set the system security manager
		System.setSecurityManager(sm);

		// perform the check
		sm.checkPropertyAccess("java.runtime.name");

		// print a message if we passed the check
		System.out.println("Allowed!");
	}
}
