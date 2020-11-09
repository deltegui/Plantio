namespace plantio.Domain.Users {
    public interface IUserRepository {
        bool ExistWithName(string name);
        void Save(User user);
        User GetByName(string name);
    }
}
