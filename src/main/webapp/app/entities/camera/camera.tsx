import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICamera } from 'app/shared/model/camera.model';
import { getEntities } from './camera.reducer';

export const Camera = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const cameraList = useAppSelector(state => state.camera.entities);
  const loading = useAppSelector(state => state.camera.loading);
  const totalItems = useAppSelector(state => state.camera.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="camera-heading" data-cy="CameraHeading">
        Cameras
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/camera/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Camera
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {cameraList && cameraList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ipAddress')}>
                  Ip Address <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('port')}>
                  Port <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('login')}>
                  Login <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('password')}>
                  Password <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mainPath')}>
                  Main Path <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('secondaryPath')}>
                  Secondary Path <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('url')}>
                  Url <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('model')}>
                  Model <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('vendorName')}>
                  Vendor Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('activated')}>
                  Activated <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hlsUrl')}>
                  Hls Url <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isTemp')}>
                  Is Temp <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('info')}>
                  Info <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Location <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Type Of Camera <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cameraList.map((camera, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/camera/${camera.id}`} color="link" size="sm">
                      {camera.id}
                    </Button>
                  </td>
                  <td>{camera.ipAddress}</td>
                  <td>{camera.port}</td>
                  <td>{camera.login}</td>
                  <td>{camera.password}</td>
                  <td>{camera.mainPath}</td>
                  <td>{camera.secondaryPath}</td>
                  <td>{camera.url}</td>
                  <td>{camera.model}</td>
                  <td>{camera.name}</td>
                  <td>{camera.vendorName}</td>
                  <td>{camera.activated ? 'true' : 'false'}</td>
                  <td>{camera.hlsUrl}</td>
                  <td>{camera.isTemp ? 'true' : 'false'}</td>
                  <td>{camera.status}</td>
                  <td>{camera.info}</td>
                  <td>{camera.location ? <Link to={`/location/${camera.location.id}`}>{camera.location.id}</Link> : ''}</td>
                  <td>
                    {camera.typeOfCamera ? <Link to={`/type-of-camera/${camera.typeOfCamera.id}`}>{camera.typeOfCamera.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/camera/${camera.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/camera/${camera.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/camera/${camera.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Cameras found</div>
        )}
      </div>
      {totalItems ? (
        <div className={cameraList && cameraList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Camera;
