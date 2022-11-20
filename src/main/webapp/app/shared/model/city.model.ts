import { IRegion } from 'app/shared/model/region.model';

export interface ICity {
  id?: number;
  name?: string;
  info?: string | null;
  regiob?: IRegion | null;
}

export const defaultValue: Readonly<ICity> = {};
