using GemidexAPI.IRepository;
using GemidexAPI.Model.Dto;

namespace GemidexAPI.Service
{
    public class GemidexService
    {
        private readonly IGemiDexRepository gemiDexRepository;
        private readonly UserService userService;

        public GemidexService(IGemiDexRepository gemiDexRepository, UserService userService)
        {
            this.gemiDexRepository = gemiDexRepository;
            this.userService = userService;
        }

        public bool AddGemiObject(GemiObjectModel gemiObject)
        {
            UserModel userModel = userService.GetUserModelByEmail(gemiObject.email);

            gemiObject.UserId = userModel.UserId;
            return gemiDexRepository.SaveGemiObject(gemiObject);
        }
    }
}
