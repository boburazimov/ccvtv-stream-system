import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Camera from './camera';
import CameraDetail from './camera-detail';
import CameraUpdate from './camera-update';
import CameraDeleteDialog from './camera-delete-dialog';

const CameraRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Camera />} />
    <Route path="new" element={<CameraUpdate />} />
    <Route path=":id">
      <Route index element={<CameraDetail />} />
      <Route path="edit" element={<CameraUpdate />} />
      <Route path="delete" element={<CameraDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CameraRoutes;
