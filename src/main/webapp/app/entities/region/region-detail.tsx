import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './region.reducer';

export const RegionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const regionEntity = useAppSelector(state => state.region.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="regionDetailsHeading">Region</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{regionEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{regionEntity.name}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{regionEntity.info}</dd>
          <dt>Country</dt>
          <dd>{regionEntity.country ? regionEntity.country.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/region" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/region/${regionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RegionDetail;
