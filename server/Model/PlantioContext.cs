using Microsoft.EntityFrameworkCore;
using plantio.Domain;

namespace plantio.Model {
    public class PlantioContext: DbContext {
        public DbSet<User> Users => Set<User>();
        public DbSet<Token> Tokens => Set<Token>();

        public PlantioContext(DbContextOptions<PlantioContext> options) : base(options) {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder) {
            modelBuilder.Entity<User>()
                .HasKey(user => user.Name);
            modelBuilder.Entity<Token>()
                .HasOne(token => token.Owner)
                .WithMany()
                .IsRequired();
            modelBuilder.Entity<Token>()
                .HasKey(token => token.Value);
        }
    }
}