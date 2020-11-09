using Microsoft.EntityFrameworkCore;
using plantio.Domain.Users;

namespace plantio.Model {
    public class PlantioContext: DbContext {
        public DbSet<User> Users => Set<User>();

        public PlantioContext(DbContextOptions<PlantioContext> options) : base(options) {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder) {
            modelBuilder.Entity<User>()
                .HasKey(user => user.Name);
        }
    }
}