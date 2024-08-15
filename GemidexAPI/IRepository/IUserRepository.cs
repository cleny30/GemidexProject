using GemidexAPI.Model.Dto;

namespace GemidexAPI.IRepository
{
    public interface IUserRepository
    {
        public UserModel GetUserModel(string id);

        public bool Register(UserModel user);
    }
}
