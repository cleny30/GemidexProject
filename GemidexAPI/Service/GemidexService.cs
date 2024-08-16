using GemidexAPI.IRepository;
using GemidexAPI.Model.Dto;

namespace GemidexAPI.Service
{
    public class GemidexService
    {
        private readonly IGemiDexRepository gemiDexRepository;
        private readonly UserService userService;
        private readonly GemiTypeService gemiTypeService;

        public GemidexService(IGemiDexRepository gemiDexRepository, UserService userService, GemiTypeService gemiTypeService)
        {
            this.gemiDexRepository = gemiDexRepository;
            this.userService = userService;
            this.gemiTypeService = gemiTypeService;
        }

        public bool AddGemiObject(GemiObjectModel gemiObject)
        {
            string typeId = gemiTypeService.GetGemiType(gemiObject.TypeName).TypeId;
            string subTypeId = gemiTypeService.GetGemiType(gemiObject.SubTypeName).TypeId;

            gemiObject.TypeId = typeId;
            gemiObject.SubTypeId = subTypeId;
            return gemiDexRepository.SaveGemiObject(gemiObject);
        }
    }
}
