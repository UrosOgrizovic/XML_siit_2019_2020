import { Deserializable } from './deserializable.model';

export class SciencePaper implements Deserializable {
  paperData: any;
  paragraf: any;
  citations: any;
  citedBy: any;
  documentType: any;
  documentId: any;
  received: any;
  revised: any;
  accepted: any;
  pp: any;

  deserialize(input: any) {
    Object.assign(this, input);
    return this;
  }
}