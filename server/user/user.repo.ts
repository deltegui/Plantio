export interface UserRepository {
  exists?(name: string): boolean
  save(user: Object): void
  getByName(name: string): Object
}