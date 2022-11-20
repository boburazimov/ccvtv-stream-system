import { ILocation } from 'app/shared/model/location.model';
import { ITypeOfCamera } from 'app/shared/model/type-of-camera.model';
import { CameraStatusEnum } from 'app/shared/model/enumerations/camera-status-enum.model';

export interface ICamera {
  id?: number;
  ipAddress?: string;
  port?: string;
  login?: string;
  password?: string;
  mainPath?: string;
  secondaryPath?: string;
  url?: string | null;
  model?: string | null;
  name?: string | null;
  vendorName?: string | null;
  activated?: boolean | null;
  hlsUrl?: string | null;
  isTemp?: boolean | null;
  status?: CameraStatusEnum | null;
  info?: string | null;
  location?: ILocation | null;
  typeOfCamera?: ITypeOfCamera | null;
}

export const defaultValue: Readonly<ICamera> = {
  activated: false,
  isTemp: false,
};
