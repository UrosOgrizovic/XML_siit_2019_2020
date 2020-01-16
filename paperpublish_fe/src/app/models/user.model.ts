import { Deserializable } from './deserializable.model';

export class User implements Deserializable {
  token: string;
  name: string;
  email: string;
  username: string;
  password: string;

  deserialize(input: any) {
    Object.assign(this, input);
    return this;
  }
}