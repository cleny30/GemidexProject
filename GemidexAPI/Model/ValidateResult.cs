using static System.Runtime.InteropServices.JavaScript.JSType;

namespace GemidexAPI.Model
{
    public class ValidateResult
    {
        public ValidateResult() { }
        public bool IsValid { get; set; } = true;
        public List<Error> Errors { get; set; } = new List<Error>();
        public void AddError(string? property, string message)
        {
            IsValid = false;
            Errors.Add(new()
            {
                Property = property,
                Message = message
            });
        }
    }
}
