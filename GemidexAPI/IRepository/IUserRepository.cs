using GemidexAPI.Model.Dto;

namespace GemidexAPI.IRepository
{
    public interface IUserRepository
    {
        public UserModel GetUserModel(string email);

        public bool Register(UserModel user);
    }
}
