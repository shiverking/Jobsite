package project.exception;

import lombok.Data;

@Data
public class ServiceException extends Exception{
    private static final long serialVersionUID = -5519743897907627214L;
    private String message ;
    private String statue;

    public ServiceException(String message, String statue) {
        this.message = message;
        this.statue = statue;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }
}
