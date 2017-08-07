package com.source.base.exception;

public class ApplicationException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3862644934041826076L;
	/*错误码*/
    private Integer code;

    public ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ApplicationException{" +
                "code=" + code +
                "message=" + getMessage() +
                '}';
    }
}
