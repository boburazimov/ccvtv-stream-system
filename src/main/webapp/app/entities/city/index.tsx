import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import City from './city';
import CityDetail from './city-detail';
import CityUpdate from './city-update';
import CityDeleteDialog from './city-delete-dialog';

const CityRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<City />} />
    <Route path="new" element={<CityUpdate />} />
    <Route path=":id">
      <Route index element={<CityDetail />} />
      <Route path="edit" element={<CityUpdate />} />
      <Route path="delete" element={<CityDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CityRoutes;
