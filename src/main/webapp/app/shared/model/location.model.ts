import { IStreet } from 'app/shared/model/street.model';

export interface ILocation {
  id?: number;
  home?: string | null;
  latitude?: string | null;
  longitude?: string | null;
  info?: string | null;
  street?: IStreet | null;
}

export const defaultValue: Readonly<ILocation> = {};
