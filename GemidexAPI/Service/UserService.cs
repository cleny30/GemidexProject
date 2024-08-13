using GemidexAPI.IRepository;
using GemidexAPI.Model.Dto;
using GemidexAPI.Repository;

namespace GemidexAPI.Service
{
    public class UserService
    {
        private readonly IUserRepository _userRepository;

        public UserService(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }

        public UserModel GetUserModelByEmail(string email)
        {
            return _userRepository.GetUserModel(email);
        }

        public bool Register(UserModel user)
        {
            return _userRepository.Register(user);
        }
    }
}
