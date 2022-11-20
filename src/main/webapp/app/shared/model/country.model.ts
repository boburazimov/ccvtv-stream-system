export interface ICountry {
  id?: number;
  name?: string;
  info?: string | null;
}

export const defaultValue: Readonly<ICountry> = {};
