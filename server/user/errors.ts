import { DomainError } from '../domain_error.ts';

export const userNotFound = (name: string): DomainError => ({
  code: 100,
  message: `User with name ${name} not found`,
  fix: `Try crating a user with name: ${name}`,
});