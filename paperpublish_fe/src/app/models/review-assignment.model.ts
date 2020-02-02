import { Deserializable } from './deserializable.model';

export class ReviewAssignment implements Deserializable {
  
  paperId: any;
  reviewerUserName: any;

  deserialize(input: any) {
    Object.assign(this, input);
    return this;
  }
}