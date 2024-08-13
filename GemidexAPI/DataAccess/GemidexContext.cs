using GemidexAPI.Model.Entity;
using Microsoft.EntityFrameworkCore;

namespace GemidexAPI.DataAccess
{
    public class GemidexContext : DbContext
    {
        public GemidexContext(DbContextOptions<GemidexContext> options) : base(options)
        {
        }

        public DbSet<User> User { get; set; }
        public DbSet<GemiObject> GemiObject { get; set; }
        public DbSet<GemiType> GemiType { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<User>().ToTable("User");
            modelBuilder.Entity<GemiObject>().ToTable("GemiObject");
            modelBuilder.Entity<GemiType>().ToTable("GemiType");
        }
    }

}
