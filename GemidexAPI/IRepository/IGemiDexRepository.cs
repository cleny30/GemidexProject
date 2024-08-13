
using GemidexAPI.Model.Dto;

namespace GemidexAPI.IRepository
{
    public interface IGemiDexRepository
    {
        public bool SaveGemiObject(GemiObjectModel gemiObject);
    }
}
