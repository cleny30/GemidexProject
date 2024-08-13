using GemidexAPI.Core;
using GemidexAPI.DataAccess;
using GemidexAPI.IRepository;
using GemidexAPI.Model.Dto;

namespace GemidexAPI.Repository
{
    public class UserRepository : IUserRepository
    {
        private readonly GemidexContext _context;

        public UserRepository(GemidexContext context)
        {
            _context = context;
        }

        public UserModel GetUserModel(string email)
        {
            User? user = _context.User.Where(a => a.Email.Equals(email)).SingleOrDefault();
            if (user != null)
            {
                var account = new UserModel();
                account.CopyProperties(user);
                return account;

            }
            return null;
        }

        public bool Register(UserModel userRegis)
        {
            try
            {
                User user = new User();

                user.CopyProperties(userRegis);

                _context.User.Add(user);

                _context.SaveChanges();


                return true;
            }
            catch
            {
                return false;
            }
        }
    }
}
