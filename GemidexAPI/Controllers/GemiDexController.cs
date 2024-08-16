using GemidexAPI.Model;
using GemidexAPI.Model.Dto;
using GemidexAPI.Service;
using Microsoft.AspNetCore.Mvc;

namespace GemidexAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GemidexController : ControllerBase
    {
        private readonly GemidexService _service;

        public GemidexController(GemidexService service)
        {
            _service = service;
        }

        [HttpPost("CreateGemidexEntry")]
        public APIResult CreateGemidexEntry([FromBody] GemiObjectModel gemiObject)
        {
            APIResult result = new APIResult();

            result.IsSuccess = _service.AddGemiObject(gemiObject);
            return result;

        }
    }
}
