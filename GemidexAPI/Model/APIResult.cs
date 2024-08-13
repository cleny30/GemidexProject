using static System.Runtime.InteropServices.JavaScript.JSType;

namespace GemidexAPI.Model
{
    public class APIResult
    {
        public APIResult() { }
        public bool IsSuccess { get; set; } = true;
        public string? Message { get; set; }
        public List<Error>? Errors { get; set; }
        public object? Result { get; set; }

        public void SetValidateResult(ValidateResult validate)
        {
            IsSuccess = validate.IsValid;
            Errors = validate.Errors;
        }
    }
}
