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

        public UserModel GetUserModelById(string id)
        {
            return _userRepository.GetUserModel(id);
        }

        public bool Register(UserModel user)
        {
            return _userRepository.Register(user);
        }
    }
}
