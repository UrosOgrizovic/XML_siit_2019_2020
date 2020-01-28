import { Deserializable } from './deserializable.model';

export class Review implements Deserializable {
  deserialize(input: any) {
    Object.assign(this, input);
    return this;
  }
}