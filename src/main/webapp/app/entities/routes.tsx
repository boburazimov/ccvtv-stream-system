import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Street from './street';
import District from './district';
import City from './city';
import Region from './region';
import Country from './country';
import Location from './location';
import Camera from './camera';
import TypeOfCamera from './type-of-camera';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="street/*" element={<Street />} />
        <Route path="district/*" element={<District />} />
        <Route path="city/*" element={<City />} />
        <Route path="region/*" element={<Region />} />
        <Route path="country/*" element={<Country />} />
        <Route path="location/*" element={<Location />} />
        <Route path="camera/*" element={<Camera />} />
        <Route path="type-of-camera/*" element={<TypeOfCamera />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
