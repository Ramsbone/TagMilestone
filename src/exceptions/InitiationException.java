/**
 * This exception checks if any rooms or items share name. Our program only works with unique names
 * therefore the exception. If some items or rooms share name, the exception is thrown.
 */
package exceptions;

public class InitiationException extends RuntimeException{

    public InitiationException(String message){
        super(message);
    }
    
}
