package com.stackabuse.postgresql;

public class HelpApp {
	
	public void helpf(String[] args) {
        System.out.println( "Hello World!" );
        System.out.println("Added maven java plugin.");
        try {
        	for (String v : args) {
				System.out.println(v);
			}
        	int i = args.length - 1;
        	System.out.println(args[i]);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage() + " args array");	
			
		}
	}

    @Override
    public String toString() { 
        return String.format(this.getClass().getName()); 
    } 
}
