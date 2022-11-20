import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRegion } from 'app/shared/model/region.model';
import { getEntities as getRegions } from 'app/entities/region/region.reducer';
import { ICity } from 'app/shared/model/city.model';
import { getEntity, updateEntity, createEntity, reset } from './city.reducer';

export const CityUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const regions = useAppSelector(state => state.region.entities);
  const cityEntity = useAppSelector(state => state.city.entity);
  const loading = useAppSelector(state => state.city.loading);
  const updating = useAppSelector(state => state.city.updating);
  const updateSuccess = useAppSelector(state => state.city.updateSuccess);

  const handleClose = () => {
    navigate('/city' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRegions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...cityEntity,
      ...values,
      regiob: regions.find(it => it.id.toString() === values.regiob.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...cityEntity,
          regiob: cityEntity?.regiob?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ccvtvApp.city.home.createOrEditLabel" data-cy="CityCreateUpdateHeading">
            Create or edit a City
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="city-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="city-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 128, message: 'This field cannot be longer than 128 characters.' },
                }}
              />
              <ValidatedField
                label="Info"
                id="city-info"
                name="info"
                data-cy="info"
                type="text"
                validate={{
                  maxLength: { value: 1024, message: 'This field cannot be longer than 1024 characters.' },
                }}
              />
              <ValidatedField id="city-regiob" name="regiob" data-cy="regiob" label="Regiob" type="select">
                <option value="" key="0" />
                {regions
                  ? regions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/city" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CityUpdate;
