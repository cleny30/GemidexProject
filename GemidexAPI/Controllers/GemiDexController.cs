using GemidexAPI.Model;
using GemidexAPI.Model.Dto;
using GemidexAPI.Model.Entity;
using GemidexAPI.Service;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace GemidexAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GemiDexController : ControllerBase
    {
        private readonly GemidexService _service;

        public GemiDexController(GemidexService service)
        {
            _service = service;
        }

        [HttpPost("AddGemiObject")]
        public APIResult AddToCart([FromBody] GemiObjectModel objetc)
        {
            APIResult result = new APIResult();
            result.IsSuccess = _service.AddGemiObject(objetc);
            return result;
        }
    }
}
