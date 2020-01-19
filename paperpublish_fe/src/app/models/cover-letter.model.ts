import { Deserializable } from './deserializable.model';

export class CoverLetter implements Deserializable {
  deserialize(input: any) {
    Object.assign(this, input);
    return this;
  }
}