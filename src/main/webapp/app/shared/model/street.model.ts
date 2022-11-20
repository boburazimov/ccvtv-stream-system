import { IDistrict } from 'app/shared/model/district.model';

export interface IStreet {
  id?: number;
  name?: string;
  info?: string | null;
  district?: IDistrict | null;
}

export const defaultValue: Readonly<IStreet> = {};
