import street from 'app/entities/street/street.reducer';
import district from 'app/entities/district/district.reducer';
import city from 'app/entities/city/city.reducer';
import region from 'app/entities/region/region.reducer';
import country from 'app/entities/country/country.reducer';
import location from 'app/entities/location/location.reducer';
import camera from 'app/entities/camera/camera.reducer';
import typeOfCamera from 'app/entities/type-of-camera/type-of-camera.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  street,
  district,
  city,
  region,
  country,
  location,
  camera,
  typeOfCamera,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
