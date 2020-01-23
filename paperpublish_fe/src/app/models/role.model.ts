import { Deserializable } from './deserializable.model';

export class Role implements Deserializable {
  role: string;

  deserialize(input: any) {
    Object.assign(this, input);
    return this;
  }
}