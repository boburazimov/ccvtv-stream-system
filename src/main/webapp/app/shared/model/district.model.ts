import { ICity } from 'app/shared/model/city.model';

export interface IDistrict {
  id?: number;
  name?: string;
  info?: string | null;
  city?: ICity | null;
}

export const defaultValue: Readonly<IDistrict> = {};
