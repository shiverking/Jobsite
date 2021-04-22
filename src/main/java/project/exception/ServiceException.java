package project.exception;

import lombok.Data;

@Data
public class ServiceException extends Exception{
    private static final long serialVersionUID = -5519743897907627214L;
    private String message ;
    private String state;

    public ServiceException(String message, String state) {
        this.message = message;
        this.state = state;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
