export interface UserRepository {
  exists(name: string): Promise<boolean>
  save(user: User): Promise<User>
  update(user: User): Promise<User>
  getByName(name: string): Promise<User>
}

export type User = {
  name: string,
  password: string,
  lastConnection: Date,
};

export interface IUserService {
  createUser(user: User): Promise<User>;
}