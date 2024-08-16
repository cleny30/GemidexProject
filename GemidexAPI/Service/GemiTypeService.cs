using GemidexAPI.IRepository;
using GemidexAPI.Model.Dto;

namespace GemidexAPI.Service
{
    public class GemiTypeService
    {
        private readonly IGemiTypeRepository repository;

        public GemiTypeService(IGemiTypeRepository repository)
        {
            this.repository = repository;
        }

        public GemiTypeModel GetGemiType(string typeName)
        {
            return repository.GetGemiType(typeName);
        }
    }
}
