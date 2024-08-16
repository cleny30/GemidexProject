using GemidexAPI.Model.Dto;

namespace GemidexAPI.IRepository
{
    public interface IGemiTypeRepository
    {
        public GemiTypeModel GetGemiType(string typeName);
    }
}
