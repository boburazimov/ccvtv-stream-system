import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './type-of-camera.reducer';

export const TypeOfCameraDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const typeOfCameraEntity = useAppSelector(state => state.typeOfCamera.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="typeOfCameraDetailsHeading">Type Of Camera</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{typeOfCameraEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{typeOfCameraEntity.name}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{typeOfCameraEntity.info}</dd>
        </dl>
        <Button tag={Link} to="/type-of-camera" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/type-of-camera/${typeOfCameraEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TypeOfCameraDetail;
