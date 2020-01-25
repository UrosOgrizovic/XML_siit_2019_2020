import { Deserializable } from './deserializable.model';
import { Role } from './role.model';
export class User implements Deserializable {
  token: string;
  name: string;
  email: string;
  username: string;
  password: string;
  roles: Role;

  deserialize(input: any) {
    Object.assign(this, input);
    this.roles = new Role().deserialize(input.roles);
    return this;
  }
}