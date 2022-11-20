import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Street from './street';
import StreetDetail from './street-detail';
import StreetUpdate from './street-update';
import StreetDeleteDialog from './street-delete-dialog';

const StreetRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Street />} />
    <Route path="new" element={<StreetUpdate />} />
    <Route path=":id">
      <Route index element={<StreetDetail />} />
      <Route path="edit" element={<StreetUpdate />} />
      <Route path="delete" element={<StreetDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StreetRoutes;
