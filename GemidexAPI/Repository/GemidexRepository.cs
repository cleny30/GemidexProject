using GemidexAPI.Core;
using GemidexAPI.DataAccess;
using GemidexAPI.IRepository;
using GemidexAPI.Model.Dto;
using GemidexAPI.Model.Entity;

namespace GemidexAPI.Repository
{
    public class GemidexRepository : IGemiDexRepository
    {
        private readonly GemidexContext _context;

        public GemidexRepository(GemidexContext context)
        {
            _context = context;
        }

        public bool SaveGemiObject(GemiObjectModel _gemiObject)
        {
            try
            {
                GemiObject gemiObject = new GemiObject();
                gemiObject.CopyProperties(_gemiObject);

                _context.GemiObject.Add(gemiObject);
                _context.SaveChanges();
                return true;
            }
            catch (Exception ex)
            {
                return false;
            }

        }
    }
}
