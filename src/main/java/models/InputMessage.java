package models;

/**
 * Created by denze on 3/17/2018.
 */
public class InputMessage {
    private String message;

    public InputMessage(){

    }

    public InputMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
