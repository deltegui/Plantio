import { deltegui } from '../deps.ts';

export interface Validator {
  isValid(obj: Object): boolean
}

export interface ValidableFactory {
  create(id: number): Validator
}

export abstract class ValidationDecorator {
  constructor(
    private validableFactory: ValidableFactory,
  ) {}

  protected throwIfInvalid({ model, validatorID, error }: { model: object, validatorID: number, error: object }) {
    const valid = this.validableFactory
      .create(validatorID)
      .isValid(model);
    if(!valid) {
      throw error;
    }
  }
}

export class JsonValidatorFactory implements ValidableFactory {
  private readonly validator: deltegui.JsonValidator;

  constructor() {
    this.validator = new deltegui.JsonValidator();
  }

  register(definition: (t: any) => Object, name: any): JsonValidatorFactory {
    this.validator.create(definition, name);
    return this;
  }

  create(name: number): Validator {
    return {
      isValid: (obj: Object) => this.validator.validate(obj, name),
    };
  }
}
