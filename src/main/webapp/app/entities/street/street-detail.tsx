import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './street.reducer';

export const StreetDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const streetEntity = useAppSelector(state => state.street.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="streetDetailsHeading">Street</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{streetEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{streetEntity.name}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{streetEntity.info}</dd>
          <dt>District</dt>
          <dd>{streetEntity.district ? streetEntity.district.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/street" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/street/${streetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StreetDetail;
