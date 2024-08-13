using GemidexAPI.Model.Dto;
using GemidexAPI.Model;
using GemidexAPI.Service;
using Microsoft.AspNetCore.Mvc;

namespace GemidexAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AccountController : ControllerBase
    {
        private readonly UserService _service;

        public AccountController(UserService service)
        {
            _service = service;
        }

        [HttpPost("Register")]
        public APIResult Register([FromBody] UserModel user)
        {
            APIResult result = new APIResult();
            if (_service.GetUserModelByEmail(user.Email) == null) {
                result.IsSuccess = _service.Register(user);
                return result;
            }
            result.IsSuccess = false;
            return result;
        }


        [HttpGet("User")]
        public APIResult GetUser(string email)
        {
            APIResult result = new APIResult();
            result.Result = _service.GetUserModelByEmail(email);
            return result;
        }
    }
}
