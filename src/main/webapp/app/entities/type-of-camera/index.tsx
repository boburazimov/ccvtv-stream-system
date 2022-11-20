import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TypeOfCamera from './type-of-camera';
import TypeOfCameraDetail from './type-of-camera-detail';
import TypeOfCameraUpdate from './type-of-camera-update';
import TypeOfCameraDeleteDialog from './type-of-camera-delete-dialog';

const TypeOfCameraRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TypeOfCamera />} />
    <Route path="new" element={<TypeOfCameraUpdate />} />
    <Route path=":id">
      <Route index element={<TypeOfCameraDetail />} />
      <Route path="edit" element={<TypeOfCameraUpdate />} />
      <Route path="delete" element={<TypeOfCameraDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TypeOfCameraRoutes;
