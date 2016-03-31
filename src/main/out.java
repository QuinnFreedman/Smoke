package main;
public abstract class out {
	public static void pln(Object... strs) {
		String s = "";
		for(int i = 0; i < strs.length; i++) {
			s += strs[i].toString();
			if(i != strs.length - 1) {
				s += ", ";
			}
		}
		
		System.out.println(s);
	}
}