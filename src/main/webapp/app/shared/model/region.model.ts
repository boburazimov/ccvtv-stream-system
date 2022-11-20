import { ICountry } from 'app/shared/model/country.model';

export interface IRegion {
  id?: number;
  name?: string;
  info?: string | null;
  country?: ICountry | null;
}

export const defaultValue: Readonly<IRegion> = {};
