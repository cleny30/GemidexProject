package Model;

public class ApiResponse<T> {
    private boolean isSuccess;
    private String message;
    private String errors;
    private T result;

    // Getters and setters
    public boolean getIsSuccess() { return isSuccess; }
    public void setIsSuccess(boolean isSuccess) { this.isSuccess = isSuccess; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getErrors() { return errors; }
    public void setErrors(String errors) { this.errors = errors; }

    public T getResult() { return result; }
    public void setResult(T result) { this.result = result; }
}
