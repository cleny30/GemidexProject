using GemidexAPI.Core;
using GemidexAPI.DataAccess;
using GemidexAPI.IRepository;
using GemidexAPI.Model.Dto;
using GemidexAPI.Model.Entity;

namespace GemidexAPI.Repository
{
    public class GemiTypeRepository : IGemiTypeRepository
    {
        private readonly GemidexContext _context;

        public GemiTypeRepository(GemidexContext context)
        {
            _context = context;
        }
        public GemiTypeModel GetGemiType(string typeName)
        {
            GemiType? type = _context.GemiType.Where(a => a.TypeName.Equals(typeName)).SingleOrDefault();
            if (type != null)
            {
                var gemiType = new GemiTypeModel();
                gemiType.CopyProperties(type);
                return gemiType;

            }
            return null;
        }
    }
}
