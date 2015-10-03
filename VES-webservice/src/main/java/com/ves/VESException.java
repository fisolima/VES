
package com.ves;

/**
 * Define an exception type for VES operations
 */
public class VESException extends Exception {
    
    private final int status;
    
    /**
     * RESTful error code
     * 
     * @return 
     */
    public int getStatus() {return status;}
    
    public VESException(int status, String msg) {
        super(msg);
        this.status = status;
    }
}
