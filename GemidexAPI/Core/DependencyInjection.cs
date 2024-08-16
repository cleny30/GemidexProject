
using GemidexAPI.IRepository;
using GemidexAPI.Repository;
using GemidexAPI.Service;

namespace GemidexAPI.Core
{
    public static class DependencyInjection
    {
        public static void ConfigureDependencyInjection(this IServiceCollection services)
        {
            services.AddScoped<IGemiDexRepository, GemidexRepository>();
            services.AddScoped<IUserRepository, UserRepository>();
            services.AddScoped<IGemiTypeRepository, GemiTypeRepository>();


            services.AddScoped<GemidexService>();
            services.AddScoped<UserService>();
            services.AddScoped<GemiTypeService>();
        }
    }
}
