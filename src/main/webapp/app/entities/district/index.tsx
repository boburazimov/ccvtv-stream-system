import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import District from './district';
import DistrictDetail from './district-detail';
import DistrictUpdate from './district-update';
import DistrictDeleteDialog from './district-delete-dialog';

const DistrictRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<District />} />
    <Route path="new" element={<DistrictUpdate />} />
    <Route path=":id">
      <Route index element={<DistrictDetail />} />
      <Route path="edit" element={<DistrictUpdate />} />
      <Route path="delete" element={<DistrictDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DistrictRoutes;
